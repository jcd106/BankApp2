package com.revature.bankapp.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.bankapp.pojos.Bank;
import com.revature.bankapp.util.ConnectionFactory;

import oracle.jdbc.OracleTypes;

/**
 * Implementation of BankDAO
 * 
 * @author Josh Dughi
 */
public class BankDAOImpl implements BankDAO {

	/**
	 * Query the database for the Bank object associated with the given accountId
	 */
	@Override
	public Bank getBankByAccountId(int id) {

		Bank bank = new Bank();
		try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

			String sql = "SELECT * FROM Bank WHERE AccountId = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bank.setAccountId(id);
				bank.setUserId(rs.getInt("UserId"));
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return bank;
	}

	/**
	 * Query the database for the Bank objects associated with the given userId
	 * using stored procedure in the database
	 */
	@Override
	public ArrayList<Bank> getBanksByUserId(int id) {
		ArrayList<Bank> userBanks = new ArrayList<Bank>();

		try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

			String sql = "{CALL get_user_bankaccounts(?,?)}";
			CallableStatement cstmt = conn.prepareCall(sql);

			// Setting parameters is the same as we would if we were using PreparedStatement
			cstmt.setInt(1, id);

			// Define the index of our second ?, and its type
			cstmt.registerOutParameter(2, OracleTypes.CURSOR);

			// Execute our callable statement
			cstmt.execute();

			ResultSet rs = (ResultSet) cstmt.getObject(2);

			while (rs.next()) {
				Bank temp = new Bank();
				temp.setUserId(id);
				temp.setAccountId(rs.getInt("AccountId"));
				userBanks.add(temp);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return userBanks;
	}

	/**
	 * Add the given Bank to the database
	 */
	@Override
	public Bank addBank(Bank bank) {
		Bank b = new Bank();
		try (Connection conn = ConnectionFactory.getInstance().getConnection();) {
			conn.setAutoCommit(false);
			String sql = "INSERT INTO Bank (AccountId, UserId) VALUES (?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bank.getAccountId());
			pstmt.setInt(2, bank.getUserId());
			int rowsUpdated = pstmt.executeUpdate();
			if (rowsUpdated != 0) {
				b.setAccountId(bank.getAccountId());
				b.setUserId(bank.getUserId());
				conn.commit();
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return b;
	}
}