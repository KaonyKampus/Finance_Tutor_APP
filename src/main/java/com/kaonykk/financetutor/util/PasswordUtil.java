package com.kaonykk.financetutor.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

	public static Object getEntityManagerFactory() {
		throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}

	private PasswordUtil() {
	}

	public static String hash(String plainPassword) {

		return BCrypt.hashpw(plainPassword, BCrypt.gensalt());

	}

	public static boolean verify(String writtenPassword, String hashedPassword) {
		
		return BCrypt.checkpw(writtenPassword, hashedPassword);

	}

}
