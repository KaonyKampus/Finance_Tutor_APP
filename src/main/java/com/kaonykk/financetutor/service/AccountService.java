package com.kaonykk.financetutor.service;

import com.kaonykk.financetutor.dao.AccountDAO;
import com.kaonykk.financetutor.model.entity.Account;

public class AccountService {

	private final AccountDAO accountDAO = new AccountDAO();

	public Account getByUserId(Long userId) {
		return accountDAO.findByUserId(userId);
	}

}
