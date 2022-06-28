package Pages.MiniGames.Chess_R;

import java.io.File;
import java.lang.ProcessBuilder.Redirect.Type;
import java.util.*;

import Pages.MiniGames.BasicBoard;
import Pages.MiniGames.BoardPiece.COLOUR;
import Pages.MiniGames.BoardPiece.TYPE;
import Tools.SoundPlayer;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class Chess extends BasicBoard {
    private TYPE promoteType = null;
    private final ArrayList<TYPE> promotionOptions = new ArrayList<TYPE>(Arrays.asList(TYPE.QUEEN, TYPE.ROOK, TYPE.KNIGHT, TYPE.BISHOP));

    public Chess(Pane page, BorderPane root, Double height, Double width) {
        super(page, root, height, width);
    }

    protected void promotePiece(int spaceListIndex) {
        if (this.promoteType == null) {
            promoteScreen();
            return;
        }

        Boolean whitePromotion = (this.selectedPiece.getColour() == COLOUR.WHITE) && (spaceListIndex == 0);
        Boolean blackPromotion = (this.selectedPiece.getColour() == COLOUR.BLACK) && (spaceListIndex == this.pieces.size() - 1);
        if (whitePromotion || blackPromotion) {
            this.selectedPiece.promote(promoteType);
            this.promoteType = null;
            SoundPlayer.playSound("Resources" + File.separator + "Sounds" + File.separator + "powerup.wav");
        }
    }

    private void promoteScreen() {
        this.grid.getChildren().remove(this.board);
        this.grid.getChildren().remove(this.informationBox);

        VBox promoteScreen = new VBox();
        promoteScreen.setAlignment(Pos.CENTER);
        FlowPane promoteChoices = new FlowPane();
        promoteChoices.setAlignment(Pos.CENTER);
        
        Label promoteLabel = new Label("Promote your piece!");

        for (TYPE type : TYPE.values()) {
            if(!promotionOptions.contains(type)) continue;

            Label typeLabel = new Label(type.name());
            typeLabel.setOnMouseClicked(e -> {
                this.promoteType = type;
                this.promoteScreen();

                this.grid.getChildren().remove(promoteScreen);
                this.grid.addRow(1, this.board, this.informationBox);
            });

            promoteChoices.getChildren().add(typeLabel);
        }

        promoteScreen.getChildren().addAll(promoteLabel, promoteChoices);
    }
}
