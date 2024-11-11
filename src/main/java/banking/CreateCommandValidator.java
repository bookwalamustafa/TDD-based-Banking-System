package banking;

public class CreateCommandValidator extends CommandValidator {

    public CreateCommandValidator(Bank bank) {
        super(bank);
    }

    public boolean validateForCreate(String[] commandList) {
        return (hasExpectedArgumentNum(commandList) &&
                super.isValidID(commandList[2]) &&
                isValidAPR(commandList[3]) &&
                !super.accountExistsInBank(commandList[2]));
    }

    public boolean hasExpectedArgumentNum(String[] commandList) {
        if ((commandList[1]).equalsIgnoreCase("checking") || (commandList[1]).equalsIgnoreCase("savings")) {
            return commandList.length == 4;
        }
        else if((commandList[1]).equalsIgnoreCase("cd")) {
            if (commandList.length == 5) {
                return balanceForCD(commandList[4]);
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    public boolean balanceForCD(String balance) {
        if (isValidAPRAndAmount(balance)) {
            return (Double.parseDouble(balance) >= 0.00) && (Double.parseDouble(balance) <= 10000.00);
        }
        else {
            return false;
        }
    }
    public boolean isValidAPR(String APR) {
        if (isValidAPRAndAmount(APR)) {
            return (Double.parseDouble(APR) >= 0.00 && Double.parseDouble(APR) <= 10.00);
        }
        else {
            return false;
        }
    }
}