package com.kaonykk.financetutor.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

	private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("financetutorPU");
	private JPAUtil() {

	}

	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}

	public static void shutdown() {
		if (factory != null && factory.isOpen()) {
			factory.close();
		}
	}

}
