package com.incra.services;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
