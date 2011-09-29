package com.incra.domain;

/**
 * The <i>UserChallenge</i> entity records that a user signed up for a challenge.  The dateCreated 
 * field is the date of sign-up.
 * 
 * @author Jeff Risberg
 * @since 09/11/11
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
@Table(name = "user_challenge")
public class UserChallenge extends AbstractDomain implements Serializable {
    public static final long serialVersionUID = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

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

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("UserChallenge[user=" + user);
        sb.append(", challenge=" + challenge.getName());
        sb.append("]");
        return sb.toString();
    }
}
