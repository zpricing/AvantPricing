package cl.zpricing.commons.utils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * SimpleAuthenticator is used to do simple authentication when the SMTP
 * server requires it.
 */
public class SMTPAuthenticator extends Authenticator {
	private String user;
	private String password;
	
	public SMTPAuthenticator() { }
	
	public SMTPAuthenticator(String user, String pass) {
		this.user = user;
		this.password = pass;
	}
	
	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(this.user, this.password);
	}
}
