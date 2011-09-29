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

import com.incra.domain.Badge;

/**
 * The <i>BadgeService</i> handles the Hibernate-session based updating of Badge
 * entities.
 * 
 * @author Jeffrey Risberg
 * @since 09/11/11
 */
@Service
@Transactional
@Repository
public class BadgeService {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public List<Badge> findEntityList() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Badge.class);

        return criteria.list();
    }

    public Badge findEntityById(int id) {
        Session session = sessionFactory.getCurrentSession();

        return (Badge) session.get(Badge.class, id);
    }

    public Badge findEntityByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Badge.class);

        criteria.add(Restrictions.eq("name", name));

        return (Badge) criteria.uniqueResult();
    }

    public void save(Badge badge) {
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(badge);
    }

    public void delete(Badge badge) {
        Session session = sessionFactory.getCurrentSession();

        session.delete(badge);
    }
}
