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

import com.incra.domain.User;
import com.incra.domain.UserActivity;

/**
 * The <i>UserActivityService</i> handles the Hibernate-session based updating
 * of UserUserActivity entities.
 * 
 * @author Jeffrey Risberg
 * @since 09/10/11
 */
@Service
@Transactional
@Repository
public class UserActivityService {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<UserActivity> findEntityList() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserActivity.class);

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<UserActivity> findEntityList(User user) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserActivity.class);

		criteria.add(Restrictions.eq("user", user));

		return criteria.list();
	}

	public UserActivity findEntityById(int id) {
		Session session = sessionFactory.getCurrentSession();

		return (UserActivity) session.get(UserActivity.class, id);
	}

	public UserActivity findEntityByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserActivity.class);

		criteria.add(Restrictions.eq("name", name));

		return (UserActivity) criteria.uniqueResult();
	}

	public void save(UserActivity userActivity) {
		Session session = sessionFactory.getCurrentSession();

		session.saveOrUpdate(userActivity);
	}

	public void delete(UserActivity userActivity) {
		Session session = sessionFactory.getCurrentSession();

		session.delete(userActivity);
	}
}
