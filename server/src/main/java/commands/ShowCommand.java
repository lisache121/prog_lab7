package commands;


import data.Dragon;
import exceptions.EmptyCollectionException;
import interaction.ResponseMessage;
import interaction.Status;
import utils.CollectionManager;

import java.util.List;

/**
 * class for command 'show' to show elements in collection
 */
public class ShowCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    public ShowCommand(CollectionManager collectionManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.collectionManager = collectionManager;
    }

    @Override
    public ResponseMessage execute() {
        try {
            ResponseMessage responseMessage = new ResponseMessage(collectionManager.showCollection());
            responseMessage.setStatus(Status.OK);

            return responseMessage;
        } catch (EmptyCollectionException e) {
            return new ResponseMessage().error(e.getMessage());
        }
    }
}
