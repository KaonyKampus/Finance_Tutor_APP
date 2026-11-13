package com.kaonykk.financetutor.dao;

import com.kaonykk.financetutor.model.entity.AuthorizationRequest;
import com.kaonykk.financetutor.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class AuthorizationRequestDAO {

	public void save(AuthorizationRequest request) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(request);
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

	public List<AuthorizationRequest> findByUserId(Long userId) {
		EntityManager em = JPAUtil.getEntityManager();

		try {
			return em.createQuery(
				"SELECT r FROM AuthorizationRequest r "
				+ "WHERE r.requestedBy.id = :userId "
				+ "ORDER BY r.createdAt DESC",
				AuthorizationRequest.class)
				.setParameter("userId", userId)
				.getResultList();

		} finally {
			em.close();
		}
	}
}
