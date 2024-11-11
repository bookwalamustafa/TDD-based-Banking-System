package banking;

public class TransferCommandProcessor extends CommandProcessor{

    public TransferCommandProcessor(Bank bank) {
        super(bank);
    }

    public void processForTransfer(String[] commandList) {
        double previousBalance = bank.getAccountByID(commandList[1]).getBalance();
        bank.withdrawMoneyByID(commandList[1], Double.parseDouble(commandList[3]));
        if (previousBalance < Double.parseDouble(commandList[3])){
            bank.depositMoneyByID(commandList[2], previousBalance);

        }
        else {
            bank.depositMoneyByID(commandList[2], Double.parseDouble(commandList[3]));
        }
    }
}