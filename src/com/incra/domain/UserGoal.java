package com.incra.domain;

/**
 * The <i>UserGoal</i> entity records when the user signed up for a goal, and when
 * the signup was effective.
 * 
 * @author Jeff Risberg
 * @since 09/10/11
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_goal")
public class UserGoal extends AbstractDomain implements Serializable {
	public static final long serialVersionUID = 0L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "goal_id")
	private Goal goal;

	@Column(nullable = false)
	private Date effectivityStart;

	@Column(nullable = true)
	private Date effectivityEnd;

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

	public Goal getGoal() {
		return goal;
	}

	public void setGoal(Goal goal) {
		this.goal = goal;
	}

	public Date getEffectivityStart() {
		return effectivityStart;
	}

	public void setEffectivityStart(Date effectivityStart) {
		this.effectivityStart = effectivityStart;
	}

	public Date getEffectivityEnd() {
		return effectivityEnd;
	}

	public void setEffectivityEnd(Date effectivityEnd) {
		this.effectivityEnd = effectivityEnd;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("UserGoal[user=" + user);
		sb.append(", goal=" + goal.getName());
		sb.append(", effectivityStart=" + effectivityStart);
		sb.append(", effectivityEnd=" + effectivityEnd);
		sb.append("]");
		return sb.toString();
	}
}
