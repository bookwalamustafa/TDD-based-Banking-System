package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DepositCommandValidatorTest {

    public static final String ID = "12345678";
    public static final double APR = 0.6;
    public static final String ID1 = "87654321";
    DepositCommandValidator depositCommandValidator;
    Bank bank;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        depositCommandValidator = new DepositCommandValidator(bank);
    }

    @Test
    public void upper_limit_amount_for_checking() {
        bank.addAccount(ID1, new Checking(ID1, APR));
        boolean actual = depositCommandValidator.isValidAmount("1000", ID1);
        assertTrue(actual);
    }

    @Test
    public void upper_limit_amount_for_savings() {
        bank.addAccount(ID, new Savings(ID, APR));
        boolean actual = depositCommandValidator.isValidAmount("2500", ID);
        assertTrue(actual);
    }

    @Test
    public void lower_limit_amount_for_checking_and_savings() {
        bank.addAccount(ID, new Savings(ID, APR));
        boolean actual = depositCommandValidator.isValidAmount("0", ID);
        assertTrue(actual);
    }

    @Test
    public void higher_amount_deposit_for_savings() {
        bank.addAccount(ID, new Savings(ID, APR));
        boolean actual = depositCommandValidator.isValidAmount("3000", ID);
        assertFalse(actual);
    }

    @Test
    public void higher_amount_deposit_for_checking() {
        bank.addAccount(ID1, new Checking(ID1, APR));
        boolean actual = depositCommandValidator.isValidAmount("1500", ID1);
        assertFalse(actual);
    }

    @Test
    public void deposit_negative_amount() {
        bank.addAccount(ID, new Savings(ID, APR));
        boolean actual = depositCommandValidator.isValidAmount("-500", ID);
        assertFalse(actual);
    }

    @Test
    public void missing_ID() {
        boolean actual = depositCommandValidator.validateForDeposit(new String[]{"Deposit", "500"});
        assertFalse(actual);
    }

    @Test
    public void missing_amount() {
        boolean actual = depositCommandValidator.validateForDeposit(new String[]{"Deposit", "12345678"});
        assertFalse(actual);
    }

    @Test
    public void invalid_ID_in_command() {
        boolean actual =depositCommandValidator.validateForDeposit(new String[]{"Deposit", "1234ABCD", "500"});
        assertFalse(actual);
    }

    @Test
    public void invalid_amount_in_command() {
        boolean actual = depositCommandValidator.validateForDeposit(new String[]{"Deposit", "12345678", "fiveHundred"});
        assertFalse(actual);
    }

    @Test
    public void arguments_in_wrong_order() {
        boolean actual = depositCommandValidator.validateForDeposit(new String[]{"12345678", "deposit", "500"});
        assertFalse(actual);
    }

    @Test
    public void double_value_in_amount() {
        bank.addAccount(ID, new Savings(ID, APR));
        boolean actual = depositCommandValidator.validateForDeposit(new String[]{"Deposit", "12345678", "500.50"});
        assertTrue(actual);
    }

    @Test
    public void scientific_notation_in_amount() {
        bank.addAccount(ID, new Savings(ID, APR));
        boolean actual = depositCommandValidator.isValidAmount("20^2", ID);
        assertFalse(actual);
    }

    @Test
    public void deposit_in_bank_that_does_not_exist() {
        boolean actual = bank.accountInBank(ID);
        assertFalse(actual);
    }
}