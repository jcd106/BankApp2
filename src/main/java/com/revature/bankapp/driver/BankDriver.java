package com.revature.bankapp.driver;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Scanner;

import com.revature.bankapp.dao.BankAccountDAO;
import com.revature.bankapp.dao.BankAccountDAOImpl;
import com.revature.bankapp.dao.BankDAO;
import com.revature.bankapp.dao.BankDAOImpl;
import com.revature.bankapp.dao.UserAccountDAO;
import com.revature.bankapp.dao.UserAccountDAOImpl;
import com.revature.bankapp.pojos.Bank;
import com.revature.bankapp.pojos.BankAccount;
import com.revature.bankapp.pojos.UserAccount;

/**
 * Driver class for BankApp2
 * 
 * @author Josh Dughi
 */
public class BankDriver {

	private static Scanner scanner;
	private static UserAccount loggedInUser = null;
	private static BankAccount currentAccount = null;
	private static UserAccountDAO uaDao = new UserAccountDAOImpl();
	private static BankAccountDAO baDao = new BankAccountDAOImpl();
	private static BankDAO bankDao = new BankDAOImpl();

	/**
	 * Main method for BankDriver
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Starting the application...");
		System.out.println("FYI: Everything after white space in your inputs will be ignored!");
		startMenu();
	}

	/**
	 * The first console prompt menu
	 */
	private static void startMenu() {

		scanner = new Scanner(System.in);
		while (true) {
			System.out.println("What would you like to do?");
			System.out.println("[C]reate Account, [L]ogin, [Q]uit");
			String selection = scanner.nextLine().trim().toUpperCase().split(" ")[0];
			switch (selection) {
			case "C":
				setupUserAccount();
				break;
			case "L":
				UserAccount temp = loginToUserAccount();
				if (temp != null) {
					loggedInUser = temp;
					System.out.println("Login Successful!");
					mainMenu();
					break;
				} else {
					break;
				}
			case "Q":
				scanner.close();
				System.out.println("Ending program...");
				return;
			// Don't tell anyone this is here, they can never know
			case "SUPERSECRETPASSWORD12345":
				ArrayList<UserAccount> allUsers = uaDao.getAllUserAccounts();
				for (UserAccount i : allUsers) {
					System.out.println(i);
				}
				break;
			default:
				System.out.println("Invalid input");
				break;
			}
		}
	}

	/**
	 * Console prompts to set up a new UserAccount
	 * 
	 * @return the user account set up
	 */
	private static UserAccount setupUserAccount() {
		UserAccount user = new UserAccount();

		String username = "", password = "";
		boolean checkUser = true;
		while (checkUser) {
			System.out.println("Input username");
			username = scanner.nextLine().trim().toLowerCase().split(" ")[0];
			if (username.length() == 0) {
				System.out.println("Can not create user account without a username. Please try again");
				continue;
			}
			if (username.length() > 25) {
				System.out.println(
						"Can not create user account with username longer than 25 characters. Please try again");
				continue;
			}
			checkUser = uaDao.checkUsernameExists(username);
			if (checkUser)
				System.out.println("Can not create user account with given username. Please try again");
		}
		boolean checkPass = true;
		while (checkPass) {
			System.out.println("Input password with length 8 or longer");
			password = scanner.nextLine().trim().split(" ")[0];
			if (password.length() < 8)
				System.out.println("Password length is too short. Please try again");
			else if (password.length() > 25)
				System.out.println("Password length can not exceed 25 characters. Please try again");
			else
				checkPass = false;
		}

		user.setUsername(username);
		user.setPassword(password);
		uaDao.addUserAccount(user);

		return user;
	}

	/**
	 * Console prompts to log in to a UserAccount
	 * 
	 * @return the logged in UserAccount
	 */
	private static UserAccount loginToUserAccount() {
		System.out.println("Input username");
		String username = scanner.nextLine().trim().toLowerCase().split(" ")[0];
		System.out.println("Input password");
		String password = scanner.nextLine().trim().split(" ")[0];
		UserAccount thisUser = uaDao.getUserByUsername(username);
		if (username.equals(thisUser.getUsername()) && thisUser.checkPassword(password))
			return thisUser;
		System.out.println("Incorrect login information");
		return null;
	}

