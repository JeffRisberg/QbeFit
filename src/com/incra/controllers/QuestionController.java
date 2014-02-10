package com.incra.controllers;

import java.util.List;

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

import com.incra.domain.Question;
import com.incra.domain.QuestionCategory;
import com.incra.domain.enums.QuestionType;
import com.incra.domain.propertyEditor.QuestionCategoryPropertyEditor;
import com.incra.services.PageFrameworkService;
import com.incra.services.QuestionCategoryService;
import com.incra.services.QuestionService;

/**
 * The <i>QuestionController</i> implements the admin functions on Questions.
 * 
 * @author Jeffrey Risberg
 * @since 11/26/11
 */
@Controller
public class QuestionController extends AbstractAdminController {

  protected static Logger logger = LoggerFactory
      .getLogger(QuestionController.class);

  @Autowired
  private QuestionService questionService;
  @Autowired
  private QuestionCategoryService questionCategoryService;
  @Autowired
  private PageFrameworkService pageFrameworkService;

  public QuestionController() {
  }

  @InitBinder
  protected void initBinder(WebDataBinder dataBinder) throws Exception {
    dataBinder.registerCustomEditor(QuestionCategory.class,
        new QuestionCategoryPropertyEditor(questionCategoryService));
  }

  @RequestMapping(value = "/question/**")
  public String index() {
    return "redirect:/question/list";
  }

  @RequestMapping(value = "/question/list")
  public ModelAndView list(Object criteria) {

    List<Question> questionList = questionService.findEntityList();

    ModelAndView modelAndView = new ModelAndView("question/list");
    modelAndView.addObject("questionList", questionList);
    return modelAndView;
  }

  @RequestMapping(value = "/question/show/{id}", method = RequestMethod.GET)
  public String show(@PathVariable int id, Model model, HttpSession session) {

    Question question = questionService.findEntityById(id);
    if (question != null) {
      model.addAttribute(question);
      return "question/show";
    } else {
      pageFrameworkService.setFlashMessage(session, "No Question with that id");
      pageFrameworkService.setIsRedirect(session, Boolean.TRUE);
      return "redirect:/question/list";
    }
  }

  @RequestMapping(value = "/question/create", method = RequestMethod.GET)
  public ModelAndView create() {

    Question question = new Question();
    question.setQuestionType(QuestionType.Boolean);

    List<QuestionCategory> questionCategoryList = questionCategoryService
        .findEntityList();

    ModelAndView modelAndView = new ModelAndView("question/create");
    modelAndView.addObject("command", question);
    modelAndView.addObject("questionCategoryList", questionCategoryList);

    return modelAndView;
  }

  @RequestMapping(value = "/question/edit/{id}", method = RequestMethod.GET)
  public ModelAndView edit(@PathVariable int id) {
    Question question = questionService.findEntityById(id);

    List<QuestionCategory> questionCategoryList = questionCategoryService
        .findEntityList();

    ModelAndView modelAndView = new ModelAndView("question/edit");
    modelAndView.addObject("command", question);
    modelAndView.addObject("questionCategoryList", questionCategoryList);

    return modelAndView;
  }

  @RequestMapping(value = "/question/save", method = RequestMethod.POST)
  public String save(final @ModelAttribute("command") @Valid Question question,
      BindingResult result, Model model, HttpSession session) {

    System.out.println("saving " + model);
    if (result.hasErrors()) {
      List<QuestionCategory> questionCategoryList = questionCategoryService
          .findEntityList();

      model.addAttribute("questionCategoryList", questionCategoryList);
      return "question/edit";
    }

    try {
      questionService.save(question);
    } catch (RuntimeException re) {
      System.out.println("setting flashMessage" + re.getMessage());
      pageFrameworkService.setFlashMessage(session, re.getMessage());
      pageFrameworkService.setIsRedirect(session, Boolean.TRUE);
      return "redirect:/question/list";
    }
    return "redirect:/question/list";
  }

  @RequestMapping(value = "/question/delete/{id}", method = RequestMethod.GET)
  public String delete(@PathVariable int id, HttpSession session) {

    Question question = questionService.findEntityById(id);
    if (question != null) {
      try {
        questionService.delete(question);
      } catch (RuntimeException re) {
        pageFrameworkService.setFlashMessage(session, re.getMessage());
        pageFrameworkService.setIsRedirect(session, Boolean.TRUE);
        return "redirect:/question/show/" + id;
      }
    } else {
      pageFrameworkService.setFlashMessage(session, "No Question with that id");
      pageFrameworkService.setIsRedirect(session, Boolean.TRUE);
    }

    return "redirect:/question/list";
  }
}