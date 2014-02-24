//package cs149hw2;

import java.util.Random;

public class Process {
	
	public float arrivalTime;
	public float runTime;
	public int priority;
	public int name; /** use int instead of char */
	
	public Process(int name)
	{
		Random rnd = new Random();
		arrivalTime = Math.round(rnd.nextFloat() * 99);
		runTime = Math.round(rnd.nextFloat() * 100);
		runTime = runTime/10;
		priority = rnd.nextInt(4) + 1;
        this.name = name;
	}

//    public Process (int name) {
//        this.name = name;
//    }

	public void setName(char newName){
		name = newName;
	}
	
	
//	public void setSeed(int i){
//		seed = seed + i;
//	}
}