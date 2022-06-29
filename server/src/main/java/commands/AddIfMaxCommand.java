package commands;

import databaseUtils.DBDragon;
import exceptions.CommandExecutingException;
import interaction.ResponseMessage;
import utils.CollectionManager;

/**
 * class for command 'add_if_max' add element if is greater than other elements in collection
 */
public class AddIfMaxCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    private DBDragon dbDragon;

    public AddIfMaxCommand(CollectionManager collectionManager, DBDragon dbDragon) {
        super("add_if_max {element}", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
        this.collectionManager = collectionManager;
        this.dbDragon = dbDragon;
    }

    @Override
    public ResponseMessage execute() {
        try{
            if (collectionManager.addIfMax(getArgs().getDragon()) || collectionManager.getCollection().size()==0){
                if (dbDragon.addDragon(getArgs().getDragon(), getArgs().getUser())){
                    collectionManager.addToCollection(getArgs().getDragon());
                    return new ResponseMessage("Element was successfully added to collection");
                }

            }
            else throw new CommandExecutingException("Element is not greater than maximum element in collection");
        } catch (CommandExecutingException e){
            return new ResponseMessage(e.getMessage());
        }
        return null;
    }
}
