package commands;




import exceptions.WrongAmountOfElementsException;
import interaction.ResponseMessage;

import java.util.Map;

/**
 * class for command 'help'
 */
public class HelpCommand extends AbstractCommand{
    private Map<String,AbstractCommand> commands;

    public HelpCommand(Map<String,AbstractCommand> commands) {
        super("help", "вывести справку по доступным командам");
        this.commands = commands;
    }

    /**
     *
     * @return if command successfully executed
     * @throws WrongAmountOfElementsException if number of arguments is not as expected
     */
    @Override
    public ResponseMessage execute()  {

        StringBuilder string = new StringBuilder();
        string.append("The full list of commands is here: \n");
        for(AbstractCommand command: commands.values()){
            if (command instanceof SignUpCommand || command instanceof SignInCommand){
                continue;
            }
            string.append(command.getName()).append(" : ").append(command.getDescription()).append("\n");
        }
        return new ResponseMessage(string.toString());


    }
}
