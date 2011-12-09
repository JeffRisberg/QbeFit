package com.incra.domain;

/**
 * The <i>QuestionCategory</i> is the outer-level classifier for Questions.
 * 
 * @author Jeff Risberg
 * @since 12/06/11
 */
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.incra.domain.util.Objects;

@Entity
@Table(name = "question_category")
public class QuestionCategory extends AbstractDomain implements Serializable {
    public static final long serialVersionUID = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionCategory")
    Set<Question> questions = new HashSet<Question>();

    @Column(unique = true)
    @Size(min = 2, max = 250, message = "The name must be at least two chars long.")
    private String name;

    @Size(min = 2, max = 18)
    private String label;

    @Size(min = 0, max = 255, message = "The description must be less than 255 chars long.")
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addQuestion(Question question) {
        question.setQuestionCategory(this);
        questions.add(question);
    }

    public void removeQuestion(Question question) {
        question.setQuestionCategory(null);
        questions.remove(question);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof QuestionCategory) {
            QuestionCategory that = (QuestionCategory) obj;

            return Objects.equal(name, that.name);
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("QuestionCategory[name=" + name);
        sb.append("]");
        return sb.toString();
    }
}
