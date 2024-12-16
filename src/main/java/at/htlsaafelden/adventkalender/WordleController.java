package at.htlsaafelden.adventkalender;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class WordleController implements Initializable {
    private Stage stage = null;
    private int line = 0;
    private int column = 0;
    private int number;

    private WordleGame currentGame;

    @FXML
    private VBox vBox;

    private Label getLabel(int row, int column) {
        try {
            Node node = this.vBox.getChildren().get(row);
            if(node instanceof HBox hBox) {
                node = hBox.getChildren().get(column);
                if(node instanceof Label label) {
                    return label;
                }
            }

            return null;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void init() {
        Label newLabel = getLabel(line, column);
        if(newLabel != null) {
            newLabel.getStyleClass().add("current");
        }


        currentGame = new WordleGame(WordleLoader.getDailyWordle(this.number));

        stage.getScene().setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCharacter().replaceAll("\\p{C}", "").length() == 1) {
                    Label oldLabel = getLabel(line, column);
                    if(oldLabel != null) {
                        oldLabel.getStyleClass().remove("current");
                        oldLabel.setText(keyEvent.getCharacter());
                    }


                    column++;
                    if(column >= 5) {
                        column = 5;
                    }

                    Label newLabel = getLabel(line, column);
                    if(newLabel != null) {
                        newLabel.getStyleClass().add("current");
                    }
                } else if(keyEvent.getCharacter().equals("\b")) {
                    Label oldLabel = getLabel(line, column);
                    if(oldLabel != null) {
                        oldLabel.getStyleClass().remove("current");
                    }

                    column--;
                    if(column < 0) {
                        column = 0;
                    }

                    Label newLabel = getLabel(line, column);
                    if(newLabel != null) {
                        newLabel.getStyleClass().add("current");
                        newLabel.setText("?");
                    }
                } else if (keyEvent.getCharacter().equals("\r")) {
                    Label oldLabel = getLabel(line, column);
                    if(oldLabel != null) {
                        oldLabel.getStyleClass().remove("current");
                    }

                    _handleLine(line);

                    line++;
                    column = 0;
                    if(line >= 6) {
                        line = 6;
                    }

                    Label newLabel = getLabel(line, column);
                    if(newLabel != null) {
                        newLabel.getStyleClass().add("current");
                    }
                }
            }
        });
    }

    private void _handleLine(int line) {
        char[] characters = new char[5];

        for (int i = 0; i < 5; i++) {
            Label label = getLabel(line, i);
            characters[i] = label.getText().charAt(0);
        }

        String s = new String(characters);

        WordleGame.Position[] positions = this.currentGame.guess(s);
        //System.out.println(Arrays.toString(positions));

        for (int i = 0; i < 5; i++) {
            Label label = getLabel(line, i);

            label.getStyleClass().add(switch (positions[i].characterType()) {
                case CORRECT_POSITION -> "correct_position";
                case IN_WORD -> "in_word";
                case NOT_USED -> "not_used";
            });
        }
    }

    public void setStage(Stage stage) {
        boolean h = this.stage == null;
        this.stage = stage;
        if(h) {
            init();
        }
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
