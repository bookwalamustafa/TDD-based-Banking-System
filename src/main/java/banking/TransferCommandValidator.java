package banking;

public class TransferCommandValidator extends CommandValidator{

    private final CommandValidator validator;

    public TransferCommandValidator(Bank bank) {
        super(bank);
        validator = new CommandValidator(bank);
    }

    public boolean validateForTransfer(String[] command) {
        return (command.length == 4) && accountExists(command);
    }

    public boolean accountExists(String[] command) {
        if (super.accountExistsInBank(command[1]) && super.accountExistsInBank(command[2])) {
            if (command[1].equals(command[2])) {
                return false;
            }
            if (DepositCommandValidator.isReferencingCD(command, bank, 1) || (DepositCommandValidator.isReferencingCD(command, bank, 2))) {
                return false;
            }
            return validateForDeposit(command) && validateForWithdraw(command);
        }
        return false;
    }

    private boolean validateForDeposit(String[] command) {
        String depositID = command[2];
        String amount = command[3];
        String depositCommand = "deposit " + depositID + " " + amount;
        return validator.validate(depositCommand);
    }

    private boolean validateForWithdraw(String[] command) {
        String withdrawID = command[1];
        String amount = command[3];
        String withdrawCommand = "withdraw " + withdrawID + " " + amount;
        return validator.validate(withdrawCommand);
    }
}