package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHelper {

    private final int PORT = 2403;
    private String host = "localhost";
    public Socket socket;
    public BufferedReader br;
    public PrintWriter os;
    public boolean state;


    public void connect() {
        try {
            socket = new Socket(host, PORT);
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
