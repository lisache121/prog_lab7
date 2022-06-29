package exceptions;


public class EmptyCollectionException extends Exception{
    public EmptyCollectionException() {
        super("Collection is empty");
    }
}
