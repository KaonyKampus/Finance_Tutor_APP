package com.kaonykk.financetutor.dao;

import com.kaonykk.financetutor.model.entity.Account;
import com.kaonykk.financetutor.model.entity.Transaction;
import com.kaonykk.financetutor.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class TransactionDAO {

	public void saveMovement(Account account, Transaction transaction) {
		EntityManager em = JPAUtil.getEntityManager();

		try {
			em.getTransaction().begin();
			Account managed = em.merge(account);
			transaction.setAccount(managed);
			em.persist(transaction);
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

	public List<Transaction> findByUserId(Long userId) {
		EntityManager em = JPAUtil.getEntityManager();

		try {
			return em.createQuery("SELECT t FROM Transaction t WHERE t.account.user.id =:userId ORDER BY t.createdAt DESC", Transaction.class)
				.setParameter("userId", userId)
				.getResultList();
		} finally {
			em.close();
		}

	}

}
