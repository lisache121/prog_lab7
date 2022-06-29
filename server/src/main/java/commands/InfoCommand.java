package commands;


import exceptions.EmptyCollectionException;
import interaction.ResponseMessage;
import utils.CollectionManager;

/**
 * class for command 'info' to print information about collection
 */
public class InfoCommand extends AbstractCommand{
    private final CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции");
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
            return new ResponseMessage("Type of collection: " + collectionManager.getType() +'\n' + "Date of initialisation: " +
                    collectionManager.getTime() + '\n' + "Number of elements: " + collectionManager.getArraySize());

        } catch (EmptyCollectionException e) {
            return new ResponseMessage().error(e.getMessage());
        }
    }
}
