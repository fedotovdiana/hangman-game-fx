package com.itis.group11801;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHelper {

    public Socket socket;
    public BufferedReader br;
    public PrintWriter os;
    public boolean state;

    public ClientHelper() {
    }


    public void connect() {
        try {
//            Scanner sc = new Scanner(System.in);
//            System.out.println("Введите хост");
//            String host = sc.nextLine();
//            System.out.println("Введите порт");
//            int port = sc.nextInt();
//            socket = new Socket(host, port);
            socket = new Socket("localhost", 2403);
            br = new BufferedReader((new InputStreamReader((socket.getInputStream()))));
            os = new PrintWriter(socket.getOutputStream(), true);
            state = Integer.parseInt(br.readLine()) == 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receive() {
        String input = null;
        try {
            input = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }

    public void send(String output) {
        os.println(output);
    }
}
