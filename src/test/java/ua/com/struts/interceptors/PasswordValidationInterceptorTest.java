package ua.com.struts.interceptors;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.XWorkTestCase;
import com.opensymphony.xwork2.mock.MockActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.ValueStackFactory;
import ua.com.struts.utils.AuthenticationConstants;

public class PasswordValidationInterceptorTest extends XWorkTestCase {

    public void testPasswordNotValid() throws Exception {
        PasswordValidationInterceptor interceptor = new PasswordValidationInterceptor();
        String password = "123";
        MockActionInvocation mockActionInvocation = new MockActionInvocation();
        ValueStack valueStack = container.getInstance(ValueStackFactory.class).createValueStack();
        valueStack.set(AuthenticationConstants.PASSWORD, password);
        mockActionInvocation.setStack(valueStack);
        String result = interceptor.intercept(mockActionInvocation);

        assertEquals(AuthenticationConstants.PASSWORD_SECURITY_ERROR, result);
    }

    public void testPasswordIsValid() throws Exception {
        PasswordValidationInterceptor interceptor = new PasswordValidationInterceptor();
        String password = "Password1#";
        MockActionInvocation mockActionInvocation = new MockActionInvocation();
        ValueStack valueStack = container.getInstance(ValueStackFactory.class).createValueStack();
        valueStack.set(AuthenticationConstants.PASSWORD, password);
        mockActionInvocation.setStack(valueStack);
        String result = interceptor.intercept(mockActionInvocation);

        assertNull(result);
    }

    public void testPasswordIsNotSetInValueStack() throws Exception {
        PasswordValidationInterceptor interceptor = new PasswordValidationInterceptor();
        MockActionInvocation mockActionInvocation = new MockActionInvocation();
        ValueStack valueStack = container.getInstance(ValueStackFactory.class).createValueStack();
        mockActionInvocation.setStack(valueStack);
        String result = interceptor.intercept(mockActionInvocation);

        assertEquals(Action.ERROR, result);
    }

    public void testPasswordIsEmptyString() throws Exception {
        PasswordValidationInterceptor interceptor = new PasswordValidationInterceptor();
        MockActionInvocation mockActionInvocation = new MockActionInvocation();
        ValueStack valueStack = container.getInstance(ValueStackFactory.class).createValueStack();
        valueStack.set("password", "");
        mockActionInvocation.setStack(valueStack);
        String result = interceptor.intercept(mockActionInvocation);

        assertEquals(Action.ERROR, result);
    }
}
