package commands;




import data.Dragon;
import exceptions.CommandExecutingException;
import exceptions.EmptyCollectionException;
import interaction.CommandRequest;
import interaction.ResponseMessage;
import utils.CollectionManager;

import java.util.List;


/**
 * class for command 'filter_less_than_age' to filter elements in collection
 */
public class FilterLessThanAgeCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    private int age;
    public FilterLessThanAgeCommand(CollectionManager collectionManager) {
        super("filter_less_than_age age", "вывести элементы, значение поля age которых меньше заданного");
        this.collectionManager = collectionManager;
    }


    @Override
    public ResponseMessage execute(){
        int age = Integer.parseInt(getArgs().getArguments());
        try{
            if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
            List<Dragon> list = collectionManager.filterByAge(age);
            if (list.isEmpty()){
                throw new CommandExecutingException("There are no dragons in collection younger than " + age);
            }

            System.out.println();
            String str = list.stream()
                    .map(e -> e.toConsole()).reduce("", (a,b)->a + b + "\n");
            return new ResponseMessage("Collection filtered by age: " + str);

        }catch(EmptyCollectionException e){
            return new ResponseMessage().error(e.getMessage());
        } catch (CommandExecutingException e){
            return new ResponseMessage(e.getMessage());
        }
    }
}