	/**
	 * For a logged in UserAccount Checks number of Bank Accounts loggedInUser has
	 * and determines which Main Menu to go to
	 */
	private static void mainMenu() {
		// Get number of bank accounts for user account
		// User can only have one checking account and one savings account
		ArrayList<Bank> banks = bankDao.getBanksByUserId(loggedInUser.getUserId());
		int numBanks = banks.size();
		switch (numBanks) {
		case 0:
			System.out.println("You have 0 open bank accounts");
			mainMenu0Accounts(banks);
			break;
		case 1:
			System.out.println("You have 1 open bank account");
			mainMenu1Account(banks);
			break;
		case 2:
			System.out.println("You have 2 open bank accounts");
			mainMenu2Accounts(banks);
			break;
		}
	}

	/**
	 * Main menu prompts for a loggedInUser with no existing BankAccounts
	 * 
	 * @param banks
	 *            will be passed around to the other main menus and the console
	 *            prompt menus for opening a new BankAccount
	 */
	private static void mainMenu0Accounts(ArrayList<Bank> banks) {
		boolean breakLoop = false;
		while (!breakLoop) {
			System.out.println("What would you like to do?");
			System.out.println("[O]pen account, [L]ogout, [Q]uit");
			String selection = scanner.nextLine().trim().toUpperCase().split(" ")[0];
			switch (selection) {
			case "O":
				openBankAccount(banks);
				breakLoop = true;
				break;
			case "L":
				loggedInUser = null;
				return;
			case "Q":
				System.out.println("Ending program...");
				scanner.close();
				System.exit(0);
			default:
				System.out.println("Invalid input");
				break;
			}
		}
		banks = bankDao.getBanksByUserId(loggedInUser.getUserId());
		mainMenu1Account(banks);
	}

	/**
	 * Main menu prompts for a loggedInUser with 1 existing BankAccount
	 * 
	 * @param banks
	 *            will be passed around to the other main menus and the console
	 *            prompt menus for opening a new BankAccount
	 */
	private static void mainMenu1Account(ArrayList<Bank> banks) {
		boolean breakLoop = false;
		while (!breakLoop) {
			System.out.println("What would you like to do?");
			System.out.println("[O]pen account, [A]ccess account, [L]ogout, [Q]uit");
			String selection = scanner.nextLine().trim().toUpperCase().split(" ")[0];
			switch (selection) {
			case "O":
				openBankAccount(banks);
				breakLoop = true;
				break;
			case "A":
				currentAccount = accessAccount(banks);
				BankAccount uAcct = updateBankAccount();
				if (uAcct == null)
					return;
				break;
			case "L":
				loggedInUser = null;
				return;
			case "Q":
				System.out.println("Ending program...");
				scanner.close();
				System.exit(0);
			default:
				System.out.println("Invalid input");
				break;
			}
		}
		banks = bankDao.getBanksByUserId(loggedInUser.getUserId());
		mainMenu2Accounts(banks);
	}

	/**
	 * Main menu prompts for a loggedInUser with 2 existing BankAccounts
	 * 
	 * @param banks
	 *            will be passed around to the other main menus and the console
	 *            prompt menus for opening a new BankAccount
	 */
	private static void mainMenu2Accounts(ArrayList<Bank> banks) {
		while (true) {
			System.out.println("What would you like to do?");
			System.out.println("[A]ccess account, [L]ogout, [Q]uit");
			String selection = scanner.nextLine().trim().toUpperCase().split(" ")[0];
			switch (selection) {
			case "A":
				while (true) {
					BankAccount acct = accessAccount(banks);
					if (acct == null)
						return;
					currentAccount = acct;
					BankAccount uAcct = updateBankAccount();
					if (uAcct == null)
						return;
				}
				// break;
			case "L":
				loggedInUser = null;
				return;
			case "Q":
				System.out.println("Ending program...");
				scanner.close();
				System.exit(0);
			default:
				System.out.println("Invalid input");
				break;
			}
		}
	}

	/**
	 * Sets up a new BankAccount and adds it to the BankAccount table and the Bank
	 * table in the database.
	 * 
	 * @param banks
	 *            is the passed around ArrayList to be passed to the prompts to set
	 *            up the new BankAccount
	 * @return banks how it is updated after a new BankAccount is opened
	 */
	private static BankAccount openBankAccount(ArrayList<Bank> banks) {
		BankAccount acct = new BankAccount();
		String typeToOpen = typeSelection(banks);
		double initialBalance = setupInitialBalance();
		acct.setType(typeToOpen);
		acct.setBalance(initialBalance);
		BankAccount account = baDao.addBankAccount(acct);
		Bank bank = new Bank();
		bank.setAccountId(account.getAccountId());
		bank.setUserId(loggedInUser.getUserId());
		bankDao.addBank(bank);
		System.out.println("Bank account successfully created");
		return account;
	}

