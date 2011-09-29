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

import com.incra.domain.TimeZone;

/**
 * The <i>TimeZoneService</i> handles the Hibernate-session based updating of
 * TimeZone entities.
 * 
 * @author Jeffrey Risberg
 * @since 01/24/11
 */
@Service
@Transactional
@Repository
public class TimeZoneService {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public List<TimeZone> findEntityList() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(TimeZone.class);

        return criteria.list();
    }

    public TimeZone findEntityById(int id) {
        Session session = sessionFactory.getCurrentSession();

        return (TimeZone) session.get(TimeZone.class, id);
    }

    public TimeZone findEntityByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(TimeZone.class);

        criteria.add(Restrictions.eq("name", name));

        return (TimeZone) criteria.uniqueResult();
    }

    public void save(TimeZone timeZone) {
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(timeZone);
    }

    public void delete(TimeZone timeZone) {
        Session session = sessionFactory.getCurrentSession();

        session.delete(timeZone);
    }
}
