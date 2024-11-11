package banking;

import java.util.regex.Pattern;

public class PassTimeCommandValidator extends CommandValidator {

    public PassTimeCommandValidator(Bank bank) {
        super(bank);
    }

    public boolean validateForPassTime(String[] command) {
        return (command.length == 2) && isValidTime(command[1]);
    }

    public boolean isValidTime(String time) {
        return Pattern.matches("^(?:[1-9]|[1-5][0-9]|60)$", time);
    }
}
