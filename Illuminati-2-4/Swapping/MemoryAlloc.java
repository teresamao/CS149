import java.util.ArrayList;

/**
 * Created by maopeiyi on 3/23/14.
 */
public class MemoryAlloc {

    private ArrayList<MemoryBlock> blocks = new ArrayList<MemoryBlock>();

    public MemoryAlloc(int size) {
        blocks.add(new MemoryBlock(size));
    }

    // updates memory partition after the given second
    public boolean update(int time) {

        boolean changes = false;

        for (int i = 0; i < blocks.size(); i++) {

            MemoryBlock currBlock = blocks.get(i);

            if (!currBlock.isAvailable()) {
                Process p = currBlock.getCurrentProcess();
                p.setTime(p.getTime() - time);

                if (p.getTime() == 0) {
                    currBlock.setCurrentProcess(null);
                }
            }

            // free memory and merge
            if (currBlock.isAvailable()) {
                if (i != 0) {
                    if (blocks.get(i - 1).isAvailable()) {
                        MemoryBlock prevBlock = blocks.get(i - 1);
                        prevBlock.setSize(prevBlock.getSize() + currBlock.getSize());
                        blocks.remove(currBlock);
                        i--;
                        changes = true;
                    }
                }
            }
        }
        return changes;
    }

    // searches for available block; returns the index if found, -1 if not found
    public int search(int startAt, int size) {

        for (int i = startAt; i < blocks.size(); i++) {
            if (blocks.get(i).isAvailable() && blocks.get(i).getSize() >= size)
                return i; // found at index i
        }
        return -1; // not found
    }

    public int searchForSmallest(int size) {

        int foundAt = -1;

        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i).isAvailable() && blocks.get(i).getSize() >= size) {
                if (foundAt == -1)
                    foundAt = i;
                else
                    if (blocks.get(i).getSize() < blocks.get(foundAt).getSize())
                        foundAt = i;
            }
        }
        return foundAt;
    }

    // swaps in a process
    public void swap(Process p, int index) {

        MemoryBlock currBlock = blocks.get(index);
        MemoryBlock newBlock = new MemoryBlock(currBlock.getSize() - p.getSize());
        blocks.add(index + 1, newBlock);
        currBlock.setSize(p.getSize());
        currBlock.setCurrentProcess(p);
    }

    public void printBitmap(int currentTime) {
        System.out.printf("%02d ", currentTime);
        for (MemoryBlock b : blocks) {
            if (!b.isAvailable()) {
                System.out.print(b.getCurrentProcess().getName());
            } else {
                for (int i = 0; i < b.getSize(); i++)
                    System.out.print(".");
            }
        }
        System.out.println();
    }
}