	/**
	 * Prompts for users to set up the account type for their new BankAccount
	 * 
	 * @param banks
	 *            is the passed around ArrayList
	 * @return the account type selected for the new BankAccount
	 */
	private static String typeSelection(ArrayList<Bank> banks) {
		String typeToOpen = null;
		boolean breakLoop = false;
		while (!breakLoop) {
			ArrayList<BankAccount> openAccounts = new ArrayList<BankAccount>();
			for (Bank b : banks) {
				openAccounts.add(baDao.getBankAccountById(b.getAccountId()));
			}
			boolean canOpenCA = true;
			boolean canOpenSA = true;
			int numOpen = openAccounts.size();
			if (numOpen == 0) {
				System.out.println("You have 0 open bank accounts.");
				System.out.println("What type of account would you like to open?");
				System.out.println("[C]hecking, [S]avings, [Q]uit");
			}
			if (numOpen == 1) {
				String openType = openAccounts.get(0).getType();
				System.out.println("You have an open " + openType + " account");
				String canBeOpened = ("checking".equals(openType) ? "[S]avings" : "[C]hecking");
				canOpenCA = openType.equals("savings");
				canOpenSA = openType.equals("checking");
				System.out.println("What would you like to do?");
				System.out.println("Open " + canBeOpened + " account, [Q]uit");
			}
			String selection = scanner.nextLine().trim().toUpperCase().split(" ")[0];
			switch (selection) {
			case "C":
				if (canOpenCA) {
					typeToOpen = "checking";
					breakLoop = true;
				} else
					System.out.println("You can not open a 2nd checking account");
				break;
			case "S":
				if (canOpenSA) {
					typeToOpen = "savings";
					breakLoop = true;
				} else
					System.out.println("You can not open a 2nd savings account");
				break;
			case "Q":
				System.out.println("Ending program...");
				scanner.close();
				System.exit(0);
			default:
				System.out.println("Invalid input");
				break;
			}
		}
		return typeToOpen;
	}

	/**
	 * Prompts for user to put in their initial balance
	 * 
	 * @return the initial balance
	 */
	private static double setupInitialBalance() {
		boolean checkBal = true;
		double balance = 0;
		while (checkBal) {
			System.out.println("Input initial balance");
			String bal = scanner.nextLine().trim().split(" ")[0];
			try {
				balance = Double.parseDouble(bal);
				NumberFormat formatter = new DecimalFormat("#0.00");
				balance = Double.parseDouble(formatter.format(balance));
			} catch (Exception e) {
				System.out.println("Invalid input. Please try again");
				continue;
			}
			if (balance < 0) {
				System.out.println("You can not have a negative balance. Please try again");
				continue;
			}
			if (balance > 9.9999999999999E11) {
				System.out.println("Can not deposit that much money. Please try again");
				continue;
			}
			checkBal = false;
		}
		return balance;
	}

	/**
	 * Prompts for a user to access a chosen BankAccount
	 * 
	 * @param banks
	 *            is the passed around Bank ArrayList
	 * @return the BankAccount being accessed by the user
	 */
	private static BankAccount accessAccount(ArrayList<Bank> banks) {
		BankAccount ba = new BankAccount();

		boolean breakLoop = false;
		while (!breakLoop) {
			ArrayList<BankAccount> openAccounts = new ArrayList<BankAccount>();
			for (Bank b : banks) {
				openAccounts.add(baDao.getBankAccountById(b.getAccountId()));
			}
			int numOpen = openAccounts.size();
			if (numOpen == 1) {
				String openType = openAccounts.get(0).getType();
				System.out.println("Accessing your " + openType + " account...");
				ba = openAccounts.get(0);
				break;
			}
			if (numOpen == 2) {
				int cIndex = -1;
				int sIndex = -1;
				if (openAccounts.get(0).getType().equals("checking")) {
					cIndex = 0;
					sIndex = 1;
				} else {
					cIndex = 1;
					sIndex = 0;
				}
				boolean breakLoop2 = false;
				while (!breakLoop2) {
					System.out.println("What account would you like to access?");
					System.out.println("[C]hecking, [S]avings, [L]ogout, [Q]uit");
					String selection = scanner.nextLine().trim().toUpperCase().split(" ")[0];
					switch (selection) {
					case "C":
						System.out.println("Accessing your checking account...");
						ba = openAccounts.get(cIndex);
						breakLoop2 = true;
						break;
					case "S":
						System.out.println("Accessing your savings account...");
						ba = openAccounts.get(sIndex);
						breakLoop2 = true;
						break;
					case "L":
						loggedInUser = null;
						return null;
					case "Q":
						System.out.println("Ending program...");
						scanner.close();
						System.exit(0);
					default:
						System.out.println("Invalid input");
						break;
					}
				}
			}
			breakLoop = true;
		}
		return ba;
	}

