package test.com.revature.bankapp.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.bankapp.dao.UserAccountDAO;
import com.revature.bankapp.dao.UserAccountDAOImpl;
import com.revature.bankapp.pojos.UserAccount;

public class UserAccountDAOImplTest {

	private static UserAccountDAO uaDao;
	private static UserAccount b;

	@BeforeClass
	public static void setupUADao() {
		uaDao = new UserAccountDAOImpl();
		b = new UserAccount();
		b.setUserId(10000);
		b.setPassword("123hello");
		b.setUsername("jcdughi");
	}

	@Test
	public void testGetAllUserAccounts() {
		ArrayList<UserAccount> uaccounts = uaDao.getAllUserAccounts();
		assertTrue(uaccounts.size() > 0);
	}

	@Test
	public void testGetUserById() {
		UserAccount a = uaDao.getUserById(10000);
		assertTrue(a.equals(b));
	}

	@Test
	public void testGetUserByUsername() {
		UserAccount a = uaDao.getUserByUsername("jcdughi");
		assertTrue(a.equals(b));
	}

	@Test
	public void testCheckUsernameExists() {
		String user = "jcdughi";
		assertTrue(uaDao.checkUsernameExists(user));
	}
}