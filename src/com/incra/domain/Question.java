package com.incra.domain;

/**
 * The <i>Question</i> entity describes one question.  Each question is associated with a goal, so
 * that a quiz can be generated for a user, with the questions drawn from the user's goals.
 * 
 * @author Jeff Risberg
 * @since 11/23/11
 */
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.incra.domain.enums.QuestionType;
import com.incra.domain.util.Objects;

@Entity
@Table(name = "question")
public class Question extends AbstractDomain implements Serializable {
    public static final long serialVersionUID = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "goal_id")
    private Goal goal;

    @Size(min = 0, max = 1000)
    private String text;

    private QuestionType questionType;
    private int points;

    @Size(min = 0, max = 1000)
    private String answer;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Question) {
            Question that = (Question) obj;
            return Objects.equal(text, that.text) && Objects.equal(goal, that.goal);
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Question[text=").append(text);
        sb.append(", points=").append(points);
        sb.append("]");
        return sb.toString();
    }
}
