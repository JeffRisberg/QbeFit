package com.incra.domain;

/**
 * The <i>Quiz</i> entity describes the list of activity questions.
 * 
 * @author Jeff Risberg
 * @since 09/10/11
 */
import java.io.Serializable;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.incra.domain.enums.EnumFieldType;
import com.incra.domain.util.Objects;

@Entity
@Table(name = "quiz")
public class Quiz extends AbstractDomain implements Serializable {
    public static final long serialVersionUID = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "activityCategory_id")
    private ActivityCategory activityCategory;

    @Size(min = 0, max = 255)
    private String question;
   
    private EnumFieldType fieldType;
    
    @Size(min = 0, max = 500, message = "answer")
    private String answer;

   	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ActivityCategory getActivityCategory() {
		return activityCategory;
	}

	public void setActivityCategory(ActivityCategory activityCategory) {
		this.activityCategory = activityCategory;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}	

	public EnumFieldType getFieldType() {
		return fieldType;
	}

	public void setFieldType(EnumFieldType fieldType) {
		this.fieldType = fieldType;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	

}
