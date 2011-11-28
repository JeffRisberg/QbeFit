package com.incra.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.authentication.encoding.PasswordEncoder;
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

import com.incra.domain.Goal;
import com.incra.domain.OrganizationType;
import com.incra.domain.TimeZone;
import com.incra.domain.User;
import com.incra.domain.UserGoal;
import com.incra.domain.propertyEditor.GoalPropertyEditor;
import com.incra.domain.propertyEditor.OrganizationTypePropertyEditor;
import com.incra.domain.propertyEditor.TimeZonePropertyEditor;
import com.incra.domain.propertyEditor.UserPropertyEditor;
import com.incra.services.GoalService;
import com.incra.services.LevelService;
import com.incra.services.OrganizationTypeService;
import com.incra.services.PageFrameworkService;
import com.incra.services.TimeZoneService;
import com.incra.services.UserGoalService;
import com.incra.services.UserService;

/**
 * The <i>UserController</i> controller defines operations on users, including
 * registration.
 * 
 * @author Jeffrey Risberg
 * @since 09/10/11
 */
@Controller
public class UserController implements ApplicationContextAware {
    protected static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private OrganizationTypeService organizationTypeService;
    @Autowired
    private GoalService goalService;
    @Autowired
    private UserGoalService userGoalService;
    @Autowired
    private TimeZoneService timeZoneService;
    @Autowired
    private LevelService levelService;
    @Autowired
    private PageFrameworkService pageFrameworkService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    ApplicationContext applicationContext;

    public UserController() {
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

    @RequestMapping(value = "/user/**")
    public String index() {
        return "redirect:/user/list";
    }

    // REGISTRATION

    /**
     * Offer the registration screen.
     */
    @RequestMapping(value = "/user/register", method = RequestMethod.GET)
    public ModelAndView register() {

        User user = new User();

        ModelAndView modelAndView = new ModelAndView("user/register");
        modelAndView.addObject("command", user);
        return modelAndView;
    }

    /**
     * Process the post from the registration screen. If successful, ask for
     * information about user.
     */
    @RequestMapping(value = "/user/registerUpdate", method = RequestMethod.POST)
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
            curUser.setLevel(levelService.computeLevel(user.getPoints()));
            curUser.setLoginCount(1);
            curUser.setLastLoggedIn(new Date());
            curUser.setTemporary(false);
            userService.save(curUser);

            userService.performProgrammaticLogin(user);

            return "redirect:/user/aboutMe";
        } else {
            pageFrameworkService.setFlashMessage(session, "Passwords must match");
            pageFrameworkService.setIsRedirect(session, Boolean.TRUE);
            return "redirect:/user/register";
        }
    }

    // INFORMATION GATHERING

    /**
     * Offer the aboutMe screen.
     */
    @RequestMapping(value = "/user/aboutMe", method = RequestMethod.GET)
    public ModelAndView aboutMe() {

        User user = userService.getCurrentUser();

        List<OrganizationType> organizationTypeList = organizationTypeService.findEntityList();
        List<Goal> goalList = goalService.findEntityList();

        List<UserGoal> priorUserGoalList = userGoalService.findEntityList(user);

        List<Boolean> selectedList = new ArrayList<Boolean>();
        for (Goal goal : goalList) {
            Integer goalId = goal.getId();
            UserGoal priorUserGoal = getUserGoalForGoalId(priorUserGoalList, goalId);

            if (priorUserGoal != null) {
                selectedList.add(Boolean.TRUE);
            } else {
                selectedList.add(Boolean.FALSE);
            }
        }

        ModelAndView modelAndView = new ModelAndView("user/aboutMe");
        modelAndView.addObject("command", user);
        modelAndView.addObject("organizationTypeList", organizationTypeList);
        modelAndView.addObject("goalList", goalList);
        modelAndView.addObject("selectedList", selectedList);

        return modelAndView;
    }

    /**
     * Process the post from the aboutMe screen, then continue to home screen.
     */
    @RequestMapping(value = "/user/aboutMeUpdate", method = RequestMethod.POST)
    public String aboutMeUpdate(final @ModelAttribute("command") @Valid User user,
            BindingResult result, Model model, HttpSession session, HttpServletRequest httpRequest) {

        User curUser = userService.getCurrentUser();

        List<Goal> goalList = goalService.findEntityList();
        List<UserGoal> priorUserGoalList = userGoalService.findEntityList(curUser);

        for (Goal goal : goalList) {
            Integer goalId = goal.getId();
            UserGoal priorUserGoal = getUserGoalForGoalId(priorUserGoalList, goalId);

            if (httpRequest.getParameter("goal_" + goalId) != null) {
                if (priorUserGoal == null) {
                    UserGoal userGoal = new UserGoal();
                    userGoal.setUser(curUser);
                    userGoal.setGoal(goal);
                    userGoal.setEffectivityStart(new Date());
                    userGoalService.save(userGoal);
                }
            } else {
                if (priorUserGoal != null) {
                    priorUserGoal.setEffectivityEnd(new Date());
                    userGoalService.save(priorUserGoal);
                }
            }
        }

        curUser.setOrganizationType(user.getOrganizationType());
        curUser.setAboutMeInfoGathered(true);
        curUser.setSplashScreenShown(true);
        userService.save(curUser);

        return "redirect:/home/index";
    }

    // ADMIN

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

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        this.applicationContext = arg0;
    }

    /** Find the userGoal for a given goalId, from a list */
    protected UserGoal getUserGoalForGoalId(List<UserGoal> userGoalList, Integer goalId) {
        for (UserGoal userGoal : userGoalList) {
            Goal goal = userGoal.getGoal();
            Integer tmpGoalId = goal.getId();

            if (userGoal.getEffectivityEnd() == null && tmpGoalId.equals(goalId))
                return userGoal;
        }
        return null;
    }
}