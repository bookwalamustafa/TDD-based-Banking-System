package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WithdrawCommandProcessorTest {

    public static final String ID = "12345678";
    public static final String ID1 = "87654321";
    public static final String ID2 = "14557936";
    Bank bank;
    CreateCommandProcessor createCommandProcessor;
    DepositCommandProcessor depositCommandProcessor;
    WithdrawCommandProcessor withdrawCommandProcessor;

    String createCommandForSavings[] = {"create", "savings", "12345678", "0.6"};
    String createCommandForChecking[] = {"create", "checking", "87654321", "0.6"};
    String createCommandForCD[] = {"create", "cd", "14557936", "1.2", "2000.0"};
    String depositCommandForSavings[] = {"deposit", "12345678", "200.00"};
    String depositCommandForChecking[] = {"deposit", "87654321", "200.00"};

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        createCommandProcessor = new CreateCommandProcessor(bank);
        depositCommandProcessor = new DepositCommandProcessor(bank);
        withdrawCommandProcessor = new WithdrawCommandProcessor(bank);
    }

    @Test
    public void withdraw_less_money_than_present_from_savings() {
        createCommandProcessor.processForCreate(createCommandForSavings);
        depositCommandProcessor.processForDeposit(depositCommandForSavings);
        withdrawCommandProcessor.processForWithdraw(new String[]{"withdraw", "12345678", "100.00"});
        double actual = bank.getAccountByID(ID).getBalance();
        assertEquals(100.0, actual);
    }

    @Test
    public void withdraw_less_money_than_present_from_checkings() {
        createCommandProcessor.processForCreate(createCommandForChecking);
        depositCommandProcessor.processForDeposit(depositCommandForChecking);
        withdrawCommandProcessor.processForWithdraw(new String[]{"withdraw", "87654321", "100.00"});
        double actual = bank.getAccountByID(ID1).getBalance();
        assertEquals(100.0, actual);
    }

    @Test
    public void withdraw_entire_amount_from_savings() {
        createCommandProcessor.processForCreate(createCommandForSavings);
        depositCommandProcessor.processForDeposit(depositCommandForSavings);
        withdrawCommandProcessor.processForWithdraw(new String[]{"withdraw", "12345678", "200.00"});
        double actual = bank.getAccountByID(ID).getBalance();
        assertEquals(0.0, actual);
    }

    @Test
    public void withdraw_entire_amount_from_checking() {
        createCommandProcessor.processForCreate(createCommandForChecking);
        depositCommandProcessor.processForDeposit(depositCommandForChecking);
        withdrawCommandProcessor.processForWithdraw(new String[]{"withdraw", "87654321", "200.0"});
        double actual = bank.getAccountByID(ID1).getBalance();
        assertEquals(0.0, actual);
    }

    @Test
    public void withdraw_zero_amount_from_savings() {
        createCommandProcessor.processForCreate(createCommandForSavings);
        depositCommandProcessor.processForDeposit(depositCommandForSavings);
        withdrawCommandProcessor.processForWithdraw(new String[]{"withdraw", "12345678", "0.0"});
        double actual = bank.getAccountByID(ID).getBalance();
        assertEquals(200.0, actual);
    }

    @Test
    public void withdraw_zero_amount_from_checking() {
        createCommandProcessor.processForCreate(createCommandForChecking);
        depositCommandProcessor.processForDeposit(depositCommandForChecking);
        withdrawCommandProcessor.processForWithdraw(new String[]{"withdraw", "87654321", "0.0"});
        double actual = bank.getAccountByID(ID1).getBalance();
        assertEquals(200.0, actual);
    }

    @Test
    public void withdraw_twice_from_checking_account() {
        createCommandProcessor.processForCreate(createCommandForChecking);
        depositCommandProcessor.processForDeposit(depositCommandForChecking);
        withdrawCommandProcessor.processForWithdraw(new String[]{"withdraw", "87654321", "50.0"});
        withdrawCommandProcessor.processForWithdraw(new String[]{"withdraw", "87654321", "50.0"});
        double actual = bank.getAccountByID(ID1).getBalance();
        assertEquals(100.0, actual);
    }

    @Test
    public void withdraw_twice_from_savings_account_after_one_month() {
        createCommandProcessor.processForCreate(createCommandForSavings);
        depositCommandProcessor.processForDeposit(depositCommandForSavings);
        withdrawCommandProcessor.processForWithdraw(new String[]{"withdraw", "12345678", "50.0"});
        bank.getAccountByID(ID).setMonths(5);
        withdrawCommandProcessor.processForWithdraw(new String[]{"withdraw", "12345678", "50.0"});
        double actual = bank.getAccountByID(ID).getBalance();
        assertEquals(100.0, actual);
    }

    @Test
    public void maximum_withdrawal_from_savings() {
        createCommandProcessor.processForCreate(createCommandForSavings);
        depositCommandProcessor.processForDeposit(new String[]{"deposit", "12345678", "2500.0"});
        withdrawCommandProcessor.processForWithdraw(new String[]{"withdraw", "12345678", "1000.0"});
        double actual = bank.getAccountByID(ID).getBalance();
        assertEquals(1500.0, actual);
    }

    @Test
    public void maximum_withdrawal_from_checking() {
        createCommandProcessor.processForCreate(createCommandForChecking);
        depositCommandProcessor.processForDeposit(new String[]{"deposit", "87654321", "1000.0"});
        withdrawCommandProcessor.processForWithdraw(new String[]{"withdraw", "87654321", "400.0"});
        double actual = bank.getAccountByID(ID1).getBalance();
        assertEquals(600.0, actual);
    }

    @Test
    public void maximum_withdrawal_from_CD_after_12_months() {
        createCommandProcessor.processForCreate(createCommandForCD);
        bank.getAccountByID(ID2).setMonths(13);
        withdrawCommandProcessor.processForWithdraw(new String[]{"withdraw", "14557936", "2000.0"});
        double actual = bank.getAccountByID(ID2).getBalance();
        assertEquals(0.0, actual);
    }

    @Test
    public void withdraw_more_than_maximum_from_CD_after_12_months() {
        createCommandProcessor.processForCreate(createCommandForCD);
        bank.getAccountByID(ID2).setMonths(13);
        withdrawCommandProcessor.processForWithdraw(new String[]{"withdraw", "14557936", "2100"});
        double actual = bank.getAccountByID(ID2).getBalance();
        assertEquals(0.0, actual);
    }

}