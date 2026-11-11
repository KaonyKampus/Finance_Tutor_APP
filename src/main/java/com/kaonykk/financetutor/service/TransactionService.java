package com.kaonykk.financetutor.service;

import com.kaonykk.financetutor.dao.AccountDAO;
import com.kaonykk.financetutor.dao.TransactionDAO;
import com.kaonykk.financetutor.exception.InsufficientBalanceException;
import com.kaonykk.financetutor.exception.ValidationException;
import com.kaonykk.financetutor.model.entity.Account;
import com.kaonykk.financetutor.model.entity.Transaction;
import com.kaonykk.financetutor.model.enums.TransactionType;
import java.math.BigDecimal;
import java.util.List;

public class TransactionService {

	private final AccountDAO accountDAO = new AccountDAO();
	private final TransactionDAO transactionDAO = new TransactionDAO();

	public void deposit(Long userId, BigDecimal amount, String description) {
		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ValidationException("El monto debe ser mayor a cero");
		}

		Account account = accountDAO.findByUserId(userId);
		if (account == null) {
			throw new ValidationException("No tienes cuenta asociada");
		}

		BigDecimal newBalance = account.getBalance().add(amount);
		account.setBalance(newBalance);

		Transaction transaction = new Transaction();
		transaction.setType(TransactionType.INCOME);
		transaction.setAmount(amount);
		transaction.setDescription(description);
		transaction.setBalanceAfter(newBalance);

		transactionDAO.saveMovement(account, transaction);

	}

	public void withdraw(Long userId, BigDecimal amount, String description) {
		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ValidationException("El monto debe ser mayor a cero");
		}

		Account account = accountDAO.findByUserId(userId);
		if (account == null) {
			throw new ValidationException("No tienes cuenta asociada");
		}

		if (account.getBalance().compareTo(amount) < 0) {
			throw new InsufficientBalanceException("Saldo insuficiente");
		}

		BigDecimal newBalance = account.getBalance().subtract(amount);
		account.setBalance(newBalance);

		Transaction transaction = new Transaction();
		transaction.setType(TransactionType.WITHDRAWAL);
		transaction.setAmount(amount);
		transaction.setDescription(description);
		transaction.setBalanceAfter(newBalance);

		transactionDAO.saveMovement(account, transaction);

	}

	public List<Transaction> getHistory(Long userId) {
		return transactionDAO.findByUserId(userId);
	}
	
	
}
