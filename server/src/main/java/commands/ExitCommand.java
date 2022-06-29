package commands;


import exceptions.ExitException;
import log.Log;

/**
 * class for command 'exit' to exit from the program
 */
public class ExitCommand extends AbstractCommand{
    public ExitCommand() {
        super("exit", "завершить программу (без сохранения в файл)");
    }


    @Override
    public String execute()  {
        Log.logger.info("exit command. program will be stopped");
        System.exit(0);
        return new ExitException().getMessage();
    }
}
