//package cs149hw2;

import java.util.Random;

public class Process {
	
	private double arrivalTime;
	private double runTime;
	private int priority;
	private int name; /** use int instead of char */
	
	public Process(int name)
	{
		Random rnd = new Random();
		arrivalTime = Math.ceil(rnd.nextFloat() * 99);
		runTime = Math.ceil(rnd.nextFloat() * 100);
		runTime = runTime/10;
		priority = rnd.nextInt(4) + 1;
        this.name = name;
    }
//
//	public void setName(char newName){
//		name = newName;
//	}

    public double getArrivalTime() {
        return arrivalTime;
    }

    public double getRunTime() {
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