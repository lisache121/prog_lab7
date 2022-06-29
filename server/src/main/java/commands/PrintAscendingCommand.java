package commands;


import exceptions.EmptyCollectionException;
import interaction.ResponseMessage;
import utils.CollectionManager;
import utils.Comparator;

/**
 * class for command 'print_ascending' to print elements in ascending order
 */
public class PrintAscendingCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    private Comparator comparator = new Comparator();

    public PrintAscendingCommand(CollectionManager collectionManager) {
        super("print_ascending", "вывести элементы коллекции в порядке возрастания");
        this.collectionManager = collectionManager;

    }

    /**
     *
     * @return if command successfully executed

     */
    @Override
    public ResponseMessage execute(){
        try{
            if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
            collectionManager.sort();
            String str = collectionManager.getCollection().stream()
                    .map(e -> e.toConsole()).reduce("", (a,b)->a + b + "\n");
            return new ResponseMessage("Elements in collection in ascending order: " + '\n' +  str);
        } catch (EmptyCollectionException e) {
            return new ResponseMessage(e.getMessage());
        }
    }
}
