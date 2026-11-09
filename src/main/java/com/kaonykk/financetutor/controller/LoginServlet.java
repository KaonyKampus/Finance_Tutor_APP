package com.kaonykk.financetutor.controller;
import com.kaonykk.financetutor.exception.InvalidCredentialsException;
import com.kaonykk.financetutor.model.entity.User;
import com.kaonykk.financetutor.model.enums.UserRole;
import com.kaonykk.financetutor.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private final AuthService authService = new AuthService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String email = req.getParameter("email");
		String password = req.getParameter("password");

		try {
			User user = authService.login(email, password);

			HttpSession session = req.getSession();
			session.setAttribute("userId", user.getId());
			session.setAttribute("userName", user.getName());
			session.setAttribute("userRole", user.getRole().name());

			if (user.getRole() == UserRole.ADMIN) {
				resp.sendRedirect(req.getContextPath() + "/admin/users/new");
			} else {
				resp.sendRedirect(req.getContextPath() + "/home");
			}
		} catch (InvalidCredentialsException e) {
			req.setAttribute("error", e.getMessage());
			req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(req, resp);

		}
	}

}
