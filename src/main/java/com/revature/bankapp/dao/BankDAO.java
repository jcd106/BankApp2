package com.revature.bankapp.dao;

import java.util.ArrayList;

import com.revature.bankapp.pojos.Bank;

/**
 * DAO Interface for Banks
 * 
 * @author Josh Dughi
 */
public interface BankDAO {

	public Bank getBankByAccountId(int id);

	public ArrayList<Bank> getBanksByUserId(int id);

	public Bank addBank(Bank bank);
}