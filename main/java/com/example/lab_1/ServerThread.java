package com.example.lab_1;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerThread extends Thread{
    public static List<ServerSend> serverSends = new ArrayList<ServerSend>();
    public static List<ServerRecive> serverRecives = new ArrayList<ServerRecive>();
    public static boolean flag_obj = true;
    public static int client_control = 0;
    public static String obj;
    @Override
    public void run()
    {
        try {
            server_start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void server_start() throws IOException
    {
        Socket clientsocket = new Socket("127.0.0.1",8000);
        ServerSend serverSend = new ServerSend(clientsocket);
        serverSend.start();
        serverSends.add(serverSend);
        ServerRecive serverRecive = new ServerRecive(clientsocket);
        serverRecive.start();
        serverRecives.add(serverRecive);
        client_control++;
    }
}
