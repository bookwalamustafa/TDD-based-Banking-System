package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WithdrawCommandValidatorTest {

    public static final String ID = "12345678";
    public static final double APR = 0.6;
    public static final String ID1 = "87654321";
    public static final double BALANCE = 500.00;
    public static final double BALANCE1 = 1000.00;
    public static final double BALANCE2 = 400.00;
    public static final double BALANCE3 = 1500.00;
    public static final double BALANCE4 = 450.00;
    Bank bank;
    CommandValidator commandValidator;
    WithdrawCommandValidator withdrawCommandValidator;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        commandValidator = new CommandValidator(bank);
        withdrawCommandValidator = new WithdrawCommandValidator(bank);
    }

    @Test
    public void valid_withdraw_command() {
        bank.addAccount(ID, new Savings(ID, APR));
        bank.depositMoneyByID(ID, BALANCE);
        boolean actual = commandValidator.validate("Withdraw 12345678 500");
        assertTrue(actual);
    }

    @Test
    public void more_arguments_than_required() {
        boolean actual = withdrawCommandValidator.validateForWithdraw(new String[]{"Withdraw 12345678 500 0.6"});
        assertFalse(actual);
    }

    @Test
    public void less_arguments_than_requried() {
        boolean actual = withdrawCommandValidator.validateForWithdraw(new String[]{"Withdraw", "500"});
        assertFalse(actual);
    }

    @Test
    public void typo_in_withdraw_command() {
        boolean actual = commandValidator.validate("Widraw 12345678 500");
        assertFalse(actual);
    }

    @Test
    public void invalid_character_in_withdrawal_amount() {
        boolean actual = withdrawCommandValidator.validateForWithdraw(new String[]{"Withdraw", "12345678", "A$23"});
        assertFalse(actual);
    }

    @Test
    public void negative_withdrawal_amount() {
        boolean actual = withdrawCommandValidator.validateForWithdraw(new String[]{"Withdraw", "12345678", "-500:"});
        assertFalse(actual);
    }

    @Test
    public void withdraw_maximum_from_savings() {
        bank.addAccount(ID, new Savings(ID, APR));
        bank.depositMoneyByID(ID, BALANCE1);
        boolean actual = withdrawCommandValidator.validateForWithdraw(new String[]{"withdraw", "12345678", "1000.00"});
        assertTrue(actual);
    }

    @Test
    public void withdraw_more_than_maximum_from_savings() {
        bank.addAccount(ID, new Savings(ID, APR));
        bank.depositMoneyByID(ID, BALANCE3);
        boolean actual = withdrawCommandValidator.validateForWithdraw(new String[]{"withdraw", "12345678", "1500"});
        assertFalse(actual);
    }

    @Test
    public void withdraw_maximum_from_checking() {
        bank.addAccount(ID1, new Checking(ID1, APR));
        bank.depositMoneyByID(ID1, BALANCE2);
        boolean actual = withdrawCommandValidator.validateForWithdraw(new String[]{"withdraw", "87654321", "400.00"});
        assertTrue(actual);
    }

    @Test
    public void withdraw_more_than_maximum_from_checking() {
        bank.addAccount(ID1, new Checking(ID1, APR));
        bank.depositMoneyByID(ID1, BALANCE4);
        boolean actual = withdrawCommandValidator.validateForWithdraw(new String[]{"withdraw", "87654321", "450.00"});
        assertFalse(actual);
    }

    @Test
    public void withdraw_zero_from_checking_or_savings() {
        bank.addAccount(ID, new Savings(ID, APR));
        bank.depositMoneyByID(ID, 0);
        boolean actual = withdrawCommandValidator.validateForWithdraw(new String[]{"withdraw", "12345678", "0.00"});
        assertTrue(actual);
    }

    @Test
    public void withdraw_negative_amount_from_checking_or_savings() {
        boolean actual = withdrawCommandValidator.validateForWithdraw(new String[]{"withhdraw", "12345678", "-500"});
        assertFalse(actual);
    }

    @Test
    public void withdraw_from_account_that_does_not_exist() {
        boolean actual = bank.accountInBank(ID);
        assertFalse(actual);
    }

    @Test
    public void ID_more_than_eight_digits() {
        boolean actual = withdrawCommandValidator.validateForWithdraw(new String[]{"withdraw", "123456789", "200"});
        assertFalse(actual);
    }

    @Test
    public void ID_less_than_eight_digits() {
        boolean actual = withdrawCommandValidator.validateForWithdraw(new String[]{"withdraw", "1234567", "200"});
        assertFalse(actual);
    }

    @Test
    public void ID_has_invalid_characters() {
        boolean actual = withdrawCommandValidator.validateForWithdraw(new String[]{"withdraw", "1234ABCD", "200"});
        assertFalse(actual);
    }

    @Test
    public void arguments_in_the_wrong_order() {
        boolean actual = withdrawCommandValidator.validateForWithdraw(new String[]{"12345678", "withdraw", "200"});
        assertFalse(actual);
    }


    @Test
    public void withdraw_from_savings_more_than_once() {
        bank.addAccount(ID, new Savings(ID, APR));
        bank.depositMoneyByID(ID, BALANCE);
        boolean actual = withdrawCommandValidator.validateForWithdraw(new String[]{"withdraw", "12345678", "200.00"});
        assertTrue(actual);
        bank.withdrawMoneyByID(ID, 200.00);
        boolean actual1 = withdrawCommandValidator.validateForWithdraw(new String[]{"withdraw", "12345678", "300.00"});
        assertFalse(actual1);
    }

    @Test
    public void withdraw_after_at_least_one_month_passes() {
        bank.addAccount(ID, new Savings(ID, APR));
        bank.depositMoneyByID(ID, BALANCE);
        boolean actual = withdrawCommandValidator.validateForWithdraw(new String[]{"withdraw", "12345678", "200.00"});
        assertTrue(actual);
        bank.withdrawMoneyByID(ID, 200.00);
        bank.getAccountByID(ID).setMonths(3);
        boolean actual1 = withdrawCommandValidator.validateForWithdraw(new String[]{"withdraw", "12345678", "100.00"});
        assertTrue(actual1);
    }

    @Test
    public void withdraw_twice_from_checking_twice_a_month() {
        bank.addAccount(ID, new Checking(ID, APR));
        bank.depositMoneyByID(ID, 500.00);
        boolean actual = withdrawCommandValidator.validateForWithdraw(new String[]{"withdraw", "12345678", "200.00"});
        assertTrue(actual);
        bank.withdrawMoneyByID(ID, 200.00);
        boolean actual1 = withdrawCommandValidator.validateForWithdraw(new String[]{"withdraw", "12345678", "100.00"});
        bank.withdrawMoneyByID(ID, 100.00);
        assertTrue(actual1);
    }

    @Test
    public void withdraw_from_CD_before_twelve_months() {
        bank.addAccount(ID, new CD(ID, APR, BALANCE));
        bank.getAccountByID(ID).setMonths(5);
        boolean actual = withdrawCommandValidator.validateForWithdraw(new String[]{"withdraw", "12345678", "500.00"});
        assertFalse(actual);
    }

    @Test
    public void withdraw_from_CD_after_twelve_months_with_same_amount_and_balance() {
        bank.addAccount(ID, new CD(ID, APR, BALANCE));
        bank.depositMoneyByID(ID, BALANCE);
        bank.getAccountByID(ID).setMonths(13);
        boolean actual = withdrawCommandValidator.validateForWithdraw(new String[]{"withdraw", "12345678", "500.00"});
        bank.withdrawMoneyByID(ID, BALANCE);
        assertTrue(actual);
    }

    @Test
    public void withdraw_amount_from_CD_greater_than_balance_after_twelve_months() {
        bank.addAccount(ID, new CD(ID, APR, BALANCE));
        bank.getAccountByID(ID).setMonths(13);
        bank.withdrawMoneyByID(ID, 600.00);
        boolean actual = withdrawCommandValidator.validateForWithdraw(new String[]{"withdraw", "12345678", "600.00"});
        assertFalse(actual);
    }

    @Test
    public void withdraw_negative_amount_from_CD_before_twelve_months() {
        bank.addAccount(ID, new CD(ID, APR, BALANCE));
        bank.depositMoneyByID(ID, BALANCE);
        bank.getAccountByID(ID).setMonths(11);
        boolean actual = withdrawCommandValidator.validateForWithdraw(new String[]{"withdraw", "12345678", "-500.00"});
        bank.withdrawMoneyByID(ID, -500.00);
        assertFalse(actual);
    }

    @Test
    public void withdraw_money_less_than_balance_from_CD_after_twelve_months() {
        bank.addAccount(ID, new CD(ID, APR, BALANCE));
        bank.depositMoneyByID(ID, BALANCE);
        bank.getAccountByID(ID).setMonths(13);
        boolean actual = withdrawCommandValidator.validateForWithdraw(new String[]{"withdraw", "12345678", "4000.00"});
        assertFalse(actual);
    }
}
