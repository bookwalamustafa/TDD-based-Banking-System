package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferCommandProcessorTest {

    Bank bank;
    CreateCommandProcessor createCommandProcessor;
    DepositCommandProcessor depositCommandProcessor;
    WithdrawCommandProcessor withdrawCommandProcessor;
    TransferCommandProcessor transferCommandProcessor;

    String createCommandForSavings[] = {"create", "savings", "12345678", "0.6"};
    String createCommandForChecking[] = {"create", "checking", "87654321", "0.6"};

    String depositCommandForSavings[] = {"deposit", "12345678", "500.0"};
    String depositCommandForChecking[] = {"deposit", "87654321", "500.0"};

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        createCommandProcessor = new CreateCommandProcessor(bank);
        depositCommandProcessor = new DepositCommandProcessor(bank);
        withdrawCommandProcessor = new WithdrawCommandProcessor(bank);
        transferCommandProcessor = new TransferCommandProcessor(bank);
    }

    @Test
    public void transfer_from_checking_to_savings() {
        createCommandProcessor.processForCreate(createCommandForChecking);
        createCommandProcessor.processForCreate(createCommandForSavings);
        depositCommandProcessor.processForDeposit(depositCommandForChecking);
        transferCommandProcessor.processForTransfer(new String[]{"transfer", "87654321", "12345678", "50.0"});
        double actual = bank.getAccountByID("12345678").getBalance();
        assertEquals(50.0, actual);
    }

    @Test
    public void transfer_from_savings_to_checking() {
        createCommandProcessor.processForCreate(createCommandForSavings);
        createCommandProcessor.processForCreate(createCommandForChecking);
        depositCommandProcessor.processForDeposit(depositCommandForSavings);
        transferCommandProcessor.processForTransfer(new String[]{"transfer", "12345678", "87654321", "50.0"});
        double actual = bank.getAccountByID("87654321").getBalance();
        assertEquals(50.0, actual);
    }

    @Test
    public void transfer_within_checking_accounts() {
        createCommandProcessor.processForCreate(createCommandForChecking);
        createCommandProcessor.processForCreate(new String[]{"create", "checking", "87654322", "0.6"});
        depositCommandProcessor.processForDeposit(depositCommandForChecking);
        transferCommandProcessor.processForTransfer(new String[]{"transfer", "87654321", "87654322", "200"});
        double actual = bank.getAccountByID("87654322").getBalance();
        assertEquals(200.0, actual);
    }

    @Test
    public void transfer_within_savings_accounts() {
        createCommandProcessor.processForCreate(createCommandForSavings);
        createCommandProcessor.processForCreate(new String[]{"create", "savings", "12345677", "0.6"});
        depositCommandProcessor.processForDeposit(depositCommandForSavings);
        transferCommandProcessor.processForTransfer(new String[]{"transfer", "12345678", "12345677", "200"});
        double actual = bank.getAccountByID("12345677").getBalance();
        assertEquals(200.0, actual);
    }

    @Test
    public void transfer_more_than_balance_from_savings_to_checking() {
        createCommandProcessor.processForCreate(createCommandForSavings);
        createCommandProcessor.processForCreate(createCommandForChecking);
        depositCommandProcessor.processForDeposit(depositCommandForSavings);
        transferCommandProcessor.processForTransfer(new String[]{"transfer", "12345678", "87654321", "600.0"});
        double actual = bank.getAccountByID("87654321").getBalance();
        assertEquals(500.0, actual);
    }

    @Test
    public void transfer_maximum_one_thousand_dollars_from_savings_to_checking() {
        createCommandProcessor.processForCreate(createCommandForSavings);
        createCommandProcessor.processForCreate(createCommandForChecking);
        depositCommandProcessor.processForDeposit(new String[]{"deposit", "12345678", "1000.0"});
        transferCommandProcessor.processForTransfer(new String[]{"transfer", "12345678", "87654321", "1000.0"});
        double actual = bank.getAccountByID("87654321").getBalance();
        assertEquals(1000.0, actual);
    }

    @Test
    public void transfer_maximum_one_thousand_dollars_within_savings() {
        createCommandProcessor.processForCreate(createCommandForSavings);
        createCommandProcessor.processForCreate(new String[]{"create", "savings", "12345677", "0.6"});
        depositCommandProcessor.processForDeposit(new String[]{"deposit", "12345678", "1000.0"});
        transferCommandProcessor.processForTransfer(new String[]{"transfer", "12345678", "12345677", "1000.0"});
        double actual = bank.getAccountByID("12345677").getBalance();
        assertEquals(1000.0, actual);
    }

    @Test
    public void transfer_maximum_four_hundred_dollars_from_checking_to_savings() {
        createCommandProcessor.processForCreate(createCommandForChecking);
        createCommandProcessor.processForCreate(createCommandForSavings);
        depositCommandProcessor.processForDeposit(new String[]{"deposit", "87654321", "400.0"});
        transferCommandProcessor.processForTransfer(new String[]{"transfer", "87654321", "12345678", "400.0"});
        double actual = bank.getAccountByID("12345678").getBalance();
        assertEquals(400.0, actual);
    }

    @Test
    public void transfer_maximum_four_hundred_dollars_within_checking() {
        createCommandProcessor.processForCreate(createCommandForChecking);
        createCommandProcessor.processForCreate(new String[]{"create", "checking", "87654322", "0.6"});
        depositCommandProcessor.processForDeposit(new String[]{"deposit", "87654321", "400.0"});
        transferCommandProcessor.processForTransfer(new String[]{"transfer", "87654321", "87654322", "400.0"});
        double actual = bank.getAccountByID("87654322").getBalance();
        assertEquals(400.0, actual);
    }

    @Test
    public void transfer_zero_dollars_from_savings_to_checking() {
        createCommandProcessor.processForCreate(createCommandForSavings);
        createCommandProcessor.processForCreate(createCommandForChecking);
        depositCommandProcessor.processForDeposit(depositCommandForSavings);
        transferCommandProcessor.processForTransfer(new String[]{"transfer", "12345678", "87654321", "0.0"});
        double actual = bank.getAccountByID("87654321").getBalance();
        assertEquals(0.0, actual);
    }

    @Test
    public void transfer_zero_dollars_from_checking_to_savings() {
        createCommandProcessor.processForCreate(createCommandForChecking);
        createCommandProcessor.processForCreate(createCommandForSavings);
        depositCommandProcessor.processForDeposit(depositCommandForChecking);
        transferCommandProcessor.processForTransfer(new String[]{"transfer", "87654321", "12345678", "0.0"});
        double actual = bank.getAccountByID("12345678").getBalance();
        assertEquals(0.0, actual);
    }

    @Test
    public void transfer_zero_dollars_within_savings() {
        createCommandProcessor.processForCreate(createCommandForSavings);
        createCommandProcessor.processForCreate(new String[]{"create", "savings", "12345677", "0.6"});
        depositCommandProcessor.processForDeposit(depositCommandForSavings);
        transferCommandProcessor.processForTransfer(new String[]{"transfer", "12345678", "12345677", "0.0"});
        double actual = bank.getAccountByID("12345677").getBalance();
        assertEquals(0.0, actual);
    }

    @Test
    public void transfer_zero_dollars_within_checking() {
        createCommandProcessor.processForCreate(createCommandForChecking);
        createCommandProcessor.processForCreate(new String[]{"create", "checking", "87654322", "0.6"});
        depositCommandProcessor.processForDeposit(depositCommandForChecking);
        transferCommandProcessor.processForTransfer(new String[]{"transfer", "87654321", "87654322", "0.0"});
        double actual = bank.getAccountByID("87654322").getBalance();
        assertEquals(0.0, actual);
    }

    @Test
    public void transfer_from_savings_to_checking_after_a_month() {
        createCommandProcessor.processForCreate(createCommandForSavings);
        createCommandProcessor.processForCreate(createCommandForChecking);
        depositCommandProcessor.processForDeposit(depositCommandForSavings);
        transferCommandProcessor.processForTransfer(new String[]{"transfer", "12345678", "87654321", "100.00"});
        bank.getAccountByID("12345678").setMonths(5);
        transferCommandProcessor.processForTransfer(new String[]{"transfer", "12345678", "87654321", "100.00"});
        double actual = bank.getAccountByID("87654321").getBalance();
        assertEquals(200.00, actual);
    }

    @Test
    public void transfer_within_savings_after_a_month() {
        createCommandProcessor.processForCreate(createCommandForSavings);
        createCommandProcessor.processForCreate(new String[]{"create", "savings", "12345677", "0.6"});
        depositCommandProcessor.processForDeposit(depositCommandForSavings);
        transferCommandProcessor.processForTransfer(new String[]{"transfer", "12345678", "12345677", "100.0"});
        bank.getAccountByID("12345678").setMonths(5);
        transferCommandProcessor.processForTransfer(new String[]{"transfer", "12345678", "12345677", "100.0"});
        double actual = bank.getAccountByID("12345677").getBalance();
        assertEquals(200.0, actual);
    }

    @Test
    public void multiple_transfers_from_checking_to_savings() {
        createCommandProcessor.processForCreate(createCommandForChecking);
        createCommandProcessor.processForCreate(createCommandForSavings);
        depositCommandProcessor.processForDeposit(depositCommandForChecking);
        transferCommandProcessor.processForTransfer(new String[]{"transfer", "87654321", "12345678", "100.0"});
        transferCommandProcessor.processForTransfer(new String[]{"transfer", "87654321", "12345678", "100.0"});
        double actual = bank.getAccountByID("12345678").getBalance();
        assertEquals(200.0, actual);
    }

    @Test
    public void multiple_transfers_within_checking() {
        createCommandProcessor.processForCreate(createCommandForChecking);
        createCommandProcessor.processForCreate(new String[]{"create", "checking", "87654322", "0.6"});
        depositCommandProcessor.processForDeposit(depositCommandForChecking);
        transferCommandProcessor.processForTransfer(new String[]{"transfer", "87654321", "87654322", "100.0"});
        transferCommandProcessor.processForTransfer(new String[]{"transfer", "87654321", "87654322", "100.0"});
        double actual = bank.getAccountByID("87654322").getBalance();
        assertEquals(200.0, actual);
    }
}