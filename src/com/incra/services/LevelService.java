package com.incra.services;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incra.domain.Level;

/**
 * The <i>LevelService</i> handles the Hibernate-session based updating of Level
 * entities.
 * 
 * @author Jeffrey Risberg
 * @since 09/11/11
 */
@Service
@Transactional
@Repository
public class LevelService {

    @Autowired
    private SessionFactory sessionFactory;

    public Level computeLevel(int points) {
        List<Level> levels = findEntityList();
        Level result = levels.get(0);

        for (Level level : levels) {
            if (level.getPoints() > points) {
                break;
            }

            result = level;
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public List<Level> findEntityList() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Level.class);
        criteria.addOrder(Order.asc("points"));

        return criteria.list();
    }

    public Level findEntityById(int id) {
        Session session = sessionFactory.getCurrentSession();

        return (Level) session.get(Level.class, id);
    }

    public Level findEntityByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Level.class);

        criteria.add(Restrictions.eq("name", name));

        return (Level) criteria.uniqueResult();
    }

    public void save(Level level) {
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(level);
    }

    public void delete(Level level) {
        Session session = sessionFactory.getCurrentSession();

        session.delete(level);
    }
}
