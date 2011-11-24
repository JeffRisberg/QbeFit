package com.incra.domain;

/**
 * The <i>OrganizationType</i> is a classifier for organizations.  Actually it is attached to users for now.
 * 
 * @author Jeff Risberg
 * @since 11/13/11
 */
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.incra.domain.util.Objects;

@Entity
@Table(name = "organization_type")
public class OrganizationType extends AbstractDomain implements Serializable {
    public static final long serialVersionUID = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organizationType")
    Set<OrganizationTypeActivity> activities = new HashSet<OrganizationTypeActivity>();

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<OrganizationTypeActivity> getActivities() {
        return activities;
    }

    public void setActivities(Set<OrganizationTypeActivity> activities) {
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

    public void addActivity(OrganizationTypeActivity activity) {
        activity.setOrganizationType(this);
        activities.add(activity);
    }

    public void removeActivity(OrganizationTypeActivity activity) {
        activity.setOrganizationType(null);
        activities.remove(activity);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OrganizationType) {
            OrganizationType that = (OrganizationType) obj;

            return Objects.equal(name, that.name);
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("OrganizationType[name=" + name);
        sb.append("]");
        return sb.toString();
    }
}
