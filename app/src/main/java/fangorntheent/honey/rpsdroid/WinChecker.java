package fangorntheent.honey.rpsdroid;

import java.util.ArrayList;

/**
 * Created by Gus Lipkin on 3/27/2016.
 */
public class WinChecker {

    public String winnerText;
    public int winnerInt;
    public String winnerWord;

    public WinChecker() {
        winnerInt = -1;
        winnerText = "error";
        winnerWord = "error";
    }

    private void setWinnerVars(int player, int ai) {
        if ((player - ai == 1) || (player - ai == -2)) {
            winnerInt = 0;
            winnerText = "player";
            winnerWord = "   BEATS   ";
        }
        else if (player == ai){
            winnerInt = 1;
            winnerText = "tie";
            winnerWord = " TIES WITH ";
        }
        else {
            winnerInt = 2;
            winnerText = "ai";
            winnerWord = " LOSES  TO ";
        }
    }

    public void setWinner(int player, int ai) {
        setWinnerVars(player, ai);
    }

    public void addWinner(int player, int ai, ArrayList<Integer> history) {
        setWinnerVars(player, ai);
        history.add(winnerInt);
    }
}
