//package cs149hw2;

import java.util.ArrayList;
import java.util.Collections;

public class ProcessScheduler {
	public static void main (String[] args){

        ArrayList<Process> plist = ProcessManager.generateProcesses(45);
        ProcessManager.printProcessList(plist);

//        nonpreemptive(plist, "FCFS");
//        nonpreemptive(plist, "SJF");
//        nonpreemptive(plist, "HPF");

        nonpreemptive(plist, "HPF");
        fcfsTimeChart(plist);

        plist = ProcessManager.generateProcesses(45);
        SRT(plist);

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
			int startTime = time;
			double runTime = time + current.getRunTime();
			if(startTime < 100){
				while(time < runTime && time < 110)
				{
					timeChart[time] = current.getName();
					time++;
				}	
			}
		}
		System.out.println("Time Chart:");
		for(int i = 0; i < timeChart.length; i++)
			System.out.print(timeChart[i] + " ");
		System.out.print("\n");
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
            totalResponseTime += startTime - p.getArrivalTime();
            totalTurnaroundTime += finishTime - p.getArrivalTime();
//            System.out.println(p.getName() + " " + p.getArrivalTime() + " " + p.getRunTime());
//            System.out.println(startTime + " " + finishTime);
//            System.out.println(totalWaitTime + " " + totalResponseTime + " " + totalTurnaroundTime + " " + throughput + "\n");
            throughput++;
        }
        System.out.println(type);
        System.out.println("Throughput : " + throughput);
        System.out.println("Average turnaround = " + (totalTurnaroundTime / throughput));
        System.out.println("Average waiting    = " + (totalWaitTime / throughput));
        System.out.println("Average response   = " + (totalResponseTime / throughput));
    }

    public static void roundRobin(ArrayList<Process> list) {

        Collections.sort(list, ProcessComparators.arrivalTimeComparator);

    }

    public static void SRT(ArrayList<Process> list)
    {
        double time = 0;
        double startTime = 0;
        double finishTime = 0;
        int throughput = 0;
        double totalWaitTime = 0;
        double totalTurnaroundTime = 0;
        double totalResponseTime = 0;
        
        Collections.sort(list, ProcessComparators.runtimeComparator);
        System.out.println();
        ProcessManager.printProcessList(list);
        
        
        while(finishTime < 100) {
            
        Collections.sort(list, ProcessComparators.runtimeComparator);
            
            //Choose the appropriate runtime.
            Process current = list.get(0);
            for (int i = 0; i < list.size() - 1; i++) {
                if (current.getArrivalTime() > finishTime) {
                    current = list.get(i + 1);
                } else if (current.getRunTime() <= 0) {
                    current = list.get(i + 1);
                } else {
                    i = list.size();
                }
            }
            
            //if all processes completed, finishtime=99. Reduce runtime by 1 since 1 quanta is passing.
            if(current.getRunTime() == 0) {
                finishTime = 99;
            } else {
                current.setRunTime(current.getRunTime()-1);
            }
            //if process runtime is less than 0, it has completed. increment throughput.
            if(current.getRunTime() <= 0){
                totalTurnaroundTime += finishTime - current.getArrivalTime();
                throughput++;
            }
            finishTime++;
        }
        
        System.out.println("Average Turnaround = " + (totalTurnaroundTime / throughput));
//          Process p = list.remove(throughput);
//            if (finishTime < p.getArrivalTime())
//                startTime = Math.ceil(p.getArrivalTime());
//            else
//                startTime = Math.ceil(finishTime);
//            if(p.getRunTime() < remainingRunTime)
//            {
//              time++;
//              remainingRunTime = p.getRunTime() - 1;
//              p.setRunTime(remainingRunTime);
//            } 
//            else
                
    }
}

