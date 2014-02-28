//package cs149hw2;

import java.util.ArrayList;
import java.util.Collections;

public class ProcessScheduler {
	public static void main (String[] args){

        ArrayList<Process> plist = ProcessManager.generateProcesses(45);
//        ProcessManager.printProcessList(plist);

        FCFS(plist);
        SJF(plist);
//        fcfsTimeChart(plist);

//        plist = ProcessManager.generateProcesses(45);
//        SRT(plist);

	}
	
//	public static void fcfsTimeChart(ArrayList<Process> p)
//	{
//		int[] timeChart = new int[110];
//		int time = 0;
//		while(!p.isEmpty())
//		{
//			Process current = p.remove(0);
//			while(current.getArrivalTime() > time)
//			{
//				timeChart[time] = 0;
//				time++;
//			}
//			int startTime = time;
//			double runTime = time + current.getRunTime();
//			if(startTime < 100){
//				while(time < runTime && time < 110)
//				{
//					timeChart[time] = current.getName();
//					time++;
//				}
//			}
//		}
//		System.out.println("Time Chart:");
//		for(int i = 0; i < timeChart.length; i++)
//			System.out.print(timeChart[i] + " ");
//		System.out.print("\n");
//	}

    public static Data FCFS(ArrayList<Process> list) {

        double startTime = 0;
        double finishTime = 0;
        double totalWaitTime = 0;
        double totalTurnaroundTime = 0;
        double totalResponseTime = 0;
        double averageTurnaroundTime, averageWaitTime, averageResponseTime;
        int throughput = 0;
        String output = "FCFS";

        // sorts process list according to scheduling type
        Collections.sort(list, ProcessComparators.arrivalTimeComparator);

        // starts processing
        while (finishTime < 100) {
            Process p = list.get(throughput);

            if (finishTime < p.getArrivalTime()) {
                startTime = Math.ceil(p.getArrivalTime());

                // prints idle time slots
                int d = (int) Math.ceil(p.getArrivalTime() - finishTime);
                for (int i = 0; i < d; i++)
                    output += " idle";
            }
            else
                startTime = Math.ceil(finishTime);

            finishTime = startTime + p.getRunTime();

            // prints current process
            for (int i = 0; i < (int) Math.ceil(p.getRunTime()); i++)
                output += (" P" + p.getName());

            totalWaitTime += startTime - p.getArrivalTime();
            totalResponseTime += startTime - p.getArrivalTime();
            totalTurnaroundTime += finishTime - p.getArrivalTime();
            throughput++;
        }
        averageTurnaroundTime = totalTurnaroundTime / throughput;
        averageWaitTime = totalWaitTime / throughput;
        averageResponseTime = totalResponseTime / throughput;

        System.out.println(output);
        System.out.println("Throughput : " + throughput);
        System.out.println("Average turnaround = " + averageTurnaroundTime);
        System.out.println("Average waiting    = " + averageWaitTime);
        System.out.println("Average response   = " + averageWaitTime);

        return new Data(averageTurnaroundTime, averageWaitTime, averageResponseTime, throughput);
    }

    public static Data SJF(ArrayList<Process> list) {

        int throughput = 0;
        double startTime = 0;
        double finishTime = 0;
        double totalWaitTime = 0;
        double totalTurnaroundTime = 0;
        double totalResponseTime = 0;
        double averageTurnaroundTime;
        double averageWaitTime;
        double averageResponseTime;
        String output = "SJF ";

        Collections.sort(list, ProcessComparators.arrivalTimeComparator);

        ProcessManager.printProcessList(list);

        while (finishTime < 100) {

            Process p = null;
            int i = 0;
            int shortestJob = 0;
            boolean hasJob = false;

            while (i < list.size() && list.get(i).getArrivalTime() <= startTime) {
                if (list.get(shortestJob).getRunTime() > list.get(i).getRunTime())
                    shortestJob = i;
                hasJob = true;
                i++;
            }

            if (hasJob) {   // shortest job found

                // retrieve current process from the queue
                p = list.get(shortestJob);
                list.remove(shortestJob);

                finishTime = startTime + p.getRunTime();

                // print current process
                for (int j = 0; j < (int) Math.ceil(p.getRunTime()); j++)
                    output += (" P" + p.getName());

                // calculation
                totalWaitTime += startTime - p.getArrivalTime();
                totalResponseTime += startTime - p.getArrivalTime();
                totalTurnaroundTime += finishTime - p.getArrivalTime();
                throughput++;

                startTime = finishTime; // update startTime for next iteration

            }
            else {  // no job in the queue currently
                startTime += 1;
                output += " idle";
            }
        }

        averageTurnaroundTime = totalTurnaroundTime / throughput;
        averageWaitTime = totalWaitTime / throughput;
        averageResponseTime = totalResponseTime / throughput;

        System.out.println(output);
        System.out.println("Throughput : " + throughput);
        System.out.println("Average turnaround = " + averageTurnaroundTime);
        System.out.println("Average waiting    = " + averageWaitTime);
        System.out.println("Average response   = " + averageWaitTime);

        return new Data(averageTurnaroundTime, averageWaitTime, averageResponseTime, throughput);
    }

    public static void roundRobin(ArrayList<Process> list) {

        // sorts by arrival time
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

