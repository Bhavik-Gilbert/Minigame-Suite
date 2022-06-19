package Pages.MiniGames.SnakesnLadders_R;

import java.io.File;

import Tools.ImageReader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class Player {
    private int playerNumber;
    private int playerX, playerY;

    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
        this.playerX = -1;//-1
        this.playerY = 9;//9
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public boolean roll() {
        int roll = (int) (Math.random() * 6);
        this.playerX += roll;

        if(this.playerY == 0 && this.playerX == 9) {
            return true;
        }
        else if(this.playerY == 0 && this.playerX >= 10) {
            this.playerX = 9 - (this.playerX - 9); 
        }
        
        while(this.playerX >= 10) {
            this.playerX -= 10;
            this.playerY--;
        }

        return false;
    }

    public static Label getImage(String path, double height, double width) {
        Label piece = new Label();

        ImageView image = ImageReader.readImage(path, height / 7, width / 7);
        piece.setGraphic(image);

        return piece;
    }

    public Label getPieceImage(double height, double width) {
        String path = "Resources" + File.separator + "Games" + File.separator + "SnakesnLadders" + File.separator
                + "p" + this.playerNumber + ".png";

        return getImage(path, height, width);
    }

    public static Label getBlankPiece(double height, double width) {
        String path = "Resources" + File.separator + "Games" + File.separator + "Blank.png";

        return getImage(path, height, width);
    }
}
