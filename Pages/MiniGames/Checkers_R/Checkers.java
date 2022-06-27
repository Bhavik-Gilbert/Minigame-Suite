package Pages.MiniGames.Checkers_R;

import java.io.File;
import java.util.ArrayList;

import Pages.MiniGames.BasicBoard;
import Pages.MiniGames.BoardPiece;
import Pages.MiniGames.BoardPiece.COLOUR;
import Pages.MiniGames.BoardPiece.TYPE;
import Tools.SoundPlayer;
import javafx.scene.layout.*;

public class Checkers extends BasicBoard {
    public Checkers(Pane page, BorderPane root, Double height, Double width) {
        super(page, root, height, width);
    }
    
    protected ArrayList<ArrayList<BoardPiece>> buildBoard() {
        ArrayList<ArrayList<BoardPiece>> pieces = new ArrayList<ArrayList<BoardPiece>>();
        this.pieceCount = 0;
        this.stuck = false;

        for (int i = 0; i < 8; i++) {
            ArrayList<BoardPiece> row = new ArrayList<BoardPiece>();

            for (int j = 0; j < 8; j++) {
                // add piece to array
                if (i <= 2) {
                    row.add(createPiece(BoardPiece.COLOUR.BLACK, TYPE.PAWN, i, j));
                    continue;
                }

                if (i >= 5) {
                    row.add(createPiece(BoardPiece.COLOUR.WHITE, TYPE.PAWN, i, j));
                    continue;
                }

                row.add(null);
            }

            pieces.add(row);
        }

        return pieces;
    }

    protected BoardPiece createPiece(COLOUR colour, TYPE type, int column, int row) {
        if ((column % 2 == 1 && row % 2 == 0) || (column % 2 == 0 && row % 2 == 1)) {
            this.pieceCount++;
            return new Piece(colour);
        }

        return null;
    }

    protected boolean selectZoneOptions(BoardPiece piece, int listIndex, int pieceIndex, boolean killed) {
        if (listIndex < 0 || listIndex > this.pieces.size() || pieceIndex < 0
                || pieceIndex > this.pieces.get(pieceIndex).size())
            return false;

        boolean kill = false;

        if (listIndex - 1 >= 0 && pieceIndex - 1 >= 0
                && (piece.getColour() == COLOUR.WHITE || piece.getType() == TYPE.KING || killed)) {
            if (selectZones(piece, listIndex - 1, listIndex - 2, pieceIndex - 1, pieceIndex - 2))
                kill = true;
        }
        if (listIndex - 1 >= 0 && pieceIndex + 1 < this.pieces.get(listIndex).size()
                && (piece.getColour() == COLOUR.WHITE || piece.getType() == TYPE.KING || killed)) {
            if (selectZones(piece, listIndex - 1, listIndex - 2, pieceIndex + 1, pieceIndex + 2))
                kill = true;
        }

        if (listIndex + 1 < this.pieces.size() && pieceIndex - 1 >= 0
                && (piece.getColour() == COLOUR.BLACK || piece.getType() == TYPE.KING || killed)) {
            if (selectZones(piece, listIndex + 1, listIndex + 2, pieceIndex - 1, pieceIndex - 2))
                kill = true;
        }
        if (listIndex + 1 < this.pieces.size() && pieceIndex + 1 < this.pieces.get(listIndex).size()
                && (piece.getColour() == COLOUR.BLACK || piece.getType() == TYPE.KING || killed)) {
            if (selectZones(piece, listIndex + 1, listIndex + 2, pieceIndex + 1, pieceIndex + 2))
                kill = true;
        }

        return kill;
    }

    protected boolean selectZones(BoardPiece piece, int lIndex, int l2Index, int pIndex, int p2Index) {
        if (this.pieces.get(lIndex).get(pIndex) == null) {
            this.selectedZones.add(new int[] { lIndex, pIndex });
        } else if (l2Index >= 0 && p2Index >= 0 && l2Index < this.pieces.size()
                && p2Index < this.pieces.get(pIndex).size() && this.pieces.get(l2Index).get(p2Index) == null) {
            if (piece.getColour() != this.pieces.get(lIndex).get(pIndex).getColour()) {
                this.selectedZones.add(new int[] { l2Index, p2Index });
                this.killZones.add(new int[] { lIndex, pIndex });
                return true;
            }
        }

        return false;
    }

    protected boolean killPlayer(int spaceListIndex, int spaceRowIndex, int pieceListIndex, int pieceRowIndex) {
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

    protected void promotePiece(int spaceListIndex) {
        if (this.selectedPiece.getColour() == COLOUR.WHITE) {
            if (spaceListIndex == 0) {
                this.selectedPiece.promote(TYPE.KING);
                SoundPlayer.playSound("Resources" + File.separator + "Sounds" + File.separator + "powerup.wav");
            }
        } else {
            if (spaceListIndex == this.pieces.size() - 1) {
                this.selectedPiece.promote(TYPE.KING);
                SoundPlayer.playSound("Resources" + File.separator + "Sounds" + File.separator + "powerup.wav");
            }
        }
    }
}
