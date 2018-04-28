package com.revature.bankapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.revature.bankapp.pojos.UserAccount;
import com.revature.bankapp.util.ConnectionFactory;

/**
 * Implementation of UserAccountDAO
 * 
 * @author Josh Dughi
 */
public class UserAccountDAOImpl implements UserAccountDAO {

	/**
	 * Query the database for all user accounts Will be used by a super secret
	 * password at the start menu
	 */
	public ArrayList<UserAccount> getAllUserAccounts() {
		ArrayList<UserAccount> users = new ArrayList<UserAccount>();
		try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

			String sql = "SELECT * FROM UserAccount";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				UserAccount temp = new UserAccount();
				temp.setUserId(rs.getInt("UserId"));
				temp.setUsername(rs.getString("Username"));
				/*
				 * Leaving out password because no one should ever be able to get the passwords
				 * of all user accounts
				 */
				// temp.setPassword(rs.getString("Password"));

				users.add(temp);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return users;
	}

	/**
	 * Query the database for the UserAccount associated with the given userId
	 */
	public UserAccount getUserById(int id) {

		UserAccount user = new UserAccount();
		try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

			String sql = "SELECT * FROM UserAccount WHERE UserId = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				user.setUserId(id);
				user.setUsername(rs.getString("Username"));
				user.setPassword(rs.getString("Password"));
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return user;
	}

	/**
	 * Query the database for the UserAccount associated with the given username
	 */
	public UserAccount getUserByUsername(String name) {

		UserAccount user = new UserAccount();
		try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

			String sql = "SELECT * FROM UserAccount WHERE Username = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				user.setUserId(rs.getInt("UserId"));
				user.setUsername(name);
				user.setPassword(rs.getString("Password"));
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return user;

	}

	/**
	 * Add the given UserAccount to the database
	 */
	public UserAccount addUserAccount(UserAccount user) {

		UserAccount acct = new UserAccount();
		try (Connection conn = ConnectionFactory.getInstance().getConnection();) {
			conn.setAutoCommit(false);
			String sql = "INSERT INTO UserAccount (Username, Password) VALUES (?, ?)";
			String[] keys = new String[1];
			keys[0] = "UserId";
			PreparedStatement pstmt = conn.prepareStatement(sql, keys);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			int rowsUpdated = pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rowsUpdated != 0) {
				while (rs.next()) {
					acct.setUserId(rs.getInt(1));
				}
				acct.setUsername(user.getUsername());
				acct.setPassword(user.getPassword());
				conn.commit();
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return acct;
	}

	/**
	 * Update the UserAccount in the database with updatedUsers's userId to have
	 * updatedAccts password
	 */
	public UserAccount updateUserAccount(UserAccount updatedUser) {
		UserAccount user = new UserAccount();
		try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

			conn.setAutoCommit(false);

			String sql = "UPDATE UserAccount SET Password = ? WHERE UserId = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, updatedUser.getPassword());
			pstmt.setInt(2, updatedUser.getUserId());

			int rowsUpdated = pstmt.executeUpdate();

			if (rowsUpdated != 0) {
				conn.commit();
				user = getUserById(updatedUser.getUserId());
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return user;
	}

	/**
	 * Checks if the given username exists in the database
	 */
	@Override
	public boolean checkUsernameExists(String username) {
		UserAccount user = getUserByUsername(username);
		return (username.equals(user.getUsername()) ? true : false);
	}

	// public static void main(String[] args) {
	// UserAccountDAO uDao = new UserAccountDAOImpl();
	// ArrayList<UserAccount> users = uDao.getAllUserAccounts();
	// for(UserAccount u : users) {
	// System.out.println(u);
	// }
	// }
}