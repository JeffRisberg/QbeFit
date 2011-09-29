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

import com.incra.domain.ActivityCategory;

/**
 * The <i>ActivityCategoryService</i> handles the Hibernate-session based
 * updating of ActivityCategory entities.
 * 
 * @author Jeffrey Risberg
 * @since 09/10/11
 */
@Service
@Transactional
@Repository
public class ActivityCategoryService {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public List<ActivityCategory> findEntityList() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(ActivityCategory.class);

        return criteria.list();
    }

    public ActivityCategory findEntityById(int id) {
        Session session = sessionFactory.getCurrentSession();

        return (ActivityCategory) session.get(ActivityCategory.class, id);
    }

    public ActivityCategory findEntityByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(ActivityCategory.class);

        criteria.add(Restrictions.eq("name", name));

        return (ActivityCategory) criteria.uniqueResult();
    }

    public void save(ActivityCategory activityCategory) {
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(activityCategory);
    }

    public void delete(ActivityCategory activityCategory) {
        Session session = sessionFactory.getCurrentSession();

        session.delete(activityCategory);
    }
}
