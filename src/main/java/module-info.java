module at.htlsaafelden.adventkalender {
    requires javafx.controls;
    requires javafx.fxml;


    opens at.htlsaafelden.adventkalender to javafx.fxml;
    exports at.htlsaafelden.adventkalender;
}