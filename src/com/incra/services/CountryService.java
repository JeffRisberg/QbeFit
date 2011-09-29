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

import com.incra.domain.Country;

/**
 * The <i>CountryService</i> handles the Hibernate-session based updating of
 * Country entities.
 * 
 * @author Jeffrey Risberg
 * @since 01/26/11
 */
@Service
@Transactional
@Repository
public class CountryService {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public List<Country> findEntityList() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Country.class);

        return criteria.list();
    }

    public Country findEntityById(String isoAlpha2) {
        Session session = sessionFactory.getCurrentSession();

        return (Country) session.get(Country.class, isoAlpha2);
    }

    public Country findEntityByIsoNumeric(int isoNumeric) {
        Session session = sessionFactory.getCurrentSession();

        Criteria criteria = session.createCriteria(Country.class);

        criteria.add(Restrictions.eq("isoNumeric", isoNumeric));

        return (Country) criteria.uniqueResult();
    }

    public Country findEntityByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Country.class);

        criteria.add(Restrictions.eq("name", name));

        return (Country) criteria.uniqueResult();
    }

    public void save(Country country) {
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(country);
    }

    public void delete(Country country) {
        Session session = sessionFactory.getCurrentSession();

        session.delete(country);
    }
}
