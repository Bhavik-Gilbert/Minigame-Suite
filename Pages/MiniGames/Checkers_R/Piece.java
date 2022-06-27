package Pages.MiniGames.Checkers_R;

import Pages.MiniGames.BoardPiece;

import java.io.File;
import javafx.scene.control.Label;

public class Piece extends BoardPiece{
    public Piece(COLOUR colour) {
        super(colour);
        this.type = type.PAWN;
    }

    public void promote(TYPE type) {
        setType(TYPE.KING);
    }

    public Label getPieceImage(double height, double width) {
        if (this.type == TYPE.DEAD) {
            getBlankPiece(height, width);
        }

        String path = "Resources" + File.separator + "Games" + File.separator + "Checkers" + File.separator + this.colour.name() + "_" + this.type.name() + ".png";

        return getImage(path, height, width);
    }
}
