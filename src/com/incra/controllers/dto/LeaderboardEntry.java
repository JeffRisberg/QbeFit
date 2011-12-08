package com.incra.controllers.dto;

import com.incra.domain.User;

/**
 * The <i>LeaderboardEntry</i> DTO holds the information for one
 * user/score/image tuple on the Leaderboard.
 * 
 * This object also implements Comparable, so that they can be sorted in the
 * list.
 * 
 * @author Jeffrey Risberg
 * @since 12/05/11
 */
public class LeaderboardEntry implements Comparable<Object> {
    private User user;
    private int score;

    public LeaderboardEntry(User user, int score) {
        this.user = user;
        this.score = score;
    }

    public User getUser() {
        return user;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(Object object) {
        if (object instanceof LeaderboardEntry) {
            LeaderboardEntry other = (LeaderboardEntry) object;

            if (other.score < score)
                return -1;
            if (other.score > score)
                return 1;
        }
        return 0;
    }
}
