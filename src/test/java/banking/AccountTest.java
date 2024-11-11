package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountTest {
	public static final double APR = 5.0;
	public static final double AMOUNT = 100;
	public static final double BALANCE = 1000.00;
	public static final String ID = "12345678";
	Checking checking;
	Savings savings;
	CD cd;

	@BeforeEach
	public void setUp() {
		checking = new Checking(ID, APR);
		savings = new Savings(ID, APR);
		cd = new CD(ID, APR, BALANCE);
	}

	@Test
	public void apr_is_correct_checking() {
		double actual = checking.getAPR();

		assertEquals(APR, actual);
	}

	@Test
	public void apr_is_correct_savings() {
		double actual = savings.getAPR();

		assertEquals(APR, actual);
	}

	@Test
	public void apr_is_correct_cd() {
		double actual = cd.getAPR();

		assertEquals(APR, actual);
	}

	@Test
	public void depositing_into_checking() {
		checking.deposit(AMOUNT);
		double actual = checking.getBalance();

		assertEquals(checking.getBalance(), actual);

	}

	@Test
	public void depositing_into_savings() {
		savings.deposit(AMOUNT);
		double actual = savings.getBalance();

		assertEquals(savings.getBalance(), actual);
	}

	@Test
	public void depositing_into_cd() {
		cd.deposit(AMOUNT);
		double actual = cd.getBalance();

		assertEquals(cd.getBalance(), actual);
	}

	@Test
	public void withdrawing_from_checking() {
		checking.withdraw(AMOUNT);
		double actual = checking.getBalance();

		assertEquals(checking.getBalance(), actual);
	}

	@Test
	public void withdrawing_from_savings() {
		savings.withdraw(AMOUNT);
		double actual = savings.getBalance();

		assertEquals(savings.getBalance(), actual);
	}

	@Test
	public void withdrawing_from_cd() {
		cd.withdraw(AMOUNT);
		double actual = cd.getBalance();

		assertEquals(cd.getBalance(), actual);
	}

	@Test
	public void depositing_twice_into_checking() {
		checking.deposit(AMOUNT);
		checking.deposit(AMOUNT);
		double actual = checking.getBalance();

		assertEquals(checking.getBalance(), actual);
	}

	@Test
	public void depositing_twice_into_savings() {
		savings.deposit(AMOUNT);
		savings.deposit(AMOUNT);
		double actual = savings.getBalance();

		assertEquals(savings.getBalance(), actual);
	}

	@Test
	public void depositing_twice_into_cd() {
		cd.deposit(AMOUNT);
		cd.deposit(AMOUNT);
		double actual = cd.getBalance();

		assertEquals(cd.getBalance(), actual);
	}

	@Test
	public void withdrawing_twice_from_checking() {
		checking.withdraw(AMOUNT);
		checking.withdraw(AMOUNT);
		double actual = checking.getBalance();

		assertEquals(checking.getBalance(), actual);
	}

	@Test
	public void withdrawing_twice_from_savings() {
		savings.withdraw(AMOUNT);
		savings.withdraw(AMOUNT);
		double actual = savings.getBalance();

		assertEquals(savings.getBalance(), actual);
	}

	@Test
	public void withdrawing_twice_from_cd() {
		cd.withdraw(AMOUNT);
		cd.withdraw(AMOUNT);
		double actual = cd.getBalance();

		assertEquals(cd.getBalance(), actual);
	}
}
