//package cs149hw2;

import java.util.Random;

public class Process {
	
	private double arrivalTime;
	private double runTime;
	private int priority;
	private String name; /** use int instead of char */
	
	public Process(String name)
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

    public String getName() {
        return name;
    }

    public void setRunTime(double t) {
        runTime = t;
    }

//	public void setSeed(int i){
//		seed = seed + i;
//	}
}