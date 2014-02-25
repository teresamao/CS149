//package cs149hw2;

import java.util.ArrayList;
import java.util.Collections;

public class ProcessScheduler {
	public static void main (String[] args){

        ArrayList<Process> plist = ProcessManager.generateProcesses(50);
        ProcessManager.printProcessList(plist);
<<<<<<< HEAD
        nonpreemptive(plist, "FCFS");
        nonpreemptive(plist, "SJF");
        nonpreemptive(plist, "HPF");
=======
        System.out.println(" ");
        Collections.sort(plist, ProcessComparators.arrivalTimeComparator);
        ProcessManager.printProcessList(plist);


        FCFS(plist);
        fcfsTimeChart(plist);

	}
	
	public static Process[] sortByArrival(Process[] p){
		for(int i = 1; i < p.length; i++){
			Process temp = p[i];
			int j;
			for(j = i - 1; j >= 0 && temp.getArrivalTime() < p[j].getArrivalTime(); j--)
				p[j+1] = p[j];
			p[j+1] = temp;
		}
		return p;
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
>>>>>>> c2bc2ce5891b3941070abf95b231e66f50561f05
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
<<<<<<< HEAD

}
=======
	
}

//old code
//char name = 'a';
//Process[] processes = new Process[10];
//for(int i = 0; i < 10; i++){
//	processes[i] = new Process();
//	processes[i].setName(name);
//	name++;
//}
//
//processes = sortByArrival(processes);

//ArrayList<Process> sortedProcess = new ArrayList<Process>();
//for(int i = 0; i < 10; i++)
//{
//	sortedProcess.add(processes[i]);
//}

//int waitingTime = 0;
//int time = 0;
//
//for(int i = 0; i < 10; i++)
//{
//	waitingTime += waitingTime + processes[i].runTime;
//	time += processes[i].runTime;
//}
//
//for(int i = 0; i < 10; i++){
//System.out.println("Process Name: " + processes[i].name);
//System.out.println("Arrival Time: " + processes[i].arrivalTime);
//System.out.println("Run Time: " + processes[i].runTime);
//System.out.println("Priority: " + processes[i].priority);
//}
>>>>>>> c2bc2ce5891b3941070abf95b231e66f50561f05
