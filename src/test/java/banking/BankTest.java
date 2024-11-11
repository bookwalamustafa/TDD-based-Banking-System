package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {

	public static final String ID = "12345678";
	public static final double APR = 5.0;
	public static final String ID1 = "14557936";
	public static final double BALANCE = 100.00;
	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
	}

	@Test
	public void bank_has_no_accounts() {
		int actual = bank.getAccounts().size();

		assertEquals(0, actual);
	}

	@Test
	public void bank_has_one_account() {
		bank.addAccount(ID, new Checking(ID, APR));
		int actual = bank.getAccounts().size();

		assertEquals(1, actual);
	}

	@Test
	public void bank_has_two_accounts() {
		bank.addAccount(ID, new Checking(ID, APR));
		bank.addAccount(ID1, new Checking(ID, APR));
		int actual = bank.getAccounts().size();

		assertEquals(2, actual);
	}

	@Test
	public void correct_account_is_retrieved() {
		Account checking = new Checking(ID, APR);
		bank.addAccount(ID, checking);
		Account actual = bank.getAccountByID(ID);

		assertEquals(checking, actual);
	}

	@Test
	public void money_is_deposited_in_correct_account_through_ID() {
		Account checking = new Checking(ID, APR);
		bank.addAccount(ID, checking);
		bank.depositMoneyByID(ID, BALANCE);

		assertEquals(BALANCE, checking.getBalance());
	}

	@Test
	public void money_is_withdrawn_from_correct_account_through_ID() {
		Account checking = new Checking(ID, APR);
		bank.addAccount(ID, checking);
		bank.depositMoneyByID(ID, BALANCE);
		bank.withdrawMoneyByID(ID, BALANCE);

		assertEquals(0, checking.getBalance());
	}

	@Test
	public void money_is_deposited_twice_in_correct_account_through_ID() {
		Account checking = new Checking(ID, APR);
		bank.addAccount(ID, checking);
		bank.depositMoneyByID(ID, BALANCE);
		bank.depositMoneyByID(ID, BALANCE);

		assertEquals((2 * BALANCE), checking.getBalance());
	}

	@Test
	public void money_is_withdrawn_twice_from_correct_account_through_ID() {
		Account checking = new Checking(ID, APR);
		bank.addAccount(ID, checking);
		bank.depositMoneyByID(ID, BALANCE);
		bank.depositMoneyByID(ID, BALANCE);
		bank.withdrawMoneyByID(ID, BALANCE);
		bank.withdrawMoneyByID(ID, BALANCE);

		assertEquals(0, checking.getBalance());
	}
}
