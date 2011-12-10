package com.incra.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.incra.domain.Goal;
import com.incra.domain.OrganizationType;
import com.incra.domain.Question;
import com.incra.domain.User;
import com.incra.domain.UserAnswer;
import com.incra.domain.UserGoal;
import com.incra.domain.enums.AnswerStatus;
import com.incra.domain.enums.QuestionType;
import com.incra.domain.propertyEditor.OrganizationTypePropertyEditor;
import com.incra.domain.propertyEditor.UserPropertyEditor;
import com.incra.services.GoalService;
import com.incra.services.OrganizationTypeService;
import com.incra.services.QuestionService;
import com.incra.services.UserAnswerService;
import com.incra.services.UserGoalService;
import com.incra.services.UserService;

/**
 * The <i>SuggestionsController</i> shows the suggestions-offering screen.
 * 
 * @author Jeff Risberg, Smitha Ramaswamy
 * @since 12/02/11
 */
@Controller
public class SuggestionsController {
    protected static int maxSuggestions = 14;

    protected static Logger logger = LoggerFactory.getLogger(SuggestionsController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private OrganizationTypeService organizationTypeService;
    @Autowired
    private GoalService goalService;
    @Autowired
    private UserGoalService userGoalService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserAnswerService userAnswerService;

    public SuggestionsController() {
    }

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) throws Exception {
        dataBinder.registerCustomEditor(User.class, new UserPropertyEditor(userService));
        dataBinder.registerCustomEditor(OrganizationType.class, new OrganizationTypePropertyEditor(
                organizationTypeService));
    }

    /**
     * Offer the aboutMe screen.
     */
    @RequestMapping(value = "/suggestions/aboutMe")
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

        ModelAndView modelAndView = new ModelAndView("suggestions/aboutMe");
        modelAndView.addObject("command", user);
        modelAndView.addObject("organizationTypeList", organizationTypeList);
        modelAndView.addObject("goalList", goalList);
        modelAndView.addObject("selectedList", selectedList);

        return modelAndView;
    }

    /**
     * Process the post from the aboutMe screen, then select questions.
     */
    @RequestMapping(value = "/suggestions/aboutMeUpdate", method = RequestMethod.POST)
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
        userService.save(curUser);

        return "redirect:/suggestions/start";
    }

    /**
     * Post a list of possible questions. These are chosen from ones that the
     * user has not selected before, and are boolean.
     */
    @RequestMapping(value = "/suggestions/start", method = RequestMethod.GET)
    public ModelAndView start(Model model, HttpSession session) {

        User user = userService.getCurrentUser();

        List<Question> fullQuestionList = questionService.findEntityList();
        List<Question> questionList = new ArrayList<Question>();
        List<UserAnswer> answerList = userAnswerService.findEntityList(user);

        // Create a map of the answers by question
        Map<Question, UserAnswer> answerMap = new HashMap<Question, UserAnswer>();
        for (UserAnswer userAnswer : answerList) {
            Question question = userAnswer.getQuestion();

            answerMap.put(question, userAnswer);
        }

        // Select the questions to offer
        for (Question question : fullQuestionList) {
            if (question.getQuestionType() == QuestionType.Boolean) {
                UserAnswer userAnswer = answerMap.get(question);

                if (userAnswer == null && Math.random() > 0.6) {
                    questionList.add(question);
                    if (questionList.size() >= maxSuggestions) {
                        break;
                    }
                }
            }
        }

        ModelAndView modelAndView = new ModelAndView("suggestions/start");
        modelAndView.addObject("questionList", questionList);
        return modelAndView;
    }

    /**
     * The user is done selecting questions.
     */
    @RequestMapping(value = "/suggestions/done")
    public String done(final @ModelAttribute("command") @Valid User user, BindingResult result,
            Model model, HttpSession session, HttpServletRequest httpRequest) {

        User curUser = userService.getCurrentUser();

        List<Question> questionList = questionService.findEntityList();

        for (Question question : questionList) {
            Integer questionId = question.getId();

            if (httpRequest.getParameter("question_" + questionId) != null) {
                UserAnswer userAnswer = new UserAnswer();
                userAnswer.setUser(curUser);
                userAnswer.setQuestion(question);
                userAnswer.setStatus(AnswerStatus.Considering);
                userAnswer.setDateCreated(new Date());
                userAnswerService.save(userAnswer);
            }
        }

        return "redirect:/home";
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
