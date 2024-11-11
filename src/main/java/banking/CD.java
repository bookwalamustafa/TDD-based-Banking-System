package banking;

public class CD extends Account {

	public CD(String id, double apr, double balance) {

		super(id, apr, balance, "Cd", 0,0);
	}

	protected Boolean isValidMaxAmountForDeposit(double amount) {
		return false;
	}

	protected Boolean isValidMaxAmountForWithdraw(double amount) {
		if ((age < 12) || (balance < amount)) {
			return false;
		} else {
			return true;
		}
	}
}