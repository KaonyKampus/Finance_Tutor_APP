package com.kaonykk.financetutor.service;

import com.kaonykk.financetutor.dao.UserDAO;
import com.kaonykk.financetutor.exception.InvalidCredentialsException;
import com.kaonykk.financetutor.model.entity.User;
import com.kaonykk.financetutor.model.enums.UserStatus;
import com.kaonykk.financetutor.util.PasswordUtil;

public class AuthService {

	private final UserDAO userDAO = new UserDAO();

	public User login(String email, String password) {

		if (email == null || email.isBlank() || password == null || password.isBlank()) {

			throw new InvalidCredentialsException("Correo y contraseña son obligatorios");

		}

		email = email.trim().toLowerCase();
		User user = userDAO.findByEmail(email);
		if (user == null) {
			throw new InvalidCredentialsException("Correo o contraseña incorrectos");
		}
		if (!PasswordUtil.verify(password, user.getPasswordHash())) {
			throw new InvalidCredentialsException("Correo o contraseña incorrectos");

		}

		if (user.getStatus() != UserStatus.ACTIVE) {
			throw new InvalidCredentialsException("Usuario inactivo o bloqueado");
		}

		return user;

	}
}
