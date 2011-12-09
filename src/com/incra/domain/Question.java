package com.incra.domain;

/**
 * The <i>Question</i> entity describes one question.  Each question is associated with a 
 * question category.  Also, Goals are linked to Questions via the GoalQuestion entity, so
 * that a quiz can be generated for a user, with the questions drawn from the user's goals.
 * 
 * @author Jeff Risberg
 * @since 11/23/11
 */
import java.io.Serializable;

import javax.persistence.Column;
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
    @JoinColumn(name = "questionCategory_id")
    private QuestionCategory questionCategory;

    // PriorQuestion must be one with QuestionType=Boolean.
    // If not null, then this is shown only if prior question was answered true.
    @ManyToOne
    @JoinColumn(name = "priorQuestion_id", nullable = true)
    private Question priorQuestion;

    @Size(min = 0, max = 1000)
    private String text;

    private QuestionType questionType;
    private int points;

    @Column(nullable = true)
    private String photo1;

    @Column(nullable = true)
    private String photo2;

    @Size(min = 0, max = 1000)
    private String explanation;

    @Size(min = 0, max = 1000)
    private String answer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public QuestionCategory getQuestionCategory() {
        return questionCategory;
    }

    public void setQuestionCategory(QuestionCategory questionCategory) {
        this.questionCategory = questionCategory;
    }

    public Question getPriorQuestion() {
        return priorQuestion;
    }

    public void setPriorQuestion(Question priorQuestion) {
        this.priorQuestion = priorQuestion;
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

    public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text) + Objects.hashCode(questionCategory);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Question) {
            Question that = (Question) obj;
            return Objects.equal(text, that.text)
                    && Objects.equal(questionCategory, that.questionCategory);
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
