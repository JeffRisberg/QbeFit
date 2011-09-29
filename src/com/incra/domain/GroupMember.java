package com.incra.domain;

/**
 * The <i>GroupMember</i> entity
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

@Entity
@Table(name = "group_member")
public class GroupMember extends AbstractDomain implements Serializable {
	public static final long serialVersionUID = 0L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "group_id")
	private UserGroup group;

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

	public UserGroup getGroup() {
		return group;
	}

	public void setGroup(UserGroup group) {
		this.group = group;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("GroupMember[user=" + user);
		sb.append(", group=" + group);
		sb.append("]");
		return sb.toString();
	}
}
