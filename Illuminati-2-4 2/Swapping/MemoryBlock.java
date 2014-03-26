/**
 * Created by maopeiyi on 3/23/14.
 */
public class MemoryBlock {
//    private boolean available;
    private int size;
    private Process currentProcess;

    public MemoryBlock(int size) {
//        this.available = true;
        this.size = size;
        this.currentProcess = null;
    }

    public boolean isAvailable() {
        return currentProcess == null;
    }

//    public void setAvailable(boolean available) {
//        this.available = available;
//    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Process getCurrentProcess() {
        return currentProcess;
    }

    public void setCurrentProcess(Process currentProcess) {
        this.currentProcess = currentProcess;
    }
}
