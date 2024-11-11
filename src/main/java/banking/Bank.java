package banking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bank {

	private Map<String, Account> accounts;
	private ArrayList<String> accountOrder = new ArrayList<>();

	public Bank() {
		accounts = new HashMap<>();
	}

	public Map<String, Account> getAccounts() {
		return accounts;
	}

	public Account getAccountByID(String ID) {
		return this.accounts.get(ID);
	}

	public void depositMoneyByID(String ID, double balance) {
		Account accountByID = getAccountByID(ID);
		accountByID.deposit(balance);
	}

	public void withdrawMoneyByID(String ID, double balance) {
		Account accountByID = getAccountByID(ID);
		accountByID.withdraw(balance);
	}

	public boolean addAccount(String ID, Account account) {
		if (!accountInBank(ID)) {
			accounts.put(ID, account);
			accountOrder.add(ID);
			return true;
		}
		else {
			return false;
		}
	}

	public void passTime(int months) {
		List<String> accountsToRemove = new ArrayList<>();

		for (String accountID : accounts.keySet()) {
			processAccount(accountID, months, accountsToRemove);
		}

		removeAccounts(accountsToRemove);
	}

	private void processAccount(String accountID, int months, List<String> accountsToRemove) {
		Account account = accounts.get(accountID);

		if (account.getBalance() == 0) {
			accountsToRemove.add(accountID);
		}
		else if (account.getBalance() < 100) {
			withdrawMoneyByID(accountID, 25);
		}

		account.passTimeAndCalculateAPR(months);
	}

	private void removeAccounts(List<String> accountsToRemove) {
		for (String accountID : accountsToRemove) {
			accounts.remove(accountID);
			accountOrder.remove(accountID);
		}
	}

	public boolean accountInBank(String ID) {
		return accounts.containsKey(ID);
	}

	List<String> getAccountOrder() {
		return accountOrder;
	}

	public void addChecking(String ID, double APR) {
		addAccount(ID, new Checking(ID, APR));
	}

	public void addSavings(String ID, double APR) {
		addAccount(ID, new Savings(ID, APR));
	}

	public void addCD(String ID, double APR, double balance) {

		addAccount(ID, new CD(ID, APR, balance));
	}
}