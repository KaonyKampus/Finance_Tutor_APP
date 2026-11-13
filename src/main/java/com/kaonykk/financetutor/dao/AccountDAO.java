package com.kaonykk.financetutor.dao;

import com.kaonykk.financetutor.model.entity.Account;
import com.kaonykk.financetutor.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class AccountDAO {

	public Account findByUserId(Long userId) {
		EntityManager em = JPAUtil.getEntityManager();

		try {
			return em.createQuery("SELECT a FROM Account a WHERE a.user.id = :userId", Account.class)
				.setParameter("userId", userId)
				.getResultStream()
				.findFirst()
				.orElse(null);
		} finally {
			em.close();
		}
	}

	public List<Account> findByTutor(Long tutorId) {

		EntityManager em = JPAUtil.getEntityManager();

		try {
			return em.createQuery(
				"SELECT acc FROM Account acc "
				+ "WHERE acc.user IN ("
				+ "    SELECT a.principal FROM TutorAssignment a "
				+ "    WHERE a.tutor.id = :tutorId AND a.active = true"
				+ ") "
				+ "ORDER BY acc.user.name",
				Account.class)
				.setParameter("tutorId", tutorId)
				.getResultList();
		} finally {
			em.close();
		}

	}

	public Account findById(Long id) {
		EntityManager em = JPAUtil.getEntityManager();

		try {
			return em.find(Account.class, id);
		} finally {
			em.close();
		}

	}

	public void update(Account account) {
		EntityManager em = JPAUtil.getEntityManager();
		try {

			em.getTransaction().begin();
			em.merge(account);
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