	/**
	 * Prompts for the user to update the BankAccount that they are currently
	 * accessing
	 * 
	 * @return the updated BankAccount
	 */
	private static BankAccount updateBankAccount() {
		BankAccount ba = new BankAccount();
		boolean breakLoop = false;
		while (!breakLoop) {
			System.out.println("What would you like to do");
			System.out.println("[D]eposit, [W]ithdraw, [B]alance, [S]witch Account, [L]ogout, [Q]uit");
			String selection = scanner.nextLine().trim().toUpperCase().split(" ")[0];
			double newBal = 0;
			switch (selection) {
			case "D":
				// Deposit money
				newBal = depositMoney();
				baDao.updateBankAccount(currentAccount);
				System.out.println("Your new balance is " + newBal);
				break;
			case "W":
				// Withdraw money
				newBal = withdrawMoney();
				baDao.updateBankAccount(currentAccount);
				System.out.println("Your new balance is " + newBal);
				break;
			case "B":
				// Check balance
				System.out.println("Balance: " + checkBalance());
				break;
			case "S":
				currentAccount = null;
				BankAccount temp = new BankAccount();
				temp.setAccountId(0);
				return temp;
			case "L":
				loggedInUser = null;
				currentAccount = null;
				return null;
			case "Q":
				System.out.println("Ending program...");
				scanner.close();
				System.exit(0);
			default:
				System.out.println("Invalid input");
				break;
			}
		}

		return ba;
	}

	/**
	 * Prompts for the user to deposit money into their BankAccount
	 * 
	 * @return the new balance
	 */
	private static double depositMoney() {
		double deposit = 0;
		boolean checkDep = true;
		while (checkDep) {
			System.out.println("Input amount to deposit");
			String dep = scanner.nextLine().trim().split(" ")[0];
			try {
				deposit = Double.parseDouble(dep);
				NumberFormat formatter = new DecimalFormat("#0.00");
				deposit = Double.parseDouble(formatter.format(deposit));
			} catch (Exception e) {
				System.out.println("Invalid input. Please try again");
				continue;
			}
			if (deposit < 0) {
				System.out.println("Can not deposit a negative amount of money. Please try again");
				continue;
			}
			if (deposit + currentAccount.getBalance() > 9.9999999999999E11) {
				System.out.println("Can not deposit that much money. Please try again");
				continue;
			}
			checkDep = false;
		}

		return currentAccount.deposit(deposit);
	}

	/**
	 * Prompts for the user to withdraw money from their BankAccount
	 * 
	 * @return the new balance
	 */
	private static double withdrawMoney() {
		double withdraw = 0;
		boolean checkWith = true;
		while (checkWith) {
			System.out.println("Input amount to withdraw");
			String with = scanner.nextLine().trim().split(" ")[0];
			try {
				withdraw = Double.parseDouble(with);
				NumberFormat formatter = new DecimalFormat("#0.00");
				withdraw = Double.parseDouble(formatter.format(withdraw));
			} catch (Exception e) {
				System.out.println("Invalid input. Please try again");
				continue;
			}
			if (withdraw < 0) {
				System.out.println("Can not withdraw a negative amount of money. Please try again");
				continue;
			}
			if (withdraw > checkBalance()) {
				System.out.println("You have insufficient funds. Your account only has enough funds to withdraw "
						+ checkBalance());
				continue;
			}
			checkWith = false;
		}
		return currentAccount.withdraw(withdraw);
	}

	/**
	 * Check the balance of the BankAccount being accessed by the user
	 * 
	 * @return the balance
	 */
	private static double checkBalance() {
		return currentAccount.getBalance();
	}
}