import java.util.ArrayList;

/**
 * Created by maopeiyi on 3/24/14.
 */
public class BestFitMemoryManager implements MemoryManager {

    MemoryAlloc memory;

    public BestFitMemoryManager() { memory = null; }

    @Override
    public void proceed(ArrayList<Process> processes, int memSize, int duration) {
        memory = new MemoryAlloc(memSize); // allocate new memory
        int totalProcessed = 0;

        for (int currentTime = 1; currentTime <= duration; currentTime++) {

            int index = memory.searchForSmallest(processes.get(0).getSize());

            while (index != -1) {
                Process p = processes.get(0);
                memory.swap(p, index);
                processes.remove(p);
                totalProcessed++;
                index = memory.searchForSmallest(processes.get(0).getSize());
                memory.printBitmap(currentTime);
            }

            if (memory.update(1))
                memory.printBitmap(currentTime);
        }
        System.out.println("\nBest Fit Total: " + totalProcessed + " processes\n");
    }
}
