//package cs149hw2;

import java.util.ArrayList;
import java.util.Collections;

public class ProcessScheduler {
	public static void main (String[] args){

        ArrayList<Process> plist;

        plist = ProcessManager.generateProcesses(45);
        FCFS(plist);
        plist = ProcessManager.generateProcesses(45);
        SJF(plist);
        plist = ProcessManager.generateProcesses(45);
        nonpreemptiveHPF(plist);
        plist = ProcessManager.generateProcesses(45);
        RR(plist);
        plist = ProcessManager.generateProcesses(45);
        SRF(plist);
        for (int i = 0; i < 5; i++) {
            plist = ProcessManager.generateProcesses(45);
            preemptiveHPF(plist);
        }

	}

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

                // next process arrives after time 99 - stops
                if (p.getArrivalTime() >= 100) {
                    for (int i = 0; i < (100 - finishTime); i++)
                        output += " idle";
                    break;
                }

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
        System.out.println("Average response   = " + averageWaitTime + "\n");

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
//        ProcessManager.printProcessList(list);

        while (finishTime < 100  && startTime < 100) {

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
        System.out.println("Average response   = " + averageWaitTime + "\n");

        return new Data(averageTurnaroundTime, averageWaitTime, averageResponseTime, throughput);
    }

    public static Data nonpreemptiveHPF(ArrayList<Process> list) {

        int throughput = 0;
        double startTime = 0;
        double finishTime = 0;
        double totalWaitTime = 0;
        double totalTurnaroundTime = 0;
        double totalResponseTime = 0;
        double averageTurnaroundTime;
        double averageWaitTime;
        double averageResponseTime;
        String output = "HPF ";

        Collections.sort(list, ProcessComparators.arrivalTimeComparator);

        while (finishTime < 100  && startTime < 100) {

            Process p = null;
            int i = 0;
            int highestPriorityJob = list.get(0).getPriority();
            boolean hasJob = false;

            while (i < list.size() && list.get(i).getArrivalTime() <= startTime) {
                if (list.get(highestPriorityJob).getPriority() > list.get(i).getPriority())
                    highestPriorityJob = i;
                hasJob = true;
                i++;
            }

            if (hasJob) {   // shortest job found

                // retrieve current process from the queue
                p = list.get(highestPriorityJob);
                list.remove(highestPriorityJob);

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
        System.out.println("Average response   = " + averageWaitTime + "\n");

        return new Data(averageTurnaroundTime, averageWaitTime, averageResponseTime, throughput);
    }

    public static Data RR(ArrayList<Process> list) {

        int throughput = 0;
        double totalWaitTime = 0;
        double totalTurnaroundTime = 0;
        double totalResponseTime = 0;
        double averageTurnaroundTime;
        double averageWaitTime;
        double averageResponseTime;
        String output = "RR  ";
        int currentTime = 0;    // current time slot
        int currentJob = 0;
        boolean done = false;

        Collections.sort(list, ProcessComparators.arrivalTimeComparator);
//        ProcessManager.printProcessList(list);

        while (!done) {

            // no job in the queue currently
            if (currentTime < list.get(currentJob).getArrivalTime()) {
                if (currentJob == 0)
                    output += " idle";
                else
                    currentJob = 0;
            }
            else { // process current job for time 1

                Process p = list.get(currentJob);

                // current hasn't been processed yet
                if (p.getStartTime() == -1) {
                    p.setStartTime(currentTime);
                }

                p.setRunTime(p.getRunTime() - 1);
                output += " P" + p.getName();

                // current job has finished
                if (p.getRunTime() <= 0) {

                    list.remove(currentJob);

                    // calculation
                    totalResponseTime += currentTime - p.getStartTime() + 1;
                    totalTurnaroundTime += currentTime - p.getArrivalTime() + 1;
                    totalWaitTime += currentTime - p.getArrivalTime() - p.getOriginalRunTime() + 1;
                    throughput++;
//                    System.out.println("here\n" + currentTime + " " + totalWaitTime + " " + totalResponseTime + " " + totalTurnaroundTime + " " + throughput);

                    if (list.isEmpty())
                        done = true;
                }

                currentJob++;

            }
            currentTime++;
            if (currentTime == 100) {
                for (int j = list.size() - 1; list.get(j).getStartTime() == -1; j--)
                    list.remove(j);

            }
            if (currentJob >= list.size())
                currentJob = 0;
        }

        averageTurnaroundTime = totalTurnaroundTime / throughput;
        averageWaitTime = totalWaitTime / throughput;
        averageResponseTime = totalResponseTime / throughput;

        System.out.println(output);
        System.out.println("Throughput : " + throughput);
        System.out.println("Average turnaround = " + averageTurnaroundTime);
        System.out.println("Average waiting    = " + averageWaitTime);
        System.out.println("Average response   = " + averageResponseTime + "\n");

        return new Data(averageTurnaroundTime, averageWaitTime, averageResponseTime, throughput);
    }

    public static Data SRF(ArrayList<Process> list) {
        int throughput = 0;
        double totalWaitTime = 0;
        double totalTurnaroundTime = 0;
        double totalResponseTime = 0;
        double averageTurnaroundTime;
        double averageWaitTime;
        double averageResponseTime;
        String output = "SRF ";
        int currentTime = 0;    // current time slot
        int currentJob = 0;
        boolean done = false;

        Collections.sort(list, ProcessComparators.arrivalTimeComparator);

        while (!done) {

            int i = 0;

            boolean hasJob = false;

            while (i < list.size() && list.get(i).getArrivalTime() <= currentTime) {
                if (list.get(i).getRunTime() < list.get(currentJob).getRunTime())
                    currentJob = i;
                hasJob = true;
                i++;
            }

            if (hasJob) {
                Process p = list.get(currentJob);

                if (p.getStartTime() == -1) {
                    p.setStartTime(currentTime);
                }

                p.setRunTime(p.getRunTime() - 1);
                output += " P" + p.getName();

                if (p.getRunTime() <= 0) {
                    list.remove(currentJob);

                    totalWaitTime += currentTime - p.getArrivalTime() - p.getOriginalRunTime() + 1;
                    totalResponseTime += currentTime - p.getStartTime() + 1;
                    totalTurnaroundTime += currentTime - p.getArrivalTime() + 1;
                    throughput++;

                    if (list.isEmpty())
                        done = true;
                }

            }
            else {
                output += " idle";
            }

            currentJob = 0;
            currentTime++;
            if (currentTime == 100) {
                for (int j = list.size() - 1; list.get(j).getStartTime() == -1; j--)
                    list.remove(j);
            }
        }

        averageTurnaroundTime = totalTurnaroundTime / throughput;
        averageWaitTime = totalWaitTime / throughput;
        averageResponseTime = totalResponseTime / throughput;

        System.out.println(output);
        System.out.println("Throughput : " + throughput);
        System.out.println("Average turnaround = " + averageTurnaroundTime);
        System.out.println("Average waiting    = " + averageWaitTime);
        System.out.println("Average response   = " + averageResponseTime + "\n");

        return new Data(averageTurnaroundTime, averageWaitTime, averageResponseTime, throughput);
    }

    public static Data preemptiveHPF(ArrayList<Process> list) {

        int throughput = 0;
        double totalWaitTime = 0;
        double totalTurnaroundTime = 0;
        double totalResponseTime = 0;
        double averageTurnaroundTime;
        double averageWaitTime;
        double averageResponseTime;
        String output = "HPF ";
        int currentTime = 0;
        int startAt;
        boolean done = false;

        // list sorted by both arrival time and priority
        Collections.sort(list, ProcessComparators.arrivalTimeComparatorForSamePriority);

        while (!done) {

            // searches for arrived job with highest priority
            startAt = -1;
            for (int i = 0; i < list.size() && startAt == -1; i++) {
                if (list.get(i).getArrivalTime() <= currentTime)
                    startAt = i;
            }

            if (startAt == -1) {    // no job found
                output += " idle";
                currentTime++;
            }
            else {  // job found, run RR on jobs with the same priority

                int priority = list.get(startAt).getPriority();
                int currentJob = startAt;
                boolean RRfinished = false;

                while (!RRfinished) {

                    Process p = list.get(currentJob);
                    p.setRunTime(p.getRunTime() - 1);
                    output += " P" + p.getName();

                    // current job has not yet started
                    if (list.get(currentJob).getStartTime() == -1) {
                        list.get(currentJob).setStartTime(currentTime);
                    }

                    // current job has finished
                    if (list.get(currentJob).getRunTime() <= 0) {

                        if (startAt >= list.size() - 1 ||
                                list.get(startAt).getPriority() != priority ||
                                list.get(startAt).getArrivalTime() > currentTime) {
                            RRfinished = true;
                        }

                        list.remove(currentJob);

                        totalWaitTime += currentTime - p.getArrivalTime() - p.getOriginalRunTime() + 1;
                        totalResponseTime += currentTime - p.getStartTime() + 1;
                        totalTurnaroundTime += currentTime - p.getArrivalTime() + 1;
                        throughput++;
                    }

                    // search for next process
                    if (!RRfinished) {
                        if (currentJob >= list.size() - 1) {
                            currentJob = startAt;
                        }
                        else {
                            if (list.get(currentJob + 1).getPriority() == priority &&
                                    list.get(currentJob + 1).getArrivalTime() <= currentTime) {
                                currentJob++;
                            }
                            else {
                                currentJob = startAt;
                            }
                        }
                    }

                    currentTime++;
                    if (currentTime == 100) {
                        done = true;
                        Process current_p = list.get(currentJob);

                        // delete not yet started jobs
                        for (int i = list.size() - 1; i >= 0; i--) {
                            if (list.get(i).getStartTime() == -1 && list.get(i) != current_p)
                                list.remove(i);
                        }

                        // update startAt and currentJob
                        startAt = 0;
                        currentJob = list.indexOf(current_p);
                    }
                }
            }
        }

        averageTurnaroundTime = totalTurnaroundTime / throughput;
        averageWaitTime = totalWaitTime / throughput;
        averageResponseTime = totalResponseTime / throughput;

        System.out.println(output);
        System.out.println("Throughput : " + throughput);
        System.out.println("Average turnaround = " + averageTurnaroundTime);
        System.out.println("Average waiting    = " + averageWaitTime);
        System.out.println("Average response   = " + averageResponseTime + "\n");

        return new Data(averageTurnaroundTime, averageWaitTime, averageResponseTime, throughput);
    }
}

