package commandRequest;

import exceptions.ExitException;
import interaction.CommandRequest;
import utils.WrongAmountOfElementsException;

public class NoArgNeedRequest implements RequestType {

    @Override
    public boolean checkArgs(CommandRequest c) {
        try{
            if (c.getArguments().equals("") && c.getDragon()==null){
                if (c.getCommandName().equals("exit")){
                    System.out.println(new ExitException().getMessage());
                    System.exit(0);
                }
                return true;
            }
            else throw new WrongAmountOfElementsException("this command must not have arguments");
        } catch (WrongAmountOfElementsException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
