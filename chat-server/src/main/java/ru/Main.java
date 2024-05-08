package ru;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.UnknownHostException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try{
            ServerSocket serverSocket = new ServerSocket(1400);
            Server server= new Server(serverSocket);
            server.runServer();


        }catch (UnknownHostException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}