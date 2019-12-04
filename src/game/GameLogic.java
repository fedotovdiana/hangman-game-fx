package game;

public class GameLogic {

    Loader loader;
    public static final int MAX_MISSES = 10;
    public int missCount = 0;
    public String keyWord;
    public String guessedWord;
    public boolean isGuessed = false;

    public GameLogic() {
        loader = Loader.getInstance();
        initWord();
    }

    private void initWord() {
        int number = (int) (Math.random() * loader.words.size());
        keyWord = loader.words.get(number);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keyWord.length(); i++) {
            sb.append("-");
        }
        guessedWord = sb.toString();
    }

    public boolean nextStep(String input) {
        if (keyWord.contains(input)) {
            char[] guessed = guessedWord.toCharArray();
            for (int i = 0; i < keyWord.length(); i++) {
                if (keyWord.charAt(i) == input.charAt(0)) {
                    guessed[i] = input.charAt(0);
                }
            }
            guessedWord = String.valueOf(guessed);
            if (!guessedWord.contains("-")) {
                isGuessed = true;
            }
            return true;
        } else {
            missCount++;
        }
        return false;
    }
}