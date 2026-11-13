package com.kaonykk.financetutor.service;

import com.kaonykk.financetutor.dao.AccountDAO;
import com.kaonykk.financetutor.dao.AuthorizationRequestDAO;
import com.kaonykk.financetutor.dao.TransactionDAO;
import com.kaonykk.financetutor.exception.InsufficientBalanceException;
import com.kaonykk.financetutor.exception.ValidationException;
import com.kaonykk.financetutor.model.entity.Account;
import com.kaonykk.financetutor.model.entity.AuthorizationRequest;
import com.kaonykk.financetutor.model.entity.Transaction;
import com.kaonykk.financetutor.model.enums.TransactionType;
import com.kaonykk.financetutor.model.enums.WithdrawalResult;
import java.math.BigDecimal;
import java.util.List;

public class TransactionService {

	private final AccountDAO accountDAO = new AccountDAO();
	private final TransactionDAO transactionDAO = new TransactionDAO();
	private final AuthorizationRequestDAO authorizationRequestDAO = new AuthorizationRequestDAO();

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

	public WithdrawalResult withdraw(Long userId, BigDecimal amount, String description) {
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

		BigDecimal spentToday = transactionDAO.sumWithdrawalsToday(account.getId());
		if (spentToday.add(amount).compareTo(account.getDailyLimit()) > 0) {

			AuthorizationRequest request = new AuthorizationRequest();
			request.setAccount(account);
			request.setRequestedBy(account.getUser());
			request.setType(TransactionType.WITHDRAWAL);
			request.setAmount(amount);
			authorizationRequestDAO.save(request);

			return WithdrawalResult.PENDING_APPROVAL;
		}

		BigDecimal newBalance = account.getBalance().subtract(amount);
		account.setBalance(newBalance);

		Transaction transaction = new Transaction();
		transaction.setType(TransactionType.WITHDRAWAL);
		transaction.setAmount(amount);
		transaction.setDescription(description);
		transaction.setBalanceAfter(newBalance);

		transactionDAO.saveMovement(account, transaction);
		return WithdrawalResult.COMPLETED;
	}

	public List<Transaction> getHistory(Long userId) {
		return transactionDAO.findByUserId(userId);
	}

	public List<AuthorizationRequest> getRequests(Long userId) {
		return authorizationRequestDAO.findByUserId(userId);
	}
	
	
}
