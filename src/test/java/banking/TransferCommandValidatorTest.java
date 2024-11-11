package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransferCommandValidatorTest {

    public static final String ID = "12345678";
    public static final double APR = 0.6;
    public static final String ID1 = "87654321";
    public static final double BALANCE = 200.00;
    public static final String ID2 = "12345677";
    public static final String ID3 = "87654322";
    Bank bank;
    CommandValidator commandValidator;
    TransferCommandValidator transferCommandValidator;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        commandValidator = new CommandValidator(bank);
        transferCommandValidator = new TransferCommandValidator(bank);
    }

    @Test
    public void empty_command() {
        boolean actual = commandValidator.validate("");
        assertFalse(actual);
    }

    @Test
    public void valid_transfer_from_savings_to_checking() {
        bank.addAccount(ID, new Savings(ID, APR));
        bank.addAccount(ID1, new Checking(ID1, APR));
        boolean actual = commandValidator.validate("transfer 12345678 87654321 200");
        assertTrue(actual);
    }

    @Test
    public void valid_transfer_from_checking_to_savings() {
        bank.addAccount(ID, new Checking(ID, APR));
        bank.addAccount(ID1, new Savings(ID1, APR));
        boolean actual = commandValidator.validate("transfer 12345678 87654321 200");
        assertTrue(actual);
    }

    @Test
    public void invalid_character_in_transfer_amount_provided() {
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "12345678", "87654321", "ABC"});
        assertFalse(actual);
    }

    @Test
    public void more_arguments_than_required() {
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "12345678", "87654321", "1.6"});
        assertFalse(actual);
    }

    @Test
    public void less_arguments_than_required() {
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"tranfser", "500"});
        assertFalse(actual);
    }

    @Test
    public void valid_for_case_sensitive_commands() {
        bank.addAccount(ID, new Savings(ID, APR));
        bank.addAccount(ID1, new Checking(ID1, APR));
        boolean actual = commandValidator.validate("TRanSFeR 12345678 87654321 500");
        assertTrue(actual);
    }

    @Test
    public void incorrect_spelling_for_transfer() {
        boolean actual = commandValidator.validate("traaaansfer 12345678 87654321 200");
        assertFalse(actual);
    }

    @Test
    public void withdrawal_ID_has_more_than_eight_digits() {
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "123456789", "87654321", "200"});
        assertFalse(actual);
    }

    @Test
    public void withdrawal_ID_has_less_than_eight_digits() {
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "1234567", "87654321", "200"});
        assertFalse(actual);
    }

    @Test
    public void deposit_ID_has_more_than_eight_digits() {
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "12345678", "987654321", "200"});
        assertFalse(actual);
    }

    @Test
    public void deposit_ID_has_less_than_eight_digits() {
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "12345678", "7654321", "200"});
        assertFalse(actual);
    }

    @Test
    public void withdraw_ID_has_invalid_character() {
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "ABCD1234", "87654321", "200"});
        assertFalse(actual);
    }

    @Test
    public void deposit_ID_has_invalid_character() {
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "12345678", "ABCD1234", "500"});
        assertFalse(actual);
    }

    @Test
    public void both_ID_are_invalid() {
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "123456789", "ABCD8765", "200"});
        assertFalse(actual);
    }

    @Test
    public void transfer_is_withdrawing_from_ID_that_does_not_exist() {
        bank.addAccount(ID1, new Checking(ID1, APR));
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "12345678", "87654321", "200"});
        assertFalse(actual);
    }

    @Test
    public void transfer_is_depositing_into_ID_that_does_not_exist() {
        bank.addAccount(ID, new Savings(ID, APR));
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "12345678", "87654321", "200"});
        assertFalse(actual);
    }

    @Test
    public void transfer_is_trying_to_deposit_into_CD_account() {
        bank.addAccount(ID, new Savings(ID,APR));
        bank.addAccount(ID1, new CD(ID1, APR, BALANCE));
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "12345678", "87654321", "200"});
        assertFalse(actual);
    }

    @Test
    public void transfer_is_trying_to_withdraw_from_CD_account() {
        bank.addAccount(ID, new CD(ID, APR, BALANCE));
        bank.addAccount(ID1, new Savings(ID1, APR));
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "12345678", "87654321", "200"});
        assertFalse(actual);
    }

    @Test
    public void transfer_within_checking() {
        bank.addAccount(ID, new Checking(ID, APR));
        bank.addAccount(ID2, new Checking(ID2, APR));
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "12345678", "12345677", "200"});
        assertTrue(actual);
    }

    @Test
    public void transfer_within_savings() {
        bank.addAccount(ID, new Savings(ID, APR));
        bank.addAccount(ID3, new Savings(ID3, APR));
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "12345678", "87654322", "200"});
        assertTrue(actual);
    }

    @Test
    public void transfer_zero_dollars_from_savings_to_checking() {
        bank.addAccount(ID, new Savings(ID, APR));
        bank.addAccount(ID1, new Checking(ID, APR));
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "12345678", "87654321", "0.0"});
        assertTrue(actual);
    }

    @Test
    public void transfer_zero_dollars_from_checkings_to_savings() {
        bank.addAccount(ID, new Checking(ID, APR));
        bank.addAccount(ID1, new Savings(ID1, APR));
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "12345678", "87654321", "0.0"});
        assertTrue(actual);
    }

    @Test
    public void transfer_zero_dollars_between_checking_accounts() {
        bank.addAccount(ID, new Checking(ID, APR));
        bank.addAccount(ID2, new Checking(ID2, APR));
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "12345678", "12345677", "0.0"});
        assertTrue(actual);
    }

    @Test
    public void transfer_zero_dollars_between_savings_accounts() {
        bank.addAccount(ID, new Savings(ID, APR));
        bank.addAccount(ID3, new Savings(ID3, APR));
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "12345678", "87654322", "0.0"});
        assertTrue(actual);
    }

    @Test
    public void transfer_negative_amount_from_savings_to_checking() {
        bank.addAccount(ID, new Savings(ID, APR));
        bank.addAccount(ID1, new Checking(ID1, APR));
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "12345678", "87654321", "-50.00"});
        assertFalse(actual);
    }

    @Test
    public void transfer_negative_amount_from_checking_to_savings() {
        bank.addAccount(ID, new Checking(ID, APR));
        bank.addAccount(ID, new Savings(ID, APR));
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "12345678", "87654321", "-50.00"});
        assertFalse(actual);
    }

    @Test
    public void transfer_negative_amount_between_checking_accounts() {
        bank.addAccount(ID, new Checking(ID, APR));
        bank.addAccount(ID2, new Checking(ID2, APR));
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "12345678", "12345677", "-50.00"});
        assertFalse(actual);
    }

    @Test
    public void transfer_negative_amount_between_savings_accounts() {
        bank.addAccount(ID, new Savings(ID, APR));
        bank.addAccount(ID3, new Savings(ID3, APR));
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "12345678", "87654322", "-50.00"});
        assertFalse(actual);
    }

    @Test
    public void transfer_maximum_four_hundred_dollars_between_checking_accounts() {
        bank.addAccount(ID, new Checking(ID, APR));
        bank.addAccount(ID2, new Checking(ID2, APR));
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "12345678", "12345677", "400.00"});
        assertTrue(actual);
    }

    @Test
    public void transfer_more_than_four_hundred_dollars_between_checking_accounts() {
        bank.addAccount(ID, new Checking(ID, APR));
        bank.addAccount(ID2, new Checking(ID2, APR));
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "12345678", "12345677", "450.00"});
        assertFalse(actual);
    }

    @Test
    public void transfer_maximum_one_thousand_dollars_between_savings_accounts() {
        bank.addAccount(ID, new Savings(ID, APR));
        bank.addAccount(ID3, new Savings(ID3, APR));
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "12345678", "87654322", "1000.00"});
        assertTrue(actual);
    }

    @Test
    public void transfer_more_than_one_thousand_dollars_between_savings_accounts() {
        bank.addAccount(ID, new Savings(ID, APR));
        bank.addAccount(ID3, new Savings(ID3, APR));
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "12345678", "87654322", "1050.00"});
        assertFalse(actual);
    }

    @Test
    public void transfer_maximum_four_hundred_dollars_from_checking_to_savings() {
        bank.addAccount(ID, new Checking(ID, APR));
        bank.addAccount(ID1, new Savings(ID1, APR));
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "12345678", "87654321", "400.00"});
        assertTrue(actual);
    }

    @Test
    public void transfer_more_than_four_hundred_dollars_from_checking_to_savings() {
        bank.addAccount(ID, new Checking(ID, APR));
        bank.addAccount(ID1, new Savings(ID1, APR));
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "12345678", "87654321", "450.00"});
        assertFalse(actual);
    }

    @Test
    public void transfer_between_same_checking_account() {
        bank.addAccount(ID, new Checking(ID, APR));
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "12345678", "12345678", "200.00"});
        assertFalse(actual);
    }

    @Test
    public void transfer_between_same_savings_account() {
        bank.addAccount(ID, new Savings(ID, APR));
        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "12345678", "12345678", "200.00"});
        assertFalse(actual);
    }


    @Test
    public void transfer_twice_from_savings_account_in_one_month() {
        bank.addAccount(ID, new banking.Savings(ID, APR));
        bank.addAccount(ID3, new banking.Savings(ID3, APR));
        bank.depositMoneyByID(ID, 500.00);

        transferCommandValidator.validateForTransfer(new String[]{"transfer", "12345678", "87654322", "200.00"});
        bank.withdrawMoneyByID(ID, 200.00);
        bank.depositMoneyByID(ID3, 200.00);

        boolean actual = transferCommandValidator.validateForTransfer(new String[]{"transfer", "12345678", "87654322", "200.00"});
        assertFalse(actual);
    }

    @Test
    public void everything_wrong() {
        boolean actual = commandValidator.validate("foobar");
        assertFalse(actual);
    }
}