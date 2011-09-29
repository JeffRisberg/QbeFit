package com.incra.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incra.domain.User;
import com.incra.services.dto.MyUserDetails;

/**
 * The <i>MyUserDetailsService</i> handles connecting the Spring Security module
 * to the data model.
 * 
 * @author Jeff Risberg
 * @since 09/10/11
 */
@Service("customUserDetailsService")
@Transactional
@Repository
public class MyUserDetailsService implements UserDetailsService {
    private final static Logger logger = Logger.getLogger(MyUserDetailsService.class);

    private DataSource dataSource;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException,
            DataAccessException {

        try {
            if (logger.isInfoEnabled()) {
                logger.info(">>loading user with name=" + username);
            }

            String sql = "select * from user where email like :username";
            MapSqlParameterSource source = new MapSqlParameterSource();
            source.addValue("username", username);

            SimpleJdbcTemplate sjt = new SimpleJdbcTemplate(getDataSource());
            User domainUser = sjt.queryForObject(sql, new UserMapper(), source);

            if (domainUser == null) {
                throw new UsernameNotFoundException("Username " + username + " not found");
            }

            String password = domainUser.getPassword();

            List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
            authList.add(new GrantedAuthorityImpl("ROLE_USER"));

            if (domainUser.isAdmin()) {
                authList.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
            }

            int userId = domainUser.getId();
            String fullName = domainUser.getFirstName() + " " + domainUser.getLastName();
            String email = domainUser.getEmail();

            MyUserDetails user = new MyUserDetails(username, password, !domainUser.isLocked(),
                    true, true, true, authList, userId, fullName, email);

            if (logger.isInfoEnabled()) {
                logger.info("<<returning user " + user);
            }
            return user;
        } catch (DataAccessException dae) {
            logger.warn("<<Can't access data for user " + username);
            throw new DataRetrievalFailureException("Name " + username + " data not accessible");
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("Username " + username + " not found " + e.getLocalizedMessage());
            throw new UsernameNotFoundException("Username " + username + " not found", e);
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /** Inner class to build a User object from a database row */
    private class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int arg1) throws SQLException {
            User result = new User();

            result.setId(rs.getInt("id"));
            result.setEmail(rs.getString("email"));
            result.setFirstName(rs.getString("firstName"));
            result.setLastName(rs.getString("lastName"));
            result.setPassword(rs.getString("password"));
            result.setAdmin(rs.getBoolean("admin"));
            result.setLocked(rs.getBoolean("locked"));

            return result;
        }
    }
}