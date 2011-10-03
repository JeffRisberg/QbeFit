package com.incra.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.incra.domain.Level;
import com.incra.services.LevelService;

/**
 * The <i>LevelController</i> controller defines operations on Level entities,
 * including selection.
 * 
 * @author Jeffrey Risberg
 * @since 09/11/11
 */
@Controller
public class LevelController {

    protected static Logger logger = LoggerFactory.getLogger(LevelController.class);

    @Autowired
    private LevelService levelService;

    public LevelController() {
    }

    @RequestMapping(value = "/level/**")
    public String index() {
        return "redirect:/level/viewAll";
    }

    @RequestMapping(value = "/level/viewAll")
    public ModelAndView viewAll(Object criteria) {

        List<Level> levelList = levelService.findEntityList();

        ModelAndView modelAndView = new ModelAndView("level/viewAll");
        modelAndView.addObject("levelList", levelList);
        return modelAndView;
    }
}