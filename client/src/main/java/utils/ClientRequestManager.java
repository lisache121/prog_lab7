package utils;

import client.Client;
import commandRequest.*;
import data.Dragon;
import exceptions.IncorrectInputInScriptException;
import interaction.CommandRequest;
import interaction.ResponseMessage;
import interaction.Status;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class ClientRequestManager {
    private DragonMaker maker = new DragonMaker();
    private final Map<String, RequestType> commandRequests = new HashMap<>();
    private Deque<Scanner> scannerStack = new ArrayDeque<>();
    private Scanner scanner;
    private Client client;
    private List<String> scriptHistory = new ArrayList<>();
    private boolean mode = false;
    private final AuthorizationModule authorizationModule = new AuthorizationModule();
    private Map.Entry<String,String> login = null;

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public ClientRequestManager(Client cl){
        this.client = cl;
        initMap(commandRequests);
        authorization();
        interactiveMod("");
    }

    public void initMap(Map<String, RequestType> commandRequests){

        commandRequests.put("add", new DragonNeedRequest());
        commandRequests.put("add_if_min", new DragonNeedRequest());
        commandRequests.put("help", new NoArgNeedRequest());
        commandRequests.put("show", new NoArgNeedRequest());
        commandRequests.put("execute_script", new ScriptNeedRequest(scriptHistory));
        commandRequests.put("filter_less_than_age", new IntArgNeedRequest());
        commandRequests.put("add_if_max", new DragonNeedRequest());
        commandRequests.put("remove_by_id", new IntArgNeedRequest());
        commandRequests.put("max_by_description", new NoArgNeedRequest());
        commandRequests.put("update", new DragonNeedRequest());
        commandRequests.put("remove_head", new NoArgNeedRequest());
        commandRequests.put("print_ascending", new NoArgNeedRequest());
        commandRequests.put("clear", new NoArgNeedRequest());
        commandRequests.put("exit", new NoArgNeedRequest());
        commandRequests.put("info", new NoArgNeedRequest());

    }

    public synchronized void authorization(){
        while (true) {
            CommandRequest authorizationRequest = authorizationModule.ask();
            ResponseMessage response = client.handleRequest(authorizationRequest);
            System.out.println(response.getMessage());
            authorizationModule.setAuthorizationDone(response.getStatus()== Status.OK);
            if (authorizationModule.isAuthorizationDone()){
                login = authorizationRequest.getUser();
                break;
            }

            else {
                System.out.println("Try again.");
            }
        }
    }
    public CommandRequest readCommand(String[] line){
        try{
            if (line.length > 2) throw new WrongCommandRequestInput("command must not have more than 2 arguments");
            String command = "";
            String arg = "";
            Dragon dragon = null;
            command = line[0];
            if (line.length == 2){ //if command has argument
                arg = line[1];
            }
            return new CommandRequest(command, arg, dragon, login);
        } catch(WrongCommandRequestInput e){
            System.out.println(e.getMessage());
        }
        return null;
    }



    private boolean scriptMode = false;

    public void interactiveMod(String scriptName) {
        scanner = new Scanner(System.in);
        maker.setScanner(scanner);
        Scanner sc = checkFile(scriptName);
        if ( sc != null){
            scanner = sc;
        }
        while (true){
            try {
                mode();
                if (!scriptMode) System.out.println("Enter next command");
                String[] line = scanner.nextLine().toLowerCase().trim().split(" ");
                if (scriptMode) {
                    System.out.println("Command from file - " + line[0]);
                }
                executeRequest(line);
            }  catch(NoSuchElementException e){
                System.out.println("You have entered the end of file symbol. Program will be terminate.");
                System.exit(0);
            }
        }
    }



    public void executeRequest(String[] line){
        if (readCommand(line) == null){
            return;
        }
        CommandRequest request = readCommand(line);
        try{
            ResponseMessage msg = new ResponseMessage();
            if (!commandRequests.containsKey(request.getCommandName())) {
                msg.setStatus(Status.ERROR);
                throw new WrongCommandException();
            }
            RequestType type = commandRequests.get(request.getCommandName());

            if (type.checkArgs(request)) {
                if (type instanceof DragonNeedRequest){
                    try{
                        Dragon d = maker.makeDragon();
                        if (!(d == null)){
                            request.setCommandObjectArgument(d);
                            System.out.println(client.handleRequest(request).toString());
                        }
                        else{
                            msg.setStatus(Status.ERROR);
                            scriptHistory.clear();
                        }
                    } catch (IncorrectInputInScriptException e){
                        System.out.println("incorrect input in script");
                    }
                }
                else{
                    System.out.println(client.handleRequest(request));
                    if (type instanceof ScriptNeedRequest){
                        interactiveMod(request.getArguments());
                    }
                }
            }
            else if(scriptMode){
                msg.setStatus(Status.ERROR);
            }
            if (scriptMode && msg.getStatus()== Status.ERROR){
                scriptMode = false;
                scanner = new Scanner(System.in);
                maker.setScanner(scanner);
                maker.setUserMode();
                scriptHistory.clear();
            }
        } catch (WrongCommandException e){
            System.out.println(e.getMessage());
        }

    }



    public Scanner checkFile(String scriptName){
        try {
            File f = new File(scriptName);
            if (f.exists()) {
                setScanner(new Scanner(Paths.get(scriptName)));
                scanner = new Scanner(Paths.get(scriptName));
                scannerStack.add(scanner);
                maker.setScanner(scanner);
                maker.setFileMode();
                scriptMode = true;
                return scanner;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void mode(){
        if (scriptMode) {
            if (!scanner.hasNextLine()) {
                scannerStack.removeLast();
                if (!scannerStack.isEmpty()){
                    scanner = scannerStack.peekLast();
                    maker.setScanner(scanner);
                    maker.setUserMode();
                }
                else{
                    scriptMode = false;
                    scanner = new Scanner(System.in);
                    maker.setScanner(scanner);
                    maker.setUserMode();
                    scriptHistory.clear();
                    scannerStack.clear();
                    System.out.println("The end of script");
                }
            }
        }
    }
}
