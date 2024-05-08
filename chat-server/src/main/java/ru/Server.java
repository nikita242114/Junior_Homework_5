package ru;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void runServer(){
        try{
            while (!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                ClienManager clienManager = new ClienManager(socket);
                System.out.println("подключен новый клиент!");
                Thread thread = new Thread(clienManager);
                thread.start();
            }

        }catch (IOException e){
            closeSocket();
        }

    }
    private void closeSocket(){
        try{
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
