package commands;


import exceptions.EmptyCollectionException;
import interaction.ResponseMessage;
import utils.CollectionManager;

/**
 * class for command 'remove_by_id
 */
public class RemoveByIdCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    private long id;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id id", "удалить элемент из коллекции по его id");
        this.collectionManager = collectionManager;
    }

    /**
     *
     * @return if command successfully executed
     */
    @Override
    public ResponseMessage execute(){

        try {
            collectionManager.updateCollection();
            if (collectionManager.getCollection().size()==0) throw new EmptyCollectionException();
            return new ResponseMessage(collectionManager.removeFromCollection(Long.parseLong(getArgs().getArguments()), getArgs().getUser()));


        } catch (EmptyCollectionException e) {
            return new ResponseMessage().error(e.getMessage());
        }

    }
}
