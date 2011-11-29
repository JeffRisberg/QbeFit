package com.incra.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.incra.domain.Activity;
import com.incra.domain.Goal;
import com.incra.domain.GoalActivity;
import com.incra.domain.OrganizationType;
import com.incra.domain.TimeZone;
import com.incra.domain.User;
import com.incra.domain.UserActivity;
import com.incra.domain.UserGoal;
import com.incra.domain.propertyEditor.GoalPropertyEditor;
import com.incra.domain.propertyEditor.OrganizationTypePropertyEditor;
import com.incra.domain.propertyEditor.TimeZonePropertyEditor;
import com.incra.domain.propertyEditor.UserPropertyEditor;
import com.incra.services.GoalService;
import com.incra.services.OrganizationTypeService;
import com.incra.services.PageFrameworkService;
import com.incra.services.TimeZoneService;
import com.incra.services.UserActivityService;
import com.incra.services.UserGoalService;
import com.incra.services.UserService;

/**
 * The <i>ActivitySelectController</i> controller handles offering the AboutMe
 * screen and processing the results (a list of goals), then takes the user
 * through the activity selection screen for each possible activity based on the
 * selected goals.
 * 
 * @author Jeffrey Risberg
 * @since 11/27/11
 */
@Controller
public class ActivitySelectController {
    protected static Logger logger = LoggerFactory.getLogger(ActivitySelectController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private UserActivityService userActivityService;
    @Autowired
    private OrganizationTypeService organizationTypeService;
    @Autowired
    private GoalService goalService;
    @Autowired
    private UserGoalService userGoalService;
    @Autowired
    private TimeZoneService timeZoneService;
    @Autowired
    private PageFrameworkService pageFrameworkService;

    public ActivitySelectController() {
    }

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) throws Exception {
        dataBinder.registerCustomEditor(User.class, new UserPropertyEditor(userService));
        dataBinder
                .registerCustomEditor(TimeZone.class, new TimeZonePropertyEditor(timeZoneService));
        dataBinder.registerCustomEditor(OrganizationType.class, new OrganizationTypePropertyEditor(
                organizationTypeService));
        dataBinder.registerCustomEditor(Goal.class, new GoalPropertyEditor(goalService));
    }

    /**
     * Offer the aboutMe screen.
     */
    @RequestMapping(value = "/activitySelect/aboutMe")
    public ModelAndView aboutMe() {

        User user = userService.getCurrentUser();

        List<OrganizationType> organizationTypeList = organizationTypeService.findEntityList();
        List<Goal> goalList = goalService.findEntityList();

        List<UserGoal> priorUserGoalList = userGoalService.findEntityList(user);

        List<Boolean> selectedList = new ArrayList<Boolean>();
        for (Goal goal : goalList) {
            Integer goalId = goal.getId();
            UserGoal priorUserGoal = getUserGoalForGoalId(priorUserGoalList, goalId);

            if (priorUserGoal != null) {
                selectedList.add(Boolean.TRUE);
            } else {
                selectedList.add(Boolean.FALSE);
            }
        }

        ModelAndView modelAndView = new ModelAndView("activitySelect/aboutMe");
        modelAndView.addObject("command", user);
        modelAndView.addObject("organizationTypeList", organizationTypeList);
        modelAndView.addObject("goalList", goalList);
        modelAndView.addObject("selectedList", selectedList);

        return modelAndView;
    }

    /**
     * Process the post from the aboutMe screen, then select activities.
     */
    @RequestMapping(value = "/activitySelect/aboutMeUpdate", method = RequestMethod.POST)
    public String aboutMeUpdate(final @ModelAttribute("command") @Valid User user,
            BindingResult result, Model model, HttpSession session, HttpServletRequest httpRequest) {

        User curUser = userService.getCurrentUser();

        List<Goal> goalList = goalService.findEntityList();
        List<UserGoal> priorUserGoalList = userGoalService.findEntityList(curUser);

        for (Goal goal : goalList) {
            Integer goalId = goal.getId();
            UserGoal priorUserGoal = getUserGoalForGoalId(priorUserGoalList, goalId);

            if (httpRequest.getParameter("goal_" + goalId) != null) {
                if (priorUserGoal == null) {
                    UserGoal userGoal = new UserGoal();
                    userGoal.setUser(curUser);
                    userGoal.setGoal(goal);
                    userGoal.setEffectivityStart(new Date());
                    userGoalService.save(userGoal);
                }
            } else {
                if (priorUserGoal != null) {
                    priorUserGoal.setEffectivityEnd(new Date());
                    userGoalService.save(priorUserGoal);
                }
            }
        }

        curUser.setOrganizationType(user.getOrganizationType());
        curUser.setAboutMeInfoGathered(true);
        userService.save(curUser);

        return "redirect:/activitySelect/start";
    }

