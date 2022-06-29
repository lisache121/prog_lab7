package utils;

import commands.*;
import databaseUtils.DBConnector;
import databaseUtils.DBDragon;
import databaseUtils.DBInitializer;
import databaseUtils.UserChecker;
import exceptions.CommandExecutingException;
import exceptions.ConnectionException;
import exceptions.WrongAmountOfElementsException;
import interaction.CommandRequest;
import interaction.ResponseMessage;
import interaction.Status;
import log.Log;
import server.Server;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.*;


public class CommandManager {
    private final String url = "jdbc:postgresql://localhost:5432/studs";
    private final String urlH = "jdbc:postgresql://pg:5432/studs";
    private final String filename = "/home/s337039/credentials.txt";
    private final Map<String, AbstractCommand> commands = new HashMap<>();
    private final CollectionManager collectionManager;
    private Scanner scanner;
    private Server server = new Server();
    private final UserChecker userChecker;
    private final DBDragon dbDragon;

    private final ExecutorService readCommands = Executors.newFixedThreadPool(10);
    private final ExecutorService executeCommands = Executors.newFixedThreadPool(10);
    private final ExecutorService sendCommands = Executors.newFixedThreadPool(10);

    public CommandManager(Server server) throws SQLException {
        this.server=server;
        ConfigFileManager configFileManager = new ConfigFileManager(urlH);
        Connection connection = configFileManager.getData(filename).getNewConnection();
        this.userChecker = new UserChecker(connection);
        DBInitializer initializer = new DBInitializer(connection);
        initializer.initialize();
        this.dbDragon = new DBDragon(connection);
        this.collectionManager = new CollectionManager(dbDragon);
        init(commands);
    }

    public void init(Map<String, AbstractCommand> commands){
        commands.put("add", new AddCommand(collectionManager, dbDragon));
        commands.put("add_if_min", new AddIfMinCommand(collectionManager, dbDragon));
        commands.put("add_if_max", new AddIfMaxCommand(collectionManager, dbDragon));
        commands.put("filter_less_than_age", new FilterLessThanAgeCommand(collectionManager));
        commands.put("remove_by_id", new RemoveByIdCommand(collectionManager));
        commands.put("update", new UpdateByIdCommand(collectionManager, dbDragon));
        commands.put("remove_head", new RemoveHeadCommand(collectionManager));
        commands.put("max_by_description", new MaxByDescriptionCommand(collectionManager));
        commands.put("print_ascending", new PrintAscendingCommand(collectionManager));
        commands.put("execute_script", new ExecuteScriptCommand());
        commands.put("show", new ShowCommand(collectionManager));
        commands.put("info", new InfoCommand(collectionManager));
        commands.put("help", new HelpCommand(commands));
        commands.put("exit", new ExitCommand());
        commands.put("clear", new ClearCommand(collectionManager));

        commands.put("sign_up", new SignUpCommand(userChecker));
        commands.put("sign_in", new SignInCommand(userChecker));
    }


    public ResponseMessage execute(CommandRequest request) throws WrongAmountOfElementsException {
        ResponseMessage res = new ResponseMessage();
        String name = request.getCommandName();
        if (commands.containsKey(name)) {
            try {
                AbstractCommand cmd = commands.get(name);
                cmd.setArgs(request);
                res= (ResponseMessage) cmd.execute();
                Log.logger.info("Command was executed");
            }catch (CommandExecutingException | SQLException e){
                res = new ResponseMessage(e.getMessage());
                res.setStatus(Status.ERROR);
            }
        } else {
            res.info("Your input doesn't match any command");
        }
        if (!name.equals("exit")) {
            return res;
        }
        return null;
    }

    public void run() {
        scanner = new Scanner(System.in);
        Runnable userInput = () -> {
            try {
                while (true) {
                    String[] userCommand = (scanner.nextLine().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                    if (!userCommand[0].equals("exit")) {
                        Log.logger.error("There is no such command on server");
                        continue;
                    }
                    CommandRequest request = new CommandRequest(userCommand[0], userCommand[1], null, null);
                    AbstractCommand cmd = commands.get(userCommand[0]);
                    cmd.setArgs(request);
                    Log.logger.info(cmd.execute());
                }
            } catch (Exception e) {
                Log.logger.info("You have entered the end of file symbol. Program will be terminate.");
                System.exit(0);
            }
        };
        Thread thread = new Thread(userInput);
        thread.start();
        processingClientRequest();
    }

    private void processingClientRequest(){
        while (true) {
            try {
                init(commands);
                Callable<AbstractCommand> readC = () ->{

                        AbstractCommand cmd;
                        CommandRequest request = server.receiveCommandRequest();
                        String name = request.getCommandName();
                        cmd = commands.get(name);
                        cmd.setArgs(request);
                        return cmd;


                };

                Future<AbstractCommand> futureCommand = readCommands.submit(readC);
                while (!futureCommand.isDone()) {
                }


                AbstractCommand c = futureCommand.get();



                Callable<ResponseMessage> executeCommand = () ->{
                    ResponseMessage res = new ResponseMessage();
                    try {
                        res= (ResponseMessage) c.execute();
                    }catch (CommandExecutingException | SQLException e){
                        res = new ResponseMessage(e.getMessage());
                        res.setStatus(Status.ERROR);
                    } catch (NullPointerException e){
                        res= (ResponseMessage) futureCommand.get().execute();
                    }

                    return res;
                };
                Future<ResponseMessage> futureResponse = executeCommands.submit(executeCommand);
                while (!futureResponse.isDone()) {
                }


                ResponseMessage res = futureResponse.get();

                Runnable sendResponse = () -> {
                    this.server.sendResponse(res);

                };

                sendCommands.execute(sendResponse);


            }catch (ConnectionException e) {
                Log.logger.error("Couldn't connect to the client during command execution.");
            }catch (NullPointerException e){
                System.out.println();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
            }
        }
    }

}



















































//    init(commands);
//    Callable<AbstractCommand> readC = () ->{
//        AbstractCommand cmd;
//        CommandRequest request = server.receiveCommandRequest();
//        String name = request.getCommandName();
//        cmd = commands.get(name);
//        cmd.setArgs(request);
//        return cmd;
//    };
//    Future<AbstractCommand> futureCommand = readCommands.submit(readC);
//                while (!futureCommand.isDone()) {
//                        }
////                AbstractCommand c = futureCommand.get();
//
//
//
//                        Callable<ResponseMessage> executeCommand = () ->{
//        ResponseMessage res;
//        try {
//        res= (ResponseMessage) futureCommand.get().execute();
//        }catch (CommandExecutingException | SQLException e){
//        res = new ResponseMessage(e.getMessage());
//        res.setStatus(Status.ERROR);
//        } catch (NullPointerException e){
//        res= (ResponseMessage) futureCommand.get().execute();
//        }
//
//        return res;
//        };
//        Future<ResponseMessage> futureResponse = executeCommands.submit(executeCommand);
//        while (!futureResponse.isDone()) {
//        }
//
//        ResponseMessage res = futureResponse.get();
//
//        Runnable sendResponse = () -> {
//        this.server.sendResponse(res);
//        };
//
//        sendCommands.execute(sendResponse);
//        catch (InterruptedException | ExecutionException e) {
//                Log.logger.error(e.getMessage());
//                e.printStackTrace();
//
//            }