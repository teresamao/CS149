import java.util.ArrayList;

/**
 * Created by maopeiyi on 3/26/14.
 */
public class MFUPagingManager extends PagingManager {
    int[] useCount;
    int hitCount;

    public MFUPagingManager() {
        useCount = new int[NUM_OF_FRAME];
        for (int i = 0; i < NUM_OF_FRAME; i++)
            useCount[i] = 0;
        hitCount = 0;
        frames = new ArrayList<PageFrame>();
    }

    public int map (int[] references) {

        // add first reference to the page frame
        PageFrame firstFrame = new PageFrame();
        firstFrame.getFrame()[0] = references[0];
        firstFrame.setIndex(0);
        frames.add(firstFrame);
        useCount[0]++;

        PageFrame lastFrame = firstFrame;

        for (int i = 1; i < references.length; i++) {
            PageFrame currentFrame = lastFrame.clone();

            if (currentFrame.searchReference(references[i]) == -1) { // reference not found
                int index = mostFrequentUsed();
                currentFrame.getFrame()[index] = references[i];
                currentFrame.setIndex(index);
                useCount[index]++;
                lastFrame = currentFrame;
            } else {
                useCount[currentFrame.searchReference(references[i])]++;
                hitCount++;
                for (int j = 0; j < NUM_OF_FRAME; j++)
                    currentFrame.getFrame()[j] = -1;
            }
            frames.add(currentFrame);
        }
        super.print(references, hitCount);
        return hitCount;
    }

    private int mostFrequentUsed() {
        int mfu_index = 0;
        for (int i = 1; i < NUM_OF_FRAME; i++) {
            if (useCount[i] == 0)
                return i;
            if (useCount[i] > useCount[mfu_index])
                mfu_index = i;
        }
        return mfu_index;
    }
}
