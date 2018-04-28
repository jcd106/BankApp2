package com.revature.bankapp.dao;

import java.util.ArrayList;

import com.revature.bankapp.pojos.UserAccount;

/**
 * DAO Interface for UserAccounts
 * 
 * @author Josh Dughi
 */
public interface UserAccountDAO {

	public ArrayList<UserAccount> getAllUserAccounts();

	public UserAccount getUserById(int id);

	public UserAccount getUserByUsername(String name);

	public UserAccount addUserAccount(UserAccount user);

	public UserAccount updateUserAccount(UserAccount user);

	public boolean checkUsernameExists(String username);
}