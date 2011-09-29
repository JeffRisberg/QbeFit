package com.incra.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.incra.controllers.dto.TrackSummaryColumn;
import com.incra.controllers.dto.TrackSummaryRow;
import com.incra.domain.Activity;
import com.incra.domain.Event;
import com.incra.domain.Goal;
import com.incra.domain.User;
import com.incra.domain.UserActivity;
import com.incra.domain.UserGoal;
import com.incra.services.ActivityService;
import com.incra.services.EventService;
import com.incra.services.LevelService;
import com.incra.services.UserActivityService;
import com.incra.services.UserGoalService;
import com.incra.services.UserService;

/**
 * The <i>HomeController</i> implements the login/logout facilities and the
 * overview screen. This screen also includes tracking.
 * 
 * @author Jeffrey Risberg
 * @since 09/10/11
 */
@Controller
public class HomeController {
    protected static int numColumns = 6;
    protected static int initialUserPoints = 20;

    protected static Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private LevelService levelService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserActivityService userActivityService;
    @Autowired
    private UserGoalService userGoalService;
    @Autowired
    private EventService eventService;

    public HomeController() {
    }

    @RequestMapping(value = "/")
    public String special() {
        return "redirect:/home";
    }

    // SESSION MANAGEMENT

    @RequestMapping(value = "/home/**", method = RequestMethod.GET)
    public ModelAndView index() {

        User user = userService.getCurrentUser();

        if (user != null) {
            return overview();
        }

        // create a dummy user
        user = new User();
        user.setPoints(initialUserPoints);

        List<Activity> activities = activityService.findEntityList();

        ModelAndView modelAndView = new ModelAndView("home/index");
        modelAndView.addObject("command", user);
        modelAndView.addObject("numActivities", activities.size());
        modelAndView.addObject("offerLogin", Boolean.TRUE);
        return modelAndView;
    }

    // LOGIN MANAGEMENT

    /** Offer the login page, setting up dummy user to collect the form inputs */
    @RequestMapping(value = "/home/login", method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest httpRequest) {

        User user = new User();
        user.setPoints(initialUserPoints);

        String errorFlag = httpRequest.getParameter("login_error");

        ModelAndView modelAndView = new ModelAndView("home/login");
        modelAndView.addObject("command", user);
        modelAndView.addObject("isError", errorFlag != null);

        return modelAndView;
    }

    /** After login is complete, we need to update the stats on the User object */
    @RequestMapping(value = "/home/loginComplete")
    public String loginComplete() {

        User user = userService.getCurrentUser();

        user.setLastLoggedIn(new Date());
        user.setLoginCount(user.getLoginCount() + 1);
        userService.save(user);

        return "redirect:/home";
    }

    // OVERVIEW SCREEN

    @RequestMapping(value = "/home/overview")
    public ModelAndView overview() {

        User user = userService.getCurrentUser();
        List<TrackSummaryRow> trackSummaryRowList = new ArrayList<TrackSummaryRow>();
        List<TrackSummaryColumn> trackSummaryColumnList = new ArrayList<TrackSummaryColumn>();

        TrackSummaryColumn trackSummaryColumn;
        Calendar calendar = new GregorianCalendar();
        Date date = new Date();

        for (int i = 0; i < numColumns; i++) {
            Date priorDate = (Date) date.clone();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            date = calendar.getTime();
            String label = "" + i + " days ago";

            if (i == 1)
                label = "1 day ago";
            if (i == 0)
                label = "Today";

            trackSummaryColumn = new TrackSummaryColumn(date, priorDate, label);
            trackSummaryColumnList.add(0, trackSummaryColumn);
        }

        List<UserActivity> userActivityList = userActivityService.findEntityList(user);

        for (UserActivity userActivity : userActivityList) {
            if (userActivity.getEffectivityEnd() == null) {
                populateRow(userActivity, trackSummaryColumnList, trackSummaryRowList);
            }
        }

        List<UserGoal> userGoalList = userGoalService.findEntityList(user);
        List<Goal> goalList = new ArrayList<Goal>();

        for (UserGoal userGoal : userGoalList) {
            if (userGoal.getEffectivityEnd() == null) {
                goalList.add(userGoal.getGoal());
            }
        }

        ModelAndView modelAndView = new ModelAndView("home/overview");
        modelAndView.addObject("userName", user.getFirstName() + " " + user.getLastName());
        modelAndView.addObject("points", user.getPoints());
        modelAndView.addObject("level", user.getLevel());
        modelAndView.addObject("userBadges", user.getUserBadges());
        modelAndView.addObject("trackSummaryColumnList", trackSummaryColumnList);
        modelAndView.addObject("trackSummaryRowList", trackSummaryRowList);
        modelAndView.addObject("goalList", goalList);
        return modelAndView;
    }

    @RequestMapping(value = "/home/activity/{id}", method = RequestMethod.GET)
    public String activity(@PathVariable int id, Model model, HttpSession session) {

        Activity activity = activityService.findEntityById(id);
        if (activity != null) {
            model.addAttribute(activity);
            return "home/activity";
        } else {
            return "redirect:/home/index";
        }
    }

    @RequestMapping(value = "/home/doneIt/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> doneIt(@PathVariable Integer id, HttpServletRequest request,
            HttpServletResponse response, HttpSession session) {
        User user = userService.getCurrentUser();

        UserActivity userActivity = userActivityService.findEntityById(id);

        if (userActivity != null) {
            Activity activity = userActivity.getActivity();
            int difficulty = activity.getDifficulty();

            Event event = new Event();
            event.setUserActivity(userActivity);
            event.setEventDate(new Date());
            eventService.save(event);

            user.setPoints(user.getPoints() + difficulty);
            user.setLevel(levelService.computeLevel(user.getPoints()));

            userService.save(user);
        }

        return null;
    }

    // ABOUT AND TERMS SCREENS

    @RequestMapping(value = "/home/about", method = RequestMethod.GET)
    public ModelAndView about() {

        ModelAndView modelAndView = new ModelAndView("home/about");
        return modelAndView;
    }

    @RequestMapping(value = "/home/terms", method = RequestMethod.GET)
    public ModelAndView terms() {

        ModelAndView modelAndView = new ModelAndView("home/terms");
        return modelAndView;
    }

    // SUPPORTING METHODS

    /**
     * Populate a row of the track summary grid. Each row is for a specific
     * userActivity.
     */
    protected void populateRow(UserActivity userActivity,
            List<TrackSummaryColumn> trackSummaryColumnList,
            List<TrackSummaryRow> trackSummaryRowList) {

        Activity activity = userActivity.getActivity();
        List<Event> events = eventService.findEntityList(userActivity);

        TrackSummaryRow trackSummaryRow = new TrackSummaryRow(activity.getName(), activity.getId(),
                userActivity.getId());
        for (TrackSummaryColumn col : trackSummaryColumnList) {
            Date fromDate = col.getFromDate();
            Date toDate = col.getToDate();

            boolean bFound = false;
            for (Event event : events) {
                Date eventDate = event.getEventDate();

                if (eventDate.after(toDate))
                    continue;
                if (eventDate.before(fromDate))
                    continue;
                bFound = true;
            }
            if (bFound) {
                trackSummaryRow.addFlag(true);
            } else {
                trackSummaryRow.addFlag(false);
            }
        }
        trackSummaryRowList.add(trackSummaryRow);
    }
}
