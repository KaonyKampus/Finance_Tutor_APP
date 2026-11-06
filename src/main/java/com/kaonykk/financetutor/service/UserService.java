package com.kaonykk.financetutor.service;

import com.kaonykk.financetutor.dao.UserDAO;
import com.kaonykk.financetutor.dto.UserRegistrationDTO;
import com.kaonykk.financetutor.exception.DuplicateEmailException;
import com.kaonykk.financetutor.exception.ValidationException;
import com.kaonykk.financetutor.model.entity.Account;
import com.kaonykk.financetutor.model.entity.User;
import com.kaonykk.financetutor.model.enums.AccountStatus;
import com.kaonykk.financetutor.model.enums.UserRole;
import com.kaonykk.financetutor.model.enums.UserStatus;
import com.kaonykk.financetutor.util.PasswordUtil;
import java.math.BigDecimal;

public class UserService {

	private final UserDAO userDAO = new UserDAO();

	public void register(UserRegistrationDTO dto) {

		String name = dto.getName();
		String email = dto.getEmail();
		String role = dto.getRole();
		String password = dto.getPassword();

		UserRole userRole;

		if (name == null || name.isBlank() || email == null || email.isBlank() || role == null || role.isBlank() || password == null || password.isBlank()) {
			throw new ValidationException("Todos los campos son obligatorios");
		}
		email = email.trim().toLowerCase();
		try {
			userRole = UserRole.valueOf(role.trim().toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new ValidationException("Rol Invalido");
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

		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPasswordHash(PasswordUtil.hash(password));
		user.setRole(userRole);
		user.setStatus(UserStatus.ACTIVE);

		Account account = null;

		if (userRole == UserRole.PRINCIPAL) {
			account = new Account();
			account.setUser(user);
			account.setBalance(BigDecimal.ZERO);
			account.setDailyLimit(BigDecimal.ZERO);
			account.setStatus(AccountStatus.ACTIVE);
		}

		userDAO.save(user, account);
	}
}
