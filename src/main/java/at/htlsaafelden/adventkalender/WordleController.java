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
import java.util.ResourceBundle;

public class WordleController implements Initializable {
    private Stage stage = null;
    private int line = 0;
    private int column = 0;

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

        stage.getScene().setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                System.out.println(keyEvent.getCharacter());
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

    public void setStage(Stage stage) {
        boolean h = this.stage == null;
        this.stage = stage;
        if(h) {
            init();
        }
    }
}
