package Pages.MiniGames.Checkers_R;

import Pages.MiniGames.Games;
import Pages.MiniGames.Checkers_R.Piece.COLOUR;
import Pages.MiniGames.Checkers_R.Piece.TYPE;

import java.io.File;
import java.util.*;

import Tools.AlertBox;
import Tools.ImageReader;
import Tools.SoundPlayer;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class Checkers extends Games {
    private ArrayList<ArrayList<Piece>> pieces;
    private HashSet<int[]> selectedZones;
    private HashSet<int[]> killZones;
    private Piece selectedPiece;
    private COLOUR turn;
    private HashMap<COLOUR, Label> score;
    private int pieceCount;
    private boolean stuck;

    private GridPane boardPieces;
    private StackPane board;
    private Label currentTurn, turnCount;

    public Checkers(Pane page, BorderPane root, Double height, Double width) {
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

    private StackPane generateBoard() {
        

        this.pieces = buildBoard();
        this.selectedZones = new HashSet<int[]>();
        this.killZones = new HashSet<int[]>();
        this.turn = COLOUR.WHITE;
        GridPane boardPieces = displayBoard(this.pieces);

        ImageView image = null;
        Label boardImage = new Label();
        while(image==null) {
            image = ImageReader.readImage("Resources" + File.separator + "Games" + File.separator + "Checkers" + File.separator  + "board.png", this.width/2.5, this.width/2.5);
        }
        boardImage.setGraphic(image);


        board = new StackPane();
        board.getChildren().addAll(boardImage, boardPieces);

        return board;
    }

    private ArrayList<ArrayList<Piece>> buildBoard() {
        ArrayList<ArrayList<Piece>> pieces = new ArrayList<ArrayList<Piece>>();
        this.pieceCount = 0;
        this.stuck = false;

        for (int i = 0; i < 8; i++) {
            ArrayList<Piece> row = new ArrayList<Piece>();

            for (int j = 0; j < 8; j++) {
                // add piece to array
                if (i <= 2) {
                    row.add(createPiece(Piece.COLOUR.BLACK, i, j));
                    continue;
                }

                if (i >= 5) {
                    row.add(createPiece(Piece.COLOUR.WHITE, i, j));
                    continue;
                }

                row.add(null);
            }

            pieces.add(row);
        }

        return pieces;
    }

    private Piece createPiece(COLOUR colour, int column, int row) {
        if ((column % 2 == 1 && row % 2 == 0) || (column % 2 == 0 && row % 2 == 1)) {
            this.pieceCount++;
            return new Piece(colour);
        }

        return null;
    }

    private VBox displayInformation() {
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

    private GridPane displayBoard(ArrayList<ArrayList<Piece>> pieces) {
        if(this.pieces==null) return null;
        

        if(boardPieces==null) boardPieces = new GridPane();
        else boardPieces.getChildren().clear();
        

        boardPieces.setId("checkersBoard");
        boardPieces.setHgap(this.width/600);
        boardPieces.setVgap(this.width/650);

        
        this.pieces.forEach((row) -> {
            row.forEach((piece) -> {
                if (piece == null) return;

                Integer listIndex = this.pieces.indexOf(row);
                Integer rowIndex = row.indexOf(piece);

                Label pieceImage = this.pieces.get(listIndex).get(rowIndex).getPieceImage(this.width/5, this.width/5);
                pieceImage.setId("piece");
                pieceImage.setOnMouseClicked((e) -> {
                    pieceActions(listIndex, rowIndex);
                });
                
                if(piece.getSelected()) {
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

        
        for(int i=0; i<this.pieces.size(); i++) {
            for(int j=0; j<this.pieces.get(i).size(); j++) {
                if(pieces.get(i).get(j)!=null) continue;

                int listIndex = i;
                int rowIndex = j;

                Label pieceImage = Piece.getBlankPiece(this.width / 5, this.width / 5);
                pieceImage.setId("zone");
                pieceImage.setOnMouseClicked((e) -> {
                    spaceActions(listIndex, rowIndex);
                });
                this.selectedZones.forEach((coord) -> {
                    if(coord[0]==listIndex && coord[1]== rowIndex) {
                        pieceImage.setId("selectorZone");
                    }
                });

                boardPieces.add(pieceImage, j, i);
            }
        }

        return boardPieces;
    }

    private void pieceActions(int listIndex, int pieceIndex) {
        Piece piece = this.pieces.get(listIndex).get(pieceIndex);

        if(piece==null || piece.getColour() != this.turn) return;

        if(piece.getSelected()) return;

        clearSelections(this.selectedZones, this.killZones);
        selectedPiece = piece;
        piece.setSelected(true);
        selectZoneOptions(piece, listIndex, pieceIndex, false);
        displayBoard(this.pieces);
    }

    private boolean selectZoneOptions(Piece piece, int listIndex, int pieceIndex, boolean killed) {
        if(listIndex<0 || listIndex>this.pieces.size() || pieceIndex<0 || pieceIndex>this.pieces.get(pieceIndex).size()) return false;

        boolean kill = false;

        if(listIndex-1>=0 && pieceIndex-1>=0 && (piece.getColour()==COLOUR.WHITE || piece.getType()==TYPE.KING || killed)) {
            if(selectZones(piece, listIndex-1, listIndex-2, pieceIndex-1, pieceIndex-2)) kill=true;
        }
        if(listIndex-1>=0 && pieceIndex+1<this.pieces.get(listIndex).size() && (piece.getColour()==COLOUR.WHITE || piece.getType()==TYPE.KING || killed)) {
            if(selectZones(piece, listIndex-1, listIndex-2, pieceIndex+1, pieceIndex+2)) kill=true;
        }

        if(listIndex+1<this.pieces.size() && pieceIndex-1>=0 && (piece.getColour()==COLOUR.BLACK || piece.getType()==TYPE.KING || killed)) {
            if(selectZones(piece, listIndex+1, listIndex+2, pieceIndex-1, pieceIndex-2)) kill=true;
        }
        if(listIndex+1<this.pieces.size() && pieceIndex+1<this.pieces.get(listIndex).size() && (piece.getColour()==COLOUR.BLACK || piece.getType()==TYPE.KING || killed)) {
            if(selectZones(piece, listIndex+1, listIndex+2, pieceIndex+1, pieceIndex+2)) kill=true;
        }

        return kill;
    }

    private boolean selectZones(Piece piece, int lIndex, int l2Index, int pIndex, int p2Index) {
        if (this.pieces.get(lIndex).get(pIndex)==null) {
            this.selectedZones.add(new int[] { lIndex, pIndex});
        } else if (l2Index>=0 && p2Index>=0 && l2Index<this.pieces.size() && p2Index<this.pieces.get(pIndex).size() && this.pieces.get(l2Index).get(p2Index) == null) {
            if (piece.getColour() != this.pieces.get(lIndex).get(pIndex).getColour()) {
                this.selectedZones.add(new int[] { l2Index, p2Index });
                this.killZones.add(new int[] {lIndex, pIndex});
                return true;
            }
        }

        return false;
    }

    private void spaceActions(int spaceListIndex, int spaceRowIndex) {
        boolean validMove = false;

        for(int[] coord: this.selectedZones) {
            if (coord[0] == spaceListIndex && coord[1] == spaceRowIndex) {
                validMove = true;
                break;
            }
        }
        
        if(!validMove) {
            return;
        }

        Integer pieceListIndex = null;
        Integer pieceRowIndex = null;

        for(ArrayList<Piece> row: this.pieces) {
            if (row.contains(this.selectedPiece)) {
                pieceListIndex = this.pieces.indexOf(row);
                pieceRowIndex = row.indexOf(this.selectedPiece);
                break;
            }
        }

        if(pieceListIndex!=null && pieceRowIndex!=null) {
            pieceMovement(spaceListIndex, spaceRowIndex, pieceListIndex, pieceRowIndex);
        }

        displayBoard(this.pieces);
    }

    private void pieceMovement(int spaceListIndex, int spaceRowIndex, int pieceListIndex , int pieceRowIndex) {
        movePiece(pieceListIndex, pieceRowIndex, spaceListIndex, spaceRowIndex);
        promotePiece(spaceListIndex);
        if(killPlayer(spaceListIndex, spaceRowIndex, pieceListIndex, pieceRowIndex)) return;
        clearSelections(this.selectedZones, this.killZones);
        changeTurn();
        possibleMoves();
    }

    private void movePiece(int pieceListIndex, int pieceRowIndex, int spaceListIndex, int spaceRowIndex) {
        if (pieceListIndex != spaceListIndex && pieceRowIndex != spaceRowIndex) {
            this.pieces.get(spaceListIndex).set(spaceRowIndex, this.selectedPiece);
            this.pieces.get(pieceListIndex).set(pieceRowIndex, null);
            SoundPlayer.playSound("Resources" +  File.separator + "Sounds" + File.separator + "piece_move.wav");
        }
    }

    private void promotePiece(int spaceListIndex) {
        if (this.selectedPiece.getColour() == COLOUR.WHITE) {
            if (spaceListIndex == 0) {
                this.selectedPiece.promote();
                SoundPlayer.playSound("Resources" +  File.separator + "Sounds" + File.separator + "powerup.wav");
            }
        } else {
            if (spaceListIndex == this.pieces.size() - 1) {
                this.selectedPiece.promote();
                SoundPlayer.playSound("Resources" +  File.separator + "Sounds" + File.separator + "powerup.wav");
            }
        }
    }

    private boolean killPlayer(int spaceListIndex, int spaceRowIndex, int pieceListIndex, int pieceRowIndex) {
        if (Math.abs(spaceListIndex - pieceListIndex) == 2 && Math.abs(spaceRowIndex - pieceRowIndex) == 2) {
            int killListIndex = ((spaceListIndex - pieceListIndex) / 2) + pieceListIndex;
            int killRowIndex = ((spaceRowIndex - pieceRowIndex) / 2) + pieceRowIndex;
            if (this.pieces.get(killListIndex).get(killRowIndex) != null
                    && this.pieces.get(killListIndex).get(killRowIndex).getClass() == Piece.class) {
                this.pieces.get(killListIndex).get(killRowIndex).setDead();
                this.pieces.get(killListIndex).set(killRowIndex, null);

                changeScore();

                clearSelections(this.selectedZones, this.killZones);
                if (selectZoneOptions(this.selectedPiece, spaceListIndex, spaceRowIndex, true)) {
                    displayBoard(this.pieces);
                    return true;
                }
            }
        }
        return false;
    }

    private void changeScore() {
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

    private void changeTurn() {
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

    private void possibleMoves() {

        this.pieces.forEach((row) -> {
            row.forEach((piece) -> {
                if (piece == null || piece.getColour() != this.turn)
                    return;

                this.selectZoneOptions(piece, this.pieces.indexOf(row), row.indexOf(piece), false);
            });
        });

        if (this.selectedZones.isEmpty()){
            if(!this.stuck) {
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

    private void clearSelections(HashSet<int[]> selected, HashSet<int[]> kill) {
        this.selectedZones = selected;
        this.killZones = kill;

        
        this.selectedZones.clear();
        this.killZones.clear();

        this.pieces.forEach((row) -> {
            row.forEach((piece) -> {
                if (piece == null) return;

                piece.setSelected(false);
            });
        });
    }

    private void gameOver(String winner, int score[]) {
        String title = "Game Over";
        String header = "Game Over";
        String message = winner + " wins with a score of " + score[0] + " to " + score[1];

        gameEnd(title, header, message);
    }

    private void gameDraw() {
        String title = "Game Over";
        String header = "Game Over";
        String message = "Both players can't move. Game is a draw.";

        gameEnd(title, header, message);
    }

    private void gameEnd(String title, String header, String message) {
        displayBoard(this.pieces);
        SoundPlayer.playSound("Resources" + File.separator + "Sounds" + File.separator + "game_end.wav");
        AlertBox.informationBox(title, header, message);
        gameFinish();
    }
}
