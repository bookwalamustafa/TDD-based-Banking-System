package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PassTimeCommandValidatorTest {

    Bank bank;
    CommandValidator commandValidator;
    PassTimeCommandValidator passTimeCommandValidator;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        passTimeCommandValidator = new PassTimeCommandValidator(bank);
        commandValidator = new CommandValidator(bank);
    }

    @Test
    public void valid_command() {
        boolean actual = commandValidator.validate("Pass 5");
        assertTrue(actual);
    }

    @Test
    public void check_upper_boundary_for_time() {
        boolean actual = passTimeCommandValidator.validateForPassTime(new String[]{"Pass", "60"});
        assertTrue(actual);
    }

    @Test
    public void check_lower_boundary_for_time() {
        boolean actual = passTimeCommandValidator.validateForPassTime(new String[]{"Pass", "1"});
        assertTrue(actual);
    }

    @Test
    public void time_cannot_be_zero() {
        boolean actual = passTimeCommandValidator.validateForPassTime(new String[]{"Pass", "0"});
        assertFalse(actual);
    }

    @Test
    public void time_cannot_be_more_than_zero() {
        boolean actual = passTimeCommandValidator.validateForPassTime(new String[]{"Pass", "61"});
        assertFalse(actual);
    }

    @Test
    public void time_is_negative() {
        boolean actual = passTimeCommandValidator.validateForPassTime(new String[]{"Pass", "-12"});
        assertFalse(actual);
    }

    @Test
    public void invalid_character_for_time() {
        boolean actual = passTimeCommandValidator.validateForPassTime(new String[]{"Pass", "a2,"});
        assertFalse(actual);
    }

    @Test
    public void more_arguments_than_required() {
        boolean actual = passTimeCommandValidator.validateForPassTime(new String[]{"Pass 12 2024"});
        assertFalse(actual);
    }

    @Test
    public void less_arguments_than_required() {
        boolean actual = passTimeCommandValidator.validateForPassTime(new String[]{"Pass"});
        assertFalse(actual);
    }

    @Test
    public void typo_in_pass_command() {
        boolean actual = commandValidator.validate("pess 20");
        assertFalse(actual);
    }

    @Test
    public void everything_incorrect() {
        boolean actual = commandValidator.validate("foobar");
        assertFalse(actual);
    }

    @Test
    public void invalid_character_in_pass() {
        boolean actual = commandValidator.validate("Pa$$ 12");
        assertFalse(actual);
    }

    @Test
    public void no_argument_provided() {
        boolean actual = commandValidator.validate("");
        assertFalse(actual);
    }
}