package commands;

import data.Dragon;
import databaseUtils.DBDragon;
import exceptions.CommandExecutingException;
import exceptions.EmptyCollectionException;
import interaction.ResponseMessage;
import utils.CollectionManager;

/**
 * class for command 'update' to update element in collection by id
 */
public class UpdateByIdCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private DBDragon dbDragon;

    public UpdateByIdCommand(CollectionManager collectionManager, DBDragon dbDragon) {
        super("update id {element}", "обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
        this.dbDragon = dbDragon;
    }

    /**
     *
     *@return if command successfully executed
     */
    @Override
    public ResponseMessage execute() {
        Long id = Long.parseLong(getArgs().getArguments());
        try {
            collectionManager.updateCollection();
            if (collectionManager.getCollection().size()==0) throw new EmptyCollectionException();
            Dragon d = collectionManager.getById(id);
            if (d!= null && d.getCreatorName().equals(getArgs().getUser().getKey())){
                    if (dbDragon.updateById(getArgs().getDragon(), id, getArgs().getUser())){
                return new ResponseMessage("Element with id " + id.toString() + " was successfully added");}
                    return new ResponseMessage("Error — couldn't update this element.");
            }
            if (d != null) throw new CommandExecutingException("The element with id " + id +
                    " can't be updated by this user");
            throw new CommandExecutingException("there is no element with id " + id + " in collection");

        } catch (EmptyCollectionException e) {
            return new ResponseMessage(e.getMessage());
        }

    }
}
