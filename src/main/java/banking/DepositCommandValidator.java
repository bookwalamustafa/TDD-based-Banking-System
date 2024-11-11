package banking;

public class DepositCommandValidator extends CommandValidator {

    public DepositCommandValidator(Bank bank) {
        super(bank);
    }

    public boolean validateForDeposit(String[] commandList) {
        return (commandList.length == 3) && super.accountExistsInBank(commandList[1]) &&
                isValidAmount(commandList[2], commandList[1]);
    }

    public boolean isValidAmount(String amount, String ID) {
        if(isValidAPRAndAmount(amount) && isValidID(ID)) {
            Account account = bank.getAccountByID(ID);
            return account.isValidMaxAmountForDeposit(Double.parseDouble(amount));
        }
        else {
            return false;
        }
    }

    static boolean isReferencingCD(String[] command, Bank bank, int accountIDIndex) {
        Account account = bank.getAccountByID(command[accountIDIndex]);
        return account instanceof CD;
    }
}