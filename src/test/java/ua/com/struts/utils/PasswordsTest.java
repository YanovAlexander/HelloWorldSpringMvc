package ua.com.struts.utils;

import org.junit.Test;
import java.security.NoSuchAlgorithmException;
import static org.junit.Assert.assertEquals;

public class PasswordsTest {

    @Test
    public void testEncryptPasswordWithArguments() throws NoSuchAlgorithmException {
        String password = "password";

        String encrypted = Passwords.encryptPassword(password);

        assertEquals("5F4DCC3B5AA765D61D8327DEB882CF99", encrypted);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEncryptEmptyPassword() throws NoSuchAlgorithmException {
        Passwords.encryptPassword(null);
    }
}
