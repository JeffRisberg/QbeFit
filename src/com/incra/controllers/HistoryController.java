package com.incra.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.incra.domain.User;
import com.incra.services.ActivityService;
import com.incra.services.EventService;
import com.incra.services.UserActivityService;
import com.incra.services.UserService;

/**
 * The <i>HistoryController</i> implements the history charts.
 * 
 * @author Jeffrey Risberg
 * @since 09/10/11
 */
@Controller
public class HistoryController {
    protected static Logger logger = LoggerFactory.getLogger(HistoryController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserActivityService userActivityService;
    @Autowired
    private EventService eventService;

    public HistoryController() {
    }

    @RequestMapping(value = "/history/**")
    public String index() {
        return "redirect:/history/activity";
    }

    /** Show the activity-level history */
    @RequestMapping(value = "/history/activity", method = RequestMethod.GET)
    public ModelAndView activity() {

        User user = userService.getCurrentUser();

        ModelAndView modelAndView = new ModelAndView("history/activity");
        modelAndView.addObject("command", user);
        modelAndView.addObject("userName", user.getFirstName() + " " + user.getLastName());
        modelAndView.addObject("points", user.getPoints());
        modelAndView.addObject("level", user.getLevel());
        return modelAndView;
    }

    /** Return the data for the activity-level history chart */
    @RequestMapping(value = "/history/activity/data/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> activityData(@PathVariable Integer id, HttpServletRequest request,
            HttpServletResponse response, HttpSession session) {
        Map<String, Object> json = new HashMap<String, Object>();
        List<Object> seriesList = new ArrayList<Object>();

        User user = userService.getCurrentUser();

        json.put("series", seriesList);

        logger.info("returning activity chart data " + json);
        return json;
    }

    /** Show the workout-level history */
    @RequestMapping(value = "/history/workout", method = RequestMethod.GET)
    public ModelAndView workout() {

        User user = userService.getCurrentUser();

        ModelAndView modelAndView = new ModelAndView("history/workout");
        modelAndView.addObject("command", user);
        modelAndView.addObject("userName", user.getFirstName() + " " + user.getLastName());
        modelAndView.addObject("points", user.getPoints());
        modelAndView.addObject("level", user.getLevel());
        return modelAndView;
    }

    /** Return the data for the workout-level history chart */
    @RequestMapping(value = "/history/workout/data/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> workoutData(@PathVariable Integer id, HttpServletRequest request,
            HttpServletResponse response, HttpSession session) {
        Map<String, Object> json = new HashMap<String, Object>();
        List<Object> seriesList = new ArrayList<Object>();

        User user = userService.getCurrentUser();

        json.put("series", seriesList);

        logger.info("returning workout chart data " + json);
        return json;
    }
}
