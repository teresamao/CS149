//package cs149hw2;

import java.util.Random;

public class Process {
	
	private double arrivalTime;
	private double runTime;
	private int priority;
	private String name;
    private int startTime;
    private int finishTime;
	
	public Process(String name)
	{
		Random rnd = new Random();
		arrivalTime = Math.ceil(rnd.nextFloat() * 99);
		runTime = Math.ceil(rnd.nextFloat() * 100);
		runTime = runTime/10;
		priority = rnd.nextInt(4) + 1;
        this.name = name;
        startTime = -1;
        finishTime = -1;
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

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

//	public void setSeed(int i){
//		seed = seed + i;
//	}
}