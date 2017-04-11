package br.com.vitrinedecristal.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static Pattern pattern = Pattern.compile(EMAIL_PATTERN);

	/**
	 * Validate email with regular expression
	 * 
	 * @param email
	 * @return true valid email, false invalid email
	 */
	public static boolean validate(final String mail) {
		Boolean result = false;
		if (mail != null && mail.length() > 0) {
			Matcher matcher = pattern.matcher(mail);
			result = matcher.matches();
		}
		return result;
	}

}