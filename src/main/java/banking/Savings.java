package banking;

public class Savings extends Account {

	public Savings(String id, double apr) {
		super(id, apr, 0, "banking.Savings", 2500, 1000);
	}

	protected Boolean isValidMaxAmountForDeposit(double amount) {
		return (amount <= maxDepositAmount) && (amount >= minDepositAmount);
	}

	public void withdraw(double amount) {
		if (getBalance() < amount) {
			balance = 0;
		}
		else {
			balance -= amount;
		}
		changeWithdrawalStatus();
	}

	protected Boolean isValidMaxAmountForWithdraw(double amount) {
		if (!getAlreadyMadeWithdrawalThisMonth()) {
			return (amount <= maxWithdrawAmount) && (amount >= minWithdrawAmount);
		}
		return false;
	}

	public void setMonths(int months) {
		if (months > super.age) {
			alreadyMadeWithdrawalThisMonth = false;
		}
		super.age = months;

	}

	public void changeWithdrawalStatus() {
		alreadyMadeWithdrawalThisMonth = !alreadyMadeWithdrawalThisMonth;
	}

	public boolean getAlreadyMadeWithdrawalThisMonth() {
		return alreadyMadeWithdrawalThisMonth;
	}
}