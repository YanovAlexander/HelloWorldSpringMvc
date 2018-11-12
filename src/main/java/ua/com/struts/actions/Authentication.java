package ua.com.struts.actions;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;
import ua.com.struts.dao.AuthenticationDao;
import ua.com.struts.dao.SybaseConnector;
import ua.com.struts.dao.impl.AuthenticationDaoImpl;
import ua.com.struts.utils.AuthenticationConstants;
import ua.com.struts.utils.DatabaseException;

import java.util.Map;

public class Authentication extends ActionSupport implements SessionAware {
    private static final Logger LOG = Logger.getLogger(Authentication.class);
    private String username;
    private String password;
    private SessionMap<String, Object> sessionMap;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String execute() {
        String user = null;
        if (sessionMap.containsKey(AuthenticationConstants.USERNAME)) {
            user = (String) sessionMap.get(AuthenticationConstants.USERNAME);
        }
        if (!StringUtils.isEmpty(user) && user.equals(username)) {
            return AuthenticationConstants.LOGGED_IN;
        }

        sessionMap.put(AuthenticationConstants.USERNAME, username);
        return Action.SUCCESS;
    }

    public String registration() {
        AuthenticationDao authenticationDao = new AuthenticationDaoImpl();
        try {
            if (authenticationDao.isUserExist(username)) {
                return "userExists";
            }

            authenticationDao.saveUser(username, password);
            return SUCCESS;
        } catch (DatabaseException e) {
            LOG.error("registration: connection refused");
            return AuthenticationConstants.CONNECTION_REFUSED;
        } catch (Exception e) {
            LOG.error("registration: Error", e);
        }
        return ERROR;
    }

    public String logout() {
        SybaseConnector.disconnect();
        sessionMap.invalidate();
        return SUCCESS;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.sessionMap = (SessionMap<String, Object>) session;
    }
}

