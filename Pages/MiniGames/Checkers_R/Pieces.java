package Pages.MiniGames.Checkers_R;

public class Pieces {
    public static enum COLOUR {
        BLACK, WHITE
    }
    private static enum TYPE {
        KING, PAWN, DEAD
    }

    private COLOUR colour;
    private TYPE type;

    public Pieces(COLOUR colour) {
        this.colour = colour;
        this.type = type.PAWN;
    }

    public COLOUR getColour() {
        return colour;
    }

    public TYPE getType() {
        return type;
    }
}
