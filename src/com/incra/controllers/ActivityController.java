package com.incra.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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

import com.incra.domain.Activity;
import com.incra.domain.ActivityCategory;
import com.incra.domain.UserActivity;
import com.incra.domain.propertyEditor.ActivityCategoryPropertyEditor;
import com.incra.domain.propertyEditor.ActivityPropertyEditor;
import com.incra.services.ActivityCategoryService;
import com.incra.services.ActivityService;
import com.incra.services.PageFrameworkService;

/**
 * The <i>ActivityController</i> implements admin operations on Activities.
 * 
 * @author Jeffrey Risberg
 * @since 09/10/11
 */
@Secured("ROLE_ADMIN")
@Controller
public class ActivityController extends AbstractAdminController {

    protected static Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    private ActivityService activityService;
    @Autowired
    private ActivityCategoryService activityCategoryService;
    @Autowired
    private PageFrameworkService pageFrameworkService;

    public ActivityController() {
    }

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) throws Exception {
        dataBinder
                .registerCustomEditor(Activity.class, new ActivityPropertyEditor(activityService));
        dataBinder.registerCustomEditor(ActivityCategory.class, new ActivityCategoryPropertyEditor(
                activityCategoryService));
    }

    @RequestMapping(value = "/activity/**")
    public String index() {
        return "redirect:/activity/list";
    }

    @RequestMapping(value = "/activity/list")
    public ModelAndView list(Object criteria) {

        List<Activity> activityList = activityService.findEntityList();

        ModelAndView modelAndView = new ModelAndView("activity/list");
        modelAndView.addObject("activityList", activityList);
        return modelAndView;
    }

    @RequestMapping(value = "/activity/show/{id}", method = RequestMethod.GET)
    public String show(@PathVariable int id, Model model, HttpSession session) {

        Activity activity = activityService.findEntityById(id);
        if (activity != null) {
            model.addAttribute(activity);
            return "activity/show";
        } else {
            pageFrameworkService.setFlashMessage(session, "No Activity with that id");
            pageFrameworkService.setIsRedirect(session, Boolean.TRUE);
            return "redirect:/activity/list";
        }
    }

    @RequestMapping(value = "/activity/create", method = RequestMethod.GET)
    public ModelAndView create() {

        Activity activity = new Activity();
        activity.setDifficulty(2);

        List<ActivityCategory> activityCategoryList = activityCategoryService.findEntityList();

        ModelAndView modelAndView = new ModelAndView("activity/create");
        modelAndView.addObject("command", activity);
        modelAndView.addObject("activityCategoryList", activityCategoryList);
        return modelAndView;
    }

    @RequestMapping(value = "/activity/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable int id) {
        Activity activity = activityService.findEntityById(id);

        List<ActivityCategory> activityCategoryList = activityCategoryService.findEntityList();

        ModelAndView modelAndView = new ModelAndView("activity/edit");
        modelAndView.addObject("command", activity);
        modelAndView.addObject("activityCategoryList", activityCategoryList);

        return modelAndView;
    }

    @RequestMapping(value = "/activity/save", method = RequestMethod.POST)
    public String save(final @ModelAttribute("command") @Valid Activity activity,
            BindingResult result, Model model, HttpSession session) {

        System.out.println("saving " + model);
        if (result.hasErrors()) {
            List<ActivityCategory> activityCategoryList = activityCategoryService.findEntityList();

            model.addAttribute("activityCategoryList", activityCategoryList);
            return "activity/edit";
        }

        try {
            activityService.save(activity);
        } catch (RuntimeException re) {
            System.out.println("setting flashMessage" + re.getMessage());
            pageFrameworkService.setFlashMessage(session, re.getMessage());
            pageFrameworkService.setIsRedirect(session, Boolean.TRUE);
            return "redirect:/activity/list";
        }
        return "redirect:/activity/list";
    }

    @RequestMapping(value = "/activity/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable int id, HttpSession session) {

        Activity activity = activityService.findEntityById(id);
        if (activity != null) {
            try {
                activityService.delete(activity);
            } catch (RuntimeException re) {
                pageFrameworkService.setFlashMessage(session, re.getMessage());
                pageFrameworkService.setIsRedirect(session, Boolean.TRUE);
                return "redirect:/activity/show/" + id;
            }
        } else {
            pageFrameworkService.setFlashMessage(session, "No Activity with that id");
            pageFrameworkService.setIsRedirect(session, Boolean.TRUE);
        }

        return "redirect:/activity/list";
    }

    /** Find the userActivity for a given activityId, from a list */
    protected UserActivity getUserActivityForActivityId(List<UserActivity> userActivityList,
            Integer activityId) {
        for (UserActivity userActivity : userActivityList) {
            Activity activity = userActivity.getActivity();
            Integer tmpActivityId = activity.getId();

            if (userActivity.getEffectivityEnd() == null && tmpActivityId.equals(activityId))
                return userActivity;
        }
        return null;
    }
}