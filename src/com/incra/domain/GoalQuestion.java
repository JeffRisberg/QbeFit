package com.incra.domain;

/**
 * The <i>GoalQuestion</i> entity represents a question that is applicable to a given goal, with a level
 * of impact multiplier (1x, 2x, 3x, ...).
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
@Table(name = "goal_question")
public class GoalQuestion extends AbstractDomain implements Serializable {
    public static final long serialVersionUID = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "goal_id")
    private Goal goal;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public int multiplier;

    public GoalQuestion() {
        this.multiplier = 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("GoalQuestion[goal=" + goal.getName());
        sb.append(", question=" + question.getText());
        sb.append(", multiplier=" + multiplier);
        sb.append("]");
        return sb.toString();
    }
}
