package com.incra.controllers;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.incra.domain.Goal;
import com.incra.domain.OrganizationType;
import com.incra.domain.TimeZone;
import com.incra.domain.User;
import com.incra.domain.propertyEditor.GoalPropertyEditor;
import com.incra.domain.propertyEditor.OrganizationTypePropertyEditor;
import com.incra.domain.propertyEditor.TimeZonePropertyEditor;
import com.incra.domain.propertyEditor.UserPropertyEditor;
import com.incra.services.GoalService;
import com.incra.services.LevelService;
import com.incra.services.OrganizationTypeService;
import com.incra.services.PageFrameworkService;
import com.incra.services.TimeZoneService;
import com.incra.services.UserService;

/**
 * The <i>RegisterController</i> ...
 * 
 * @author Jeffrey Risberg
 * @since 10/12/11
 */
@Controller
public class RegisterController {
    protected static Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private OrganizationTypeService organizationTypeService;
    @Autowired
    private GoalService goalService;
    @Autowired
    private TimeZoneService timeZoneService;
    @Autowired
    private LevelService levelService;
    @Autowired
    private PageFrameworkService pageFrameworkService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public RegisterController() {
    }

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) throws Exception {
        dataBinder.registerCustomEditor(User.class, new UserPropertyEditor(userService));
        dataBinder
                .registerCustomEditor(TimeZone.class, new TimeZonePropertyEditor(timeZoneService));
        dataBinder.registerCustomEditor(OrganizationType.class, new OrganizationTypePropertyEditor(
                organizationTypeService));
        dataBinder.registerCustomEditor(Goal.class, new GoalPropertyEditor(goalService));
    }

    /**
     * Offer the registration screen.
     */
    @RequestMapping(value = "/register/**", method = RequestMethod.GET)
    public ModelAndView register() {

        User user = new User();

        ModelAndView modelAndView = new ModelAndView("register/index");
        modelAndView.addObject("command", user);
        return modelAndView;
    }

    /**
     * Process the post from the registration screen. If successful, ask for
     * information about user.
     */
    @RequestMapping(value = "/register/update", method = RequestMethod.POST)
    public String registerUpdate(final @ModelAttribute("command") @Valid User user,
            BindingResult result, Model model, HttpSession session) {

        String password = user.getPassword();
        String confirmPassword = user.getConfirmPassword();

        if (password.equals(confirmPassword)) {
            String encPassword = passwordEncoder.encodePassword(password, null);

            User curUser = userService.getCurrentUser();

            curUser.setFirstName(user.getFirstName());
            curUser.setLastName(user.getLastName());
            curUser.setEmail(user.getEmail());
            curUser.setPassword(encPassword);
            curUser.setLevel(levelService.computeLevel(curUser.getPoints()));
            curUser.setLoginCount(1);
            curUser.setLastLoggedIn(new Date());
            curUser.setTemporary(false);
            userService.save(curUser);

            userService.performProgrammaticLogin(curUser);

            if (curUser.isAboutMeInfoGathered()) {
                return "redirect:/home";
            } else {
                return "redirect:/activitySelect/aboutMe";
            }
        } else {
            pageFrameworkService.setFlashMessage(session, "Passwords must match");
            pageFrameworkService.setIsRedirect(session, Boolean.TRUE);
            return "redirect:/register";
        }
    }
}