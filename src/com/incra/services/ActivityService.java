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

import com.incra.domain.Activity;

/**
 * The <i>ActivityService</i> handles the Hibernate-session based updating of
 * Activity entities.
 * 
 * @author Jeffrey Risberg
 * @since 09/10/11
 */
@Service
@Transactional
@Repository
public class ActivityService {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<Activity> findEntityList() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Activity.class);

		return criteria.list();
	}

	public Activity findEntityById(int id) {
		Session session = sessionFactory.getCurrentSession();

		return (Activity) session.get(Activity.class, id);
	}

	public Activity findEntityByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Activity.class);

		criteria.add(Restrictions.eq("name", name));

		return (Activity) criteria.uniqueResult();
	}

	public void save(Activity activity) {
		Session session = sessionFactory.getCurrentSession();

		session.saveOrUpdate(activity);
	}

	public void delete(Activity activity) {
		Session session = sessionFactory.getCurrentSession();

		session.delete(activity);
	}
}
