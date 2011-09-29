package com.incra.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.incra.domain.Badge;
import com.incra.services.BadgeService;

/**
 * The <i>BadgeController</i> controller defines operations on Badge entities,
 * including selection.
 * 
 * @author Jeffrey Risberg
 * @since 09/11/11
 */
@Controller
public class BadgeController {

    protected static Logger logger = LoggerFactory.getLogger(BadgeController.class);

    @Autowired
    private BadgeService badgeService;

    public BadgeController() {
    }

    @RequestMapping(value = "/badge/**")
    public String index() {
        return "redirect:/badge/viewAll";
    }

    @RequestMapping(value = "/badge/viewAll")
    public ModelAndView viewAll(Object criteria) {

        List<Badge> badgeList = badgeService.findEntityList();

        ModelAndView modelAndView = new ModelAndView("badge/viewAll");
        modelAndView.addObject("badgeList", badgeList);
        return modelAndView;
    }
}