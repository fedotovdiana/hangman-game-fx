package com.itis.group11801;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Loader {

    private static final Loader INSTANCE = new Loader();
    public ArrayList<String> words;

    private Loader() {
        words = new ArrayList<>();
        try {
            URL url = Loader.class.getClassLoader().getResource("abc.txt");
            assert url != null;
            Scanner sc = new Scanner(new File(url.toURI()));
            while (sc.hasNextLine()) {
                words.add(sc.nextLine());
            }
            sc.close();
        } catch (FileNotFoundException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static Loader getInstance() {
        return INSTANCE;
    }
}
