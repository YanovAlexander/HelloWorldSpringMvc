package ua.com.struts.dao.impl;

import org.apache.log4j.Logger;
import ua.com.struts.dao.AuthenticationDao;
import ua.com.struts.dao.SybaseConnector;
import ua.com.struts.utils.AuthenticationConstants;
import ua.com.struts.utils.DatabaseException;

import java.sql.*;

public class AuthenticationDaoImpl implements AuthenticationDao {
    private static final Logger LOG = Logger.getLogger(AuthenticationDaoImpl.class);

    @Override
    public boolean isUserExist(String username, String  password) {
        boolean status = false;
        try {
            Connection connection = SybaseConnector.connect(AuthenticationConstants.DATABASE_USERNAME, AuthenticationConstants.DATABASE_PASSWORD);

            PreparedStatement ps = connection.prepareStatement(
                    "SELECT username, password FROM users WHERE username = ? AND password = ?");
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet resultSet = ps.executeQuery();
            status = resultSet.next();

            resultSet.close();
            ps.close();
        } catch (SQLException e) {
            LOG.error("isUserExist: unexpected exception : " + e.toString() + ", sqlstate = " + e.getSQLState());
            return status;
        } catch (DatabaseException e) {
            LOG.error("isUserExist: connection refused");
            throw new DatabaseException(e.getMessage(), e);
        } catch (Exception e) {
            LOG.error("isUserExist: Error", e);
            return status;
        }

        return status;
    }

    @Override
    public void saveUser(String username, String userPassword) {
       try {
           Connection connection = SybaseConnector.connect(AuthenticationConstants.DATABASE_USERNAME, AuthenticationConstants.DATABASE_PASSWORD);

           PreparedStatement ps = connection.prepareStatement(
                   "INSERT INTO users(username, password) VALUES (?,?)");
           ps.setString(1, username);
           ps.setString(2, userPassword);
           ps.executeUpdate();

           ps.close();
       } catch (SQLException e) {
           LOG.error("saveUser: unexpected exception : " + e.toString() + ", sqlstate = " + e.getSQLState());
           throw new RuntimeException("saveUser error");
       } catch (DatabaseException e) {
           LOG.error("saveUser: connection refused");
           throw new DatabaseException(e.getMessage(), e);
       } catch (Exception e) {
           LOG.error("saveUser: Error", e);
           throw new RuntimeException("saveUser error");
       }
    }

    @Override
    public boolean isUserExist(String username) {
        boolean status = false;
        try {
            Connection connection = SybaseConnector.connect(AuthenticationConstants.DATABASE_USERNAME, AuthenticationConstants.DATABASE_PASSWORD);

            PreparedStatement ps = connection.prepareStatement(
                    "SELECT username, password FROM users WHERE username = ?");
            ps.setString(1, username);

            ResultSet resultSet = ps.executeQuery();
            status = resultSet.next();

            resultSet.close();
            ps.close();
        } catch (SQLException e) {
            LOG.error("isUserExist: unexpected exception : " + e.toString() + ", sqlstate = " + e.getSQLState());
            return status;
        } catch (DatabaseException e) {
          LOG.error("isUserExist: connection refused");
          throw new DatabaseException(e.getMessage(), e);
        } catch (Exception e) {
            LOG.error("isUserExist: Error", e);
            return status;
        }

        return status;
    }
}
