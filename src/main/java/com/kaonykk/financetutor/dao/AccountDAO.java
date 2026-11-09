package com.kaonykk.financetutor.dao;

import com.kaonykk.financetutor.model.entity.Account;
import com.kaonykk.financetutor.util.JPAUtil;
import jakarta.persistence.EntityManager;

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

}
