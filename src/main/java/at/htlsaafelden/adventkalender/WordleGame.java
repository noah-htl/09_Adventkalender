package at.htlsaafelden.adventkalender;

public class WordleGame {
    private final String result;

    public WordleGame(String result) {
        this.result = result;
        System.out.println(result);
    }

    public Position[] guess(String s) {
        if(s.length() != 5) {
            throw new RuntimeException("String not 5 characters long");
        }

        Position[] positions = new Position[5];
        Boolean[] usedChars = new Boolean[] {false, false, false, false, false};

        for (int i = 0; i < 5; i++) {
            Character guessChar =  s.toUpperCase().charAt(i);
            Character resultChar = result.toUpperCase().charAt(i);

            if(guessChar.equals(resultChar)) {
                positions[i] = new Position(guessChar, CharacterType.CORRECT_POSITION);
                usedChars[i] = true;
            }
        }

        for (int i = 0; i < 5; i++) {
            Character guessChar =  s.toUpperCase().charAt(i);

            for (int j = 0; j < 5; j++) {
                if (guessChar.equals(result.toUpperCase().charAt(j)) && !usedChars[j]) {
                    positions[i] = new Position(guessChar, CharacterType.IN_WORD);
                    usedChars[j] = true;
                    break;
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            if(positions[i] == null) {
                positions[i] = new Position(s.toUpperCase().charAt(i), CharacterType.NOT_USED);
            }
        }

        return positions;
    }

    public record Position(Character character, CharacterType characterType) {
    }

    public enum CharacterType {
        CORRECT_POSITION,
        IN_WORD,
        NOT_USED
    }
}
