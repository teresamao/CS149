import java.util.ArrayList;

/**
 * Created by maopeiyi on 3/23/14.
 */
public class SwappingTest {
    public static void main (String [] args) {

        ArrayList<Process> pl1 = new ArrayList<Process>();
        ArrayList<Process> pl2 = new ArrayList<Process>();
        ArrayList<Process> pl3 = new ArrayList<Process>();
        FirstFitMemoryManager ffManager = new FirstFitMemoryManager();
        NextFitMemoryManager nfManager = new NextFitMemoryManager();
        BestFitMemoryManager bfManager = new BestFitMemoryManager();

        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 150; i++) {
                Process p = new Process(i);
                pl1.add(p.clone());
                pl2.add(p.clone());
                pl3.add(p.clone());
            }
            ffManager.proceed(pl1, 100, 60);
            nfManager.proceed(pl2, 100, 60);
            bfManager.proceed(pl3, 100, 60);
        }

    }
}
