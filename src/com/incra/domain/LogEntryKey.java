package com.incra.domain;

/**
 * The <i>LogEntryKey</i> entity
 * 
 * @author Jeff Risberg
 * @since 09/10/11
 */
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import com.incra.domain.util.Objects;

@Entity
@Table(name = "log_entry_key", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
public class LogEntryKey extends AbstractDomain implements Serializable {
    public static final long serialVersionUID = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

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

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LogEntryKey) {
            LogEntryKey that = (LogEntryKey) obj;
            return Objects.equal(name, that.name);
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Challenge[name=" + name);
        sb.append("]");
        return sb.toString();
    }
}
