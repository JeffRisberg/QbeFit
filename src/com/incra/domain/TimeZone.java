package com.incra.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The <i>TimeZone</i> entity ...
 * 
 * @author Jeff Risberg
 * @since early 2009
 */
@Entity
@Table(name = "time_zone")
public class TimeZone implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", length = 5)
    private String id;

    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = "relativeUTC", nullable = false)
    private int relativeUTC = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRelativeUTC() {
        return relativeUTC;
    }

    public void setRelativeUTC(int relativeUTC) {
        this.relativeUTC = relativeUTC;
    }
}
