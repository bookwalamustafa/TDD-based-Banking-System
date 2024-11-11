package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateCommandProcessorTest {

    public static final String ID = "12345678";
    public static final String ID1 = "14557936";
    public static final double BALANCE = 1000;
    public static final double APR = 0.6;

    private CreateCommandProcessor createCommandProcessor;
    private Bank bank;
    String[] command = {"Create", "checking", "12345678", "0.6"};

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        createCommandProcessor = new CreateCommandProcessor(bank);
    }

    @Test
    public void one_account_added() {
        createCommandProcessor.processForCreate(command);
        int actual = bank.getAccounts().size();

        assertEquals(1, actual);
    }

    @Test
    public void two_accounts_added() {
        String[] command1 = {"Create", "savings", "87654321", "0.6"};
        createCommandProcessor.processForCreate(command);
        createCommandProcessor.processForCreate(command1);
        int actual = bank.getAccounts().size();

        assertEquals(2, actual);
    }

    @Test
    public void account_has_correct_ID() {
        createCommandProcessor.processForCreate(command);
        String actual = bank.getAccountByID(ID).getID();

        assertEquals(ID, actual);
    }

    @Test
    public void account_has_correct_APR() {
        createCommandProcessor.processForCreate(command);
        double actual = bank.getAccountByID(ID).getAPR();

        assertEquals(APR, actual);
    }

    @Test
    public void cd_account_has_correct_balance() {
        String[] command = {"Create", "cd", "14557936", "1.6", "1000"};
        createCommandProcessor.processForCreate(command);
        double actual = bank.getAccountByID(ID1).getBalance();
        assertEquals(BALANCE, actual);
    }
}