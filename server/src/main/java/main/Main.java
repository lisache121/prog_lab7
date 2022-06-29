package main;

import exceptions.ConnectionException;
import log.Log;
import server.Server;
import utils.CommandManager;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        int port = 0;
        //args = new String[]{"100"};
        Server s = new Server();
        try{
            if (args.length != 1) {
                port = 3023;
            }
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e){
                s.startConnection(3023);
            }
        }catch(ConnectionException e){
            Log.logger.error(e.getMessage());
        }

        s.startConnection(port);

        CommandManager manager = new CommandManager(s);
        manager.run();
    }
}
