package com.kaonykk.financetutor.service;

import com.kaonykk.financetutor.dao.AccountDAO;
import com.kaonykk.financetutor.dao.TutorAssignmentDAO;
import com.kaonykk.financetutor.exception.ValidationException;
import com.kaonykk.financetutor.model.entity.Account;
import java.math.BigDecimal;
import java.util.List;

public class AccountService {

	private final AccountDAO accountDAO = new AccountDAO();
	private final TutorAssignmentDAO tutorAssignmentDAO = new TutorAssignmentDAO();

	public Account getByUserId(Long userId) {
		return accountDAO.findByUserId(userId);
	}

	public List<Account> getByTutor(Long tutorId) {
		return accountDAO.findByTutor(tutorId);
	}

	public void updateLimit(Long tutorId, Long accountId, BigDecimal newLimit) {
		if (newLimit == null || newLimit.compareTo(BigDecimal.ZERO) < 0) {
			throw new ValidationException("El limite no puede ser negativo");
		}
		Account account = accountDAO.findById(accountId);
		if (account == null) {
			throw new ValidationException("Cuenta no encontrada");
		}

		Long principalId = account.getUser().getId();
		if (!tutorAssignmentDAO.exists(tutorId, principalId)) {
			throw new ValidationException("No puedes modificar esta cuenta");
		}

		account.setDailyLimit(newLimit);
		accountDAO.update(account);
	}

}
