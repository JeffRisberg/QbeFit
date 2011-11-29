package com.incra.controllers;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.incra.domain.Activity;
import com.incra.domain.ActivityCategory;
import com.incra.domain.propertyEditor.ActivityCategoryPropertyEditor;
import com.incra.domain.propertyEditor.ActivityPropertyEditor;
import com.incra.services.ActivityCategoryService;
import com.incra.services.ActivityService;

/**
 * The <i>LeaderboardController</i> is under development.
 * 
 * @author Jeff Risberg
 * @since 11/20/11
 */
@Controller
public class LeaderboardController {

    protected static Logger logger = LoggerFactory.getLogger(LeaderboardController.class);

    @Autowired
    private ActivityService activityService;
    @Autowired
    private ActivityCategoryService activityCategoryService;

    public LeaderboardController() {
    }

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) throws Exception {
        dataBinder
                .registerCustomEditor(Activity.class, new ActivityPropertyEditor(activityService));
        dataBinder.registerCustomEditor(ActivityCategory.class, new ActivityCategoryPropertyEditor(
                activityCategoryService));
    }

    @RequestMapping(value = "/leaderboard/show/{id}", method = RequestMethod.GET)
    public ModelAndView show(@PathVariable int id, Model model, HttpSession session) {
        ActivityCategory activityCategory = activityCategoryService.findEntityById(id);

        if (activityCategory != null) {
            // List<Quiz> quizList =
            // quizService.findEntityListByActivityCategory(activityCategory);

            ModelAndView modelAndView = new ModelAndView("leaderboard/show");
            // modelAndView.addObject("quizList", quizList);
            modelAndView.addObject("activityCategory", activityCategory.getName());
            return modelAndView;
        }

        throw new RuntimeException("Internal error, no Activity category");
    }
}