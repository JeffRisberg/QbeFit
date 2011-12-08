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

import com.incra.domain.OrganizationType;
import com.incra.domain.TimeZone;
import com.incra.domain.User;
import com.incra.domain.propertyEditor.OrganizationTypePropertyEditor;
import com.incra.domain.propertyEditor.TimeZonePropertyEditor;
import com.incra.domain.propertyEditor.UserPropertyEditor;
import com.incra.services.OrganizationTypeService;
import com.incra.services.PageFrameworkService;
import com.incra.services.TimeZoneService;
import com.incra.services.UserService;

/**
 * The <i>UserController</i> controller implements admin operations on Users.
 * 
 * @author Jeffrey Risberg
 * @since 09/10/11
 */
@Controller
public class UserController extends AbstractAdminController {

    protected static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private OrganizationTypeService organizationTypeService;
    @Autowired
    private TimeZoneService timeZoneService;
    @Autowired
    private PageFrameworkService pageFrameworkService;

    public UserController() {
    }

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) throws Exception {
        dataBinder.registerCustomEditor(User.class, new UserPropertyEditor(userService));
        dataBinder
                .registerCustomEditor(TimeZone.class, new TimeZonePropertyEditor(timeZoneService));
        dataBinder.registerCustomEditor(OrganizationType.class, new OrganizationTypePropertyEditor(
                organizationTypeService));
    }

    @RequestMapping(value = "/user/**")
    public String index() {
        return "redirect:/user/list";
    }

    @RequestMapping(value = "/user/list")
    public ModelAndView list(Object criteria) {

        List<User> userList = userService.findEntityList();

        ModelAndView modelAndView = new ModelAndView("user/list");
        modelAndView.addObject("userList", userList);
        return modelAndView;
    }

    @RequestMapping(value = "/user/show/{id}", method = RequestMethod.GET)
    public String show(@PathVariable int id, Model model, HttpSession session) {

        User user = userService.findEntityById(id);
        if (user != null) {
            model.addAttribute(user);
            return "user/show";
        } else {
            pageFrameworkService.setFlashMessage(session, "No User with that id");
            pageFrameworkService.setIsRedirect(session, Boolean.TRUE);
            return "redirect:/user/list";
        }
    }

    @RequestMapping(value = "/user/create", method = RequestMethod.GET)
    public ModelAndView create() {

        User user = new User();

        ModelAndView modelAndView = new ModelAndView("user/create");
        modelAndView.addObject("command", user);
        return modelAndView;
    }

    @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable int id) {
        User user = userService.findEntityById(id);

        ModelAndView modelAndView = new ModelAndView("user/edit");
        modelAndView.addObject("command", user);

        return modelAndView;
    }

    @RequestMapping(value = "/user/save", method = RequestMethod.POST)
    public String save(final @ModelAttribute("command") @Valid User user, BindingResult result,
            Model model, HttpSession session) {

        if (result.hasErrors()) {
            return "user/edit";
        }

        try {
            userService.save(user);
        } catch (RuntimeException re) {
            pageFrameworkService.setFlashMessage(session, re.getMessage());
            pageFrameworkService.setIsRedirect(session, Boolean.TRUE);
            return "redirect:/user/list";
        }
        return "redirect:/user/list";
    }

    @RequestMapping(value = "/user/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable int id, HttpSession session) {

        User user = userService.findEntityById(id);
        if (user != null) {
            try {
                userService.delete(user);
            } catch (RuntimeException re) {
                pageFrameworkService.setFlashMessage(session, re.getMessage());
                pageFrameworkService.setIsRedirect(session, Boolean.TRUE);
                return "redirect:/user/show/" + id;
            }
        } else {
            pageFrameworkService.setFlashMessage(session, "No User with that id");
            pageFrameworkService.setIsRedirect(session, Boolean.TRUE);
        }

        return "redirect:/user/list";
    }
}