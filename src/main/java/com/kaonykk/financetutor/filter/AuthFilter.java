package com.kaonykk.financetutor.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/home", "/admin/*"})
public class AuthFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		if (req.getServletPath().startsWith("/admin") && !"ADMIN".equals(session.getAttribute("userRole"))) {
			resp.sendRedirect(req.getContextPath() + "/home");

			return;

		}
		chain.doFilter(request, response);
	}

}
