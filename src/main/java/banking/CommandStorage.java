package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandStorage {

    protected Bank bank;
    List<String> invalidCommands = new ArrayList<>();
    Map<String, List<String>> validCommands = new HashMap<>();

    public CommandStorage(Bank bank) {

        this.bank = bank;
    }

    public void addValidCommands(String command) {
        String[] commandArguments = command.stripTrailing().split(" ");

        if (commandArguments[0].equalsIgnoreCase("create") || commandArguments[0].equalsIgnoreCase("withdraw") || commandArguments[0].equalsIgnoreCase("deposit")) {
            insertIntoMap(validCommands, commandArguments[1], command);
        }
        else if (commandArguments[0].equalsIgnoreCase("transfer")) {
            insertIntoMap(validCommands, commandArguments[1], command);
            insertIntoMap(validCommands, commandArguments[2], command);
        }
    }

    private void insertIntoMap(Map<String, List<String>> map, String accountID, String command) {
        if (map.get(accountID) != null) {
            map.get(accountID).add(command);
        }
        else if (map.get(accountID) == null) {
            map.put(accountID, new ArrayList<>());
            map.get(accountID).add(command);
        }
    }

    public List<String> getOutput() {
        List<String> output = new ArrayList<>();
        for (String accountID : bank.getAccountOrder()) {
            output.add(getFormattedAccountStatus(accountID));
            if (validCommands.get(accountID) != null) {
                output.addAll(validCommands.get(accountID));
            }
        }
        output.addAll(invalidCommands);
        return output;
    }

    private String getFormattedAccountStatus(String accountID) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);
        Account account = bank.getAccounts().get(accountID);
        String formattedBalance = decimalFormat.format(account.getBalance());
        String formattedAPR = decimalFormat.format(account.getAPR());
        return account.getAccountType() + " " + account.getID() + " " + formattedBalance + " " + formattedAPR;
    }

    public void addInvalidCommand(String command) {
        invalidCommands.add(command);
    }

    public List<String> getInvalidCommands() {
        return invalidCommands;
    }
}
