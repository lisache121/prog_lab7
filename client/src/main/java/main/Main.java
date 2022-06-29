package main;
import client.Client;
import utils.ClientRequestManager;

public class Main {
    public static void main(String[] args) {
        //args = new String[]{"100", "1000"};
        int port = 0;
        int TIMEOUT = 0;
        Client cl;
        if (args.length >0) {
            try {
                port = Integer.parseInt(args[0]);

            } catch (NumberFormatException e){
                port = 3023;
            }
        }
        if (args.length > 1){
            try {
                TIMEOUT = Integer.parseInt(args[1]);
            } catch (NumberFormatException e){
                TIMEOUT = 1000;
            }
        }

        cl = new Client(port, TIMEOUT);
        cl.run();
        ClientRequestManager manager = new ClientRequestManager(cl);
        manager.interactiveMod("");
    }
}
