package com.incra.domain;

/**
 * The <i>Event</i> entity records when a user performed an activity.
 * 
 * @author Jeff Risberg
 * @since 09/10/11
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.incra.domain.util.Objects;

@Entity
@Table(name = "event")
public class Event extends AbstractDomain implements Serializable {
	public static final long serialVersionUID = 0L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "userActivity_id")
	private UserActivity userActivity;

	private Date eventDate;

	private String note;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserActivity getUserActivity() {
		return userActivity;
	}

	public void setUserActivity(UserActivity userActivity) {
		this.userActivity = userActivity;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(userActivity);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Event) {
			Event that = (Event) obj;

			return Objects.equal(userActivity, that.userActivity);
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Event[userActivity=" + userActivity);
		sb.append(", eventDate=" + eventDate);
		sb.append("]");
		return sb.toString();
	}
}
