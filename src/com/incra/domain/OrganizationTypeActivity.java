package com.incra.domain;

/**
 * The <i>OrganizationTypeActivity</i> indicates an activity that is applicable to a given organizationType.
 * 
 * @author Jeff Risberg
 * @since 11/13/11
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
@Table(name = "organizationType_activity")
public class OrganizationTypeActivity extends AbstractDomain implements Serializable {
    public static final long serialVersionUID = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "organizationType_id")
    private OrganizationType organizationType;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrganizationType getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(OrganizationType organizationType) {
        this.organizationType = organizationType;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("OrganizationType[orgType=" + organizationType);
        sb.append(", activity=" + activity.getName());
        sb.append("]");
        return sb.toString();
    }
}
