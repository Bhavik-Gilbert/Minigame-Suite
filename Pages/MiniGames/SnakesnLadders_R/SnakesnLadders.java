package Pages.MiniGames.SnakesnLadders_R;

import Pages.MiniGames.Games;
import Tools.AlertBox;
import Tools.ImageReader;
import Tools.SoundPlayer;

import java.io.File;
import java.util.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class SnakesnLadders extends Games {
    private GridPane boardPieces;
    private StackPane board;
    private Label turnLabel, actionLabel;
    private Button roll;

    private ArrayList<ArrayList<Player>> pieces;
    private ArrayList<Player> players;
    private int turn;
    private double playerHeight = width/4;
    private double playerWidth = width/4;

    public SnakesnLadders(Pane page, BorderPane root, Double height, Double width) {
        super(page, root, height, width);
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
        for(int i = 1; i <= this.playerCount; i++) {
            Player player = new Player(i);
            players.add(player);
        }
        return players;
    }
    

    private GridPane displayBoard(ArrayList<ArrayList<Player>> pieces) {
        if (this.pieces == null) return null;

        if(this.boardPieces==null) this.boardPieces = new GridPane();
        else boardPieces.getChildren().clear();

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
            boolean validPrevious = (player.getPreviousX() >= 0 && player.getPreviousY() >= 0 && player.getPreviousX() <= 9 && player.getPreviousY() <= 9);
            if(validPrevious) {
                this.pieces.get(player.getPreviousY()).set(player.getPreviousX(), null);
            } 
        }
        for (Player player : players) {
            boolean validCurrent = (player.getPlayerX() >= 0 && player.getPlayerY() >= 0 && player.getPlayerX() <= 9
                    && player.getPlayerY() <= 9);
            if (validCurrent) {
                this.pieces.get(player.getPlayerY()).set(player.getPlayerX(), player);
            }
        }
    }

    private VBox displayInformation() {
        VBox information = new VBox();
        information.setAlignment(Pos.CENTER);
        information.spacingProperty().set(height/100);
        
        this.turnLabel = new Label("Player" + this.turn);
        this.turnLabel.setId("information");

        this.actionLabel = new Label("Roll the dice");
        this.actionLabel.setId("information");

        this.roll = new Button("Roll");
        buttonRoll(this.roll, this.actionLabel);

        VBox informationBox = new VBox();
        informationBox.setId("informationbox");
        informationBox.setAlignment(Pos.CENTER);
        informationBox.getChildren().addAll(this.turnLabel, this.actionLabel);

        information.getChildren().addAll(informationBox, this.roll);

        return information;
    }

    private void playerRoll() {
        int score = this.players.get(turn-1).roll();
        SoundPlayer.playSound("Resources" + File.separator + "Sounds" + File.separator + "dice_roll.wav");
        rollBoardChanges();
        displayBoard(this.pieces);
        buttonNext(this.roll, this.actionLabel, score);
    }

    private void playerNext() {
        nextTurn(this.turnLabel);
        buttonRoll(this.roll, this.actionLabel);
    }

    private void nextTurn(Label turnLabel) {
        this.turn++;
        if(this.turn > this.players.size()) {
            this.turn = 1;
        }

        turnLabel.setText("Player" + this.turn);
    }

    private void buttonRoll(Button roll, Label actionLabel) {
        roll.setText("Roll");
        roll.setOnAction(e -> playerRoll());

        actionLabel.setGraphic(null);
        actionLabel.setText("Roll the dice");
    }

    private void buttonNext(Button roll, Label actionLabel, int score) {
        roll.setText("Next");

        ImageView image = null;
        while (image == null) {
            image = ImageReader.readImage("Resources" + File.separator + "Games" + File.separator + "SnakesnLadders" + File.separator + "dice" + score + ".png", this.width/40, this.width/40);
        }
        actionLabel.setGraphic(image);
        actionLabel.setText(null);

        //game over
        if (score == 100) {
            gameOver();
            return;
        }
            
        roll.setOnAction(e -> playerNext());
    }

    private void gameOver() {
        String title = "Game Over";
        String header = "Game Over";
        String message = "Player " + this.turn + " wins!";

        displayBoard(this.pieces);
        SoundPlayer.playSound("Resources" + File.separator + "Sounds" + File.separator + "game_end.wav");
        AlertBox.informationBox(title, header, message);
        gameFinish();
    }

    private void rollBoardChanges() {
        //ladders
        snakenladders(2,9,0,4);
        snakenladders(5,9,6,7);
        snakenladders(9,8,9,3);
        snakenladders(5,6,4,4);
        snakenladders(2,3,4,0);
        snakenladders(7,3,7,0);

        //snakes
        snakenladders(8,0,8,3);
        snakenladders(0,0,0,3);
        snakenladders(6,1,6,4);
        snakenladders(4,3,1,4);
        snakenladders(6,5,8,8);
        snakenladders(3,6,0,9);
        snakenladders(4,7,4,9);
    }

    private void snakenladders(int x, int y, int goX, int goY) {
        Player player = this.players.get(turn-1);

        if(player.getPlayerX()==x && player.getPlayerY()==y) player.changeLocation(goX, goY);
    }

}