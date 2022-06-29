package interaction;

import data.Dragon;
import java.util.Map;

public class CommandRequest implements Request{
    private String commandName;
    private String commandStringArgument;
    private Dragon commandObjectArgument;
    private final Map.Entry<String, String> loginData;

    public CommandRequest(String commandNm, String commandSA, Dragon commandOA, Map.Entry<String, String> logData) {
        commandName = commandNm;
        commandStringArgument = commandSA;
        commandObjectArgument = commandOA;
        loginData = logData;
    }
    @Override
    public String getArguments() {
        return commandStringArgument;
    }

    @Override
    public Dragon getDragon() {
        return (Dragon) commandObjectArgument;
    }

    @Override
    public String getCommandName() {
        return commandName;
    }

    @Override
    public Map.Entry<String, String> getUser() {
        return loginData;
    }

    public void setCommandObjectArgument(Dragon commandObjectArgument) {
        this.commandObjectArgument = commandObjectArgument;
    }
}
