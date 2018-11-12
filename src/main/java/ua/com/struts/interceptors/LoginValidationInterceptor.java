package ua.com.struts.interceptors;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.util.ValueStack;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import ua.com.struts.dao.AuthenticationDao;
import ua.com.struts.dao.impl.AuthenticationDaoImpl;
import ua.com.struts.utils.AuthenticationConstants;
import ua.com.struts.utils.DatabaseException;
import ua.com.struts.utils.Passwords;

public class LoginValidationInterceptor implements Interceptor {
    private static final Logger LOG = Logger.getLogger(LoginValidationInterceptor.class);

    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        ValueStack stack = invocation.getStack();
        String username = stack.findString(AuthenticationConstants.USERNAME);
        String password = stack.findString(AuthenticationConstants.PASSWORD);
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return Action.ERROR;
        }
        AuthenticationDao authenticationDao = new AuthenticationDaoImpl();
        try {
            if (authenticationDao.isUserExist(username, Passwords.encryptPassword(password))) {
                return invocation.invoke();
            }
        } catch (DatabaseException e) {
            LOG.error("execute: connection refused");
            return AuthenticationConstants.CONNECTION_REFUSED;
        } catch (Exception e) {
            LOG.error("execute: Error", e);
        }
        return Action.ERROR;
    }
}
