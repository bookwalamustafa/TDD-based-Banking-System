package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CheckingTest {

	public static final double APR = 5.0;
	public static final String ID = "12345678";
	Checking checking;

	@Test
	public void starting_balance_is_zero() {
		checking = new Checking(ID, APR);
		double actual = checking.getBalance();

		assertEquals(0, actual);
	}
}
