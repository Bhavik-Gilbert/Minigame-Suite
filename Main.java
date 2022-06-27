import Tools.SoundPlayer;

import javax.swing.JOptionPane;

import Application.Song;
import Application.Volume;
import Pages.Home;
import Pages.Page;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Screen;

public class Main extends Application{
	private Scene scene;
	private Stage stage;
	private BorderPane root;

	private boolean fullScreen;
	public static void main(String[] args) {
		launch(args);
	}
		
	@Override 
	public void start(Stage stage) throws Exception {
		Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		double height = screenBounds.getHeight();
		double width = screenBounds.getWidth();
		this.fullScreen = true;

		this.stage = stage;
		this.root = new BorderPane();
		Pane page = new Pane();
		MenuBar menu = new MenuBar();
		HBox infoBox = new HBox();
		
		this.root.setBottom(drawInfoLabel(infoBox));
		this.root.setTop(drawStartMenu(menu, height, width));
		this.root.setCenter(drawStartPage(page, root, height, width));

		this.scene = new Scene(root, width/1.2, height/1.2);
		this.scene.getStylesheets().add("resources/CSS/main.css");

		this.stage.setTitle("Game Suite");
		this.stage.setMinHeight(height/4);
		this.stage.setMinWidth(width/4);
		this.stage.setScene(this.scene);
		this.stage.setFullScreen(true);
		this.stage.show();
 	}   

	private MenuBar drawStartMenu(MenuBar menu, double height, double width) {
		Menu window = new Menu("Window");
		MenuItem fullScreen = new MenuItem("Exit Full Screen");
		fullScreen.setAccelerator(new KeyCodeCombination(KeyCode.ESCAPE));
		fullScreen.setOnAction((e)->{
			this.stage.setFullScreen(!this.fullScreen);
			this.fullScreen = !this.fullScreen;

			if(fullScreen.getText().equals("Full Screen")) {
				fullScreen.setText("Exit Full Screen");
			} else {
				fullScreen.setText("Full Screen");
			}
		});
		MenuItem exit = new MenuItem("Exit");
		exit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.SHORTCUT_DOWN));
		exit.setOnAction((e) -> {
			System.exit(0);
		});
		window.getItems().addAll(fullScreen, exit);

		Menu sound = new Menu("Sound");
		MenuItem volumeMenu = new MenuItem("Volume Controls");
		volumeMenu.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_DOWN));
		volumeMenu.setOnAction((e) -> {
			try {
				Volume.start(height, width);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Error Loading Page");
			}
		});
		MenuItem music = new MenuItem("Change Song");
		music.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.SHORTCUT_DOWN));
		music.setOnAction((e) -> {
			try {
				Song.start(height, width);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Error Loading Page");
			}
		});
		SoundPlayer.setMusicHome(music);
		Page.setMusicMenu(music);
		sound.getItems().addAll(volumeMenu, music);

	

		menu.getMenus().addAll(window, sound);

		return menu;
	}

	private Pane drawStartPage(Pane page, BorderPane root, double height, double width) {
		Page currentPage = new Home(page, root, height, width);
		currentPage.draw();

		return page;
	}

	private HBox drawInfoLabel(HBox infoBox) {
		Label currentSong = new Label("");
		currentSong.setId("smallInfo");
		infoBox.getChildren().add(currentSong);
		SoundPlayer.setSongLabel(currentSong);
		return infoBox;
	}
}
