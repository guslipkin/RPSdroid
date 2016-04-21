package fangorntheent.honey.rpsdroid;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Gus Lipkin on 3/27/2016.
 */
public class AlgGeneral {

    public int totalAlgNumber;
    public int chosenAlgNumber;
    public int matchNumber;

    public ArrayList history;
    public ArrayList winHistory;
    public ArrayList algResults;

    public AlgGeneral() {

        totalAlgNumber = 7;
        chosenAlgNumber = -1;
        matchNumber = 0;

        history = new ArrayList<>();
        winHistory = new ArrayList<>();
        algResults = new ArrayList<>();

        for (int i = 0; i < totalAlgNumber; i++)
            algResults.add(3);
    }

    private int equalPlaySeeder(int a, int b) {
        int random = new Random().nextInt(2);

        if (random == 0)
            return a;
        else
            return b;
    }

    public int winningPlaySeeder(int r, int p, int s) {

        WinningPlay winningPlay = new WinningPlay();

        if ((r > p) && (r > s))
            winningPlay.setWinningPlay(r);
        else if ((p > r) && (p > s))
            winningPlay.setWinningPlay(p);
        else if ((s > r) && (s > p))
            winningPlay.setWinningPlay(s);
        else if (r == p)
            winningPlay.setWinningPlay(equalPlaySeeder(r, p));
        else if (r == s)
            winningPlay.setWinningPlay(equalPlaySeeder(r, s));
        else if (p == s)
            winningPlay.setWinningPlay(equalPlaySeeder(p, s));
        else
            winningPlay.setWinningPlay(new Random().nextInt(3));

        return winningPlay.winningPlay;
    }

    public int losingPlaySeeder(int r, int p, int s) {

        WinningPlay winningPlay = new WinningPlay();

        if ((r < p) && (r < s))
            winningPlay.setWinningPlay(r);
        else if ((p < r) && (p < s))
            winningPlay.setWinningPlay(p);
        else if ((s < r) && (s < p))
            winningPlay.setWinningPlay(s);
        else if (r == p)
            winningPlay.setWinningPlay(equalPlaySeeder(r, p));
        else if (r == s)
            winningPlay.setWinningPlay(equalPlaySeeder(r, s));
        else if (p == s)
            winningPlay.setWinningPlay(equalPlaySeeder(p, s));
        else
            winningPlay.setWinningPlay(new Random().nextInt(3));

        return winningPlay.winningPlay;
    }
}
