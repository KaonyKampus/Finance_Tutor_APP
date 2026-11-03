package com.kaonykk.financetutor.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

	private PasswordUtil() {
	}

	public static String hash(String plainPassword) {

		return BCrypt.hashpw(plainPassword, BCrypt.gensalt());

	}

	public static boolean verify(String writtenPassword, String hashedPassword) {
		
		return BCrypt.checkpw(writtenPassword, hashedPassword);

	}

}
