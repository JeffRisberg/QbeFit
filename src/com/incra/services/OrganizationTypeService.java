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

import com.incra.domain.OrganizationType;

/**
 * The <i>OrganizationTypeService</i> handles the Hibernate-session based
 * updating of OrganizationType entities.
 * 
 * @author Jeffrey Risberg
 * @since 09/10/11
 */
@Service
@Transactional
@Repository
public class OrganizationTypeService {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public List<OrganizationType> findEntityList() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(OrganizationType.class);

        return criteria.list();
    }

    public OrganizationType findEntityById(int id) {
        Session session = sessionFactory.getCurrentSession();

        return (OrganizationType) session.get(OrganizationType.class, id);
    }

    public OrganizationType findEntityByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(OrganizationType.class);

        criteria.add(Restrictions.eq("name", name));

        return (OrganizationType) criteria.uniqueResult();
    }

    public void save(OrganizationType organizationType) {
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(organizationType);
    }

    public void delete(OrganizationType organizationType) {
        Session session = sessionFactory.getCurrentSession();

        session.delete(organizationType);
    }
}
