package com.example.lab_1;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Scanner;

public class ServerSend extends ServerMain{
    public Socket clientsocket;
    public ServerSend(Socket client) {
        clientsocket=client;
    }
    @Override
    public void run()
    {
        try
        {
            DataOutputStream write = new DataOutputStream(clientsocket.getOutputStream());
            /*ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientsocket.getOutputStream());
            objectOutputStream.writeObject(Habitat.students);*/
            if (ServerThread.flag_obj)
                write.writeUTF(Habitat.students.toString());
            ServerThread.flag_obj=false;
            string_check="Hello";
            while (true)
            {
                if (!string_check.equalsIgnoreCase("Hello"))
                {
                    break;
                }
                Thread.sleep(2000);
                write.writeUTF(string_check);
            }
            System.out.println("Поток вывода " + " закончил работу");

        }

        catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
