import java.util.ArrayList;

/**
 * Created by maopeiyi on 2/24/14.
 */
public class ProcessManager {

    public ProcessManager() {}

    public ArrayList<Process> generateProcesses(int count) {
        ArrayList<Process> list = new ArrayList<Process>();
        for (int i = 0; i < count; i++) {
            list.add(new Process(i));
        }
        return list;
    }
}
