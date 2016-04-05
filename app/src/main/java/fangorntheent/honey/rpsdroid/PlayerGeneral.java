package fangorntheent.honey.rpsdroid;

import java.util.ArrayList;

/**
 * Created by Gus Lipkin on 3/28/2016.
 */
public class PlayerGeneral {

    public int rCount;
    public int pCount;
    public int sCount;
    public ArrayList history;

    public PlayerGeneral() {
        rCount = 0;
        pCount = 0;
        sCount = 0;
        history = new ArrayList<Integer>();
    }

    public void setThrowCount(int range) {

        rCount = 0;
        pCount = 0;
        sCount = 0;

        for (int i = 0; i <= range; i++) {
            if ((Integer)(history.get(i)) == 0)
                rCount++;
            else if ((Integer)(history.get(i)) == 1)
                pCount++;
            else if ((Integer)(history.get(i)) == 2)
                sCount++;
        }

    }
}
