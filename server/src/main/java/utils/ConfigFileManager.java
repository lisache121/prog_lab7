package utils;

import databaseUtils.DBConnector;
import log.Log;

import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConfigFileManager {
    private final String url;
    private Scanner credentials;

    public ConfigFileManager(String url) {
        this.url = url;

    }

    public DBConnector getData(String filename){
        try {
            Path path = Paths.get(filename);
            credentials = new Scanner(new FileReader(filename));

            if (!Files.exists(path) | Files.isDirectory(path) | !Files.isReadable(path)) {
                throw new FileNotFoundException();
            }
        } catch (NullPointerException | FileNotFoundException e) {
            Log.logger.error("There is no configuration file. Try to authorize using console");
            return initDB();
        }
        try {
            String username = credentials.nextLine().trim();
            String password = credentials.nextLine().trim();
            Log.logger.info("Authorization was succesfull");
            return new DBConnector(url, username, password);
        } catch(NoSuchElementException e){
            Log.logger.error("There is no data to enter the database. Try to authorize using console");
            initDB();
        }
        return null;
    }

    public DBConnector initDB(){
        Console autorization = System.console();
        if (autorization != null) {
            autorization.printf("Enter the username: ");
            String usernameServer = autorization.readLine();
            autorization.printf("Enter the password: ");
            String passwordServer = new String(autorization.readPassword());
            return new DBConnector(url, usernameServer, passwordServer);
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the username: ");
            String usernameServer = scanner.nextLine();
            System.out.println("Enter the password: ");
            String passwordServer = scanner.nextLine();
            return new DBConnector(url, usernameServer, passwordServer);
        }
    }
}
