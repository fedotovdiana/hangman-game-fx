package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final static int PORT = 2403;
    private Socket socket1;
    private Socket socket2;
    private boolean state = false; //false = ходит первый
    private GameLogic gameLogic = new GameLogic();

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();
    }

    private void startServer() {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(PORT);
            socket1 = ss.accept();
            socket2 = ss.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        startGame();
    }

    private void startGame() {
        try {
            BufferedReader br1 = new BufferedReader((new InputStreamReader((socket1.getInputStream()))));
            PrintWriter os1 = new PrintWriter(socket1.getOutputStream(), true);
            BufferedReader br2 = new BufferedReader((new InputStreamReader((socket2.getInputStream()))));
            PrintWriter os2 = new PrintWriter(socket2.getOutputStream(), true);
            os1.println(0); //этот игрок ходит первым
            os2.println(1); //этот игрок ходит вторым
            os1.println(gameLogic.guessedWord);
            os2.println(gameLogic.guessedWord);
            label:
            while (!gameLogic.isGuessed) {
                while (!state) {
                    if (!listen(br1, os1, os2)) break label;
                }
                while (state) {
                    if (!listen(br2, os2, os1)) break label;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean listen(BufferedReader br1, PrintWriter os1, PrintWriter os2) {
        try {
            String letter = br1.readLine();
            if (gameLogic.nextStep(letter)) {
                if (gameLogic.isGuessed) {
                    //слово угадано
                    os1.println(1);
                    os1.println(1); //выиграл
                    os1.println(gameLogic.keyWord);
                    os2.println(1);
                    os2.println(0); //проиграл
                    os2.println(gameLogic.keyWord);
                    return false;
                } else {
                    //обновитесь
                    os1.println(0);
                    os1.println(letter);
                    os1.println(1); //есть такая буква
                    os1.println(gameLogic.guessedWord);
                    os2.println(0);
                    os2.println(letter);
                    os2.println(1); //есть такая буква
                    os2.println(gameLogic.guessedWord);
                }
            } else if (gameLogic.missCount >= GameLogic.MAX_MISSES) {
                //попыток больше нет
                os1.println(1);
                os1.println(0); //проиграл
                os1.println(gameLogic.keyWord);
                os2.println(1);
                os2.println(1); //выиграл
                os2.println(gameLogic.keyWord);
                return false;
            } else {
                //обновитесь
                os1.println(0);
                os1.println(letter);
                os1.println(0); //нет такой буквы
                os1.println(gameLogic.guessedWord);
                os2.println(0);
                os2.println(letter);
                os2.println(0); //нет такой буквы
                os2.println(gameLogic.guessedWord);
                state = !state;
            }
            System.out.println("конец метода");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
