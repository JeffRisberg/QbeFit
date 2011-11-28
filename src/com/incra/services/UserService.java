package com.incra.services;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incra.domain.User;
import com.incra.services.dto.MyUserDetails;

/**
 * The <i>UserService</i> handles the Hibernate-session based updating of User
 * entities.
 * 
 * @author Jeffrey Risberg
 * @since 02/15/11
 */
@Service
@Transactional
@Repository
public class UserService {

    @Autowired
    private SessionFactory sessionFactory;

    public User getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return null;
        }
        Authentication authentication = context.getAuthentication();
        if (authentication == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal != null && principal instanceof MyUserDetails) {
            MyUserDetails userDetail = (MyUserDetails) principal;

            return findEntityById(userDetail.getUserId());
        }
        return null;
    }

    /**
     * Create a User record that contains dummy data. The temporary user record
     * provides a way for a non-registered user to try out the site. A temporary
     * user record must be persisted into the database in order for activities
     * to be attached to it.
     */
    public User createTemporaryUser() {
        long timeNow = System.currentTimeMillis();

        User user = new User();
        user.setEmail("dummy" + timeNow + "@dummy.com");
        user.setFirstName("dummyF");
        user.setLastName("dummyL");
        user.setPassword("dummyP");
        user.setTemporary(true);
        user.setSplashScreenShown(false);

        save(user);
        return user;
    }

    /**
     * Mimic the steps associated with authentication using Spring Security.
     * This is performed after a new user registers, for example.
     */
    public void performProgrammaticLogin(User user) {
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        if (user.isTemporary()) {
            authList.add(new GrantedAuthorityImpl("ROLE_TEMP"));
        } else {
            authList.add(new GrantedAuthorityImpl("ROLE_USER"));
        }

        int userId = user.getId();
        String fullName = user.getFirstName() + " " + user.getLastName();
        UserDetails userDetails = new MyUserDetails(user.getEmail(), user.getPassword(), false,
                true, true, true, authList, userId, fullName, user.getEmail());

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                        userDetails.getAuthorities()));
    }

    @SuppressWarnings("unchecked")
    public List<User> findEntityList() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(User.class);

        return criteria.list();
    }

    public User findEntityById(int id) {
        Session session = sessionFactory.getCurrentSession();

        return (User) session.get(User.class, id);
    }

    public User findEntityByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(User.class);

        criteria.add(Restrictions.eq("name", name));

        return (User) criteria.uniqueResult();
    }

    public void save(User user) {
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(user);
    }

    public void delete(User user) {
        Session session = sessionFactory.getCurrentSession();

        session.delete(user);
    }
}
