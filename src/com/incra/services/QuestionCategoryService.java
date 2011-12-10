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

import com.incra.domain.QuestionCategory;

/**
 * The <i>QuestionCategoryService</i> handles the Hibernate-session based
 * updating of QuestionCategory entities.
 * 
 * @author Jeffrey Risberg
 * @since 12/06/11
 */
@Service
@Transactional
@Repository
public class QuestionCategoryService {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public List<QuestionCategory> findEntityList() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(QuestionCategory.class);

        return criteria.list();
    }

    public QuestionCategory findEntityById(int id) {
        Session session = sessionFactory.getCurrentSession();

        return (QuestionCategory) session.get(QuestionCategory.class, id);
    }

    public QuestionCategory findEntityByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(QuestionCategory.class);

        criteria.add(Restrictions.eq("name", name));

        return (QuestionCategory) criteria.uniqueResult();
    }

    public void save(QuestionCategory questionCategory) {
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(questionCategory);
    }

    public void delete(QuestionCategory questionCategory) {
        Session session = sessionFactory.getCurrentSession();

        session.delete(questionCategory);
    }
}
