package com.kaonykk.financetutor.controller;

import com.kaonykk.financetutor.exception.ValidationException;
import com.kaonykk.financetutor.service.AssignmentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/assignments")
public class AssignmentServlet extends HttpServlet {

	private final AssignmentService assignmentService = new AssignmentService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("tutors", assignmentService.getTutors());
		req.setAttribute("principals", assignmentService.getPrincipals());
		req.getRequestDispatcher("/WEB-INF/views/admin/assignment.jsp").forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		try {
			Long tutorId = Long.valueOf(req.getParameter("tutorId"));
			Long principalId = Long.valueOf(req.getParameter("principalId"));
			assignmentService.assign(tutorId, principalId);
			req.setAttribute("success", "Vinculación creada");
			
		} catch (NumberFormatException e) {
			req.setAttribute("error", "Selecciona ambos usuarios");
		} catch (ValidationException e) {
			req.setAttribute("error", e.getMessage());
		}
		doGet(req, resp);
	}

}
