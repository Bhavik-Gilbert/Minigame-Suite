package Pages.MiniGames.SnakesnLadders_R;

import java.io.File;
import java.util.*;

import javax.print.attribute.standard.RequestingUserName;

import Pages.MiniGames.Games;
import Pages.MiniGames.Checkers_R.Piece;
import Tools.ImageReader;
import javafx.css.Style;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class SnakesnLadders extends Games {
    private GridPane boardPieces;
    private StackPane board;
    private ArrayList<ArrayList<Player>> pieces;
    private ArrayList<Player> players;
    private int turn;
    private double playerHeight = width/4;
    private double playerWidth = width/4;

    public SnakesnLadders(Pane page, Double height, Double width) {
        super(page, height, width);
    }

    public void draw() {
        this.clear();
        transitionIn(page);

        if(this.playerCount == null) {
            page.getChildren().add(playerNumber(4));
            return;
        } 

        this.grid = drawGrid();
        this.board = generateBoard();

        this.grid.addRow(1, this.board, displayInformation());

        page.getChildren().add(this.grid);
    }

    private StackPane generateBoard() {

        buildBoard();
        GridPane boardPieces = displayBoard(this.pieces);
        boardPieces.setHgap(this.width/275);
        boardPieces.setVgap(this.width/275);
        boardPieces.setPadding(new Insets(width/300, 0, 0, width/300));

        ImageView image = null;
        Label boardImage = new Label();
        while (image == null) {
            image = ImageReader.readImage("Resources" + File.separator + "Games" + File.separator + "SnakesnLadders" + File.separator + "board.png",this.width / 2.5, this.width / 2.5);
        }
        boardImage.setGraphic(image);

        StackPane board = new StackPane();
        board.getChildren().addAll(boardImage, boardPieces);

        return board;
    }

    private void buildBoard() {
        this.pieces = new ArrayList<ArrayList<Player>>();
        this.players = getPlayers();
        this.turn = 1;

        for(int i = 0; i < 10; i++) {
            ArrayList<Player> row = new ArrayList<>();
            for(int j = 0; j < 10; j++) {
                row.add(null);
            }
            this.pieces.add(row);
        }
    }

    private ArrayList<Player> getPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        for(int i = 0; i < this.playerCount; i++) {
            Player player = new Player(i);
            players.add(player);
        }
        return players;
    }
    

    private GridPane displayBoard(ArrayList<ArrayList<Player>> pieces) {
        if (this.pieces == null) return null;

        this.boardPieces = new GridPane();

        updateBoard();
        
        for(int i = 0; i < this.pieces.size(); i++) {
            for(int j = 0; j < this.pieces.get(i).size(); j++) {
                if(this.pieces.get(i).get(j) == null) {
                    this.boardPieces.add(Player.getBlankPiece(this.playerHeight, this.playerWidth), j, i);
                    continue;
                } 
                
                this.boardPieces.add(this.pieces.get(i).get(j).getPieceImage(this.playerHeight, this.playerWidth), j, i);
            }
        }
        return this.boardPieces;
    }

    private void updateBoard() {
        for(Player player: players) {
            if(player.getPlayerX() >=0 && player.getPlayerY() >= 0 && player.getPlayerX() <= 9 && player.getPlayerY() <= 9) {
                this.pieces.get(player.getPlayerY()).set(player.getPlayerX(), player);
            }   
        }
    }

    private VBox displayInformation() {
        VBox information = new VBox();
        information.setAlignment(Pos.CENTER);
        information.spacingProperty().set(height/100);

        Button roll = new Button("Roll");
        roll.setOnAction((e) -> playerRoll());
        
        Label turnLabel = new Label("Turn: Player" + this.turn);
        turnLabel.setId("information");

        VBox informationBox = new VBox();
        informationBox.setId("informationbox");
        informationBox.setAlignment(Pos.CENTER);
        informationBox.getChildren().addAll(turnLabel);

        information.getChildren().addAll(informationBox, roll);

        return information;
    }

    private void playerRoll() {
        System.out.print(turn);
        nextTurn();
    }

    private void nextTurn() {
        this.turn++;
        if(this.turn > this.players.size()) {
            this.turn = 1;
        }
    }
}