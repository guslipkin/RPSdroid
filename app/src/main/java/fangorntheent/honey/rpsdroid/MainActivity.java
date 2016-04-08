package fangorntheent.honey.rpsdroid;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private static Translator translator = new Translator();
    private static int matchNumber = 0;
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

    private void printWinner(int playerPrev, int algPrev) {

        algPrev = new Random().nextInt(3);
        WinChecker winChecker = new WinChecker();
        winChecker.setWinner(playerPrev, algPrev);

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
    }

    /**private static int parseInput(String str) {

        str.trim().toLowerCase();
        if (!((str.equals("r")) || (str.equals("p")) || (str.equals("s")))) {
            playerGeneral.history.remove(playerGeneral.history.size() - 1);
            if (str.equals("scores")) {
                System.out.println(" Player: " + playerScore);
                System.out.println(" Ai: " + aiScore);
                System.out.println(" Tie: " + tieScore);
            }
            if (str.equals("help") || str.equals("h")) {
                System.out.println(" To play: 'r', 'p', or 's'");
                System.out.println(" To see scores: 'scores'");
                System.out.println(" To exit: 'stop', 'exit', or 'quit'");
                System.out.println(" To get help: 'help' or 'h'");
            }
            if (str.equals("stop") || str.equals("exit") || str.equals("quit")) {
                System.out.println("The program will now exit.");
                return -1;
            }
            return 0;
        }
        else
            printWinner((Integer)(playerGeneral.history.get(playerGeneral.history.size() - 1)), (Integer)(algGeneral.history.get(algGeneral.history.size() - 1)));
        return 1;
    }*/

    private static int combineAlgs(ArrayList<AlgInterface> algs) {

        algIndex = 0;

        for (int j = 0; j < algs.size(); j++) {
            algs.get(j).getAlg(playerGeneral, algGeneral);
            algs.get(j).setTotal(0);
            for (int i = 0; i < matchNumber; i++)
                algs.get(j).setTotal(algs.get(j).getTotal() + ((Integer)(algs.get(j).getWinHistory().get(algs.get(j).getWinHistory().size() - 1)) * algs.get(j).getWeight()));
            if (matchNumber > 2)
                for (int i = 0; i <= 2; i++)
                    algs.get(j).setTotal(algs.get(j).getTotal() + ((Integer)(algs.get(j).getWinHistory().get(algs.get(j).getWinHistory().size() - 1)) * algs.get(j).getWeight() * 3));
            if (matchNumber > 3)
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
            if (alg.getWinHistory().size() > 0) {
                if (alg.getWinHistory().get(alg.getWinHistory().size() - 1) == 2)
                    alg.setWeight(alg.getWeight() + 2);
                else if (alg.getWinHistory().get(alg.getWinHistory().size() - 1) == 1)
                    alg.setWeight(alg.getWeight() + 1);
            }
        }
    }

    private static void addWinHistory(ArrayList<AlgInterface> algs) {

        for (AlgInterface alg : algs) {
            if (alg.getHistory().size() > 0)
                winChecker.setWinner((Integer) (playerGeneral.history.get(playerGeneral.history.size() - 1)), alg.getHistory().get(alg.getHistory().size() - 1));
            alg.getWinHistory().add(winChecker.winnerInt);
        }
    }

    /**public static void main(String[] args) {

        algGeneral = new AlgGeneral();
        playerGeneral = new PlayerGeneral();
        winChecker = new WinChecker();
        for (int i = 0; i < 2; i++) {
            playerGeneral.history.add(new Random().nextInt(3));
            algGeneral.history.add(new Random().nextInt(3));
            algGeneral.winHistory.add(new Random().nextInt(3));
        }

        AlgInterface algOne = new AlgOne();
        AlgInterface algTwo = new AlgTwo();
        AlgInterface algThree = new AlgThree();
        AlgInterface algFour = new AlgFour();
        AlgInterface algFive = new AlgFive();
        AlgInterface algSix = new AlgSix();
        AlgInterface algSeven = new AlgSeven();

        ArrayList algList = new ArrayList<AlgInterface>();

        algList.add(algOne);
        algList.add(algTwo);
        algList.add(algThree);
        algList.add(algFour);
        algList.add(algFive);
        algList.add(algSix);
        algList.add(algSeven);

        addWinHistory(algList);

        Scanner reader = new Scanner(System.in);
        String input;
        int parseResult;

        while (true) {
            if (matchNumber == 0)
                System.out.println("To see options, press 'h', or type 'help'." + "\n");
            System.out.println("Round: " + matchNumber);
            System.out.print(" Choose your throw: ");
            input = reader.nextLine();
            playerGeneral.history.add(translator.wordsToNum(input));
            parseResult = parseInput(input);
            if (parseResult == -1)
                break;
            else if (parseResult == 1) {
                setWeight(algList);
                algGeneral.chosenAlgNumber = combineAlgs(algList);
                addChosenAlg(algList);
                addWinHistory(algList);
                matchNumber++;
            }
        }
    }*/

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void onRockButtonClick(View view) {
        playerGeneral.history.add(0);
        onPlayerClick(R.string.rock);
    }

    public void onPaperButtonClick(View view) {
        playerGeneral.history.add(1);
        onPlayerClick(R.string.paper);
    }

    public void onScissorsButtonClick(View view) {
        playerGeneral.history.add(2);
        onPlayerClick(R.string.scissors);
    }

    public void onPlayerClick(int stringID) {
        if (matchNumber == 0) {
            for (int i = 0; i < 2; i++) {
                playerGeneral.history.add(new Random().nextInt(3));
                algGeneral.history.add(new Random().nextInt(3));
                algGeneral.winHistory.add(new Random().nextInt(3));
            }

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
        matchNumber++;

        TextView textView = (TextView) findViewById(R.id.playerPlayTextID);
        textView.setText(stringID);

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
