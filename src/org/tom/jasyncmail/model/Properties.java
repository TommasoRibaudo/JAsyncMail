package org.tom.jasyncmail.model;

import java.io.Serializable;

/**
 *
 * @author Tommaso Ribaudo
 */
public class Properties implements Serializable {

    private static final long serialVersionUID = 1L;
    private static Properties INSTANCE = null;
    private final String email;
    private final String password;
    private final String host;
    private final String port;
    private final String starttls;
    private final String ssl;
    private final String auth;

    /**
     * Other attributes starttls, ssl and auth are set respectively to true,
     * false and true.
     *
     * @param email the email that sends the mail
     * @param password the password of the email
     * @param host the host of the email
     * @param port the port of the email
     */
    public Properties(String email, String password, String host, String port) {
        this.email = email;
        this.password = password;
        this.host = host;
        this.port = port;
        this.starttls = "true";
        this.ssl = "false";
        this.auth = "true";
        INSTANCE = this;
    }

    /**
     *
     * @param email the email that sends the mail
     * @param password the password of the email
     * @param host the host of the email
     * @param port the port of the email
     * @param starttls true if the email uses starttls
     * @param ssl true if the email uses ssl
     * @param auth true if the email uses authentication
     */
    public Properties(String email, String password, String host, String port, boolean starttls, boolean ssl, boolean auth) {
        this.email = email;
        this.password = password;
        this.host = host;
        this.port = port;
        this.starttls = Boolean.toString(starttls);
        this.ssl = Boolean.toString(ssl);
        this.auth = Boolean.toString(auth);
        INSTANCE = this;
    }

    public static Properties getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("Properties not initialized.\n");
        }
        return INSTANCE;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getStarttls() {
        return starttls;
    }

    public String getSsl() {
        return ssl;
    }

    public String getAuth() {
        return auth;
    }

    public java.util.Properties toProperties() {
        java.util.Properties props = new java.util.Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.starttls.enable", starttls);
        props.put("mail.smtp.ssl.enable", ssl);
        props.put("mail.smtp.auth", auth);
        return props;
    }
}
