package com.incra.domain;

/**
 * The <i>UserActivity</i> entity records when a user signed up for an activity, and
 * when the sign-up was effective.
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
@Table(name = "user_activity")
public class UserActivity extends AbstractDomain implements Serializable {
	public static final long serialVersionUID = 0L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "activity_id")
	private Activity activity;

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

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
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
		sb.append("UserActivity[user=" + user);
		sb.append(", activity=" + activity.getName());
		sb.append(", effectivityStart=" + effectivityStart);
		sb.append(", effectivityEnd=" + effectivityEnd);
		sb.append("]");
		return sb.toString();
	}
}
