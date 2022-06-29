package commands;

import exceptions.EmptyCollectionException;
import interaction.ResponseMessage;
import utils.CollectionManager;

/**
 * class for command 'max_by_description' to print element with max description
 */
public class MaxByDescriptionCommand extends AbstractCommand{
    private final CollectionManager collectionManager;

    public MaxByDescriptionCommand(CollectionManager collectionManager) {
        super("max_by_description", "вывести любой объект из коллекции, значение поля description которого является максимальным");
        this.collectionManager = collectionManager;

    }


    @Override
    public ResponseMessage execute(){
        try{
            if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
            return new ResponseMessage(collectionManager.maxByDescription());
        } catch (EmptyCollectionException e) {
            return new ResponseMessage(e.getMessage());
        }
    }
}
