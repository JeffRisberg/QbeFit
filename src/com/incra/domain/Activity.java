package com.incra.domain;

/**
 * The <i>Activity</i> entity describes one action that can be selected and tracked.
 * 
 * @author Jeff Risberg
 * @since 09/10/11
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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.incra.domain.util.Objects;

@Entity
@Table(name = "activity", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
public class Activity extends AbstractDomain implements Serializable {
    public static final long serialVersionUID = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "activityCategory_id", nullable = true)
    private ActivityCategory activityCategory;

    @Column(unique = true)
    @Size(min = 2, max = 250, message = "The name must be at least two chars long.")
    private String name;

    // 1=easy, 4=hard
    @Min(1)
    @Max(4)
    private int difficulty;

    @Column(nullable = true)
    private String photo1;

    @Column(nullable = true)
    private String photo2;

    @Size(min = 0, max = 255, message = "The description must be less than 255 chars long.")
    private String description;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Activity) {
            Activity that = (Activity) obj;
            return Objects.equal(name, that.name);
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Activity[name=" + name);
        sb.append(", difficulty=" + difficulty);
        sb.append("]");
        return sb.toString();
    }
}
