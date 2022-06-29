package server;


import databaseUtils.DBConnector;
import exceptions.ConnectionException;
import exceptions.InvalidResponseException;
import interaction.CommandRequest;
import interaction.ResponseMessage;
import log.Log;

import java.io.*;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

public class Server {

    private DatagramChannel datagramChannel;
    private volatile SocketAddress clientAddress;
    private final int BUFFER_SIZE = 4096;
    private Selector selector;

    /**
     * Creates a channel
     * @param port Port
     */
    public synchronized void startConnection(int port) {
        try {
            Log.logger.trace("server started");
            SocketAddress localAddress = new InetSocketAddress(port);
            datagramChannel = DatagramChannel.open();
            datagramChannel.bind(localAddress);
            datagramChannel.configureBlocking(false);
            selector = Selector.open();
            datagramChannel.register(selector, SelectionKey.OP_READ);
        } catch (BindException e) {
            Log.logger.fatal(("This port is already in use. The application will be closed." +
                    "Restart the application with another port."));
            System.exit(0);
        } catch (IOException e) {
            throw new ConnectionException("something went wrong while creating a channel");
        }
    }

    /**
     * Receives command request
     * @return Command request
     */
    public synchronized CommandRequest receiveCommandRequest() {
        try {
            if (selector.select() > 0) {
                Set<SelectionKey> keys = selector.selectedKeys();
                for (Iterator<SelectionKey> iter = keys.iterator(); iter.hasNext(); ) {
                    SelectionKey key = iter.next();
                    iter.remove();
                    if (key.isReadable()) {
                        ByteBuffer buf = ByteBuffer.allocate(BUFFER_SIZE);
                        clientAddress = datagramChannel.receive(buf);
                        datagramChannel.register(selector, SelectionKey.OP_WRITE);
                        try {
                            CommandRequest request = deserialize(buf.array());
                            Log.logger.info("New " + request.getCommandName() + " command request has been received.");
                            return request;
                        } catch (ClassNotFoundException | IOException e) {
                            throw new ConnectionException("Couldn't deserialize the received request");
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new ConnectionException("Couldn't receive the command request.");
        }
        return new CommandRequest("p", null, null, null);
    }

    /**
     * Sends response
     * @param response
     */
    public synchronized void sendResponse(ResponseMessage response) {
        try {
            if (selector.select() > 0) {
                Set<SelectionKey> keys = selector.selectedKeys();
                for (Iterator<SelectionKey> iter = keys.iterator(); iter.hasNext(); ) {
                    SelectionKey key = iter.next();
                    iter.remove();
                    if (key.isWritable()) {
                        sendResponse(serializeResponse(response), clientAddress);
                        datagramChannel.register(selector, SelectionKey.OP_READ);
                        Log.logger.info("The response has been sent to the client.");
                    }
                }
            }
        } catch (InvalidResponseException e) {
            Log.logger.error("Couldn't serialize the response.");
        } catch (IOException e) {
            Log.logger.error("Couldn't send the response to command request to client.");
            throw new ConnectionException("sending request failed");
        }
    }

    private synchronized void sendResponse(ByteBuffer[] response, SocketAddress clientAddress) {
        try {
            Integer size = response.length;
            datagramChannel.send(ByteBuffer.wrap(serialize(size)), clientAddress);
            for (ByteBuffer buf : response) {
                datagramChannel.send(buf, clientAddress);
            }
        } catch (IOException e) {
            throw new ConnectionException("something went wrong while sending response");
        }
    }

    private synchronized ByteBuffer[] serializeResponse(ResponseMessage response) {
        try {
            byte[] tmp = serialize(response);
            int size = (int) Math.ceil((double) tmp.length / BUFFER_SIZE);
            ByteBuffer[] res = new ByteBuffer[size];
            int start = 0;
            int end = BUFFER_SIZE;
            for (int i = 0; i < size; i++) {
                res[i] = ByteBuffer.wrap(Arrays.copyOfRange(tmp, start, end));
                start += BUFFER_SIZE;
                end += BUFFER_SIZE;
            }
            return res;
        } catch (IOException e) {
            throw new InvalidResponseException();
        }
    }


    public synchronized CommandRequest deserialize(byte[] buf) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buf);
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            CommandRequest response = (CommandRequest) objectInputStream.readObject();
            byteArrayInputStream.close();
            objectInputStream.close();
            return response;
        }
    }
    public synchronized <T> byte[] serialize(T object) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            byteArrayOutputStream.flush();
            byte[] buf = byteArrayOutputStream.toByteArray();
            objectOutputStream.close();
            byteArrayOutputStream.close();
            return buf;
        }
    }



}
