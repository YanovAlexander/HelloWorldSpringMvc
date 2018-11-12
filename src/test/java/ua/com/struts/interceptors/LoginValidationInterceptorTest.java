package ua.com.struts.interceptors;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.XWorkTestCase;
import com.opensymphony.xwork2.mock.MockActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.ValueStackFactory;
import ua.com.struts.actions.Authentication;
import ua.com.struts.utils.AuthenticationConstants;
import ua.com.struts.utils.Passwords;

public class LoginValidationInterceptorTest extends XWorkTestCase {

    public void setUp() throws Exception {
        super.setUp();
        Authentication authentication = new Authentication();
        authentication.setUsername("alexanderTest123");
        authentication.setPassword(Passwords.encryptPassword("Password1#"));

        authentication.registration();
    }

    public void testLoginWithPasswordIsNull() throws Exception {
        LoginValidationInterceptor interceptor = new LoginValidationInterceptor();
        String username = "errorLogin123";
        MockActionInvocation mockActionInvocation = new MockActionInvocation();
        ValueStack valueStack = container.getInstance(ValueStackFactory.class).createValueStack();
        valueStack.set(AuthenticationConstants.USERNAME, username);
        mockActionInvocation.setStack(valueStack);
        String result = interceptor.intercept(mockActionInvocation);

        assertEquals(Action.ERROR, result);
    }

    public void testLoginWithLoginIsNull() throws Exception {
        LoginValidationInterceptor interceptor = new LoginValidationInterceptor();
        String password = "errorLogin123";
        MockActionInvocation mockActionInvocation = new MockActionInvocation();
        ValueStack valueStack = container.getInstance(ValueStackFactory.class).createValueStack();
        valueStack.set(AuthenticationConstants.PASSWORD, password);
        mockActionInvocation.setStack(valueStack);
        String result = interceptor.intercept(mockActionInvocation);

        assertEquals(Action.ERROR, result);
    }

    public void testLoginWithCorrectData() throws Exception {
        LoginValidationInterceptor interceptor = new LoginValidationInterceptor();
        String username = "alexanderTest123";
        String password = "Password1#";
        MockActionInvocation mockActionInvocation = new MockActionInvocation();
        ValueStack valueStack = container.getInstance(ValueStackFactory.class).createValueStack();
        valueStack.set(AuthenticationConstants.USERNAME, username);
        valueStack.set(AuthenticationConstants.PASSWORD, password);
        mockActionInvocation.setStack(valueStack);
        String result = interceptor.intercept(mockActionInvocation);

        assertNull(result);
    }
}
