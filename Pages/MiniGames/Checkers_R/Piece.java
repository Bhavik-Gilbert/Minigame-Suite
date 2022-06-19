package Pages.MiniGames.Checkers_R;

import java.io.File;

import Tools.ImageReader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class Piece {
    public static enum COLOUR {
        BLACK, WHITE
    }
    public static enum TYPE {
        KING, PAWN, DEAD 
    }

    private COLOUR colour;
    private TYPE type;
    private boolean selected;

    public Piece(COLOUR colour) {
        this.colour = colour;
        this.type = type.PAWN;
        selected = false;
    }

    public COLOUR getColour() {
        return colour;
    }

    public TYPE getType() {
        return type;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    private void setType(TYPE type) {
        this.type = type;
    }
    public void setDead() {
        setType(TYPE.DEAD);
    }

    public void promote() {
        setType(TYPE.KING);
    }

    public static Label getImage(String path, double height, double width) {
        Label piece = new Label();

        ImageView image = ImageReader.readImage(path, height/7, width/7);
        piece.setGraphic(image);

        return piece;
    }

    public Label getPieceImage(double height, double width) {
        if(this.type == TYPE.DEAD) {
            getBlankPiece(height, width);
        }

        String path = "Resources" + File.separator + "Games" + File.separator + "Checkers" + File.separator + this.colour.name()  + "_" + this.type.name() + ".png";

        return getImage(path, height, width);
    }

    public static Label getBlankPiece(double height, double width) {
        String path = "Resources" + File.separator + "Games" + File.separator + "Blank.png";

        return getImage(path, height, width);
    }
}
