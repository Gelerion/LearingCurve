package com.denis.learning.rpc.invoke.network.server;

import com.denis.learning.rpc.invoke.model.Employee;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class RpcServer {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        ServerSocket serverSocket = new ServerSocket(9878);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);
            outToClient.println("Hello!");

            BufferedReader toServerFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ObjectInputStream objIn = new ObjectInputStream(clientSocket.getInputStream());
            Employee employee = (Employee) objIn.readObject();
            System.out.println(employee);

//            String line;
//            while ((line = toServerFromClient.readLine()) != null) {
//                System.out.println("Server: " + line);
//                outToClient.println("Got: " + line);
//            }

            toServerFromClient.close();
            clientSocket.close();
            outToClient.close();
        }
    }
}
