package fangorntheent.honey.rpsdroid;

import java.util.ArrayList;

/**
 * Created by Gus Lipkin on 3/27/2016.
 */
public class WinChecker {

    public String winnerText;
    public int winnerInt;

    public WinChecker() {
        winnerInt = -1;
        winnerText = "error";
    }

    public void setWinner(int player, int ai) {
        if ((player - ai == 1) || (player - ai == -2)) {
            winnerInt = 0;
            winnerText = "player";
        }
        else if (player == ai){
            winnerInt = 1;
            winnerText = "tie";
        }
        else {
            winnerInt = 2;
            winnerText = "ai";
        }
    }

    public void addWinner(int player, int ai, ArrayList<Integer> history) {
        if ((player - ai == 1) || (player - ai == -2)) {
            winnerInt = 0;
            winnerText =  "player";
        }
        else if (player == ai){
            winnerInt = 1;
            winnerText = "tie";
        }
        else {
            winnerInt = 2;
            winnerText = "ai";
        }

        history.add(winnerInt);
    }
}
