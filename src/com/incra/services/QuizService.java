package com.incra.services;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incra.domain.ActivityCategory;
import com.incra.domain.Quiz;
import com.incra.domain.OrganizationType;
import com.incra.domain.OrganizationTypeActivity;
import com.incra.domain.Quiz;

/**
 *
 */
@Service
@Transactional
@Repository
public class QuizService {

    @Autowired
    private SessionFactory sessionFactory;
    

    @SuppressWarnings("unchecked")
    public List<Quiz> findEntityListByActivityCategory(ActivityCategory activityCategory) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Quiz.class);
           
        criteria.add(Restrictions.eq("activityCategory", activityCategory));        
        
        return criteria.list();
    }

    public void save(Quiz quiz) {
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(quiz);
    }

    public void delete(Quiz quiz) {
        Session session = sessionFactory.getCurrentSession();

        session.delete(quiz);
    }
}
