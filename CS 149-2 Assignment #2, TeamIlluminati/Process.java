//package cs149hw2;

import java.util.Random;

public class Process {
	
	public float arrivalTime;
	public float runTime;
	public int priority;
	public char name;
	
	public Process()
	{
		Random rnd = new Random();
		arrivalTime = Math.round(rnd.nextFloat() * 99);
		runTime = Math.round(rnd.nextFloat() * 100);
		runTime = runTime/10;
		priority = rnd.nextInt(4) + 1;
	}

	public void setName(char newName){
		name = newName;
	}
	
	
//	public void setSeed(int i){
//		seed = seed + i;
//	}
}