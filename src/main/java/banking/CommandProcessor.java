package banking;

public class CommandProcessor {

    protected Bank bank;

    public CommandProcessor(Bank bank) {
        this.bank = bank;
    }

    public void process(String command) {
        String[] commandList = command.split("\\s+");
        if (commandList[0].equalsIgnoreCase("create")) {
            CreateCommandProcessor createCommandProcessor = new CreateCommandProcessor(bank);
            createCommandProcessor.processForCreate(commandList);
        }
        else if (commandList[0].equalsIgnoreCase("deposit")) {
            DepositCommandProcessor depositCommandProcessor = new DepositCommandProcessor(bank);
            depositCommandProcessor.processForDeposit(commandList);
        }
        else if (commandList[0].equalsIgnoreCase("withdraw")){
            WithdrawCommandProcessor withdrawCommandProcessor = new WithdrawCommandProcessor(bank);
            withdrawCommandProcessor.processForWithdraw(commandList);
        }
        else if (commandList[0].equalsIgnoreCase("transfer")){
            TransferCommandProcessor transferCommandProcessor = new TransferCommandProcessor(bank);
            transferCommandProcessor.processForTransfer(commandList);
        }
        else {
            PassTimeCommandProcessor passTimeCommandProcessor = new PassTimeCommandProcessor(bank);
            passTimeCommandProcessor.processForPassTime(commandList);
        }
    }
}