    /**
     * Start an activity select sequence
     */
    @RequestMapping(value = "/activitySelect/start")
    public ModelAndView start(HttpSession httpSession) {

        User user = userService.getCurrentUser();
        List<UserGoal> userGoalList = userGoalService.findEntityList(user);
        List<Activity> activityList = new ArrayList<Activity>();

        for (UserGoal userGoal : userGoalList) {
            Goal goal = userGoal.getGoal();
            Set<GoalActivity> goalActivitySet = goal.getActivities();

            for (GoalActivity goalActivity : goalActivitySet) {
                Activity activity = goalActivity.getActivity();

                if (activityList.contains(activity) == false) {
                    activityList.add(activity);
                }
            }
        }

        ModelAndView modelAndView;

        if (activityList.size() > 0) {
            // Disable currently selected activities
            List<UserActivity> priorUserActivityList = userActivityService.findEntityList(user);
            for (UserActivity priorUserActivity : priorUserActivityList) {
                priorUserActivity.setEffectivityEnd(new Date());
                userActivityService.save(priorUserActivity);
            }

            httpSession.setAttribute("activityList", activityList);
            httpSession.setAttribute("activityIndex", new Integer(0));

            modelAndView = new ModelAndView("activitySelect/offer");
            modelAndView.addObject("activity", activityList.get(0));
            modelAndView.addObject("index", 1);
            modelAndView.addObject("total", activityList.size());
        } else {
            // no activities, start over
            RedirectView rv = new RedirectView("/activitySelect/aboutMe");
            rv.setContextRelative(true);
            rv.setExposeModelAttributes(false);
            modelAndView = new ModelAndView(rv);

            pageFrameworkService
                    .setFlashMessage(httpSession, "Please select some additional goals");
            pageFrameworkService.setIsRedirect(httpSession, true);
        }
        return modelAndView;
    }

    /**
     * The user wants to go back to the prior activity.
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/activitySelect/back")
    public ModelAndView back(HttpSession httpSession) {

        List<Activity> activityList = (List<Activity>) httpSession.getAttribute("activityList");
        Integer activityIndex = (Integer) httpSession.getAttribute("activityIndex");

        activityIndex--;
        if (activityIndex < 0) {
            activityIndex = 0;
        }

        httpSession.setAttribute("activityIndex", new Integer(activityIndex));

        ModelAndView modelAndView = new ModelAndView("activitySelect/offer");

        modelAndView.addObject("activity", activityList.get(activityIndex));
        modelAndView.addObject("index", activityIndex + 1);
        modelAndView.addObject("total", activityList.size());
        return modelAndView;
    }

    /**
     * The user says 'yes' to the indicated activity, and would like the next
     * one to be offered.
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/activitySelect/yes")
    public ModelAndView yes(HttpSession httpSession) {

        User user = userService.getCurrentUser();

        List<Activity> activityList = (List<Activity>) httpSession.getAttribute("activityList");
        Integer activityIndex = (Integer) httpSession.getAttribute("activityIndex");
        Activity activity = activityList.get(activityIndex);

        UserActivity userActivity = userActivityService.findEntityByUserAndActivity(user, activity);
        if (userActivity != null) {
            userActivity.setEffectivityStart(new Date());
            userActivity.setEffectivityEnd(null);
            userActivityService.save(userActivity);
        } else {
            userActivity = new UserActivity();
            userActivity.setUser(user);
            userActivity.setActivity(activity);
            userActivity.setEffectivityStart(new Date());
            userActivityService.save(userActivity);
        }

        ModelAndView modelAndView;

        activityIndex++;
        if (activityIndex < activityList.size()) {
            httpSession.setAttribute("activityIndex", new Integer(activityIndex));

            modelAndView = new ModelAndView("activitySelect/offer");
            modelAndView.addObject("activity", activityList.get(activityIndex));
            modelAndView.addObject("index", activityIndex + 1);
            modelAndView.addObject("total", activityList.size());
        } else {
            modelAndView = new ModelAndView("redirect:/home");
        }

        return modelAndView;
    }

    /**
     * The user says 'no' to the indicated activity, and would like the next one
     * to be offered.
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/activitySelect/no")
    public ModelAndView no(HttpSession httpSession) {

        List<Activity> activityList = (List<Activity>) httpSession.getAttribute("activityList");
        Integer activityIndex = (Integer) httpSession.getAttribute("activityIndex");

        ModelAndView modelAndView;

        activityIndex++;
        if (activityIndex < activityList.size()) {
            httpSession.setAttribute("activityIndex", new Integer(activityIndex));

            modelAndView = new ModelAndView("activitySelect/offer");
            modelAndView.addObject("activity", activityList.get(activityIndex));
            modelAndView.addObject("index", activityIndex + 1);
            modelAndView.addObject("total", activityList.size());
        } else {
            modelAndView = new ModelAndView("redirect:/home");
        }

        return modelAndView;
    }

    /**
     * The user is done selecting activities.
     */
    @RequestMapping(value = "/activitySelect/done")
    public String done() {

        return "redirect:/home";
    }

    /** Find the userGoal for a given goalId, from a list */
    protected UserGoal getUserGoalForGoalId(List<UserGoal> userGoalList, Integer goalId) {
        for (UserGoal userGoal : userGoalList) {
            Goal goal = userGoal.getGoal();
            Integer tmpGoalId = goal.getId();

            if (userGoal.getEffectivityEnd() == null && tmpGoalId.equals(goalId))
                return userGoal;
        }
        return null;
    }
}