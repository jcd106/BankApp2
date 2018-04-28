package com.revature.bankapp.pojos;

/**
 * @author Josh Dughi
 */
public class UserAccount {

	private int userId;
	private String username;
	private String password;

	/**
	 * Default constructor
	 */
	public UserAccount() {
	}

	/**
	 * Constructor
	 * 
	 * @param userId
	 * @param username
	 * @param password
	 */
	public UserAccount(int userId, String username, String password) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
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
	 * Getter for username
	 * 
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Setter for username
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Getter for password
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Checks if the input password matches the real password
	 * 
	 * @param check
	 * @return true if match, false otherwise
	 */
	public boolean checkPassword(String check) {
		return password.equals(check);
	}

	/**
	 * Setter for password
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Hash code function for UserAccount object
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + userId;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	/**
	 * Equals function for UserAccount object
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAccount other = (UserAccount) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userId != other.userId)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	/**
	 * To string function for UserAccount object
	 */
	@Override
	public String toString() {
		return "UserAccount [userId=" + userId + ", username=" + username + "]";
	}
}