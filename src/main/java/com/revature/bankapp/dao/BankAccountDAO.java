package com.revature.bankapp.dao;

import com.revature.bankapp.pojos.BankAccount;

/**
 * DAO Interface for BankAccounts
 * 
 * @author Josh Dughi
 */
public interface BankAccountDAO {

	public BankAccount getBankAccountById(int id);

	public BankAccount addBankAccount(BankAccount acct);

	public boolean updateBankAccount(BankAccount acct);
}