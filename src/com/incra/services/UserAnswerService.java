package com.incra.services;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incra.domain.User;
import com.incra.domain.UserAnswer;

/**
 * The <i>UserAnswerService</i> handles the Hibernate-session based updating of
 * UserAnswerService entities.
 * 
 * @author Jeffrey Risberg
 * @since 11/26/11
 */
@Service
@Transactional
@Repository
public class UserAnswerService {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public List<UserAnswer> findEntityList() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(UserAnswer.class);

        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    public List<UserAnswer> findEntityList(User user) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(UserAnswer.class);

        criteria.add(Restrictions.eq("user", user));

        return criteria.list();
    }

    public UserAnswer findEntityById(int id) {
        Session session = sessionFactory.getCurrentSession();

        return (UserAnswer) session.get(UserAnswer.class, id);
    }

    public UserAnswer findEntityByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(UserAnswer.class);

        criteria.add(Restrictions.eq("name", name));

        return (UserAnswer) criteria.uniqueResult();
    }

    public void save(UserAnswer userAnswer) {
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(userAnswer);
    }

    public void delete(UserAnswer userAnswer) {
        Session session = sessionFactory.getCurrentSession();

        session.delete(userAnswer);
    }
}
