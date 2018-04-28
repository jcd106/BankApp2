package com.revature.bankapp.pojos;

/**
 * @author Josh Dughi
 */
public class Bank {

	private int accountId;
	private int userId;

	/**
	 * Default constructor
	 */
	public Bank() {
	}

	/**
	 * Constructor
	 * 
	 * @param accountId
	 * @param userId
	 */
	public Bank(int accountId, int userId) {
		super();
		this.accountId = accountId;
		this.userId = userId;
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
	 * Getter for userId
	 * 
	 * @return userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Setter for userId
	 * 
	 * @param userId
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Hash code function for Bank object
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountId;
		result = prime * result + userId;
		return result;
	}

	/**
	 * Equals function for Bank object
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bank other = (Bank) obj;
		if (accountId != other.accountId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	/**
	 * To string function for Bank object
	 */
	@Override
	public String toString() {
		return "Bank [accountId=" + accountId + ", userId=" + userId + "]";
	}
}