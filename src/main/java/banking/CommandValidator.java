package banking;

public class CommandValidator {

    protected Bank bank;

    public CommandValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command) {
        command = command.trim();
        String[] commandList = command.split("\\s+");
        if (commandList[0].equalsIgnoreCase("create")) {
            CreateCommandValidator createCommandValidator = new CreateCommandValidator(bank);
            return createCommandValidator.validateForCreate(commandList);
        }
        else if (commandList[0].equalsIgnoreCase("deposit")) {
            DepositCommandValidator depositCommandValidator = new DepositCommandValidator(bank);
            return depositCommandValidator.validateForDeposit(commandList);
        }
        else if (commandList[0].equalsIgnoreCase("withdraw")) {
            WithdrawCommandValidator withdrawCommandValidator = new WithdrawCommandValidator(bank);
            return withdrawCommandValidator.validateForWithdraw(commandList);
        }
        else if (commandList[0].equalsIgnoreCase("transfer")) {
            TransferCommandValidator transferCommandValidator = new TransferCommandValidator(bank);
            return transferCommandValidator.validateForTransfer(commandList);
        }
        else if (commandList[0].equalsIgnoreCase("pass")) {
            PassTimeCommandValidator passTimeCommandValidator = new PassTimeCommandValidator(bank);
            return passTimeCommandValidator.validateForPassTime(commandList);
        }
        else {
            return false;
        }
    }

    public boolean isValidID(String ID) {
        return ID.matches("\\d{8}");
    }

    public boolean isValidAPRAndAmount(String number) {
        return number.matches("\\d+\\.?\\d*|\\.\\d+([eE][+-]?\\d+)?");
    }

    public boolean accountExistsInBank(String ID) {
        return bank.accountInBank(ID);
    }
}