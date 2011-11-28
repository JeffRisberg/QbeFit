package com.incra.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import com.incra.domain.util.Objects;

/**
 * The <i>User</i> entity holds user information. Users are keyed by their email
 * address, and have a password.
 * 
 * @author Jeff Risberg
 * @since 02/09/11
 */
@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = { "email" }))
public class User extends AbstractDomain implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = true)
    private Country country;

    @ManyToOne
    @JoinColumn(name = "organizationType_id", nullable = true)
    private OrganizationType organizationType;

    @ManyToOne
    @JoinColumn(name = "level_id", nullable = true)
    private Level level;

    @ManyToOne
    @JoinColumn(name = "timeZone_id", nullable = true)
    private TimeZone timeZone;

    @Size(min = 2, max = 80, message = "The email must be at least two chars long.")
    private String email;

    @Size(min = 2, max = 80, message = "The firstName must be at least two chars long.")
    private String firstName;

    @Size(min = 2, max = 80, message = "The lastName must be at least two chars long.")
    private String lastName;

    @Size(min = 2, message = "The password must be at least two chars long.")
    private String password;

    @Size(min = 2, message = "The confirm password must be at least two chars long.")
    @Transient
    private String confirmPassword;

    private int points;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserBadge> userBadges;

    private boolean admin;
    private boolean locked;

    private int loginCount;

    private boolean aboutMeInfoGathered;

    @Column(nullable = true)
    private Date lastLoggedIn;

    @Transient
    private boolean temporary;

    @Transient
    private boolean splashScreenShown;

    /** Constructor */
    public User() {
        this.userBadges = new ArrayList<UserBadge>();
        this.temporary = false;
        this.splashScreenShown = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public OrganizationType getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(OrganizationType organizationType) {
        this.organizationType = organizationType;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public List<UserBadge> getUserBadges() {
        return userBadges;
    }

    public void setUserBadges(List<UserBadge> userBadges) {
        this.userBadges = userBadges;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isAboutMeInfoGathered() {
        return aboutMeInfoGathered;
    }

    public void setAboutMeInfoGathered(boolean aboutMeInfoGathered) {
        this.aboutMeInfoGathered = aboutMeInfoGathered;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public Date getLastLoggedIn() {
        return lastLoggedIn;
    }

    public void setLastLoggedIn(Date lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }

    public boolean isTemporary() {
        return temporary;
    }

    public void setTemporary(boolean temporary) {
        this.temporary = temporary;
    }

    public boolean isSplashScreenShown() {
        return splashScreenShown;
    }

    public void setSplashScreenShown(boolean splashScreenShown) {
        this.splashScreenShown = splashScreenShown;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User that = (User) obj;
            return Objects.equal(email, that.email);
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("User[email=" + email);
        sb.append("]");
        return sb.toString();
    }
}
