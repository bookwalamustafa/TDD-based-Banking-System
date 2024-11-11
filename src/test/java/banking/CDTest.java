package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CDTest {

	public static final double BALANCE = 1000.00;
	public static final double APR = 5.0;
	public static final String ID = "12345678";
	CD cd;

	@Test
	public void starting_balance_is_supplied_balance() {
		cd = new CD(ID, APR, BALANCE);
		double actual = cd.getBalance();

		assertEquals(BALANCE, actual);

	}
}
