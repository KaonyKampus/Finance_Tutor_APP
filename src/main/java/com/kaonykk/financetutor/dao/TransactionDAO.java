package com.kaonykk.financetutor.dao;

import com.kaonykk.financetutor.model.entity.Account;
import com.kaonykk.financetutor.model.entity.Transaction;
import com.kaonykk.financetutor.model.enums.TransactionType;
import com.kaonykk.financetutor.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TransactionDAO {
	//funcion de guardar movimientos

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

	//Funcion de buscar los movimientos por usuario ID
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

//suma de retiros diarios 
	public BigDecimal sumWithdrawalsToday(Long accountId) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			return em.createQuery("SELECT COALESCE(SUM(t.amount),0) FROM Transaction t " + "WHERE t.account.id =:accountId " + "AND t.type = :type " + "AND t.createdAt >= :startOfDay", BigDecimal.class)
				.setParameter("accountId", accountId)
				.setParameter("type", TransactionType.WITHDRAWAL)
				.setParameter("startOfDay", LocalDate.now().atStartOfDay())
				.getSingleResult();
		} finally {
			em.close();
		}

	}

}
