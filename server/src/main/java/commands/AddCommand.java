package commands;


import databaseUtils.DBDragon;
import interaction.ResponseMessage;
import utils.CollectionManager;

/**
 * class for command 'add' to add elements to collection
 */
public class AddCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    private DBDragon dbDragon;

    public AddCommand(CollectionManager collectionManager, DBDragon dbDragon) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
        this.dbDragon = dbDragon;

    }

    /**
     *
     * @return if command successfully executed
     *      * @throws WrongAmountOfElementsException if number of arguments is not as expected
     */
    @Override
    public ResponseMessage execute() {
        if (dbDragon.addDragon(getArgs().getDragon(), getArgs().getUser())){
            collectionManager.addToCollection(getArgs().getDragon());
            return new ResponseMessage("Element was successfully added to collection");
        }
        return new ResponseMessage().error("Element was not added");
    }


}
