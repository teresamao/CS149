import java.util.ArrayList;

/**
 * Created by maopeiyi on 3/26/14.
 */
public class LRUPagingManager extends PagingManager {

    private int hitCount;
    private int[] rank; // keeps tracks of when and which frame was referred
    private final int MOST_RECENT_REFERRED = 100;

    public LRUPagingManager() {
        frames = new ArrayList<PageFrame>();
        hitCount = 0;
        rank = new int[NUM_OF_FRAME];
        for (int i = 0; i < NUM_OF_FRAME; i++) {
            rank[i] = 0;
        }
    }

    public int map (int[] references) {

        // add first reference to the page frame
        PageFrame firstFrame = new PageFrame();
        firstFrame.getFrame()[0] = references[0];
        firstFrame.setIndex(0);
        frames.add(firstFrame);
        updateRank(0);

        PageFrame lastFrame = firstFrame;

        for (int i = 1; i < references.length; i++) {
            PageFrame currentFrame = lastFrame.clone();

            if (currentFrame.searchReference(references[i]) == -1) { // reference not found

                int index = searchLeastRecentReferredRank();
                updateRank(index);

                currentFrame.getFrame()[index] = references[i];
                currentFrame.setIndex(index);
                lastFrame = currentFrame;
            } else {
                updateRank(currentFrame.searchReference(references[i]));
                hitCount++;
                for (int j = 0; j < NUM_OF_FRAME; j++)
                    currentFrame.getFrame()[j] = -1;
            }
            frames.add(currentFrame);
        }
        super.print(references, hitCount);
        return hitCount;
    }

    private int searchLeastRecentReferredRank() {
        int r = 0;
        for (int i = 1; i < NUM_OF_FRAME; i++) {
            if (rank[i] < rank[r])
                r = i;
        }
        return r;
    }

    private void updateRank(int index) {
        rank[index] = MOST_RECENT_REFERRED;
        for (int i = 0; i < NUM_OF_FRAME; i++) {
            if (i != index)
                rank[i]--;
        }
    }
}
