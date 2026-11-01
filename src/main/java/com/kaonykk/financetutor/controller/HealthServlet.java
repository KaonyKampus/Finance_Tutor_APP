package com.kaonykk.financetutor.controller;

import com.kaonykk.financetutor.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/health/test")
public class HealthServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");

		EntityManager em = JPAUtil.getEntityManager();


		try {
			Object db = em.createNativeQuery("SELECT DATABASE()").getSingleResult();
			resp.getWriter().write(db.toString());

		} finally {
			em.close();
		}

	}

}
