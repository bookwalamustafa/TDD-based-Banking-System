package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SavingsTest {

	public static final double APR = 5.0;
	public static final String ID = "12345678";
	Savings savings;

	@Test
	public void starting_balance_is_zero() {
		savings = new Savings(ID, APR);
		double actual = savings.getBalance();

		assertEquals(0, actual);
	}
}
