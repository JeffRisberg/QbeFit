package com.incra.services;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incra.domain.LogEntry;

/**
 * The <i>LogEntryService</i> handles all logging operations.
 * 
 * @author Jeffrey Risberg
 * @since 01/26/11
 */
@Service
@Transactional
@Repository
public class LogEntryService {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public List<LogEntry> findEntityList() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(LogEntry.class);

        return criteria.list();
    }

    public LogEntry findEntityById(int id) {
        Session session = sessionFactory.getCurrentSession();

        return (LogEntry) session.get(LogEntry.class, id);
    }
}
