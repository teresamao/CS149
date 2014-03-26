/**
 * Created by maopeiyi on 3/23/14.
 */
public class Process implements Cloneable {
    private String name;
    private int size;
    private int time;
    private int[] durations = {5, 11, 17, 31};

    public Process (int name) {
        this.size = generateSize();
        this.time = generateDuration();
        this.name = generateName(name);
    }

    public Process(String name, int size, int time) {
    	this.name = name;
    	this.size = size;
    	this.time = time;
    }

    private String generateName(int num) {
        String name = "";
        for (int i = 0; i < this.size; i++) {
            name += ((char) ((num % 26) + 65));
        }
        return name;
    }

    private int generateDuration() {
        return (int) (Math.random() * 5 + 1);
    }

    private int generateSize() {
        return durations[(int) (Math.random() * 4)];
    }

    public Process clone() {
        return new Process(name, size, time);
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
