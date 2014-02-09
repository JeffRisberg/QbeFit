package com.incra.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.incra.domain.Question;
import com.incra.domain.QuestionCategory;
import com.incra.domain.User;
import com.incra.services.QuestionCategoryService;
import com.incra.services.QuestionService;
import com.incra.services.UserQuestionCategoryScoreService;
import com.incra.services.UserService;

/**
 * The <i>QuizController</i> implements the quiz function, which is based on
 * offering a set of questions, checking the answers, and increasing the user's
 * score.
 * 
 * @author Jeffrey Risberg, Smitha Ramaswamy
 * @since 11/26/11
 */
@Controller
public class QuizController {

  @Autowired
  private UserService userService;
  @Autowired
  private QuestionService questionService;
  @Autowired
  private QuestionCategoryService questionCategoryService;
  @Autowired
  private UserQuestionCategoryScoreService userQuestionCategoryScoreService;

  public QuizController() {
  }

  @InitBinder
  protected void initBinder(WebDataBinder dataBinder) throws Exception {
  }

  /**
   * Method to display the quiz.
   */
  @RequestMapping(value = "/quiz/{id}", method = RequestMethod.GET)
  public ModelAndView show(@PathVariable int id, Model model,
      HttpSession session) {

    QuestionCategory questionCategory = questionCategoryService
        .findEntityById(id);

    List<Question> questionList = questionService
        .findEntityListByQuestionCategory(questionCategory);

    ModelAndView modelAndView = new ModelAndView("quiz/index");
    modelAndView.addObject("questionList", questionList);
    modelAndView.addObject("questionCategory", questionCategory);
    return modelAndView;
  }

  /**
   * Method to process the posted answers.
   */
  @SuppressWarnings("unused")
  @RequestMapping(value = "/quiz/update", method = RequestMethod.POST)
  public String indexUpdate(HttpSession httpSession) {
    User curUser = userService.getCurrentUser();

    return "redirect:/home";
  }
}
