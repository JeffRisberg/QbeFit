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

import com.incra.domain.Challenge;
import com.incra.domain.propertyEditor.ChallengePropertyEditor;
import com.incra.services.ChallengeService;
import com.incra.services.PageFrameworkService;

/**
 * The <i>ChallengeController</i> controller defines admin operations on
 * challenges.
 * 
 * @author Jeffrey Risberg
 * @since 09/10/11
 */
@Controller
public class ChallengeController {

    protected static Logger logger = LoggerFactory.getLogger(ChallengeController.class);

    @Autowired
    private ChallengeService challengeService;
    @Autowired
    private PageFrameworkService pageFrameworkService;

    public ChallengeController() {
    }

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) throws Exception {
        dataBinder.registerCustomEditor(Challenge.class, new ChallengePropertyEditor(
                challengeService));
    }

    @RequestMapping(value = "/challenge/**")
    public String index() {
        return "redirect:/challenge/list";
    }

    // ADMIN

    @RequestMapping(value = "/challenge/list")
    public ModelAndView list(Object criteria) {

        List<Challenge> challengeList = challengeService.findEntityList();

        ModelAndView modelAndView = new ModelAndView("challenge/list");
        modelAndView.addObject("challengeList", challengeList);
        return modelAndView;
    }

    @RequestMapping(value = "/challenge/show/{id}", method = RequestMethod.GET)
    public String show(@PathVariable int id, Model model, HttpSession session) {

        Challenge challenge = challengeService.findEntityById(id);
        if (challenge != null) {
            model.addAttribute(challenge);
            return "challenge/show";
        } else {
            pageFrameworkService.setFlashMessage(session, "No Challenge with that id");
            pageFrameworkService.setIsRedirect(session, Boolean.TRUE);
            return "redirect:/challenge/list";
        }
    }

    @RequestMapping(value = "/challenge/create", method = RequestMethod.GET)
    public ModelAndView create() {

        Challenge challenge = new Challenge();

        ModelAndView modelAndView = new ModelAndView("challenge/create");
        modelAndView.addObject("command", challenge);
        return modelAndView;
    }

    @RequestMapping(value = "/challenge/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable int id) {
        Challenge challenge = challengeService.findEntityById(id);

        ModelAndView modelAndView = new ModelAndView("challenge/edit");
        modelAndView.addObject("command", challenge);

        return modelAndView;
    }

    @RequestMapping(value = "/challenge/save", method = RequestMethod.POST)
    public String save(final @ModelAttribute("command") @Valid Challenge challenge,
            BindingResult result, Model model, HttpSession session) {

        if (result.hasErrors()) {
            return "challenge/edit";
        }

        try {
            challengeService.save(challenge);
        } catch (RuntimeException re) {
            pageFrameworkService.setFlashMessage(session, re.getMessage());
            pageFrameworkService.setIsRedirect(session, Boolean.TRUE);
            return "redirect:/challenge/list";
        }
        return "redirect:/challenge/list";
    }

    @RequestMapping(value = "/challenge/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable int id, HttpSession session) {

        Challenge challenge = challengeService.findEntityById(id);
        if (challenge != null) {
            try {
                challengeService.delete(challenge);
            } catch (RuntimeException re) {
                pageFrameworkService.setFlashMessage(session, re.getMessage());
                pageFrameworkService.setIsRedirect(session, Boolean.TRUE);
                return "redirect:/challenge/show/" + id;
            }
        } else {
            pageFrameworkService.setFlashMessage(session, "No Challenge with that id");
            pageFrameworkService.setIsRedirect(session, Boolean.TRUE);
        }

        return "redirect:/challenge/list";
    }
}