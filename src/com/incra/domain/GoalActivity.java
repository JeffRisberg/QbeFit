package com.incra.domain;

/**
 * The <i>GoalActivity</i> indicates an activity that is applicable to a given goal, with a level
 * of impact multiplier (1x, 2x, 3x, ...).
 * 
 * @author Jeff Risberg
 * @since 11/25/11
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
@Table(name = "goal_activity")
public class GoalActivity extends AbstractDomain implements Serializable {
    public static final long serialVersionUID = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "goal_id")
    private Goal goal;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    public int multiplier;

    public GoalActivity() {
        this.multiplier = 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("GoalActivity[goal=" + goal.getName());
        sb.append(", activity=" + activity.getName());
        sb.append(", multiplier=" + multiplier);
        sb.append("]");
        return sb.toString();
    }
}
