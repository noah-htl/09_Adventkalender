package at.htlsaafelden.adventkalender;

import java.util.Locale;

public class WordleGame {
    private final String result;

    public WordleGame(String result) {
        this.result = result;
        System.out.println(result);
    }

    private String toUpper(String in) { // fuck unicode
        String temp = in.replace('ß', '\0').replace('ä', '\1').replace('ö', '\2').replace('ü', '\3').toUpperCase();
        return temp.replace('\0', 'ß').replace('\1', 'Ä').replace('\2', 'Ö').replace('\3', 'Ü');
    }

    public Position[] guess(String s) {
        if(s.length() != 5) {
            throw new RuntimeException("String not 5 characters long");
        }

        Position[] positions = new Position[5];
        Boolean[] usedChars = new Boolean[] {false, false, false, false, false};

        for (int i = 0; i < 5; i++) {
            Character guessChar =  toUpper(s).charAt(i);
            Character resultChar = toUpper(result).charAt(i);

            if(guessChar.equals(resultChar)) {
                positions[i] = new Position(guessChar, CharacterType.CORRECT_POSITION);
                usedChars[i] = true;
            }
        }

        for (int i = 0; i < 5; i++) {
            Character guessChar = toUpper(s).charAt(i);

            for (int j = 0; j < 5; j++) {
                if (guessChar.equals(toUpper(result).charAt(j)) && !usedChars[j]) {
                    positions[i] = new Position(guessChar, CharacterType.IN_WORD);
                    usedChars[j] = true;
                    break;
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            if(positions[i] == null) {
                positions[i] = new Position(toUpper(s).charAt(i), CharacterType.NOT_USED);
            }
        }

        return positions;
    }

    public String getResult() {
        return result;
    }

    public record Position(Character character, CharacterType characterType) {
    }

    public enum CharacterType {
        CORRECT_POSITION,
        IN_WORD,
        NOT_USED
    }
}
