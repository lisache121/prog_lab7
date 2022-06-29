package exceptions;

public class ExitException extends Exception{
    public ExitException() {
        super("exit command. program will be stopped");

    }
}
