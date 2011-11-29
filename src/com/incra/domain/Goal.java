package com.incra.domain;

/**
 * The <i>Goal</i> entity describes a goal such as "cardio fitness", and has a name, description, and
 * a set of activities.
 * 
 * @author Jeff Risberg
 * @since 09/10/11
 */
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import com.incra.domain.util.Objects;

@Entity
@Table(name = "goal", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
public class Goal extends AbstractDomain implements Serializable {
    public static final long serialVersionUID = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "goal")
    Set<GoalActivity> activities = new HashSet<GoalActivity>();

    @Size(min = 2, max = 80, message = "The name must be at least two chars long.")
    private String name;

    @Size(min = 2, max = 255, message = "The description must be at least two chars long.")
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<GoalActivity> getActivities() {
        return activities;
    }

    public void setActivities(Set<GoalActivity> activities) {
        this.activities = activities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addActivity(GoalActivity activity) {
        activity.setGoal(this);
        activities.add(activity);
    }

    public void removeActivity(GoalActivity activity) {
        activity.setGoal(null);
        activities.remove(activity);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Goal) {
            Goal that = (Goal) obj;
            return Objects.equal(name, that.name);
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Goal[name=").append(name);
        sb.append("]");
        return sb.toString();
    }
}
