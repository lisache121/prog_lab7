package commands;


import exceptions.WrongAmountOfElementsException;
import interaction.CommandRequest;


import java.sql.SQLException;

/**
 * Abstract Command class contains Object methods, name and description.
 */
public abstract class AbstractCommand {
    private CommandRequest arguments;
    private final String name;
    private final String description;

    public AbstractCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * @return arguments of the command
     */
    public CommandRequest getArgs() {
        return arguments;
    }

    public void setArgs(CommandRequest request) {
        this.arguments = request;
    }

    /**
     * @return Name of the command
     */
    public String getName() {
        return name;
    }

    /**
     * @return description of the command
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return if command successfully executed
     * @throws WrongAmountOfElementsException if number of arguments is not as expected
     */
    public abstract Object execute() throws SQLException;


}
