package com.kaonykk.financetutor.service;

import com.kaonykk.financetutor.dao.UserDAO;
import com.kaonykk.financetutor.dto.UserRegistrationDTO;
import com.kaonykk.financetutor.exception.DuplicateEmailException;
import com.kaonykk.financetutor.exception.ValidationException;

public class UserService {

	private final UserDAO userDAO = new UserDAO();

	public void register(UserRegistrationDTO dto) {

		String name = dto.getName();
		String email = dto.getEmail();
		String role = dto.getRole();
		String password = dto.getPassword();

		if (name == null || name.isBlank() || email == null || email.isBlank() || role == null || role.isBlank() || password == null || password.isBlank()) {
			throw new ValidationException("Todos los campos son obligatorios");
		}
		if (password.length() < 8) {

			throw new ValidationException("La contraseña debe tener al menos 8 caracteres");
		}
		if (name.length() > 100) {
			throw new ValidationException("El nombre es muy largo");

		}
		if (email.length() > 150) {
			throw new ValidationException("Correo inválido");
		}
		if (userDAO.findByEmail(email) != null) {
			throw new DuplicateEmailException("El correo ya está registrado");	
		}
	}
}
