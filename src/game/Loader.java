package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Loader {

    private static final Loader INSTANCE = new Loader();
    public ArrayList<String> words;

    private Loader() {
        words = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File("abc.txt"));
            while (sc.hasNextLine()) {
                words.add(sc.nextLine());
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Loader getInstance() {
        return INSTANCE;
    }
}
