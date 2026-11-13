package com.kaonykk.financetutor.controller;

import com.kaonykk.financetutor.exception.InsufficientBalanceException;
import com.kaonykk.financetutor.exception.ValidationException;
import com.kaonykk.financetutor.service.AuthorizationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/tutor/requests")
public class RequestServlet extends HttpServlet {

	private final AuthorizationService authorizationService = new AuthorizationService();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		Long tutorId = (Long) session.getAttribute("userId");

		try {
			Long requestId = Long.valueOf(req.getParameter("requestId"));
			String action = req.getParameter("action");

			if ("approve".equals(action)) {
				authorizationService.approve(tutorId, requestId);
				session.setAttribute("requestMessage", "Solicitud aprobada");

			} else if ("reject".equals(action)) {
				authorizationService.reject(tutorId, requestId);
				session.setAttribute("requestMessage", "Solicitud rechazada");

			}

		} catch (NumberFormatException e) {

			session.setAttribute("requestMessage", "Solicitud Invalida");
		} catch (ValidationException | InsufficientBalanceException e) {
			session.setAttribute("requestMessage", e.getMessage());
		}
		resp.sendRedirect(req.getContextPath() + "/home");
	}

}