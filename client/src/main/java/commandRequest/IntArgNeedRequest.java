package commandRequest;

import interaction.CommandRequest;
import utils.WrongAmountOfElementsException;

public class IntArgNeedRequest implements RequestType {

    @Override
    public boolean checkArgs(CommandRequest c) {
        try{
            if (!c.getArguments().isEmpty() & c.getDragon()==null){
                if (c.getArguments().matches("[-+]?\\d+")) {
                    return true;
                }
                else throw new WrongAmountOfElementsException("this command must have an integer argument");
            }
            else throw new WrongAmountOfElementsException("this command must have an integer argument");
        } catch (WrongAmountOfElementsException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
