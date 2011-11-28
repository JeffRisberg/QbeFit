package com.incra.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.incra.controllers.dto.AdminPanel;

/**
 * The <i>AdministrationController</i> controller generates the admin screen
 * 
 * @author Jeffrey Risberg
 * @since 10/15/11
 */
@Controller
public class AdministrationController {

    protected static Logger logger = LoggerFactory.getLogger(AdministrationController.class);

    public AdministrationController() {
    }

    @RequestMapping(value = "/administration/**")
    public ModelAndView index() {

        List<AdminPanel> adminPanelList = new ArrayList<AdminPanel>();
        AdminPanel adminPanel;

        adminPanel = new AdminPanel("Users", "/user");
        adminPanelList.add(adminPanel);

        adminPanel = new AdminPanel("Levels", "/level");
        adminPanelList.add(adminPanel);

        adminPanel = new AdminPanel("Badges", "/badge");
        adminPanelList.add(adminPanel);

        adminPanel = new AdminPanel("Challenges", "/challenge");
        adminPanelList.add(adminPanel);

        adminPanel = new AdminPanel("Activities", "/activity");
        adminPanelList.add(adminPanel);

        adminPanel = new AdminPanel("Goals", "/goal");
        adminPanelList.add(adminPanel);

        ModelAndView modelAndView = new ModelAndView("administration/index");
        modelAndView.addObject("adminPanelList", adminPanelList);

        return modelAndView;
    }
}