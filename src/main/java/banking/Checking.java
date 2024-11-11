package banking;

public class Checking extends Account {

	public Checking(String id, double apr) {

		super(id, apr, 0, "banking.Checking", 1000, 400);
	}

	protected Boolean isValidMaxAmountForDeposit(double amount) {
		return (amount <= maxDepositAmount) && (amount >= minDepositAmount);
	}

	protected Boolean isValidMaxAmountForWithdraw(double amount) {
		return (amount <= maxWithdrawAmount) && (amount >= minWithdrawAmount);
	}
}