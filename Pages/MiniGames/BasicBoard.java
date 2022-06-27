package Pages.MiniGames;

import Pages.MiniGames.Games;
import Pages.MiniGames.BoardPiece;
import Pages.MiniGames.BoardPiece.COLOUR;
import Pages.MiniGames.BoardPiece.TYPE;

import java.io.File;
import java.util.*;

import Tools.AlertBox;
import Tools.ImageReader;
import Tools.SoundPlayer;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

abstract public class BasicBoard extends Games{
    protected ArrayList<ArrayList<BoardPiece>> pieces;
    protected HashSet<int[]> selectedZones;
    protected HashSet<int[]> killZones;
    protected BoardPiece selectedPiece;
    protected COLOUR turn;
    protected HashMap<COLOUR, Label> score;
    protected int pieceCount;
    protected boolean stuck;

    protected GridPane boardPieces;
    protected StackPane board;
    protected Label currentTurn, turnCount;
    protected double boardWidth = 576;

    public BasicBoard(Pane page, BorderPane root, Double height, Double width) {
        super(page, root, height, width);
    }

    public void draw() {
        this.clear();
        transitionIn(page);

        this.grid = drawGrid();
        this.board = generateBoard();

        this.grid.addRow(1, this.board, displayInformation());

        page.getChildren().addAll(this.grid);
    }

    protected StackPane generateBoard() {

        this.pieces = buildBoard();
        this.selectedZones = new HashSet<int[]>();
        this.killZones = new HashSet<int[]>();
        this.turn = COLOUR.WHITE;
        GridPane boardPieces = displayBoard(this.pieces);

        ImageView image = null;
        Label boardImage = new Label();
        while (image == null) {
            image = ImageReader.readImage(
                    "Resources" + File.separator + "Games" + File.separator + "Checkers" + File.separator + "board.png",
                    this.boardWidth, this.boardWidth);
        }
        boardImage.setGraphic(image);

        board = new StackPane();
        board.getChildren().addAll(boardImage, boardPieces);

        return board;
    }

    abstract protected ArrayList<ArrayList<BoardPiece>> buildBoard();

    abstract protected BoardPiece createPiece(COLOUR colour, TYPE type, int column, int row);

    protected VBox displayInformation() {
        VBox filler = new VBox();

        VBox information = new VBox();
        information.setId("informationbox");

        this.currentTurn = new Label(this.turn.name() + "'s Turn");
        this.currentTurn.setId("timer");
        this.turnCount = new Label("Turn : 0");
        turnCount.setId("information");
        Label whiteScore = new Label("White : 0");
        whiteScore.setId("information");
        Label blackScore = new Label("Black : 0");
        blackScore.setId("information");

        information.getChildren().addAll(currentTurn, turnCount, whiteScore, blackScore);
        filler.getChildren().addAll(information);

        score = new HashMap<COLOUR, Label>();
        score.put(COLOUR.WHITE, whiteScore);
        score.put(COLOUR.BLACK, blackScore);

        return filler;
    }

    protected GridPane displayBoard(ArrayList<ArrayList<BoardPiece>> pieces) {
        if (this.pieces == null)
            return null;

        if (boardPieces == null)
            boardPieces = new GridPane();
        else
            boardPieces.getChildren().clear();

        boardPieces.setId("checkersBoard");
        boardPieces.setHgap(this.boardWidth / 240);
        boardPieces.setVgap(this.boardWidth / 260);

        this.pieces.forEach((row) -> {
            row.forEach((piece) -> {
                if (piece == null)
                    return;

                Integer listIndex = this.pieces.indexOf(row);
                Integer rowIndex = row.indexOf(piece);

                Label pieceImage = this.pieces.get(listIndex).get(rowIndex).getPieceImage(this.boardWidth / 2,
                        this.boardWidth / 2);
                pieceImage.setId("piece");
                pieceImage.setOnMouseClicked((e) -> {
                    pieceActions(listIndex, rowIndex);
                });

                if (piece.getSelected()) {
                    pieceImage.setId("selectorPiece");
                }
                this.killZones.forEach((coord) -> {
                    if (coord[0] == listIndex && coord[1] == rowIndex) {
                        pieceImage.setId("killPiece");
                    }
                });

                boardPieces.add(pieceImage, rowIndex, listIndex);
            });
        });

        for (int i = 0; i < this.pieces.size(); i++) {
            for (int j = 0; j < this.pieces.get(i).size(); j++) {
                if (pieces.get(i).get(j) != null)
                    continue;

                int listIndex = i;
                int rowIndex = j;

                Label pieceImage = BoardPiece.getBlankPiece(this.boardWidth / 2, this.boardWidth / 2);
                pieceImage.setId("zone");
                pieceImage.setOnMouseClicked((e) -> {
                    spaceActions(listIndex, rowIndex);
                });
                this.selectedZones.forEach((coord) -> {
                    if (coord[0] == listIndex && coord[1] == rowIndex) {
                        pieceImage.setId("selectorZone");
                    }
                });

                boardPieces.add(pieceImage, j, i);
            }
        }

        return boardPieces;
    }

    protected void pieceActions(int listIndex, int pieceIndex) {
        BoardPiece piece = this.pieces.get(listIndex).get(pieceIndex);

        if (piece == null || piece.getColour() != this.turn)
            return;

        if (piece.getSelected())
            return;

        clearSelections(this.selectedZones, this.killZones);
        selectedPiece = piece;
        piece.setSelected(true);
        selectZoneOptions(piece, listIndex, pieceIndex, false);
        displayBoard(this.pieces);
    }

