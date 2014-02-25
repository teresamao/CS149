//package cs149hw2;

import java.util.ArrayList;
import java.util.Collections;

public class ProcessScheduler {
	public static void main (String[] args){

        ArrayList<Process> plist = ProcessManager.generateProcesses(30);


//        nonpreemptive(plist, "FCFS");
//        nonpreemptive(plist, "SJF");
//        nonpreemptive(plist, "HPF");

        nonpreemptive(plist, "HPF");
        fcfsTimeChart(plist);

	}
	
	public static void fcfsTimeChart(ArrayList<Process> p)
	{
		int[] timeChart = new int[110];
		int time = 0;
		while(!p.isEmpty())
		{
			Process current = p.remove(0);
			while(current.getArrivalTime() > time)
			{
				timeChart[time] = 0;
				time++;
			}
			double tempTime = time + current.getRunTime();
			while(time < tempTime && time < 110)
			{
				timeChart[time] = current.getName();
				time++;
			}	
		}
		for(int i = 0; i < timeChart.length; i++)
			System.out.print(timeChart[i]);
	}

    public static void nonpreemptive(ArrayList<Process> list, String type) {
        double startTime = 0;
        double finishTime = 0;
        int throughput = 0;
        double totalWaitTime = 0;
        double totalTurnaroundTime = 0;
        double totalResponseTime = 0;

        // sorts process list according to scheduling type
        if (type.equals("FCFS"))
            Collections.sort(list, ProcessComparators.arrivalTimeComparator);
        else if (type.equals("SJF"))
            Collections.sort(list, ProcessComparators.runtimeComparator);
        else if (type.equals("HPF"))
            Collections.sort(list, ProcessComparators.priorityComparator);
        else
            return;

        ProcessManager.printProcessList(list);

        // starts processing
        while (finishTime < 100) {
            Process p = list.get(throughput);
            if (finishTime < p.getArrivalTime())
                startTime = Math.ceil(p.getArrivalTime());
            else
                startTime = Math.ceil(finishTime);
            finishTime = startTime + p.getRunTime();
            totalWaitTime += startTime - p.getArrivalTime();
            totalResponseTime = totalWaitTime;
            totalTurnaroundTime += finishTime - p.getArrivalTime();
//            System.out.println(p.getName() + " " + p.getArrivalTime() + " " + p.getRunTime());
//            System.out.println(startTime + " " + finishTime);
//            System.out.println(totalWaitTime + " " + totalResponseTime + " " + totalTurnaroundTime + " " + throughput + "\n");
            throughput++;
        }
        System.out.println(type);
        System.out.println("Average turnaround = " + (totalTurnaroundTime / throughput));
        System.out.println("Average waiting    = " + (totalWaitTime / throughput));
        System.out.println("Average response   = " + (totalResponseTime / throughput));
    }

    public static void roundRobin(ArrayList<Process> list) {

        Collections.sort(list, ProcessComparators.arrivalTimeComparator);

    }
}

