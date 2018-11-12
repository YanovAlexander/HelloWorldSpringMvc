package ua.com.struts.utils;

public final class AuthenticationConstants {
    public static final String MD5 = "MD5";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    public static final String HOST = "oyanov-lp";
    public static final String PORT = "5000";
    public static final String DRIVER_CLASSNAME = "net.sourceforge.jtds.jdbc.Driver";

    public static final String DATABASE_USERNAME = "sa";
    public static final String DATABASE_PASSWORD = "123456";

    public static final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}";
    public static final String PASSWORD_SECURITY_ERROR = "passwordNotSecure";
    public static final String LOGGED_IN = "loggedIn";
    public static final String CONNECTION_REFUSED = "connectionRefused";
}
