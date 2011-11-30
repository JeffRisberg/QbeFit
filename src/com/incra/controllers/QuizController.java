package com.incra.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.incra.domain.Goal;
import com.incra.domain.Question;
import com.incra.domain.User;
import com.incra.domain.UserGoal;
import com.incra.services.QuestionService;
import com.incra.services.UserGoalService;
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
    private UserGoalService userGoalService;
    @Autowired
    private QuestionService questionService;

    public QuizController() {
    }

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) throws Exception {
    }

    /**
     * Method to display the quiz.
     */
    @RequestMapping(value = "/quiz/**")
    public ModelAndView index(HttpSession session) {
        User user = userService.getCurrentUser();

        List<UserGoal> userGoalList = userGoalService.findEntityList(user);
        List<Question> questionList = new ArrayList<Question>();

        for (UserGoal userGoal : userGoalList) {
            Goal goal = userGoal.getGoal();
            List<Question> goalQuestionList = questionService.findEntityListByGoal(goal);

            for (Question question : goalQuestionList) {
                if (questionList.contains(question) == false) {
                    questionList.add(question);
                }
            }
        }

        ModelAndView modelAndView = new ModelAndView("quiz/index");
        modelAndView.addObject("questionList", questionList);
        return modelAndView;
    }

    /**
     * Method to process the posted answers.
     */
}
