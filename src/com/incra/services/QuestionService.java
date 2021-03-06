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

import com.incra.domain.Question;
import com.incra.domain.QuestionCategory;

/**
 * The <i>QuestionService</i> handles the Hibernate-session based updating of
 * Question entities.
 * 
 * @author Jeff Risberg
 * @since 11/26/11
 */
@Service
@Transactional
@Repository
public class QuestionService {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public List<Question> findEntityList() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Question.class);

        return criteria.list();
    }

    public Question findEntityById(int id) {
        Session session = sessionFactory.getCurrentSession();

        return (Question) session.get(Question.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Question> findEntityListByQuestionCategory(QuestionCategory questionCategory) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Question.class);

        criteria.add(Restrictions.eq("questionCategory", questionCategory));

        return criteria.list();
    }

    public void save(Question quiz) {
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(quiz);
    }

    public void delete(Question quiz) {
        Session session = sessionFactory.getCurrentSession();

        session.delete(quiz);
    }
}
