package com.incra.services;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incra.domain.UserOption;

/**
 * The <i>UserOptionService</i> provides support for persistence of UserOption
 * entities as well as convenience routines for fetching and creating them.
 * 
 * @author Jeffrey Risberg
 * @since 12/20/10
 */
@Service
@Transactional
@Repository
public class UserOptionService {

    @Autowired
    private SessionFactory sessionFactory;

    /** Return all userOptions */
    @SuppressWarnings("unchecked")
    public List<UserOption> findEntityList() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(UserOption.class);

        return criteria.list();
    }
}
