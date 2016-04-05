package fangorntheent.honey.rpsdroid;

import java.util.ArrayList;

/**
 * Created by Gus Lipkin on 4/2/2016
 */
public interface AlgInterface {

	public int getAlg(AlgGeneral algGeneral);
	public int getAlg(PlayerGeneral playerGeneral, AlgGeneral algGeneral);
	
	public int getTotal();
	public void setTotal();
	public void setTotal(int total);
	public int getWeight();
	public void setWeight();
	public void setWeight(int weight);

	public ArrayList<Integer> getHistory();
    public ArrayList<Integer> getWinHistory();

}
