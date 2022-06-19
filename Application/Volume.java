package Application;
import Tools.SoundPlayer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Volume {
    private static Stage stage = new Stage();
    private static float volume = 0.5f;
    private static float sfx = 1f;

    public static float getVolume() {
        return volume;
    }

    public static float getSFX() {
        return sfx;
    }

    public static void start(double height, double width) throws Exception {
        GridPane root = new GridPane();
        root.alignmentProperty().setValue(Pos.CENTER);

        displayVolume(root);
        displaySFX(root);
        displayMute(root);

        Scene scene = new Scene(root, width/4, height/4);
        scene.getStylesheets().add("resources/CSS/volume.css");

        stage.setTitle("Volume");
        stage.setScene(scene);
        stage.show();
        stage.show();
    }

    private static void displayVolume(GridPane root) {
        Label volumeLabel = new Label("Volume");
        volumeLabel.setId("header");

        Slider volumeSlider = new Slider(0, 1f, getVolume());
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setBlockIncrement(0.05f);
        volumeSlider.setMajorTickUnit(0.1f);
        volumeSlider.setSnapToTicks(true);
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                SoundPlayer.changeVolume(newValue.floatValue());
                Volume.volume = newValue.floatValue();
            }
        });

        root.addRow(0, volumeLabel, volumeSlider);
    }

    private static void displaySFX(GridPane root) {
        Label SFXLabel = new Label("SFX");
        SFXLabel.setId("header");

        Slider SFXSlider = new Slider(0, 1f, getSFX());
        SFXSlider.setShowTickMarks(true);
        SFXSlider.setBlockIncrement(0.1f);
        SFXSlider.setMajorTickUnit(0.2f);
        SFXSlider.setSnapToTicks(true);
        SFXSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Volume.sfx = newValue.floatValue();
            }
        });

        root.addRow(1, SFXLabel, SFXSlider);
    }

    private static void displayMute(GridPane root) {
        Label muteLabel = new Label("\uD83D\uDD07");
        muteLabel.setId("button");
        muteLabel.setOnMouseClicked((e) ->{
            SoundPlayer.toggleMute();
            if (SoundPlayer.getMute()) {
                SoundPlayer.stopMusic();
                muteLabel.setText("\uD83D\uDD0A");
            } else muteLabel.setText("\uD83D\uDD07");
        });

        root.addRow(2, muteLabel);
    }

    public static void stop() {
        stage.hide();
    }
}
