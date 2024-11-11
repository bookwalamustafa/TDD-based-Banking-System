package banking;

public class PassTimeCommandProcessor extends CommandProcessor{

    public PassTimeCommandProcessor(Bank bank) {
        super(bank);
    }

    public void processForPassTime(String[] commandList) {
            bank.passTime(Integer.parseInt(commandList[1]));
        }
}