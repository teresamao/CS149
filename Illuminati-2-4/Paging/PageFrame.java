/**
 * Created by maopeiyi on 3/26/14.
 */
public class PageFrame implements Cloneable {

    public static final int NUM_OF_FRAME = 4;
    private int[] frame;
    private int index; // index of frame which was updated

    public PageFrame() {
        frame = new int[NUM_OF_FRAME];
        for (int i = 0; i < NUM_OF_FRAME; i++) {
            frame[i] = -1;
        }
        index = -1;
    }

    public PageFrame(int[] frame) {
        this.frame = new int[NUM_OF_FRAME];
        for (int i = 0; i < NUM_OF_FRAME; i++) {
            this.frame[i] = frame[i];
        }
        this.index = -1;
    }

    public int searchReference(int reference) {
        for (int i = 0; i < NUM_OF_FRAME; i++) {
            if (frame[i] == reference)
                return i;
        }
        return -1;
    }

    public int[] getFrame() {
        return frame;
    }

    public void setFrame(int[] frame) {
        this.frame = frame;
    }

    public PageFrame clone() {
        return new PageFrame(this.frame);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
