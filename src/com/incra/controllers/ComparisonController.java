package com.incra.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.incra.domain.QuestionCategory;
import com.incra.domain.User;
import com.incra.domain.UserQuestionCategoryScore;
import com.incra.services.QuestionCategoryService;
import com.incra.services.UserQuestionCategoryScoreService;
import com.incra.services.UserService;

/**
 * 1-1 shown graphically.
 * 
 * shown as circle containing smaller cricle, where size of smaller is based on
 * # number of questions answer trued for the at QuestionCategory.
 * 
 * comparing userQuestionCategory ojbects for user1 vs user2
 */
@Controller
public class ComparisonController {

    protected static Logger logger = LoggerFactory.getLogger(ComparisonController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private QuestionCategoryService questionCategoryService;
    @Autowired
    private UserQuestionCategoryScoreService userQuestionCategoryScoreService;

    public ComparisonController() {
    }

    /**
     * Perform a comparison of user (Self) with user indicated by the (id)
     * value.
     */
    @RequestMapping(value = "/comparison/{id}", method = RequestMethod.GET)
    public ModelAndView index(@PathVariable int id, Model model, HttpSession session) {

        User curUser = userService.getCurrentUser();
        List<QuestionCategory> questionCategoryList = questionCategoryService.findEntityList();

        List<UserQuestionCategoryScore> scoreList = userQuestionCategoryScoreService
                .findEntityList(curUser);

        ModelAndView modelAndView = new ModelAndView("comparison/index");
        modelAndView.addObject("scoreList", scoreList);
        modelAndView.addObject("questionCategoryList", questionCategoryList);

        return modelAndView;
    }
}
