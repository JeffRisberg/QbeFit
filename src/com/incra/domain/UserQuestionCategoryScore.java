package com.incra.domain;

/**
 * The <i>UserQuestionCategoryScore</i> entity holds the portion of the user's score that is for a given
 * QuestionCategory.  For instance, Water:150 points, or Energy:300 points.
 * 
 * @author Jeff Risberg
 * @since 11/25/11
 */
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_question_category_score")
public class UserQuestionCategoryScore extends AbstractDomain implements Serializable {
    public static final long serialVersionUID = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "questionCategory_id")
    private QuestionCategory questionCategory;

    private int score;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public QuestionCategory getQuestionCategory() {
        return questionCategory;
    }

    public void setQuestionCategory(QuestionCategory questionCategory) {
        this.questionCategory = questionCategory;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("UserQuestionCategoryScore[user=" + user);
        sb.append(", questionCategory=" + questionCategory.getName());
        sb.append(", score=" + score);
        sb.append("]");
        return sb.toString();
    }
}
