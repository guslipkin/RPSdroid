package fangorntheent.honey.rpsdroid;

/**
 * Created by Gus Lipkin on 3/27/2016.
 */
public class Translator {

    private int wordsToNum;
    private String numToWords;

    public Translator() {
        wordsToNum = -1;
        numToWords = "Error";
    }

    public int wordsToNum(String input) {
        switch (input) {
            case "r":
                wordsToNum = 0;
                break;
            case "p":
                wordsToNum = 1;
                break;
            case "s":
                wordsToNum = 2;
                break;
            case "rock":
                wordsToNum = 0;
                break;
            case "paper":
                wordsToNum = 1;
                break;
            case "scissors":
                wordsToNum = 2;
                break;
            case "Rock":
                wordsToNum = 0;
                break;
            case "Paper":
                wordsToNum = 1;
                break;
            case "Scissors":
                wordsToNum = 2;
                break;
            default:
                wordsToNum = -1;
                break;
        }

        return wordsToNum;
    }

    public String wordsToNumString(String input) {
        switch (input) {
            case "r":
                wordsToNum = 0;
                break;
            case "p":
                wordsToNum = 1;
                break;
            case "s":
                wordsToNum = 2;
                break;
            case "rock":
                wordsToNum = 0;
                break;
            case "paper":
                wordsToNum = 1;
                break;
            case "scissors":
                wordsToNum = 2;
                break;
            case "Rock":
                wordsToNum = 0;
                break;
            case "Paper":
                wordsToNum = 1;
                break;
            case "Scissors":
                wordsToNum = 2;
                break;
            default:
                wordsToNum = -1;
                break;
        }

        return ("" + wordsToNum);
    }

    public String numToWords(int input) {
        switch (input) {
            case 0:
                numToWords = "Rock";
                break;
            case 1:
                numToWords = "Paper";
                break;
            case 2:
                numToWords = "Scissors";
                break;
            default:
                numToWords = "Error";
                break;
        }

        return numToWords;
    }
}
