package fangorntheent.honey.rpsdroid;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Gus Lipkin on 3/27/2016.
 *
 * ALGORITHM PURPOSE
 *  Backbone of ai
 * ALGORITHM SUMMARY
 *  if player won, repeat play
 *  if ai won, play player's most recent play
 *  if tie, play what would have lost to player's most recent play
 */
public class AlgOne implements AlgInterface {

    // Alg number
    public int algNumber;
    // How valuable Alg results are
    public int total;
    // How often Alg is correct
    public int weight;
    // History of Alg's throws
    public ArrayList history;
    // History of Alg's success or lack thereof
    public ArrayList winHistory;

    // Constructor
    public AlgOne() {

        algNumber = 0;
        total = 1;
        weight = 1;
        history = new ArrayList<>();
        winHistory = new ArrayList<>();
    }

    // Add and return Alg's throw
    public int getAlg(AlgGeneral algGeneral) {

        algGeneral.algResults.set(algNumber, new Random().nextInt(3));
        history.add(algGeneral.algResults.get(algNumber));
        return (Integer)(history.get(history.size() - 1));
    }

    // Add and return Alg's throw
    public int getAlg(PlayerGeneral playerGeneral, AlgGeneral algGeneral) {

        if (algGeneral.matchNumber < 2)
            return getAlg(algGeneral);

        int algPrev = (Integer)(algGeneral.history.get(algGeneral.history.size() - 1));
        int playerPrev = (Integer)(playerGeneral.history.get(playerGeneral.history.size() - 1));

        WinChecker winChecker = new WinChecker();
        winChecker.setWinner(playerPrev, algPrev);
        WinningPlay winningPlay = new WinningPlay(playerPrev);

        if (winChecker.winnerText.equals("player"))
            algGeneral.algResults.set(algNumber, algPrev);
        else if (winChecker.winnerText.equals("ai"))
            algGeneral.algResults.set(algNumber, playerPrev);
        else if (winChecker.winnerText.equals("tie"))
            if (playerPrev == 0)
                algGeneral.algResults.set(algNumber, winningPlay.losingPlay);
            else if (playerPrev == 1)
                algGeneral.algResults.set(algNumber, winningPlay.losingPlay);
            else if (playerPrev == 2)
                algGeneral.algResults.set(algNumber, winningPlay.losingPlay);

        history.add(algGeneral.algResults.get(algNumber));
        return (Integer)(history.get(history.size() - 1));
    }

    // Returns total
    public int getTotal() {
        return total;
    }

    // Returns weight
    public int getWeight() {
        return weight;
    }

    // Returns history
    public ArrayList<Integer> getHistory() {
        return history;
    }

    // Returns winHistory
    public ArrayList<Integer> getWinHistory() {
        return winHistory;
    }

    // Sets total to default
    public void setTotal() {
        total = 1;
    }

    // Sets total to value
    public void setTotal(int value) {
        total = value;
    }

    // Sets weight to default
    public void setWeight() {
        weight = 1;
    }

    // Sets weight to value
    public void setWeight(int value) {
        weight = value;
    }
}