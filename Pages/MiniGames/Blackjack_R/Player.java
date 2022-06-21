package Pages.MiniGames.Blackjack_R;

import java.util.ArrayList;

public class Player {
    private int playerNumber;
    private int playerScore;
    private ArrayList<Blackjack.CARDS> hand;
    private boolean bust;
    private boolean stand;

    public Player(int player, ArrayList<Blackjack.CARDS> hand, int score) {
        this.playerNumber = player;
        this.bust = false;
        this.hand = hand;
        this.playerScore = score;
    } 
    
    public int getPlayerNumber() {
        return this.playerNumber;
    }

    public int getPlayerScore() {
        return this.playerScore;
    }

    public boolean getBust() { 
        return this.bust;
    }

    public boolean getStand() {
        return this.stand;
    }

    public void setPlayerScore(int score) {
        this.playerScore = score;
    }

    public void setBust() {
        this.bust = true;
    }

    public void setStand() {
        this.stand = true;
    }

    public void addHand(Blackjack.CARDS card) {
        this.hand.add(card);
    }
    
    public ArrayList<Blackjack.CARDS> getHand() {
        return this.hand;
    }
}
