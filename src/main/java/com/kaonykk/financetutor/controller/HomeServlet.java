package com.kaonykk.financetutor.controller;

import com.kaonykk.financetutor.model.entity.Account;
import com.kaonykk.financetutor.service.AccountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

	private final AccountService accountService = new AccountService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(false);
		String role = (String) session.getAttribute("userRole");

		
		if("PRINCIPAL".equals(role)){
			Long userId =(Long) session.getAttribute("userId");
			Account account = accountService.getByUserId(userId);
			req.setAttribute("account", account);
		}
		req.getRequestDispatcher("/WEB-INF/views/home/home.jsp").forward(req, resp);
	}

}
