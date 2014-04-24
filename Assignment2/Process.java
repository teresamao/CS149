import java.util.Random;

public class Process {
	
	private double arrivalTime;
	private double runTime;
    private double originalRunTime;
	private int priority;
	private String name;
    private int startTime;

	public Process(String name)
	{
		Random rnd = new Random();
		arrivalTime = Math.ceil(rnd.nextFloat() * 99);
		runTime = Math.ceil(rnd.nextFloat() * 100);
		runTime = runTime/10;
		priority = rnd.nextInt(4) + 1;
        this.name = name;
        startTime = -1;
        originalRunTime = runTime;
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

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public double getOriginalRunTime() {
        return originalRunTime;
    }

//	public void setSeed(int i){
//		seed = seed + i;
//	}
}