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
import com.incra.domain.UserGoal;

/**
 * The <i>UserGoalService</i> handles the Hibernate-session based updating of
 * UserUserGoal entities.
 * 
 * @author Jeffrey Risberg
 * @since 09/10/11
 */
@Service
@Transactional
@Repository
public class UserGoalService {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<UserGoal> findEntityList() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserGoal.class);

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<UserGoal> findEntityList(User user) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserGoal.class);

		criteria.add(Restrictions.eq("user", user));

		return criteria.list();
	}

	public UserGoal findEntityById(int id) {
		Session session = sessionFactory.getCurrentSession();

		return (UserGoal) session.get(UserGoal.class, id);
	}

	public UserGoal findEntityByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserGoal.class);

		criteria.add(Restrictions.eq("name", name));

		return (UserGoal) criteria.uniqueResult();
	}

	public void save(UserGoal userGoal) {
		Session session = sessionFactory.getCurrentSession();

		session.saveOrUpdate(userGoal);
	}

	public void delete(UserGoal userGoal) {
		Session session = sessionFactory.getCurrentSession();

		session.delete(userGoal);
	}
}
