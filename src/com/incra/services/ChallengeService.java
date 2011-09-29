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

import com.incra.domain.Challenge;

/**
 * The <i>ChallengeService</i> handles the Hibernate-session based updating of
 * Challenge entities.
 * 
 * @author Jeffrey Risberg
 * @since 09/11/11
 */
@Service
@Transactional
@Repository
public class ChallengeService {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public List<Challenge> findEntityList() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Challenge.class);

        return criteria.list();
    }

    public Challenge findEntityById(int id) {
        Session session = sessionFactory.getCurrentSession();

        return (Challenge) session.get(Challenge.class, id);
    }

    public Challenge findEntityByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Challenge.class);

        criteria.add(Restrictions.eq("name", name));

        return (Challenge) criteria.uniqueResult();
    }

    public void save(Challenge challenge) {
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(challenge);
    }

    public void delete(Challenge challenge) {
        Session session = sessionFactory.getCurrentSession();

        session.delete(challenge);
    }
}
