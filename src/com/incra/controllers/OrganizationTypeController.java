package com.incra.controllers;

import java.util.List;

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

import com.incra.domain.OrganizationType;
import com.incra.services.OrganizationTypeService;
import com.incra.services.PageFrameworkService;

/**
 * The <i>OrganizationTypeController</i> controller defines the list method for
 * organization types. We don't have admin control to edit this yet.
 * 
 * @author Jeffrey Risberg
 * @since 11/13/11
 */
@Controller
public class OrganizationTypeController {

    protected static Logger logger = LoggerFactory.getLogger(OrganizationTypeController.class);

    @Autowired
    private OrganizationTypeService organizationTypeService;
    @Autowired
    private PageFrameworkService pageFrameworkService;

    public OrganizationTypeController() {
    }

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) throws Exception {
        //
    }

    @RequestMapping(value = "/organizationType/**")
    public String index() {
        return "redirect:/organizationType/list";
    }

    // ADMIN

    @RequestMapping(value = "/organizationType/list")
    public ModelAndView list(Object criteria) {

        List<OrganizationType> organizationTypeList = organizationTypeService.findEntityList();

        ModelAndView modelAndView = new ModelAndView("organizationType/list");
        modelAndView.addObject("organizationTypeList", organizationTypeList);
        return modelAndView;
    }

    @RequestMapping(value = "/organizationType/show/{id}", method = RequestMethod.GET)
    public String show(@PathVariable int id, Model model, HttpSession session) {

        OrganizationType organizationType = organizationTypeService.findEntityById(id);
        if (organizationType != null) {
            model.addAttribute(organizationType);
            return "organizationType/show";
        } else {
            pageFrameworkService.setFlashMessage(session, "No OrganizationType with that id");
            pageFrameworkService.setIsRedirect(session, Boolean.TRUE);
            return "redirect:/organizationType/list";
        }
    }
}