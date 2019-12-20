package com.itis.group11801;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final static int PORT = 2403;

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.startServer();
    }

    private void startServer() throws IOException {
        ServerSocket ss = new ServerSocket(PORT);
        try {
            while (true) {
                Socket socket1 = ss.accept();
                Socket socket2 = ss.accept();
                Thread thread = new Thread(new Room(socket1, socket2));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class Room implements Runnable {
        Socket socket1;
        Socket socket2;
        boolean state = false; //false = ходит первый
        GameLogic gameLogic = new GameLogic();

        public Room(Socket socket1, Socket socket2) {
            this.socket1 = socket1;
            this.socket2 = socket2;
        }

        @Override
        public void run() {
            try {
                BufferedReader br1 = new BufferedReader((new InputStreamReader((socket1.getInputStream()))));
                PrintWriter os1 = new PrintWriter(socket1.getOutputStream(), true);
                BufferedReader br2 = new BufferedReader((new InputStreamReader((socket2.getInputStream()))));
                PrintWriter os2 = new PrintWriter(socket2.getOutputStream(), true);
                try {
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
                } finally {
                    try {
                        br1.close();
                        os1.close();
                        br2.close();
                        os2.close();
                        socket1.close();
                        socket2.close();
                    } catch (IOException e) {
                        System.out.println("DISCONNECTED");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public boolean listen(BufferedReader br1, PrintWriter os1, PrintWriter os2) throws IOException {
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
                os2.println(0); //тоже проиграл
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
            os1.println(gameLogic.missCount);
            os2.println(gameLogic.missCount);
            return true;
        }
    }
}
