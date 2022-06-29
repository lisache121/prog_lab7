package exceptions;

public class CommandExecutingException extends RuntimeException{
    public CommandExecutingException(String message) {
        super(message);
    }
}
