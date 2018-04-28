package com.revature.bankapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.bankapp.pojos.BankAccount;
import com.revature.bankapp.util.ConnectionFactory;

/**
 * Implementation of BankAccountDAO
 * 
 * @author Josh Dughi
 */
public class BankAccountDAOImpl implements BankAccountDAO {

	/**
	 * Query the database for the BankAccount associated with the given id
	 */
	@Override
	public BankAccount getBankAccountById(int id) {
		BankAccount ba = new BankAccount();

		try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

			String sql = "SELECT * FROM BankAccount WHERE AccountId = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				ba.setAccountId(id);
				ba.setBalance(rs.getDouble("Balance"));
				ba.setType(rs.getString("AccountType"));
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return ba;
	}

	/**
	 * Add the given BankAccount to the database
	 */
	@Override
	public BankAccount addBankAccount(BankAccount acct) {
		BankAccount ba = new BankAccount();
		try (Connection conn = ConnectionFactory.getInstance().getConnection();) {
			conn.setAutoCommit(false);
			String sql = "INSERT INTO BankAccount (Balance, AccountType) VALUES (?, ?)";
			String[] keys = new String[1];
			keys[0] = "AccountId";
			PreparedStatement pstmt = conn.prepareStatement(sql, keys);
			pstmt.setDouble(1, acct.getBalance());
			pstmt.setString(2, acct.getType());
			int rowsUpdated = pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rowsUpdated != 0) {
				while (rs.next()) {
					ba.setAccountId(rs.getInt(1));
				}
				ba.setBalance(acct.getBalance());
				ba.setType(acct.getType());
				conn.commit();
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return ba;
	}

	/**
	 * Update the BankAccount in the database with updatedAcct's accountId to have
	 * updatedAccts balance
	 */
	@Override
	public boolean updateBankAccount(BankAccount updatedAcct) {
		try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

			conn.setAutoCommit(false);

			String sql = "UPDATE BankAccount SET Balance = ? WHERE AccountId = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setDouble(1, updatedAcct.getBalance());
			pstmt.setInt(2, updatedAcct.getAccountId());

			int rowsUpdated = pstmt.executeUpdate();

			if (rowsUpdated != 0) {
				conn.commit();
				return true;
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return false;
	}
}