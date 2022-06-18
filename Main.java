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
		
		root.setTop(drawStartMenu(menu));
		root.setCenter(drawStartPage(page, height, width));

		this.scene = new Scene(root, width/1.2, height/1.2);
		this.scene.getStylesheets().add("resources/CSS/main.css");

		this.stage.setTitle("Game Suite");
		this.stage.setMinHeight(height/4);
		this.stage.setMinWidth(width/4);
		this.stage.setScene(this.scene);
		this.stage.show();
 	}   

	private MenuBar drawStartMenu(MenuBar menu) {
		Menu controls = new Menu("File");
		MenuItem fullScreen = new MenuItem("Full Screen");
		fullScreen.setOnAction((e)->{
			this.stage.setFullScreen(!this.fullScreen);
			this.fullScreen = !this.fullScreen;
		});

		controls.getItems().addAll(fullScreen);

		menu.getMenus().addAll(controls);

		return menu;
	}

	private Pane drawStartPage(Pane page, double height, double width) {
		Page currentPage = new Home(page, height, width);
		currentPage.draw();

		return page;
	}
}
