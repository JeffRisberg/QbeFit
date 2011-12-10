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
import com.incra.domain.UserQuestionCategoryScore;

/**
 * The <i>UserQuestionCategoryScoreService</i> handles the Hibernate-session
 * based updating of UserQuestionCategoryScore entities.
 * 
 * @author Jeffrey Risberg
 * @since 12/06/11
 */
@Service
@Transactional
@Repository
public class UserQuestionCategoryScoreService {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public List<UserQuestionCategoryScore> findEntityList() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(UserQuestionCategoryScore.class);

        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    public List<UserQuestionCategoryScore> findEntityList(User user) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(UserQuestionCategoryScore.class);

        criteria.add(Restrictions.eq("user", user));

        return criteria.list();
    }

    public UserQuestionCategoryScore findEntityById(int id) {
        Session session = sessionFactory.getCurrentSession();

        return (UserQuestionCategoryScore) session.get(UserQuestionCategoryScore.class, id);
    }

    public void save(UserQuestionCategoryScore userQuestionCategoryScore) {
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(userQuestionCategoryScore);
    }

    public void delete(UserQuestionCategoryScore userQuestionCategoryScore) {
        Session session = sessionFactory.getCurrentSession();

        session.delete(userQuestionCategoryScore);
    }
}
