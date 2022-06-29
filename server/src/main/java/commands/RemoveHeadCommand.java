package commands;


import exceptions.EmptyCollectionException;
import interaction.ResponseMessage;
import utils.CollectionManager;

/**
 * class for command 'remove_head' to remove first element
 */
public class RemoveHeadCommand extends AbstractCommand{
    private final CollectionManager collectionManager;


    public RemoveHeadCommand(CollectionManager collectionManager) {
        super("remove_head", "вывести первый элемент коллекции и удалить его");
        this.collectionManager = collectionManager;
    }

    /**
     *
     * @return if command successfully executed
     */
    @Override
    public ResponseMessage execute(){
        try{
            collectionManager.updateCollection();
            if (collectionManager.getCollection().size()==0) throw new EmptyCollectionException();
            Long id = collectionManager.getCollection().getFirst().getId();
//            collectionManager.removeFromCollection(d);
            return new ResponseMessage(collectionManager.removeFromCollection(id, getArgs().getUser()));

        } catch (EmptyCollectionException e){
            return new ResponseMessage(e.getMessage());
        }
    }
}
