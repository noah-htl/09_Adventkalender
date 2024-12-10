package at.htlsaafelden.adventkalender;

import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;

import java.io.IOException;

public class Door extends AnchorPane {

    @FXML
    private ImageView imageView;

    @FXML
    private Label label;

    @FXML
    private BorderPane borderPane;

    private int number;
    private boolean open = false;

    public Door() {
        FXMLLoader fxmlLoader = new FXMLLoader(AdventApplication.class.getResource("door-component.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setClassLoader(getClass().getClassLoader());
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize() {

    }

    @FXML
    public void onClick() {
        if(!this.open) {
            this.open = true;
            this.open();
        }
    }

    private void open() {
        this.borderPane.getStyleClass().add("open");
    }

    public void setNumber(int x) {
        this.number = x;
        imageView.setImage(ImageCache.get(1 + ".jpg", Door.class));
        label.setText(String.valueOf(x));
    }

    public int getNumber() {
        return this.number;
    }
}
