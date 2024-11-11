package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandStorageTest {

    CommandStorage commandStorage;
    Bank bank;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        commandStorage = new CommandStorage(bank);
    }

    @Test
    public void add_one_invalid_command() {
        commandStorage.addInvalidCommand("Kriyate savings 12345678 0.6");
        int actual = (commandStorage.getInvalidCommands()).size();

        assertEquals(1, actual);
    }

    @Test
    public void add_two_invalid_commands() {
        commandStorage.addInvalidCommand("Create savings 12345678");
        commandStorage.addInvalidCommand("Create che2456 87654321 0.6");
        int actual = (commandStorage.getInvalidCommands()).size();

        assertEquals(2, actual);
    }

    @Test
    public void get_one_invalid_command() {
        commandStorage.addInvalidCommand("Deposit 500");
        List<String> actual = commandStorage.getInvalidCommands();
        List<String> tempList = new ArrayList<>();
        tempList.add("Deposit 500");

        assertEquals(tempList, actual);
    }


}