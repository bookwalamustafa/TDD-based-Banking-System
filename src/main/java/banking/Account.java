package banking;

public abstract class Account {

	protected String id;
	protected double apr;
	protected double balance;
	protected int age = 0;
	protected double minDepositAmount = 0;
	protected double maxDepositAmount;
	protected double minWithdrawAmount = 0;
	protected double maxWithdrawAmount;
	protected String accountType;
	protected boolean alreadyMadeWithdrawalThisMonth = false;

	protected Account(String id, double apr, double balance, String accountType, double maxDepositAmount, double maxWithdrawAmount) {
		this.id = id;
		this.apr = apr;
		this.balance = balance;
		this.accountType = accountType;
		this.maxDepositAmount = maxDepositAmount;
		this.maxWithdrawAmount = maxWithdrawAmount;
	}

	public double getAPR() {
		return apr;
	}

	public double getBalance() {
		return balance;
	}

	public void deposit(double amount) {
		balance += amount;
	}

	public void withdraw(double amount) {
		if (getBalance() < amount) {
			setBalance(0);
		}
		else {
			balance -= amount;
		}
	}

	public String getID() {
		return id;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public void setMonths(int months) {
		this.age = months;
	}

	public String getAccountType() {
		return accountType;
	}

	protected abstract Boolean isValidMaxAmountForDeposit(double amount);

	protected abstract Boolean isValidMaxAmountForWithdraw(double amount);

	public void passTimeAndCalculateAPR(int months) {
		age += months;

		int loopCount = (accountType.equalsIgnoreCase("cd")) ? 4 : 1;

		for (int monthLoopIndex = 0; monthLoopIndex < months;monthLoopIndex++) {
			for (int multipleCalculationCounter = 0; multipleCalculationCounter < loopCount; multipleCalculationCounter++) {
				balance += ((apr / 100) / 12) * balance;
			}
		}
		alreadyMadeWithdrawalThisMonth = false;
	}
}