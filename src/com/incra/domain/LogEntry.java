package com.incra.domain;

/**
 * The <i>LogEntry</i> entity records a each user action and a set of parameters.
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

import com.incra.domain.util.Objects;

@Entity
@Table(name = "log_entry")
public class LogEntry extends AbstractDomain implements Serializable {
    public static final long serialVersionUID = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "logEntryKey_id", nullable = true)
    private LogEntryKey logEntryKey;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    private String param1;
    private String param2;
    private String param3;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LogEntryKey getLogEntryKey() {
        return logEntryKey;
    }

    public void setLogEntryKey(LogEntryKey logEntryKey) {
        this.logEntryKey = logEntryKey;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getParam3() {
        return param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(user);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LogEntry) {
            LogEntry that = (LogEntry) obj;
            return Objects.equal(user, that.user);
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("LogEntry[user=" + user);
        sb.append("]");
        return sb.toString();
    }
}
