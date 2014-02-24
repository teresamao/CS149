//package cs149hw2;

import java.util.ArrayList;
import java.util.Collections;

public class ProcessScheduler {
	public static void main (String[] args){

        ArrayList<Process> plist = ProcessManager.generateProcesses(10);
        ProcessManager.printProcessList(plist);
        System.out.println(" ");
        Collections.sort(plist, ProcessComparators.arrivalTimeComparator);
        ProcessManager.printProcessList(plist);
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
			while(time < tempTime)
			{
				timeChart[time] = current.getName();
				time++;
			}	
		}
		for(int i = 0; i < timeChart.length; i++)
			System.out.print(timeChart[i]);
	}
	
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