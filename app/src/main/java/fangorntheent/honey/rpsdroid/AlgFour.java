package fangorntheent.honey.rpsdroid;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Gus Lipkin on 3/28/2016.
 *
 * ALGORITHM PURPOSE
 *  Detect the likelihood of player repeating a winning move
 * ALGORITHM SUMMARY
 *  if ai lost, calculate winning play and adjust weight as necessary
 */
public class AlgFour implements AlgInterface {

    // Alg number
    public int algNumber;
    // How valuable Alg results are
    public int total;
    // How often Alg is correct
    public int weight;
    // How many times in a row player has repeated a move
    public int repeat;
    // History of Alg's throws
    public ArrayList history;
    // History of Alg's success or lack thereof
    public ArrayList winHistory;

    // Constructor
    public AlgFour() {

        algNumber = 3;
        total = 1;
        weight = 1;
        repeat = 1;
        history = new ArrayList<>();
        winHistory = new ArrayList<>();
    }

    // Add and return Alg's throw
    public int getAlg(AlgGeneral algGeneral) {

        algGeneral.algResults.set(algNumber, new Random().nextInt(3));
        history.add(algGeneral.algResults.get(algNumber));
        return (Integer)(history.get(history.size() - 1));
    }

    public int getAlg(PlayerGeneral playerGeneral, AlgGeneral algGeneral) {

        if (playerGeneral.history.size() < 2)
            return getAlg(algGeneral);

        int playerPrev = (Integer)(playerGeneral.history.get(playerGeneral.history.size() - 1));
        int playerPrevPrev = (Integer)(playerGeneral.history.get(playerGeneral.history.size() - 2));
        int winPrevPrev = (Integer)(algGeneral.winHistory.get(algGeneral.winHistory.size() - 2));

        WinningPlay winningPlay = new WinningPlay(playerPrev);

        if (playerPrev == playerPrevPrev) {
            algGeneral.algResults.set(algNumber, winningPlay.winningPlay);

            if (winPrevPrev == 0)
                repeat += 3;
            repeat += 2;
        }
        else {
            repeat = 1;
            weight = 1;
        }

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
        weight = repeat;
    }

    // Sets weight to value
    public void setWeight(int value) {
        weight = value * repeat;
    }
}
