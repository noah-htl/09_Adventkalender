package at.htlsaafelden.adventkalender;

import at.htlsaafelden.adventkalender.File.FileLoader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class Door extends AnchorPane {

    @FXML
    private ImageView imageView;

    @FXML
    private ImageView imageViewTop;

    @FXML
    private ImageView imageViewChain;

    @FXML
    private Label label;

    @FXML
    private AnchorPane root;

    @FXML
    private BorderPane borderPane;

    private int number;

    private ObservableValueImpl<Boolean> open = new ObservableValueImpl<>(false);
    private ObservableValueImpl<Boolean> locked = new ObservableValueImpl<>(false);

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
        root.widthProperty().addListener((_, _, t1) -> borderPane.setPrefWidth(t1.doubleValue()));
        root.heightProperty().addListener((_, _, t1) -> borderPane.setPrefHeight(t1.doubleValue()));

        borderPane.setPrefWidth(root.widthProperty().get());
        borderPane.setPrefHeight(root.heightProperty().get());

        this.locked.addListener((_, _, t1) -> {
            System.out.println(t1);
            if (t1) {
                borderPane.getStyleClass().add("locked");
                imageViewChain.setOpacity(1);
            } else {
                borderPane.getStyleClass().remove("locked");
                imageViewChain.setOpacity(0);
            }
        });

        this.open.addListener((_, _, t1) -> {
            if (t1) {
                borderPane.getStyleClass().add("open");
                imageView.setClip(new Rectangle(0,50,100,100));

                Rectangle rectangle2 = new Rectangle(0,0,100,50);
                imageViewTop.setClip(rectangle2);
                imageViewTop.setOpacity(1);
            } else {
                borderPane.getStyleClass().remove("open");

                imageView.setClip(null);
                imageViewTop.setClip(null);
                imageViewTop.setOpacity(0);
            }
        });

        //this.open.setValue(false);
    }

    private boolean canOpen() {
        Calendar calendar = Calendar.getInstance();

        if(calendar.get(Calendar.MONTH) == Calendar.DECEMBER && calendar.get(Calendar.DAY_OF_MONTH) >= this.number) {
            return true;
        }

        return false;
    }

    @FXML
    public void onClick() {
        if(!this.open.getValue() && canOpen()) {
            this.open.setValue(true);
            this.open();

            FileLoader.save(this.number, this.open.getValue());
        }
    }

    private void open() {
        FXMLLoader fxmlLoader = new FXMLLoader(AdventApplication.class.getResource("wordle-view.fxml"));
        Stage stage = new Stage();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), Screen.getPrimary().getBounds().getWidth() / 2,
                    Screen.getPrimary().getBounds().getHeight() / 2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        stage.setTitle("Wordle!");
        stage.setScene(scene);
        WordleController controller = fxmlLoader.getController();
        controller.setNumber(this.number);
        controller.setStage(stage);
        stage.show();
    }

    public void setNumber(int x) {
        this.number = x;

        imageView.setImage(ImageCache.get(x + ".png", Door.class));

        imageViewTop.setImage(ImageCache.get(x + ".png", Door.class));

        label.setText(String.valueOf(x));

        if(FileLoader.load(this.number)) {
            this.open.setValue(true);
        }

        this.locked.setValue(!canOpen());
    }

    public int getNumber() {
        return this.number;
    }
}
