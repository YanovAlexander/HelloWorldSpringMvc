package ua.com.struts.interceptors;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.util.ValueStack;
import org.apache.commons.lang3.StringUtils;
import ua.com.struts.actions.Authentication;
import ua.com.struts.utils.AuthenticationConstants;
import ua.com.struts.utils.Passwords;

public class PasswordValidationInterceptor implements Interceptor {
    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        ValueStack stack = invocation.getStack();
        String password = stack.findString(AuthenticationConstants.PASSWORD);
        if (StringUtils.isEmpty(password)) {
            return Action.ERROR;
        }

        if (password.matches(AuthenticationConstants.PASSWORD_PATTERN)) {
            if (invocation.getAction() instanceof Authentication) {
                Authentication authentication = (Authentication) invocation.getAction();
                authentication.setPassword(Passwords.encryptPassword(password));
            }

            return invocation.invoke();
        }

        return AuthenticationConstants.PASSWORD_SECURITY_ERROR;
    }
}
