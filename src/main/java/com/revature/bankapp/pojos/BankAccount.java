package com.revature.bankapp.pojos;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author Josh Dughi
 */
public class BankAccount {

	private int accountId;
	private double balance;
	private String type;

	/**
	 * Default constructor
	 */
	public BankAccount() {
	}

	/**
	 * Constructor
	 * 
	 * @param accountId
	 * @param balance
	 * @param type
	 */
	public BankAccount(int accountId, double balance, String type) {
		super();
		this.accountId = accountId;
		this.balance = balance;
		this.type = type;
	}

	/**
	 * Getter for accountId
	 * 
	 * @return accountId
	 */
	public int getAccountId() {
		return accountId;
	}

	/**
	 * Setter for accountId
	 * 
	 * @param accountId
	 */
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	/**
	 * Getter for balance
	 * 
	 * @return balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * Setter for balance
	 * 
	 * @param balance
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * Getter for account type
	 * 
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Setter for account type
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Hash code function for BankAccount object
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountId;
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/**
	 * Equals function for BankAccount object
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BankAccount other = (BankAccount) obj;
		if (accountId != other.accountId)
			return false;
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	/**
	 * To string function for BankAccount object
	 */
	@Override
	public String toString() {
		return "BankAccount [accountId=" + accountId + ", balance=" + balance + ", type=" + type + "]";
	};

	/**
	 * Deposit money into the account
	 * 
	 * @param value
	 * @return the new balance
	 */
	public double deposit(double value) {
		balance = balance + value;
		NumberFormat formatter = new DecimalFormat("#0.00");
		balance = Double.parseDouble(formatter.format(balance));
		return balance;
	}

	/**
	 * Withdraw money from the account
	 * 
	 * @param value
	 * @return the new balance
	 */
	public double withdraw(double value) {
		balance = balance - value;
		NumberFormat formatter = new DecimalFormat("#0.00");
		balance = Double.parseDouble(formatter.format(balance));
		return balance;
	}
}