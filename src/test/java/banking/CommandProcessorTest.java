package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandProcessorTest {

    public static final String ID = "12345678";
    public static final double BALANCE = 500.00;
    public static final String ID1 = "87654321";
    CommandProcessor commandProcessor;
    Bank bank;

    String command0 = "Create savings 12345678 0.6";
    String commandX = "Create checking 87654321 0.6";
    String command1 = "Deposit 12345678 500.0";
    String command2 = "Withdraw 12345678 100.0";
    String command3 = "transfer 12345678 87654321 50.0";
    String command4 = "pass 1";

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        commandProcessor = new CommandProcessor(bank);
    }

    @Test
    public void one_account_added() {
        commandProcessor.process(command0);
        int actual = bank.getAccounts().size();

        assertEquals(1, actual);
    }

    @Test
    public void ID_has_correct_balance_for_one_account() {
        commandProcessor.process(command0);
        commandProcessor.process(command1);
        double actual = bank.getAccountByID(ID).getBalance();
        assertEquals(BALANCE, actual);
    }

    @Test
    public void ID_has_correct_balance_after_withdrawal() {
        commandProcessor.process(command0);
        commandProcessor.process(command1);
        commandProcessor.process(command2);
        double actual = bank.getAccountByID(ID).getBalance();
        assertEquals(400.0, actual);
    }

    @Test
    public void ID_has_correct_balance_after_transfer() {
        commandProcessor.process(command0);
        commandProcessor.process(commandX);
        commandProcessor.process(command1);
        commandProcessor.process(command3);
        double actual = bank.getAccountByID(ID1).getBalance();
        assertEquals(50.0, actual);
    }

    @Test
    public void ID_has_correct_balance_after_one_month() {
        commandProcessor.process(command0);
        commandProcessor.process(command1);
        commandProcessor.process(command4);
        double actual = bank.getAccountByID(ID).getBalance();
        assertEquals(500.25, actual);
    }

}
