package com.incra.domain;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.incra.domain.util.Objects;

/**
 * The <i>UserOption</i> entity...
 * 
 * @author Jeff Risberg
 * @since 02/15/11
 */
@Entity
@Table(name = "user_option")
public class UserOption implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	protected User user;

	private String name;

	private String strValue;
	private Integer intValue;
	private Boolean boolValue;

	private Date dateCreated;
	private Date lastUpdated;

	/** Default Constructor */
	public UserOption() {
	}

	/** Constructor */
	public UserOption(User user, String name) {
		this.user = user;
		this.name = name;
		this.dateCreated = new Date(System.currentTimeMillis());
		this.lastUpdated = new Date(System.currentTimeMillis());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStrValue() {
		return strValue;
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}

	public Integer getIntValue() {
		return intValue;
	}

	public void setIntValue(Integer intValue) {
		this.intValue = intValue;
	}

	public Boolean getBoolValue() {
		return boolValue;
	}

	public void setBoolValue(Boolean boolValue) {
		this.boolValue = boolValue;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name) + Objects.hashCode(user);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserOption) {
			UserOption that = (UserOption) obj;

			return Objects.equal(name, that.name)
					&& Objects.equal(user, that.user);
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("UserOption[user=").append(user);
		sb.append(", name=").append(name).append("]");
		return sb.toString();
	}
}
