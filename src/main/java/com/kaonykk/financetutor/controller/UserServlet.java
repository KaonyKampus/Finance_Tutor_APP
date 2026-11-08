package com.kaonykk.financetutor.controller;

import com.kaonykk.financetutor.dto.UserRegistrationDTO;
import com.kaonykk.financetutor.exception.DuplicateEmailException;
import com.kaonykk.financetutor.exception.ValidationException;
import com.kaonykk.financetutor.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/users/new")
public class UserServlet extends HttpServlet {

	private final UserService userService = new UserService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/views/users/register.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		UserRegistrationDTO dto = new UserRegistrationDTO();

		dto.setName(req.getParameter("name"));
		dto.setEmail(req.getParameter("email"));
		dto.setPassword(req.getParameter("password"));
		dto.setRole(req.getParameter("role"));

		try {

			userService.register(dto);
			req.setAttribute("success", "Usuario registrado correctamente");

		} catch (ValidationException | DuplicateEmailException e) {
			req.setAttribute("error", e.getMessage());

		}
		req.getRequestDispatcher("/WEB-INF/views/users/register.jsp").forward(req, resp);
	}
}
