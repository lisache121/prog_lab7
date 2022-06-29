package commandRequest;

import interaction.CommandRequest;
import utils.RecursionException;
import utils.WrongAmountOfElementsException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ScriptNeedRequest implements RequestType {
    private List<String> scriptHistory;

    public ScriptNeedRequest(List<String> scriptHistory) {
        this.scriptHistory = scriptHistory;
    }

    @Override
    public boolean checkArgs(CommandRequest c) {
        try {
            String scriptFile;
            if (!c.getArguments().isEmpty() & c.getDragon()==null){
                scriptFile = c.getArguments();
            }
            else throw new WrongAmountOfElementsException("this command must have an integer argument");

            if (new File(scriptFile).isDirectory() ) {
                System.out.println("Filename cannot be a directory.");
            }
            if (!Files.isReadable(new File(scriptFile).toPath())){
                System.out.println("There is no rights for reading file.");
            }
            Scanner scanner = new Scanner(Paths.get(scriptFile));
            if (!new File(scriptFile).exists()) throw new FileNotFoundException();
            if (!scanner.hasNext())
                throw new NoSuchElementException();
            for (String scripts : scriptHistory) {
                if (scriptFile.equals(scripts)) throw new RecursionException();
            }
            scriptHistory.add(scriptFile);
            return true;
        } catch (FileNotFoundException exception) {
            System.out.println("There is no file with this filename");
        } catch (NoSuchElementException exception) {
            System.out.println("This file is empty");
        } catch (RecursionException e) {
            System.out.println("You cannot execute script recursively.");
        } catch (IllegalStateException exception) {
            System.out.println("Unpredictable error.");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Run program again with correct filename.");
        } catch (WrongAmountOfElementsException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
