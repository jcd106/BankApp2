package com.revature.bankapp.services;

import com.revature.bankapp.dao.UserAccountDAO;
import com.revature.bankapp.dao.UserAccountDAOImpl;
import com.revature.bankapp.pojos.UserAccount;

public class UserService {
	
	public static UserAccountDAO userDao = new UserAccountDAOImpl();
	
	public UserAccount createUser(UserAccount user) {
		if(isUsernameAvailable(user.getUsername())) {
			return userDao.addUserAccount(user);
		}
		return null;
	}
	
	public UserAccount getUserById(int userId) {
		return userDao.getUserById(userId);
	}
	
	public UserAccount getUserByUsername(String username) {
		return userDao.getUserByUsername(username);
	}
	
	public UserAccount updateUserAccount(UserAccount user) {
		return userDao.updateUserAccount(user);
	}
	
	public UserAccount loginUser(UserAccount user) {
		UserAccount usernameMatch = userDao.getUserByUsername(user.getUsername());
		if (user.getUsername() != null) {
			if (user.getPassword().equals(usernameMatch.getPassword())) {
				return usernameMatch;
			}
		}
		
		return null;
	}

	public boolean isUsernameAvailable(String username) {
		for (UserAccount user: userDao.getAllUserAccounts()) {
			if (user.getUsername().equalsIgnoreCase(username)) {
				return false;
			}
		}
		return true;
	}
}
