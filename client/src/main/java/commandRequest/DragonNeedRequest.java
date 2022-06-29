package commandRequest;

import interaction.CommandRequest;
import utils.WrongAmountOfElementsException;

import java.util.Scanner;

public class DragonNeedRequest implements RequestType {
    private Scanner scanner;


    @Override
    public boolean checkArgs(CommandRequest c) {
        try{
            if (c.getCommandName().equals("update")  ){
                if (!c.getArguments().isEmpty() && c.getArguments().matches("[-+]?\\d+")){
                    return true;
                }
                throw new WrongAmountOfElementsException("this command must have an integer argument");
            }
            else if (c.getArguments().isEmpty()){
                return true;
            }
            else throw new WrongAmountOfElementsException("this command must not have arguments");
        } catch (WrongAmountOfElementsException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }




}
