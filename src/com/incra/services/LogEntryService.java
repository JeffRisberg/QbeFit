package com.incra.services;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incra.domain.LogEntry;
import com.incra.domain.LogEntryKey;
import com.incra.domain.User;

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
    @Autowired
    private LogEntryKeyService logEntryKeyService;

    // BUSINESS LOGIC

    public LogEntry publish(String keyStr, User user, List<String> parameters) {
        LogEntryKey key = logEntryKeyService.findEntityByName(keyStr);
        if (key == null) {
            key = new LogEntryKey(keyStr);
            logEntryKeyService.save(key);
        }

        LogEntry logEntry = new LogEntry();
        logEntry.setLogEntryKey(key);
        logEntry.setUser(user);
        logEntry.setDateCreated(new Date());

        save(logEntry);
        return logEntry;
    }

    // PERSISTENCE METHODS

    @SuppressWarnings("unchecked")
    public List<LogEntry> findEntityList() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(LogEntry.class);
        criteria.addOrder(Order.desc("dateCreated"));
        criteria.setFetchSize(50);

        return criteria.list();
    }

    public LogEntry findEntityById(int id) {
        Session session = sessionFactory.getCurrentSession();

        return (LogEntry) session.get(LogEntry.class, id);
    }

    public void save(LogEntry logEntry) {
        Session session = sessionFactory.getCurrentSession();

        session.save(logEntry);
    }
}
