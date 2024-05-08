package ru;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClienManager implements Runnable {

    private final Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String name;

    public static ArrayList<ClienManager> clients = new ArrayList<>();

    public ClienManager(Socket socket) {
        this.socket = socket;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            name  = bufferedReader.readLine();
            clients.add(this);
            System.out.println(name + " подключился к чату.");
            broadcastMessage("Server: " + name + " подключился к чату.");

        } catch (IOException e){
            closeEverything(socket,bufferedReader,bufferedWriter );
        }
    }
    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        //удаление клиента из коллекции
        removeClient();
        try {
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
            if(socket != null){
                socket.close();
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String messageFromClient;

        while (socket.isConnected()){
            try{
                messageFromClient= bufferedReader.readLine();
                broadcastMessage(messageFromClient);

            }catch (IOException e){
                closeEverything(socket,bufferedReader,bufferedWriter );
                break;
            }
        }
    }

    private void broadcastMessage(String message){

        for(ClienManager cllient : clients){
            try{
                if(!cllient.name.equals(name)){
                    cllient.bufferedWriter.write(message);
                    cllient.bufferedWriter.newLine();
                    cllient.bufferedWriter.flush();
                }

            } catch (IOException e){
                closeEverything(socket,bufferedReader,bufferedWriter );
            }
        }
    }

    private void  removeClient(){
        clients.remove(this);
        System.out.println(name + "покинул чат.");
        broadcastMessage("Server: " + name + " покинул чат");
    }
}