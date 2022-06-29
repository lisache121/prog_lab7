package utils;

/**
 * is throwing when number of command arguments incorrect
 */
public class WrongAmountOfElementsException extends Exception{
    public WrongAmountOfElementsException(String message) {
        super(message);
    }
}
