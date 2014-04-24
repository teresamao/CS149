/**
 * Created by maopeiyi on 3/26/14.
 */
public class PagingTest {

    public static final int NUM_OF_PAGE = 10;

    public static void main (String [] args) {

        int[][] refArray = new int[5][100];
        for (int i = 0; i < 5; i++)
            refArray[i] = referencesGenerator(100);
        double averageRatio = 0;

        System.out.println("---------------------------- FIFO --------------------------\n");
        for (int i = 0; i < 5; i++) {
            FIFOPagingManager manager = new FIFOPagingManager();
            averageRatio += manager.map(refArray[i]);
        }
        System.out.println("FIFO average ratio after 5 runs: " + (averageRatio / 5) + "%\n");

        System.out.println("---------------------------- LRU ---------------------------\n");
        averageRatio = 0;
        for (int i = 0; i < 5; i++) {
            LRUPagingManager manager = new LRUPagingManager();
            averageRatio += manager.map(refArray[i]);
        }
        System.out.println("LRU average ratio after 5 runs: " + (averageRatio / 5) + "%\n");

        System.out.println("---------------------------- LFU ---------------------------\n");
        averageRatio = 0;
        for (int i = 0; i < 5; i++) {
            LFUPagingManager manager = new LFUPagingManager();
            averageRatio += manager.map(refArray[i]);
        }
        System.out.println("LFU average ratio after 5 runs: " + (averageRatio / 5) + "%\n");

        System.out.println("---------------------------- MFU ---------------------------\n");
        averageRatio = 0;
        for (int i = 0; i < 5; i++) {
            MFUPagingManager manager = new MFUPagingManager();
            averageRatio += manager.map(refArray[i]);
        }
        System.out.println("MFU average ratio after 5 runs: " + (averageRatio / 5) + "%\n");

        System.out.println("------------------------ Random Pick ------------------------\n");
        averageRatio = 0;
        for (int i = 0; i < 5; i++) {
            RPPagingManager manager = new RPPagingManager();
            averageRatio += manager.map(refArray[i]);
        }
        System.out.println("Random pick average ratio after 5 runs: " + (averageRatio / 5) + "%\n");
    }

    public static int[] referencesGenerator (int numOfReferences) {

        int[] references = new int[numOfReferences];

        for (int i = 0; i < numOfReferences; i++) {
            int r = (int) (Math.random() * 10);
            if (r < 7)
                r += (int) ((Math.random() * 3) - 1);
            else
                r += ((int) ((Math.random() * 7) + 2)) * Math.pow(-1, (int) (Math.random() * 2));
            if (r == -1)
                r = 8;
            references[i] = r % NUM_OF_PAGE;
        }

        return references;
    }
}
