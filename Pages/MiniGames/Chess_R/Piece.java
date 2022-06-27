package Pages.MiniGames.Chess_R;

import Pages.MiniGames.BoardPiece;

import java.io.File;
import javafx.scene.control.Label;

public class Piece extends BoardPiece{

    public Piece(COLOUR colour) {
        super(colour);
    }

    public void promote(TYPE type) {
        if(type == TYPE.KING || type == TYPE.PAWN) return;

        setType(type);
    }

    public Label getPieceImage(double height, double width) {
        if (this.type == TYPE.DEAD) {
            getBlankPiece(height, width);
        }

        String path = "Resources" + File.separator + "Games" + File.separator + "Chess" + File.separator
                + this.colour.name() + "_" + this.type.name() + ".png";

        return getImage(path, height, width);
    }
}
