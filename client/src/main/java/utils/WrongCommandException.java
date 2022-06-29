package utils;

public class WrongCommandException extends Exception{
    public WrongCommandException() {
        super("You have entered wrong command name or arguments. Try again.");
    }
}
