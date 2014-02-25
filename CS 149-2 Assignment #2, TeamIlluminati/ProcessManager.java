//package cs149hw2;

import java.util.ArrayList;

/**
 * Created by maopeiyi on 2/24/14.
 */
public class ProcessManager {

    public ProcessManager() {}

    public static ArrayList<Process> generateProcesses(int count) {
        ArrayList<Process> list = new ArrayList<Process>();
        for (int i = 1; i < count + 1; i++) {
            if (i < 10)
                list.add(new Process("0" + i));
            list.add(new Process(new Integer(i).toString()));
        }
        return list;
    }

    public static void printProcessList(ArrayList<Process> list) {
        for (Process p : list)
            System.out.println(p.getName() + " " + p.getArrivalTime() + " " + p.getPriority() + " " + p.getRunTime());
        System.out.println();
    }



}
