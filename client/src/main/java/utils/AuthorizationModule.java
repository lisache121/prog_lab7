package utils;

import interaction.CommandRequest;

import java.io.Console;
import java.util.AbstractMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class AuthorizationModule {
    private static final Scanner scanner = new Scanner(System.in);

    private boolean authorizationDone = false;
    Console autorization = System.console();
    public CommandRequest ask() {
        try {
            System.out.println("Do you want to create a new user? [y/n]");
            String input = scanner.nextLine();
            if ("y".equalsIgnoreCase(input)) {
                return createNewUser();
            } else if ("n".equalsIgnoreCase(input)) {
                return authorize();
            }
            System.out.println("Incorrect answer. Try again.");
            return ask();
        } catch (NoSuchElementException e){
            System.out.println("you have entered the end of file symbol. the program will be terminate");
            System.exit(0);
        }
        return null;
    }

    private CommandRequest createNewUser() {
        System.out.println("Enter your name: ");
        String login = scanner.nextLine();
        String password;
        System.out.println("Enter your password (it must contain at least 7 letters): ");
        if (autorization!=null){
            password = new String(autorization.readPassword());
        }
        else {
            password = scanner.nextLine();
        }
        if (password.length()<7){
            while (!(password.length()>=7)){
                System.out.println("password is too short. Try again.");
                password = scanner.nextLine();
            }
        }
        System.out.println("Repeat your password: ");
        if (autorization!= null){
            if (new String(autorization.readPassword()).equals(password)){
                System.out.println("Trying to create new user... ");
                Map.Entry<String,String> entry =
                        new AbstractMap.SimpleEntry<String, String>(login, password);
                return new CommandRequest("sign_up", null, null, entry);
            }
        }
        else if (scanner.nextLine().equals(password)) {
            System.out.println("Trying to create new user... ");
            Map.Entry<String,String> entry =
                    new AbstractMap.SimpleEntry<String, String>(login, password);
            return new CommandRequest("sign_up", null, null, entry);
        }

        System.out.println("Passwords didn't match, try again");
        return createNewUser();
    }

    private CommandRequest authorize() {
        System.out.println("Enter your name: ");
        String login = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password;
        if (autorization!=null){
            password = new String(autorization.readPassword());
        }
        else {
            password = scanner.nextLine();
        }
        System.out.println("Trying to log in... ");
        Map.Entry<String,String> entry =
                new AbstractMap.SimpleEntry<String, String>(login, password);
        return new CommandRequest("sign_in", null, null, entry);
    }

    public boolean isAuthorizationDone() {
        return authorizationDone;
    }

    public void setAuthorizationDone(boolean authorizationDone) {
        this.authorizationDone = authorizationDone;
    }
}
