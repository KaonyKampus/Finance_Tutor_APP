package com.kaonykk.financetutor.dao;

import com.kaonykk.financetutor.exception.ValidationException;
import com.kaonykk.financetutor.model.entity.Account;
import com.kaonykk.financetutor.model.entity.AuthorizationRequest;
import com.kaonykk.financetutor.model.entity.Transaction;
import com.kaonykk.financetutor.model.enums.RequestStatus;
import com.kaonykk.financetutor.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
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

	public List<AuthorizationRequest> findPendingByTutor(Long tutorId) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			return em.createQuery(
				"SELECT r FROM AuthorizationRequest r "
				+ "WHERE r.status = :status "
				+ "AND r.requestedBy IN ("
				+ "    SELECT a.principal FROM TutorAssignment a "
				+ "    WHERE a.tutor.id = :tutorId AND a.active = true"
				+ ") "
				+ "ORDER BY r.createdAt",
				AuthorizationRequest.class)
				.setParameter("status", RequestStatus.PENDING)
				.setParameter("tutorId", tutorId)
				.getResultList();

		} finally {

			em.close();
		}
	}

	public AuthorizationRequest findById(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			return em.find(AuthorizationRequest.class, id);
		} finally {
			em.close();
		}
	}

	public void saveDecision(AuthorizationRequest request, Account account, Transaction transaction) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();

			AuthorizationRequest locked = em.find(
				AuthorizationRequest.class, request.getId(), LockModeType.PESSIMISTIC_WRITE);

			if (locked.getStatus() != RequestStatus.PENDING) {
				throw new ValidationException("Esta solicitud ya fue procesada");
			}

			if (transaction != null) {
				Account managedAccount = em.merge(account);
				transaction.setAccount(managedAccount);
				em.persist(transaction);
				request.setTransaction(transaction);
			}

			em.merge(request);

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

}
