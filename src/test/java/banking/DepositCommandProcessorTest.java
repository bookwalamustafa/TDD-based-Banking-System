package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepositCommandProcessorTest {

    public static final String ID = "12345678";
    public static final double BALANCE = 500.00;

    DepositCommandProcessor depositCommandProcessor;
    CreateCommandProcessor createCommandProcessor;
    Bank bank;

    String[] command = {"Deposit", "12345678", "500"};
    String[] command1 = {"Create", "Savings", "12345678", "1.6"};

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        depositCommandProcessor = new DepositCommandProcessor(bank);
        createCommandProcessor = new CreateCommandProcessor(bank);

        createCommandProcessor.processForCreate(command1);
    }

    @Test
    public void ID_has_correct_balance_for_one_account() {
        depositCommandProcessor.processForDeposit(command);
        double actual = bank.getAccountByID(ID).getBalance();

        assertEquals(BALANCE, actual);
    }

    @Test
    public void ID_has_correct_balance_for_two_accounts() {
        String[] command2 = {"Deposit", "12345678", "600"};
        depositCommandProcessor.processForDeposit(command);
        depositCommandProcessor.processForDeposit(command2);
        double actual = bank.getAccountByID(ID).getBalance();

        assertEquals(1100.00, actual);
    }
}