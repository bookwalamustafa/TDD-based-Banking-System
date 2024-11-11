package banking;

public class WithdrawCommandValidator extends CommandValidator{

    public WithdrawCommandValidator(Bank bank) {
        super(bank);
    }

    public boolean validateForWithdraw(String[] command) {
        return (command.length == 3) && super.accountExistsInBank(command[1]) && isValidAmount(command[1], command[2]);
    }

    public boolean isValidAmount(String ID, String amount) {
        if (super.isValidAPRAndAmount(amount) && super.isValidID(ID)) {
            Account account = bank.getAccountByID(ID);
            return account.isValidMaxAmountForWithdraw(Double.parseDouble(amount));
        }
        else {
            return false;
        }
    }
}