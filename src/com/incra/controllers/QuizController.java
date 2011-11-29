package com.incra.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.incra.domain.User;
import com.incra.services.QuestionService;
import com.incra.services.UserService;

/**
 * The <i>QuizController</i> will implement the quiz function, which is based on
 * offering a set of questions, checking the answers, and increasing the user's
 * score.
 * 
 * @author Jeffrey Risberg
 * @since 11/26/11
 */
@Controller
public class QuizController {

    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;

    public QuizController() {
    }

    @RequestMapping(value = "/quiz")
    public String root() {
        User user = userService.getCurrentUser();

        return "redirect:/home";
    }

    // one method to start the quiz

    // one method to display a list of question

    // one method to process the posted answers
}