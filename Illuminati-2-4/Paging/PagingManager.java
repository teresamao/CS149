import java.util.ArrayList;

/**
 * Created by maopeiyi on 3/26/14.
 */
public class PagingManager {

    public static final int NUM_OF_FRAME = 4;
    ArrayList<PageFrame> frames;


    public void print (int[] references, int hitCounts) {

        for (int i = 0; i < references.length; i++) {
            System.out.printf(" %d ", references[i]);
            for (int j = 0; j < 4; j++) {
                if (frames.get(i).getFrame()[j] == -1)
                    System.out.print("   ");
                else
                    if (j == frames.get(i).getIndex())
                        System.out.printf("(%d)", frames.get(i).getFrame()[j]);
                    else
                        System.out.printf(" %d ", frames.get(i).getFrame()[j]);
            }
            System.out.println();
        }
        System.out.println("\nAverage hit ratio: " + ((double) hitCounts / references.length * 100) + "%\n");
    }

}
