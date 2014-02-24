//package cs149hw2;

import java.util.Random;

public class Process {
	
	private float arrivalTime;
	private float runTime;
	private int priority;
	private int name; /** use int instead of char */
	
	public Process(int name)
	{
		Random rnd = new Random();
		arrivalTime = Math.round(rnd.nextFloat() * 99);
		runTime = Math.round(rnd.nextFloat() * 100);
		runTime = runTime/10;
		priority = rnd.nextInt(4) + 1;
        this.name = name;
    }
//
//	public void setName(char newName){
//		name = newName;
//	}

    public float getArrivalTime() {
        return arrivalTime;
    }

    public float getRunTime() {
        return runTime;
    }

    public int getPriority() {
        return priority;
    }

    public int getName() {
        return name;
    }


//	public void setSeed(int i){
//		seed = seed + i;
//	}
}