import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.stage.Screen;

import java.util.*;
import java.util.stream.Collectors;

public class Main extends Application{
	private Scene scene;
	private BorderPane root;
	private Pane page;
	private MenuBar menu;

	private Page currentPage;
	private double height;
	private double width;
	public static void main(String[] args) {
		launch(args);
	}
		
	@Override 
	public void start(Stage stage) throws Exception {
		Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		this.height = screenBounds.getHeight()/1.2;
		this.width = screenBounds.getWidth()/1.2;

		this.root = new BorderPane();
	
		this.page = new Pane();
		this.menu = new MenuBar();
		
		root.setTop(createStartMenu(this.menu));
		root.setCenter(createStartPage(this.page));

		this.scene = new Scene(this.root);
		this.scene.getStylesheets().add("CSS/main.css");

		stage.setTitle("Game Suite");
		stage.setMinHeight(this.height);
		stage.setMinWidth(this.width);
		stage.setMaxHeight(this.height);
		stage.setMaxWidth(this.width);
		stage.setScene(this.scene);
		stage.show();
 	}   

	

	private MenuBar createStartMenu(MenuBar menu) {
		return menu;
	}

	private Pane createStartPage(Pane page) {
		this.currentPage = new Home(this.page, this.height, this.width);
		this.currentPage.draw();

		return page;
	}
}
