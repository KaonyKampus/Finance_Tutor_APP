package com.kaonykk.financetutor.controller;

import com.kaonykk.financetutor.exception.InsufficientBalanceException;
import com.kaonykk.financetutor.exception.LimitExceededException;
import com.kaonykk.financetutor.exception.ValidationException;
import com.kaonykk.financetutor.model.entity.Account;
import com.kaonykk.financetutor.model.entity.Transaction;
import com.kaonykk.financetutor.model.enums.WithdrawalResult;
import com.kaonykk.financetutor.service.AccountService;
import com.kaonykk.financetutor.service.TransactionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

	private final AccountService accountService = new AccountService();
	private final TransactionService transactionService = new TransactionService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(false);
		String role = (String) session.getAttribute("userRole");

		if ("PRINCIPAL".equals(role)) {
			Long userId = (Long) session.getAttribute("userId");
			Account account = accountService.getByUserId(userId);
			req.setAttribute("account", account);

			List<Transaction> history = transactionService.getHistory(userId);
			req.setAttribute("history", history);
			req.setAttribute("requests", transactionService.getRequests(userId));

		} else if ("TUTOR".equals(role)) {
			Long userId = (Long) session.getAttribute("userId");
			req.setAttribute("wards", accountService.getByTutor(userId));
		}

		req.getRequestDispatcher("/WEB-INF/views/home/home.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		Long userId = (Long) req.getSession(false).getAttribute("userId");

		String type = req.getParameter("type");
		String amountText = req.getParameter("amount");
		String description = req.getParameter("description");

		try {
			BigDecimal amount = new BigDecimal(amountText);
			if ("INCOME".equals(type)) {
				transactionService.deposit(userId, amount, description);
				req.setAttribute("success", "Movimiento registrado");

			} else if ("WITHDRAWAL".equals(type)) {
				WithdrawalResult result = transactionService.withdraw(userId, amount, description);

				if (result == WithdrawalResult.PENDING_APPROVAL) {
					req.setAttribute("success", "Tu retiro supera el límite diario. Se envió una solicitud a tu tutor.");
				} else {
					req.setAttribute("success", "Movimiento registrado");
				}
			}
		} catch (NumberFormatException | NullPointerException e) {
			req.setAttribute("error", "Ingresa un monto válido");
		} catch (ValidationException | InsufficientBalanceException | LimitExceededException e) {
			req.setAttribute("error", e.getMessage());
		}
		doGet(req, resp);
	}

}
