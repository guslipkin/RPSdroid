package fangorntheent.honey.rpsdroid;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Gus Lipkin on 3/28/2016.
 *
 * ALGORITHM PURPOSE
 *  Counteracts player spamming a throw
 * ALGORITHM SUMMARY
 *  if player repeated, increase repeat counter, then play the winning move
 *      repeat counter directly affects weight
 *  if player didn't repeat, reset the repeat counter to 1 then assume they will repeat
 */
public class AlgTwo implements AlgInterface {

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
    public AlgTwo() {

        algNumber = 1;
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

    // Add and return Alg's throw
    public int getAlg(PlayerGeneral playerGeneral, AlgGeneral algGeneral) {

        if (playerGeneral.history.size() < 2)
            return getAlg(algGeneral);

        int algPrev = (Integer)(algGeneral.history.get(algGeneral.history.size() - 1));
        int algPrevPrev = (Integer)(algGeneral.history.get(algGeneral.history.size() - 2));
        int playerPrev = (Integer)(playerGeneral.history.get(playerGeneral.history.size() - 1));
        int playerPrevPrev = (Integer)(playerGeneral.history.get(playerGeneral.history.size() - 2));

        WinChecker winCheckerPrev = new WinChecker();
        winCheckerPrev.setWinner(playerPrev, algPrev);
        WinChecker winCheckerPrevPrev = new WinChecker();
        winCheckerPrevPrev.setWinner(playerPrevPrev, algPrevPrev);
        WinningPlay winningPlay = new WinningPlay(playerPrev);

        if (playerPrev == playerPrevPrev)
            repeat += 1;
        else
            repeat = 1;

        algGeneral.algResults.set(algNumber, winningPlay.winningPlay);
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
