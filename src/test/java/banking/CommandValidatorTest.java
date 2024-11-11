package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandValidatorTest {

    public static final String ID = "12345678";
    public static final double APR = 0.6;
    public static final String ID1 = "87654321";

    CommandValidator commandValidator;
    Bank bank;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        commandValidator = new CommandValidator(bank);
    }

    @Test
    public void command_with_space_for_create() {
        boolean actual = commandValidator.validate("       Create   savings     12345678     0.6");
        assertTrue(actual);
    }

    @Test
    public void missing_create_command() {
        boolean actual = commandValidator.validate("savings 12345678 1.6");
        assertFalse(actual);
    }

    @Test
    public void validate_command() {
        boolean actual = commandValidator.validate("Create savings 12345678 0.6");
        assertTrue(actual);
    }

    @Test
    public void case_sensitive_for_create() {
        boolean actual = commandValidator.validate("CREate savings 12345678 0.6");
        assertTrue(actual);
    }

    @Test
    public void invalid_create_command() {
        boolean actual = commandValidator.validate("Kriyate savings 12345678 0.6");
        assertFalse(actual);
    }

    @Test
    public void create_account_that_exists() {
        bank.addAccount(ID, new Savings(ID, APR));
        boolean actual = bank.addAccount(ID, new Savings(ID, APR));
        commandValidator.validate("Create savings 12345678 0.6");
        assertFalse(actual);
    }

    // CREATE ENDS HERE
    // DEPOSIT STARTS HERE

    @Test
    public void invalid_deposit_command() {
        boolean actual = commandValidator.validate("Dipaseet 12345678 500");
        assertFalse(actual);
    }

    @Test
    public void valid_deposit_command_for_savings() {
        bank.addAccount(ID, new Savings(ID, APR));
        boolean actual = commandValidator.validate("Deposit 12345678 500");
        assertTrue(actual);
    }

    @Test
    public void valid_deposit_command_for_checkings() {
        bank.addAccount(ID1, new Checking(ID1, APR));
        boolean actual = commandValidator.validate("Deposit 87654321 500");
        assertTrue(actual);
    }

    @Test
    public void missing_deposit_command() {
        boolean actual = commandValidator.validate("12345678 500");
        assertFalse(actual);
    }

    @Test
    public void case_sensitive_for_deposit() {
        bank.addAccount(ID, new Savings(ID, APR));
        boolean actual = commandValidator.validate("DEPosIT 12345678 500");
        assertTrue(actual);
    }

    @Test
    public void command_with_space_for_deposit() {
        bank.addAccount(ID, new Savings(ID, APR));
        boolean actual = commandValidator.validate("      Deposit     12345678   0.6   ");
        assertTrue(actual);
    }

    @Test
    public void invalid_character_in_ID() {
        boolean actual = commandValidator.isValidID("1234ABCD");
        assertFalse(actual);
    }

    @Test
    public void ID_less_than_eight_digits() {
        boolean actual = commandValidator.isValidID("1234567");
        assertFalse(actual);
    }

    @Test
    public void ID_more_than_eight_digits() {
        boolean actual = commandValidator.isValidID("123456789");
        assertFalse(actual);
    }

    @Test
    public void valid_ID() {
        boolean actual = commandValidator.isValidID("12345678");
        assertTrue(actual);
    }

    @Test
    public void no_arguments_provided() {
        boolean actual = commandValidator.validate(" ");
        assertFalse(actual);
    }

    @Test
    public void everything_incorrect() {
        boolean actual = commandValidator.validate("foobar");
        assertFalse(actual);
    }

    @Test
    public void withdraw_is_valid() {
        bank.addAccount("12345678", new Savings("12345678", 0.6));
        boolean actual = commandValidator.validate("withdraw 12345678 200");
        assertTrue(actual);
    }

    @Test
    public void invalid_spelling_of_withdraw() {
        boolean actual = commandValidator.validate("weedraa 12345678 200");
        assertFalse(actual);
    }

    @Test
    public void transfer_is_valid() {
        bank.addAccount("12345678", new Savings("12345678", 0.6));
        bank.addAccount("87654321", new Savings("87654321", 0.6));
        bank.depositMoneyByID("12345678", 500.00);
        boolean actual = commandValidator.validate("transfer 12345678 87654321 200.0");
        assertTrue(actual);
    }

    @Test
    public void invalid_spelling_of_transfer() {
        boolean actual = commandValidator.validate("traaaaansfer 500");
        assertFalse(actual);
    }

    @Test
    public void pass_time_is_valid() {
        boolean actual = commandValidator.validate("pass 5");
        assertTrue(actual);
    }

    @Test
    public void invalid_pass_time() {
        boolean actual = commandValidator.validate("pass 5.5");
        assertFalse(actual);
    }
}
