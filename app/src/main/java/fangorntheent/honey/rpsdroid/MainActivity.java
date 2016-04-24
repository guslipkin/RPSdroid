package fangorntheent.honey.rpsdroid;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static Translator translator = new Translator();
    private static int algIndex;
    private static int playerScore = 0;
    private static int aiScore = 0;
    private static int tieScore = 0;

    private static AlgGeneral algGeneral = new AlgGeneral();
    private static PlayerGeneral playerGeneral = new PlayerGeneral();
    private static WinChecker winChecker = new WinChecker();

    private ArrayList algList = new ArrayList<AlgInterface>();
    AlgInterface algOne = new AlgOne();
    AlgInterface algTwo = new AlgTwo();
    AlgInterface algThree = new AlgThree();
    AlgInterface algFour = new AlgFour();
    AlgInterface algFive = new AlgFive();
    AlgInterface algSix = new AlgSix();
    AlgInterface algSeven = new AlgSeven();

    static int[] matchNumberHistory = new int[21];
    //static int[] weightArray = new int[6];

    private void newGame() {

        translator = new Translator();
        playerScore = 0;
        aiScore = 0;
        tieScore = 0;

        algGeneral = new AlgGeneral();
        playerGeneral = new PlayerGeneral();
        winChecker = new WinChecker();

        algList = new ArrayList<AlgInterface>();
        algOne = new AlgOne();
        algTwo = new AlgTwo();
        algThree = new AlgThree();
        algFour = new AlgFour();
        algFive = new AlgFive();
        algSix = new AlgSix();
        algSeven = new AlgSeven();

        for (int i = 0; i < 21; i++)
            matchNumberHistory[i] = -1;
    }

    private void printStats() {
        String s;

        for (int i = 0; i < 20; i++)
            matchNumberHistory[i] = matchNumberHistory[i + 1];
        matchNumberHistory[20] = algGeneral.matchNumber;
        s = "\n";
        for (int i = 20; i > 0; i--) {
            if ((matchNumberHistory[i] == -1) || matchNumberHistory[i] == 0)
                s += "";
            else
                s += (matchNumberHistory[i] + "\n");
        }

        TextView matchNumberTextView = (TextView) findViewById(R.id.matchNumberTextID);
        //s = "";
        //for (int i = 0; i < 6; i++)
        //    s += weightArray[i] + "\n";
        matchNumberTextView.setText(s);

        String playerHistory = "\n";
        String algHistory = "\n";
        String outcomeHistory = "\n";
        int size = playerGeneral.history.size();
        if (playerGeneral.history.size() >= 20)
            size = 20;
        for (int i = 0; i < size; i++) {
            playerHistory += (translator.numToWords((Integer)(playerGeneral.history.get(playerGeneral.history.size() - 1 - i))) + "\n");
            algHistory += (translator.numToWords((Integer)(algGeneral.history.get(algGeneral.history.size() - 1 - i))) + "\n");
            winChecker.setWinner(
                    (Integer)(playerGeneral.history.get(playerGeneral.history.size() - 1 - i)),
                    (Integer)(algGeneral.history.get(algGeneral.history.size() - 1 - i)));
            outcomeHistory += (winChecker.winnerWord + "\n");
        }
        TextView playerHistoryTextView = (TextView) findViewById(R.id.playerHistoryTextID);
        TextView algHistoryTextView = (TextView) findViewById(R.id.algHistoryTextID);
        TextView outcomeHistoryTextView = (TextView) findViewById(R.id.outcomeHistoryTextID);
        playerHistoryTextView.setText(playerHistory);
        algHistoryTextView.setText(algHistory);
        outcomeHistoryTextView.setText(outcomeHistory);


        /** Score text updater */
        s = ("Player: " + playerScore + "\n" +
                "AI: " + aiScore + "\n" +
                "Ties: " + tieScore);
        TextView scoreTextView = (TextView) findViewById(R.id.scoreTextID);
        scoreTextView.setText(s);
    }

    private void printWinner(int playerPrev, int algPrev) {

        algPrev = new Random().nextInt(3);
        winChecker.setWinner(playerPrev, algPrev);

        TextView playerTextView = (TextView) findViewById(R.id.playerPlayTextID);
        playerTextView.setText(translator.numToWords(playerPrev));

        TextView algTextView = (TextView) findViewById(R.id.algPlayTextID);
        algTextView.setText(translator.numToWords(algPrev));

        TextView outcomeTextView = (TextView) findViewById(R.id.outcomeTextID);
        if (winChecker.winnerInt == 0) {
            outcomeTextView.setText(R.string.beats);
            playerScore++;
        }
        else if (winChecker.winnerInt == 1) {
            outcomeTextView.setText(R.string.ties_with);
            tieScore++;
        }
        else if (winChecker.winnerInt == 2) {
            outcomeTextView.setText(R.string.loses_to);
            aiScore++;
        }

        printStats();
    }

    private static int combineAlgs(ArrayList<AlgInterface> algs) {

        algIndex = 0;

        for (int j = 0; j < algs.size(); j++) {
            algs.get(j).getAlg(playerGeneral, algGeneral);
            algs.get(j).setTotal(0);
            for (int i = 0; i < algGeneral.matchNumber; i++)
                algs.get(j).setTotal(algs.get(j).getTotal() + ((Integer)(algs.get(j).getWinHistory().get(algs.get(j).getWinHistory().size() - 1)) * algs.get(j).getWeight()));
            if (algGeneral.matchNumber > 2)
                for (int i = 0; i <= 2; i++)
                    algs.get(j).setTotal(algs.get(j).getTotal() + ((Integer)(algs.get(j).getWinHistory().get(algs.get(j).getWinHistory().size() - 1)) * algs.get(j).getWeight() * 3));
            if (algGeneral.matchNumber > 3)
                for (int i = 3; i <= 4; i++)
                    algs.get(j).setTotal(algs.get(j).getTotal() + ((Integer)(algs.get(j).getWinHistory().get(algs.get(j).getWinHistory().size() - 1)) * algs.get(j).getWeight() * 2));

        }

        for (int i = 0; i < algGeneral.algResults.size(); i++) {
            algGeneral.algResults.set(i, algs.get(i).getTotal());
            if ((Integer)(algGeneral.algResults.get(i)) > (Integer)(algGeneral.algResults.get(algIndex)))
                algIndex = i;
        }

        algGeneral.chosenAlgNumber = algIndex;
        algGeneral.history.add(algs.get(algIndex).getHistory().get(algs.get(algIndex).getHistory().size() - 1));
        return algIndex;
    }

    private static void addChosenAlg(ArrayList<AlgInterface> algs) {

        int algPrev = algs.get(algGeneral.chosenAlgNumber).getHistory().get(algs.get(algGeneral.chosenAlgNumber).getHistory().size() - 1);
        winChecker.addWinner((Integer) (playerGeneral.history.get(playerGeneral.history.size() - 1)), algPrev, algGeneral.winHistory);
    }

    private static void setWeight(ArrayList<AlgInterface> algs) {

        for (AlgInterface alg : algs) {
            double weight = alg.getWeight();
            if (alg.getWinHistory().size() > 0) {
                if (alg.getWinHistory().get(alg.getWinHistory().size() - 1) == 2)
                    alg.setWeight((int) Math.ceil((alg.getWeight() / algGeneral.matchNumber) + 2));
                else if (alg.getWinHistory().get(alg.getWinHistory().size() - 1) == 1)
                    alg.setWeight((int) Math.ceil((alg.getWeight() / algGeneral.matchNumber) + 1));
            }
        }

        //for (int i = 0; i < algs.size() - 1; i++)
        //    weightArray[i] = algs.get(i).getTotal();
    }

    private static void addWinHistory(ArrayList<AlgInterface> algs) {

        for (AlgInterface alg : algs) {
            if (alg.getHistory().size() > 0)
                winChecker.setWinner((Integer) (playerGeneral.history.get(playerGeneral.history.size() - 1)), alg.getHistory().get(alg.getHistory().size() - 1));
            alg.getWinHistory().add(winChecker.winnerInt);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newGame();
                Snackbar.make(view, "The game will reset on your next throw", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void onRockButtonClick(View view) {
        playerGeneral.history.add(0);
        onPlayerClick();
    }

    public void onPaperButtonClick(View view) {
        playerGeneral.history.add(1);
        onPlayerClick();
    }

    public void onScissorsButtonClick(View view) {
        playerGeneral.history.add(2);
        onPlayerClick();
    }

    public void onPlayerClick() {
        if (algGeneral.matchNumber == 0) {

            algList.add(algOne);
            algList.add(algTwo);
            algList.add(algThree);
            algList.add(algFour);
            algList.add(algFive);
            algList.add(algSix);
            algList.add(algSeven);
        }


        setWeight(algList);
        algGeneral.chosenAlgNumber = combineAlgs(algList);
        addChosenAlg(algList);
        addWinHistory(algList);
        algGeneral.matchNumber++;

        printWinner((Integer)(playerGeneral.history.get(playerGeneral.history.size()- 1)), (Integer)(algGeneral.history.get(algGeneral.history.size() - 1)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
