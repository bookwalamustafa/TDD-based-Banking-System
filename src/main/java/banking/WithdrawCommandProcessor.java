package banking;

public class WithdrawCommandProcessor extends CommandProcessor{

    public WithdrawCommandProcessor(Bank bank) {
        super(bank);
    }

    public void processForWithdraw(String[] commandList) {
        bank.withdrawMoneyByID(commandList[1], Double.parseDouble(commandList[2]));
    }
}
