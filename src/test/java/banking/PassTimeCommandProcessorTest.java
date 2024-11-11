package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PassTimeCommandProcessorTest {

    Bank bank;
    CreateCommandProcessor createCommandProcessor;
    DepositCommandProcessor depositCommandProcessor;
    PassTimeCommandProcessor passTimeCommandProcessor;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        createCommandProcessor = new CreateCommandProcessor(bank);
        depositCommandProcessor = new DepositCommandProcessor(bank);
        passTimeCommandProcessor = new PassTimeCommandProcessor(bank);
    }

    @Test
    public void account_closes_if_balance_is_zero() {
       createCommandProcessor.processForCreate(new String[]{"create", "checking", "87654321", "0.6"});
       passTimeCommandProcessor.processForPassTime(new String[]{"pass", "1"});
       assertNull(bank.getAccounts().get("87654321"));
    }

    @Test
    public void balance_between_zero_and_one_hundred_dollars() {
        createCommandProcessor.processForCreate(new String[]{"create", "checking", "87654321", "1.0"});
        depositCommandProcessor.processForDeposit(new String[]{"deposit", "87654321", "55"});
        passTimeCommandProcessor.processForPassTime(new String[]{"pass", "1"});
        double actual = Math.round(bank.getAccountByID("87654321").getBalance() * 100.0) / 100.0;
        assertEquals(30.03, actual);
    }

    @Test
    public void balance_greater_than_zero() {
        createCommandProcessor.processForCreate(new String[]{"create", "checking", "87654321", "1.0"});
        depositCommandProcessor.processForDeposit(new String[]{"deposit", "87654321", "500.0"});
        passTimeCommandProcessor.processForPassTime(new String[]{"pass", "1"});
        double actual = Math.round(bank.getAccountByID("87654321").getBalance() * 100) / 100.0;
        assertEquals(500.42, actual);
    }

    @Test
    public void pass_time_twice() {
        createCommandProcessor.processForCreate(new String[]{"create", "checking", "87654321", "3.0"});
        depositCommandProcessor.processForDeposit(new String[]{"deposit", "87654321", "1000.0"});
        passTimeCommandProcessor.processForPassTime(new String[]{"pass", "2"});
        double actual = Math.round(bank.getAccountByID("87654321").getBalance() * 100) / 100.0;
        assertEquals(1005.01, actual);
    }

    @Test
    public void pass_time_for_CD_account() {
        createCommandProcessor.processForCreate(new String[]{"create", "cd", "14557936", "2.1", "2000"});
        passTimeCommandProcessor.processForPassTime(new String[]{"pass", "1"});
        double actual = Math.round(bank.getAccountByID("14557936").getBalance() * 100) / 100.0;
        assertEquals(2014.04, actual);
    }
}
