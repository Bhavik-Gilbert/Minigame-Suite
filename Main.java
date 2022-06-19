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
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Screen;

public class Main extends Application{
	private Scene scene;
	private Stage stage;

	private boolean fullScreen;
	public static void main(String[] args) {
		launch(args);
	}
		
	@Override 
	public void start(Stage stage) throws Exception {
		Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		double height = screenBounds.getHeight();
		double width = screenBounds.getWidth();
		this.fullScreen = false;

		this.stage = stage;
		BorderPane root = new BorderPane();
		Pane page = new Pane();
		MenuBar menu = new MenuBar();
		HBox infoBox = new HBox();
		
		root.setBottom(drawInfoLabel(infoBox));
		root.setTop(drawStartMenu(menu, height, width));
		root.setCenter(drawStartPage(page, height, width));

		this.scene = new Scene(root, width/1.2, height/1.2);
		this.scene.getStylesheets().add("resources/CSS/main.css");

		this.stage.setTitle("Game Suite");
		this.stage.setMinHeight(height/4);
		this.stage.setMinWidth(width/4);
		this.stage.setScene(this.scene);
		this.stage.show();
 	}   

	private MenuBar drawStartMenu(MenuBar menu, double height, double width) {
		Menu window = new Menu("Window");
		MenuItem fullScreen = new MenuItem("Full Screen");
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
		exit.setOnAction((e) -> {
			System.exit(0);
		});
		window.getItems().addAll(fullScreen, exit);

		Menu sound = new Menu("Sound");
		MenuItem volumeMenu = new MenuItem("Volume Controls");
		volumeMenu.setOnAction((e) -> {
			try {
				Volume.start(height, width);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Error Loading Page");
			}
		});
		MenuItem music = new MenuItem("Change Song");
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

	private Pane drawStartPage(Pane page, double height, double width) {
		Page currentPage = new Home(page, height, width);
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
