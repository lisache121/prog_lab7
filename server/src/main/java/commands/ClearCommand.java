package commands;


import exceptions.CommandExecutingException;
import interaction.ResponseMessage;
import utils.CollectionManager;

/**
 * class for command 'clear' to clear collection
 */
public class ClearCommand extends AbstractCommand{
    private final CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
    }


    @Override
    public ResponseMessage execute() {
        try{
            return new ResponseMessage(collectionManager.clearCollection(getArgs().getUser()));
        } catch (CommandExecutingException e) {
            return new ResponseMessage().error(e.getMessage());
        }
    }
}
