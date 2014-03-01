/**
 * Created by maopeiyi on 2/24/14.
 */
public class Data {
    private double averageTurnaround;
    private double averageWaiting;
    private double averageResponse;
    private int throughput;

    public Data () {}

    public Data(double averageTurnaround, double averageWaiting, double averageResponse, int throughput) {
        this.averageTurnaround = averageTurnaround;
        this.averageWaiting = averageWaiting;
        this.averageResponse = averageResponse;
        this.throughput = throughput;
    }

    public double getAverageTurnaround() {
        return averageTurnaround;
    }

    public double getAverageWaiting() {
        return averageWaiting;
    }

    public double getAverageResponse() {
        return averageResponse;
    }

    public int getThroughput() {
        return throughput;
    }

    public void setAverageTurnaround(double averageTurnaround) {
        this.averageTurnaround = averageTurnaround;
    }

    public void setAverageWaiting(double averageWaiting) {
        this.averageWaiting = averageWaiting;
    }

    public void setAverageResponse(double averageResponse) {
        this.averageResponse = averageResponse;
    }

    public void setThroughput(int throughput) {
        this.throughput = throughput;
    }
}
