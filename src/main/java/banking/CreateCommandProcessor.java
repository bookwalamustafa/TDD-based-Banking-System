package banking;

public class CreateCommandProcessor extends CommandProcessor{

    public CreateCommandProcessor(Bank bank) {
        super(bank);
    }

    public void processForCreate(String[] commandList) {
        if (commandList[1].equalsIgnoreCase("savings")) {
            bank.addSavings(commandList[2], Double.parseDouble(commandList[3]));
        }
        else if (commandList[1].equalsIgnoreCase("checking")) {
            bank.addChecking(commandList[2], Double.parseDouble(commandList[3]));
        }
        else {
            bank.addCD(commandList[2], Double.parseDouble(commandList[3]), Double.parseDouble(commandList[4]));
        }
    }
}