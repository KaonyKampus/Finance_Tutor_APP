package com.kaonykk.financetutor.service;

import com.kaonykk.financetutor.dao.AuthorizationRequestDAO;
import com.kaonykk.financetutor.dao.TutorAssignmentDAO;
import com.kaonykk.financetutor.dao.UserDAO;
import com.kaonykk.financetutor.exception.InsufficientBalanceException;
import com.kaonykk.financetutor.exception.ValidationException;
import com.kaonykk.financetutor.model.entity.Account;
import com.kaonykk.financetutor.model.entity.AuthorizationRequest;
import com.kaonykk.financetutor.model.entity.Transaction;
import com.kaonykk.financetutor.model.enums.RequestStatus;
import com.kaonykk.financetutor.model.enums.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class AuthorizationService {

	private final AuthorizationRequestDAO requestDAO = new AuthorizationRequestDAO();
	private final TutorAssignmentDAO tutorAssignmentDAO = new TutorAssignmentDAO();
	private final UserDAO userDAO = new UserDAO();

	public List<AuthorizationRequest> getPending(Long tutorId) {
		return requestDAO.findPendingByTutor(tutorId);
	}

	public void approve(Long tutorId, Long requestId) {
		AuthorizationRequest request = requestDAO.findById(requestId);
		if (request == null) {
			throw new ValidationException("Solicitud no encontrada");

		}
		Long principalId = request.getRequestedBy().getId();
		if (!tutorAssignmentDAO.exists(tutorId, principalId)) {
			throw new ValidationException("No puedes decidir esta solicitud");
		}

		Account account = request.getAccount();
		if (account.getBalance().compareTo(request.getAmount()) < 0) {
			throw new InsufficientBalanceException("El usuario ya no tiene saldo suficiente");
		}

		BigDecimal newBalance = account.getBalance().subtract(request.getAmount());
		account.setBalance(newBalance);

		Transaction transaction = new Transaction();
		transaction.setType(TransactionType.WITHDRAWAL);
		transaction.setAmount(request.getAmount());
		transaction.setDescription("Retiro aprobado por el tutor");
		transaction.setBalanceAfter(newBalance);

		request.setStatus(RequestStatus.APPROVED);
		request.setReviewedBy(userDAO.findById(tutorId));
		request.setReviewedAt(LocalDateTime.now());

		requestDAO.saveDecision(request, account, transaction);

	}

	public void reject(Long tutorId, Long requestId) {
		AuthorizationRequest request = requestDAO.findById(requestId);
		if (request == null) {
			throw new ValidationException("Solicitud no encontrada");

		}
		Long principalId = request.getRequestedBy().getId();
		if (!tutorAssignmentDAO.exists(tutorId, principalId)) {
			throw new ValidationException("No puedes decidir esta solicitud");
		}

		request.setStatus(RequestStatus.REJECTED);
		request.setReviewedBy(userDAO.findById(tutorId));
		request.setReviewedAt(LocalDateTime.now());

	
		requestDAO.saveDecision(request, null, null);
	}

}
