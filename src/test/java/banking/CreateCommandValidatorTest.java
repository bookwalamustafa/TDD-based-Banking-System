package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateCommandValidatorTest {

    public static final String ID = "12345678";
    public static final double APR = 0.6;

    CreateCommandValidator createCommandValidator;
    Bank bank;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        createCommandValidator = new CreateCommandValidator(bank);
    }

    @Test
    public void invalid_account_type() {
        boolean actual = createCommandValidator.validateForCreate(new String[]{"Create", "bankAccount", "12345678", "0.6"});
        assertFalse(actual);
    }

    @Test
    public void incorrect_spelling_of_account_type() {
        boolean actual = createCommandValidator.validateForCreate(new String[]{"Create", "saaaavings", "12345678", "0.6"});
        assertFalse(actual);
    }

    @Test
    public void lower_limit_APR() {
        boolean actual = createCommandValidator.isValidAPR("0.0");
        assertTrue(actual);
    }

    @Test
    public void upper_limit_APR() {
        boolean actual = createCommandValidator.isValidAPR("10.0");
        assertTrue(actual);
    }

    @Test
    public void APR_in_range() {
        boolean actual = createCommandValidator.isValidAPR("5");
        assertTrue(actual);
    }

    @Test
    public void APR_out_of_range() {
        boolean actual = createCommandValidator.isValidAPR("12");
        assertFalse(actual);
    }

    @Test
    public void APR_is_negative() {
        boolean actual = createCommandValidator.isValidAPR("-12");
        assertFalse(actual);
    }

    @Test
    public void lower_limit_AMOUNT() {
        boolean actual = createCommandValidator.validateForCreate(new String[]{"Create", "cd", "14557936", "1.6", "1000"});
        assertTrue(actual);
    }

    @Test
    public void upper_limit_AMOUNT() {
        boolean actual = createCommandValidator.validateForCreate(new String[]{"Create", "cd", "14557936", "1.6", "10000"});
        assertTrue(actual);
    }

    @Test
    public void AMOUNT_out_of_range() {
        boolean actual = createCommandValidator.validateForCreate(new String[]{"Create", "cd", "14557936", "1.6", "12000"});
        assertFalse(actual);
    }

    @Test
    public void AMOUNT_is_negative() {
        boolean actual = createCommandValidator.validateForCreate(new String[]{"Create", "cd", "14557936", "1.6", "-12000"});
        assertFalse(actual);
    }

    @Test
    public void more_arguments_than_required() {
        boolean actual = createCommandValidator.validateForCreate(new String[]{"Create", "savings", "1245678", "0.6", "1000"});
        assertFalse(actual);
    }

    @Test
    public void less_arguments_than_required() {
        boolean actual = createCommandValidator.validateForCreate(new String[]{"Create", "savings", "12345678"});
        assertFalse(actual);
    }

    @Test
    public void invalid_character_in_APR() {
        boolean actual = createCommandValidator.isValidAPR("A.6");
        assertFalse(actual);
    }

    @Test
    public void invalid_character_in_BALANCE() {
        boolean actual = createCommandValidator.validateForCreate(new String[]{"Create", "cd", "14557936", "1.6", "AB00"});
        assertFalse(actual);
    }

    @Test
    public void arguments_in_wrong_order() {
        boolean actual = createCommandValidator.validateForCreate(new String[]{"savings", "create", "12345678", "0.6"});
        assertFalse(actual);
    }

    @Test
    public void missing_account_type() {
        boolean actual = createCommandValidator.validateForCreate(new String[]{"create", "12345678", "0.6"});
        assertFalse(actual);
    }

    @Test
    public void missing_ID() {
        boolean actual = createCommandValidator.validateForCreate(new String[]{"Create", "savings", "0.6"});
        assertFalse(actual);
    }

    @Test
    public void missing_APR() {
        boolean actual = createCommandValidator.validateForCreate(new String[]{"Create", "savings", "12345678"});
        assertFalse(actual);
    }

    @Test
    public void missing_AMOUNT_for_CD() {
        boolean actual = createCommandValidator.validateForCreate(new String[]{"Create", "cd", "14557936", "0.6"});
        assertFalse(actual);
    }

    @Test
    public void AMOUNT_with_decimal_values() {
        boolean actual = createCommandValidator.validateForCreate(new String[]{"Create", "cd", "14557936", "0.6", "2000.50"});
        assertTrue(actual);
    }

    @Test
    public void AMOUNT_with_scientific_notation() {
        boolean actual = createCommandValidator.validateForCreate(new String[]{"Create", "cd", "14557936", "0.6", "40^2"});
        assertFalse(actual);
    }

    @Test
    public void APR_with_two_decimal_places() {
        boolean actual = createCommandValidator.isValidAPR("1.78");
        assertTrue(actual);
    }

    @Test
    public void APR_with_scientific_notation() {
        boolean actual = createCommandValidator.isValidAPR("1.6^4");
        assertFalse(actual);
    }
}