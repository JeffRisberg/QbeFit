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

import com.incra.domain.Goal;

/**
 * The <i>GoalService</i> handles the Hibernate-session based updating of Goal
 * entities.
 * 
 * @author Jeffrey Risberg
 * @since 09/10/11
 */
@Service
@Transactional
@Repository
public class GoalService {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<Goal> findEntityList() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Goal.class);

		return criteria.list();
	}

	public Goal findEntityById(int id) {
		Session session = sessionFactory.getCurrentSession();

		return (Goal) session.get(Goal.class, id);
	}

	public Goal findEntityByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Goal.class);

		criteria.add(Restrictions.eq("name", name));

		return (Goal) criteria.uniqueResult();
	}

	public void save(Goal goal) {
		Session session = sessionFactory.getCurrentSession();

		session.saveOrUpdate(goal);
	}

	public void delete(Goal goal) {
		Session session = sessionFactory.getCurrentSession();

		session.delete(goal);
	}
}
