package com.kaonykk.financetutor.dao;

import com.kaonykk.financetutor.model.entity.TutorAssignment;
import com.kaonykk.financetutor.util.JPAUtil;
import jakarta.persistence.EntityManager;

public class TutorAssignmentDAO {

	public void save(TutorAssignment assignment) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(assignment);
			em.getTransaction().commit();
		} catch (RuntimeException e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw e;
		} finally {
			em.close();
		}
	}

	public boolean exists(Long tutorId, Long principalId) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			Long count = em.createQuery(
				"SELECT COUNT(a) FROM TutorAssignment a "
				+ "WHERE a.tutor.id = :tutorId "
				+ "AND a.principal.id =:principalId "
				+ "AND a.active = true ",
				Long.class)
				.setParameter("tutorId", tutorId)
				.setParameter("principalId", principalId)
				.getSingleResult();
			return count > 0;
		} finally {
			em.close();
		}
	}
}
