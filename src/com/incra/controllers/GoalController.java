package com.incra.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.incra.domain.Goal;
import com.incra.domain.User;
import com.incra.domain.UserGoal;
import com.incra.domain.propertyEditor.GoalPropertyEditor;
import com.incra.services.GoalService;
import com.incra.services.PageFrameworkService;
import com.incra.services.UserGoalService;
import com.incra.services.UserService;

/**
 * The <i>GoalController</i> controller defines operations on goals, including
 * selection.
 * 
 * @author Jeffrey Risberg
 * @since 09/10/11
 */
@Controller
public class GoalController {

    protected static Logger logger = LoggerFactory.getLogger(GoalController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private GoalService goalService;
    @Autowired
    private UserGoalService userGoalService;
    @Autowired
    private PageFrameworkService pageFrameworkService;

    public GoalController() {
    }

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) throws Exception {
        dataBinder.registerCustomEditor(Goal.class, new GoalPropertyEditor(goalService));
    }

    @RequestMapping(value = "/goal/**")
    public String index() {
        return "redirect:/goal/list";
    }

    // USER

    @RequestMapping(value = "/goal/select")
    public ModelAndView select(Object criteria) {

        User user = userService.getCurrentUser();
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

        ModelAndView modelAndView = new ModelAndView("goal/select");
        modelAndView.addObject("goalList", goalList);
        modelAndView.addObject("selectedList", selectedList);
        return modelAndView;
    }

    @RequestMapping(value = "/goal/selectUpdate", method = RequestMethod.POST)
    public String selectUpdate(HttpServletRequest httpRequest) {

        User user = userService.getCurrentUser();
        List<Goal> goalList = goalService.findEntityList();
        List<UserGoal> priorUserGoalList = userGoalService.findEntityList(user);

        for (Goal goal : goalList) {
            Integer goalId = goal.getId();
            UserGoal priorUserGoal = getUserGoalForGoalId(priorUserGoalList, goalId);

            if (httpRequest.getParameter("goal_" + goalId) != null) {
                if (priorUserGoal == null) {
                    UserGoal userGoal = new UserGoal();
                    userGoal.setUser(user);
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
        return "redirect:/home";
    }

    // ADMIN

    @RequestMapping(value = "/goal/list")
    public ModelAndView list(Object criteria) {

        List<Goal> goalList = goalService.findEntityList();

        ModelAndView modelAndView = new ModelAndView("goal/list");
        modelAndView.addObject("goalList", goalList);
        return modelAndView;
    }

    @RequestMapping(value = "/goal/show/{id}", method = RequestMethod.GET)
    public String show(@PathVariable int id, Model model, HttpSession session) {

        Goal goal = goalService.findEntityById(id);
        if (goal != null) {
            model.addAttribute(goal);
            return "goal/show";
        } else {
            pageFrameworkService.setFlashMessage(session, "No Goal with that id");
            pageFrameworkService.setIsRedirect(session, Boolean.TRUE);
            return "redirect:/goal/list";
        }
    }

    @RequestMapping(value = "/goal/create", method = RequestMethod.GET)
    public ModelAndView create() {

        Goal goal = new Goal();

        ModelAndView modelAndView = new ModelAndView("goal/create");
        modelAndView.addObject("command", goal);
        return modelAndView;
    }

    @RequestMapping(value = "/goal/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable int id) {
        Goal goal = goalService.findEntityById(id);

        ModelAndView modelAndView = new ModelAndView("goal/edit");
        modelAndView.addObject("command", goal);

        return modelAndView;
    }

    @RequestMapping(value = "/goal/save", method = RequestMethod.POST)
    public String save(final @ModelAttribute("command") @Valid Goal goal, BindingResult result,
            Model model, HttpSession session) {

        if (result.hasErrors()) {
            return "goal/edit";
        }

        try {
            goalService.save(goal);
        } catch (RuntimeException re) {
            pageFrameworkService.setFlashMessage(session, re.getMessage());
            pageFrameworkService.setIsRedirect(session, Boolean.TRUE);
            return "redirect:/goal/list";
        }
        return "redirect:/goal/list";
    }

    @RequestMapping(value = "/goal/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable int id, HttpSession session) {

        Goal goal = goalService.findEntityById(id);
        if (goal != null) {
            try {
                goalService.delete(goal);
            } catch (RuntimeException re) {
                pageFrameworkService.setFlashMessage(session, re.getMessage());
                pageFrameworkService.setIsRedirect(session, Boolean.TRUE);
                return "redirect:/goal/show/" + id;
            }
        } else {
            pageFrameworkService.setFlashMessage(session, "No Goal with that id");
            pageFrameworkService.setIsRedirect(session, Boolean.TRUE);
        }

        return "redirect:/goal/list";
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