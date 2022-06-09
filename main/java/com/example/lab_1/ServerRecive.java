package com.example.lab_1;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerRecive extends ServerMain
{
    public Socket clientsocket;
    public ServerRecive(Socket client) {
        clientsocket=client;
    }

    @Override
    public void run()
    {
        try
        {
            flag=true;
            DataInputStream reader = new DataInputStream(clientsocket.getInputStream());
            if(ServerThread.client_control==2)
            {
               ServerThread.obj=reader.readUTF();
               System.out.println(ServerThread.obj);
            }
            while (true)
            {
                if (!flag)
                    break;
                Thread.sleep(2000);
                String string = reader.readUTF();
                System.out.println(string);
            }
            System.out.println("Поток получения закончил работу");
        }


        catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
