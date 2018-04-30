package com.revature.bankapp.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Simple ConnectionFactory class
 * 
 * @author Josh Dughi
 */
public class ConnectionFactory {
	private static ConnectionFactory cf = null;
	private static Boolean build = true;

	/**
	 * Singleton constructor
	 */
	private ConnectionFactory() {
		build = false;
	}

	/**
	 * Getter for the instance of ConnectionFactory
	 * 
	 * @return the instance
	 */
	public static synchronized ConnectionFactory getInstance() {
		return build ? cf = new ConnectionFactory() : cf;
	}

	/**
	 * Getter for a connection to the database
	 * 
	 * @return the connection
	 */
	public Connection getConnection() {
		Connection conn = null;
		Properties prop = new Properties();

		try {
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
			Class.forName(prop.getProperty("driver"));
			conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("user"),
					prop.getProperty("pwd"));
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return conn;
	}
}