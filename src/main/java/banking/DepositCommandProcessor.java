package banking;

public class DepositCommandProcessor extends CommandProcessor{

    public DepositCommandProcessor(Bank bank) {
        super(bank);
    }

    public void processForDeposit(String[] commandList) {
        bank.depositMoneyByID(commandList[1], Double.parseDouble(commandList[2]));
    }
}