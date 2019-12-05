package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHelper {

    private BufferedReader br;
    private PrintWriter os;

    public ClientHelper(Socket socket) {
        try {
            br = new BufferedReader((new InputStreamReader((socket.getInputStream()))));
            os = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String letter) {
        os.println(letter);
    }

    public String getMsg() {
        String letter = "";
        try {
            letter = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return letter;
    }
}
