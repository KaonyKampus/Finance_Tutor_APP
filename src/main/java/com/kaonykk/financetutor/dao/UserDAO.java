package com.kaonykk.financetutor.dao;

import com.kaonykk.financetutor.model.entity.Account;
import com.kaonykk.financetutor.model.entity.User;
import com.kaonykk.financetutor.model.enums.UserRole;
import com.kaonykk.financetutor.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class UserDAO {

	public User findByEmail(String email) {

		EntityManager em = JPAUtil.getEntityManager();

		try {
			return em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
				.setParameter("email", email)
				.getResultStream()
				.findFirst()
				.orElse(null);
		} finally {
			em.close();
		}

	}

	public void save(User user, Account account) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(user);

			if (account != null) {
				em.persist(account);
			}
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

	public List<User> findByRole(UserRole role) {
		EntityManager em = JPAUtil.getEntityManager();

		try {
			return em.createQuery("SELECT u FROM User u WHERE u.role = :role ORDER BY u.name", User.class)
				.setParameter("role", role)
				.getResultList();

		} finally {
			em.close();
		}

	}

	public User findById(Long id) {
		EntityManager em = JPAUtil.getEntityManager();

		try {
			return em.find(User.class, id);

		} finally {
			em.close();
		}
	}

}
