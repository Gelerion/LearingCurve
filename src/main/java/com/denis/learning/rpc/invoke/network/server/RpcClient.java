package com.denis.learning.rpc.invoke.network.server;

import com.denis.learning.rpc.invoke.model.Employee;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class RpcClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 9878);
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line = inFromServer.readLine();
        if (line != null) {
            System.out.println("Server: " + line);
        }


        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(new Employee("Denis2", "Boss"));
        out.flush();


/*        PrintWriter outToServer = new PrintWriter(socket.getOutputStream(), true);
        outToServer.println("MisterWinner!");
//        DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
//        outToServer.writeUTF("MisterWinner!\n\r");

        line = inFromServer.readLine();
        if (line != null) {
            System.out.println(line);
        }*/

//        outToServer.close();
        out.close();
        inFromServer.close();



    }
}
