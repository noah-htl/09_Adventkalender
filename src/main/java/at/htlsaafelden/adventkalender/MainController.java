package at.htlsaafelden.adventkalender;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class MainController implements Initializable {
    @FXML
    public AnchorPane anchorPane;

    @FXML
    public GridPane gridPane;

    @FXML
    public ImageView imageView;

    private int[] ints;

    public MainController() {
        ints = new int[24];
        for (int i = 1; i <= 24; i++) {
            ints[i-1] = i;
        }

        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            int a = random.nextInt(24);
            int b = random.nextInt(24);

            int x = ints[a];
            ints[a] = ints[b];
            ints[b] = x;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int i = 0;

        for(Node node : gridPane.getChildren()) {
            if (node instanceof Door door) {
                door.setNumber(ints[i]);
                i++;
            }
        }

        imageView.setPreserveRatio(true);
        //imageView.setFitHeight(Screen.getPrimary().getBounds().getHeight());
        imageView.setFitWidth(Screen.getPrimary().getBounds().getWidth());

        anchorPane.widthProperty().addListener((_, _, t1) -> gridPane.setPrefWidth(t1.doubleValue()));
        anchorPane.heightProperty().addListener((_, _, t1) -> gridPane.setPrefHeight(t1.doubleValue()));

        gridPane.setPrefWidth(anchorPane.widthProperty().get());
        gridPane.setPrefHeight(anchorPane.heightProperty().get());
    }
}