    abstract protected boolean selectZoneOptions(BoardPiece piece, int listIndex, int pieceIndex, boolean killed);

    abstract protected boolean selectZones(BoardPiece piece, int lIndex, int l2Index, int pIndex, int p2Index);

    protected void spaceActions(int spaceListIndex, int spaceRowIndex) {
        boolean validMove = false;

        for (int[] coord : this.selectedZones) {
            if (coord[0] == spaceListIndex && coord[1] == spaceRowIndex) {
                validMove = true;
                break;
            }
        }

        if (!validMove) {
            return;
        }

        Integer pieceListIndex = null;
        Integer pieceRowIndex = null;

        for (ArrayList<BoardPiece> row : this.pieces) {
            if (row.contains(this.selectedPiece)) {
                pieceListIndex = this.pieces.indexOf(row);
                pieceRowIndex = row.indexOf(this.selectedPiece);
                break;
            }
        }

        if (pieceListIndex != null && pieceRowIndex != null) {
            pieceMovement(spaceListIndex, spaceRowIndex, pieceListIndex, pieceRowIndex);
        }

        displayBoard(this.pieces);
    }

    protected void pieceMovement(int spaceListIndex, int spaceRowIndex, int pieceListIndex, int pieceRowIndex) {
        movePiece(pieceListIndex, pieceRowIndex, spaceListIndex, spaceRowIndex);
        promotePiece(spaceListIndex);
        if (killPlayer(spaceListIndex, spaceRowIndex, pieceListIndex, pieceRowIndex))
            return;
        clearSelections(this.selectedZones, this.killZones);
        changeTurn();
        possibleMoves();
    }

    protected void movePiece(int pieceListIndex, int pieceRowIndex, int spaceListIndex, int spaceRowIndex) {
        if (pieceListIndex != spaceListIndex && pieceRowIndex != spaceRowIndex) {
            this.pieces.get(spaceListIndex).set(spaceRowIndex, this.selectedPiece);
            this.pieces.get(pieceListIndex).set(pieceRowIndex, null);
            SoundPlayer.playSound("Resources" + File.separator + "Sounds" + File.separator + "piece_move.wav");
        }
    }

    abstract protected void promotePiece(int spaceListIndex);

    abstract protected boolean killPlayer(int spaceListIndex, int spaceRowIndex, int pieceListIndex, int pieceRowIndex);

    protected void changeScore() {
        Label scoreLabel = this.score.get(turn);
        String[] scoreText = scoreLabel.getText().split(" ");
        int scoreNumber = Integer.parseInt(scoreText[scoreText.length - 1]);
        scoreNumber += 1;

        Label opponentScoreLabel = null;
        switch (turn) {
            case WHITE:
                scoreLabel.setText("White : " + scoreNumber);

                opponentScoreLabel = this.score.get(COLOUR.BLACK);
                break;
            case BLACK:
                scoreLabel.setText("Black : " + scoreNumber);

                opponentScoreLabel = this.score.get(COLOUR.WHITE);
                break;
        }

        if (scoreNumber >= (this.pieceCount / 2) && opponentScoreLabel != null) {
            String[] opponentScoreText = opponentScoreLabel.getText().split(" ");
            int opponentScoreNumber = Integer.parseInt(opponentScoreText[opponentScoreText.length - 1]);
            gameOver(turn.name(), new int[] { scoreNumber, opponentScoreNumber });
        }
    }

    protected void changeTurn() {
        if (this.turn == COLOUR.WHITE) {
            this.turn = COLOUR.BLACK;
            currentTurn.setText("Black's Turn");
        } else {
            this.turn = COLOUR.WHITE;
            currentTurn.setText("White's Turn");
        }

        String[] turnText = turnCount.getText().split(" ");
        int turnNumber = Integer.parseInt(turnText[turnText.length - 1]);
        turnNumber += 1;
        turnCount.setText("Turn : " + turnNumber);
    }

    protected void possibleMoves() {

        this.pieces.forEach((row) -> {
            row.forEach((piece) -> {
                if (piece == null || piece.getColour() != this.turn)
                    return;

                this.selectZoneOptions(piece, this.pieces.indexOf(row), row.indexOf(piece), false);
            });
        });

        if (this.selectedZones.isEmpty()) {
            if (!this.stuck) {
                changeTurn();
                this.stuck = true;
                possibleMoves();
            } else {
                gameDraw();
            }
            return;
        }

        this.stuck = false;
        clearSelections(this.selectedZones, this.killZones);
    }

    protected void clearSelections(HashSet<int[]> selected, HashSet<int[]> kill) {
        this.selectedZones = selected;
        this.killZones = kill;

        this.selectedZones.clear();
        this.killZones.clear();

        this.pieces.forEach((row) -> {
            row.forEach((piece) -> {
                if (piece == null)
                    return;

                piece.setSelected(false);
            });
        });
    }

    protected void gameOver(String winner, int score[]) {
        String title = "Game Over";
        String header = "Game Over";
        String message = winner + " wins with a score of " + score[0] + " to " + score[1];

        gameEnd(title, header, message);
    }

    protected void gameDraw() {
        String title = "Game Over";
        String header = "Game Over";
        String message = "Both players can't move. Game is a draw.";

        gameEnd(title, header, message);
    }

    protected void gameEnd(String title, String header, String message) {
        displayBoard(this.pieces);
        SoundPlayer.playSound("Resources" + File.separator + "Sounds" + File.separator + "game_end.wav");
        AlertBox.informationBox(title, header, message);
        gameFinish();
    }
    
}
