package com.kaonykk.financetutor.controller;

import com.kaonykk.financetutor.exception.ValidationException;
import com.kaonykk.financetutor.service.AccountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/tutor/limit")
public class LimitServlet extends HttpServlet {

	private final AccountService accountService = new AccountService();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession(false);
		Long tutorId = (Long) session.getAttribute("userId");

		try {
			Long accountId = Long.valueOf(req.getParameter("accountId"));
			BigDecimal newLimit = new BigDecimal(req.getParameter("dailyLimit"));

			accountService.updateLimit(tutorId, accountId, newLimit);
			session.setAttribute("limitMessage", "Limite Actualizado");

		} catch (NumberFormatException | NullPointerException e) {
			session.setAttribute("limitMessage", "Ingresa un valor válido");
		} catch (ValidationException e) {

			session.setAttribute("limitMessage", e.getMessage());
		}

		resp.sendRedirect(req.getContextPath() + "/home");
	}
}
