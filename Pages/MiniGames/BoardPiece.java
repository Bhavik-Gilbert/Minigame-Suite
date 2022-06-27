package Pages.MiniGames;

import java.io.File;

import Tools.ImageReader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

abstract public class BoardPiece {
    abstract public Label getPieceImage(double height, double width);
    abstract public void promote(TYPE type);

    public static enum COLOUR {
        BLACK, WHITE
    }

    public static enum TYPE {
        KING, PAWN, DEAD
    }

    protected COLOUR colour;
    protected TYPE type;
    protected boolean selected;

    public BoardPiece(COLOUR colour) {
        this.colour = colour;
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

    protected void setType(TYPE type) {
        this.type = type;
    }

    public void setDead() {
        setType(TYPE.DEAD);
    }

    public static Label getImage(String path, double height, double width) {
        Label piece = new Label();

        ImageView image = ImageReader.readImage(path, height / 7, width / 7);
        piece.setGraphic(image);

        return piece;
    }

    public static Label getBlankPiece(double height, double width) {
        String path = "Resources" + File.separator + "Games" + File.separator + "Blank.png";

        return getImage(path, height, width);
    }
}
