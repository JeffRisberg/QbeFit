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

import com.incra.domain.Event;
import com.incra.domain.UserActivity;

/**
 * The <i>EventService</i> handles the Hibernate-session based updating of Event
 * entities.
 * 
 * @author Jeffrey Risberg
 * @since 09/10/11
 */
@Service
@Transactional
@Repository
public class EventService {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<Event> findEntityList() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Event.class);

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Event> findEntityList(UserActivity userActivity) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Event.class);

		criteria.add(Restrictions.eq("userActivity", userActivity));
		return criteria.list();
	}

	public Event findEntityById(int id) {
		Session session = sessionFactory.getCurrentSession();

		return (Event) session.get(Event.class, id);
	}

	public Event findEntityByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Event.class);

		criteria.add(Restrictions.eq("name", name));

		return (Event) criteria.uniqueResult();
	}

	public void save(Event event) {
		Session session = sessionFactory.getCurrentSession();

		session.saveOrUpdate(event);
	}

	public void delete(Event event) {
		Session session = sessionFactory.getCurrentSession();

		session.delete(event);
	}
}
