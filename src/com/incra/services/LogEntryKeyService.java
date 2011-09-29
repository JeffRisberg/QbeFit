package com.incra.services;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incra.domain.LogEntryKey;

/**
 * The <i>LogEntryKeyService</i> handles all logging operations.
 * 
 * @author Jeffrey Risberg
 * @since 01/26/11
 */
@Service
@Transactional
@Repository
public class LogEntryKeyService {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public List<LogEntryKey> findEntityList() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(LogEntryKey.class);

        return criteria.list();
    }

    public LogEntryKey findEntityById(int id) {
        Session session = sessionFactory.getCurrentSession();

        return (LogEntryKey) session.get(LogEntryKey.class, id);
    }
}
