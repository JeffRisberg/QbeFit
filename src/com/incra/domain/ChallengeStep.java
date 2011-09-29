package com.incra.domain;

/**
 * The <i>ChallengeStep</i> entity
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
import javax.validation.constraints.Size;

import com.incra.domain.util.Objects;

@Entity
@Table(name = "challenge_step", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
public class ChallengeStep extends AbstractDomain implements Serializable {
    public static final long serialVersionUID = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "challenge_id", nullable = true)
    private Challenge challenge;

    @Size(min = 2, max = 255, message = "The name must be at least two chars long.")
    private String name;

    private int seqNum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(int seqNum) {
        this.seqNum = seqNum;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ChallengeStep) {
            ChallengeStep that = (ChallengeStep) obj;
            return Objects.equal(name, that.name);
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("ChallengeStep[name=" + name);
        sb.append(", seqNum=" + seqNum);
        sb.append("]");
        return sb.toString();
    }
}
