package com.kaonykk.financetutor.service;

import com.kaonykk.financetutor.dao.TutorAssignmentDAO;
import com.kaonykk.financetutor.dao.UserDAO;
import com.kaonykk.financetutor.exception.ValidationException;
import com.kaonykk.financetutor.model.entity.TutorAssignment;
import com.kaonykk.financetutor.model.entity.User;
import com.kaonykk.financetutor.model.enums.UserRole;
import java.util.List;

public class AssignmentService {

	private final UserDAO userDAO = new UserDAO();
	private final TutorAssignmentDAO tutorAssignmentDAO = new TutorAssignmentDAO();

	public List<User> getTutors() {
		return userDAO.findByRole(UserRole.TUTOR);
	}

	public List<User> getPrincipals() {
		return userDAO.findByRole(UserRole.PRINCIPAL);
	}

	public void assign(Long tutorId, Long principalId) {
		if (tutorId == null || principalId == null) {
			throw new ValidationException("Selecciona un tutor y un usuario");
		}

		User tutor = userDAO.findById(tutorId);
		User principal = userDAO.findById(principalId);

		if (tutor == null || principal == null) {
			throw new ValidationException("Usuario no encontrado");
		}

		if (tutor.getRole() != UserRole.TUTOR) {
			throw new ValidationException("El primero debe ser un tutor");
		}

		if (principal.getRole() != UserRole.PRINCIPAL) {
			throw new ValidationException("El segundo debe ser un usuario principal");

		}
		if (tutorAssignmentDAO.exists(tutorId, principalId)) {
			throw new ValidationException("Ya están vinculados");
		}
		
		TutorAssignment assignment = new TutorAssignment();
		assignment.setTutor(tutor);
		assignment.setPrincipal(principal);
		tutorAssignmentDAO.save(assignment);

	}
}
