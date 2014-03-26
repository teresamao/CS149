import java.util.ArrayList;

/**
 * Created by maopeiyi on 3/26/14.
 */
public class RPPagingManager extends PagingManager {
    int hitCount;

    public RPPagingManager() {
        hitCount = 0;
        frames = new ArrayList<PageFrame>();
    }

    public int map (int[] references) {

        // add first reference to the page frame
        PageFrame firstFrame = new PageFrame();
        firstFrame.getFrame()[0] = references[0];
        firstFrame.setIndex(0);
        frames.add(firstFrame);

        PageFrame lastFrame = firstFrame;

        for (int i = 1; i < references.length; i++) {
            PageFrame currentFrame = lastFrame.clone();

            if (currentFrame.searchReference(references[i]) == -1) { // reference not found
                int index = (int) (Math.random() * 4);
                currentFrame.getFrame()[index] = references[i];
                currentFrame.setIndex(index);
                lastFrame = currentFrame;
            } else {
                hitCount++;
                for (int j = 0; j < NUM_OF_FRAME; j++)
                    currentFrame.getFrame()[j] = -1;
            }
            frames.add(currentFrame);
        }
        super.print(references, hitCount);
        return hitCount;
    }
}
