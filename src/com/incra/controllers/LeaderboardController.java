package com.incra.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.incra.controllers.dto.LeaderboardEntry;
import com.incra.domain.QuestionCategory;
import com.incra.domain.User;
import com.incra.domain.UserQuestionCategoryScore;
import com.incra.services.QuestionCategoryService;
import com.incra.services.UserQuestionCategoryScoreService;
import com.incra.services.UserService;

/**
 * The <i>LeaderboardController</i> provides a list-based only against other
 * users of the same organization type. A leaderboard typically only shows the
 * top 10 users. There should be an option-link to show the full leaderboard.
 * 
 * From the leaderboard, we can run the comparison screen, to go more details.
 * 
 * @author Jeff Risberg
 * @since 12/02/11
 */
@Controller
public class LeaderboardController {

    protected static Logger logger = LoggerFactory.getLogger(LeaderboardController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private QuestionCategoryService questionCategoryService;
    @Autowired
    private UserQuestionCategoryScoreService userQuestionCategoryScoreService;

    public LeaderboardController() {
    }

    /**
     * Display the leaderboard
     */
    @RequestMapping(value = "/leaderboard/**", method = RequestMethod.GET)
    public ModelAndView index(Model model, HttpSession session) {

        List<User> userList = userService.findEntityList();
        List<QuestionCategory> questionCategoryList = questionCategoryService.findEntityList();

        List<LeaderboardEntry> leaderboardEntries = new ArrayList<LeaderboardEntry>();

        for (User user : userList) {
            if (user.isTemporary() == false) {
                List<UserQuestionCategoryScore> qcScoreList = userQuestionCategoryScoreService
                        .findEntityList(user);
                int totalScore = 0;

                for (UserQuestionCategoryScore qcScore : qcScoreList) {
                    totalScore += qcScore.getScore();
                }

                LeaderboardEntry leaderboardEntry = new LeaderboardEntry(user, totalScore);
                leaderboardEntries.add(leaderboardEntry);
            }
        }

        Collections.sort(leaderboardEntries);
        // prune to 10 max

        ModelAndView modelAndView = new ModelAndView("leaderboard/index");
        modelAndView.addObject("leaderboardEntries", leaderboardEntries);
        modelAndView.addObject("questionCategoryList", questionCategoryList);

        return modelAndView;
    }
}
