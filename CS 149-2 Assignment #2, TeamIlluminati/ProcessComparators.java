//package cs149hw2;

import java.util.Comparator;

/**
 * Created by maopeiyi on 2/24/14.
 */
public class ProcessComparators {

    public static Comparator<Process> arrivalTimeComparator = new Comparator<Process>() {
        @Override
        public int compare(Process p1, Process p2) {
            if (p1.getArrivalTime() - p2.getArrivalTime() > 0)
                return 1;
            else if (p1.getArrivalTime() - p2.getArrivalTime() == 0)
                return 0;
            else
                return -1;
        }
    };

    public static Comparator<Process> priorityComparator = new Comparator<Process>() {
        @Override
        public int compare(Process p1, Process p2) {
            return p1.getPriority() - p2.getPriority();
        }
    };
}
