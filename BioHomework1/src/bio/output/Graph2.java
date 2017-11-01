package bio.output;

public class Graph2 {
	private long timeToRun;
	private int queryLength;
	
	public Graph2(long timeToRun, int queryLength) {
		this.timeToRun = timeToRun;
		this.queryLength = queryLength;
	}
	
	public long getTimeToRun() {
		return timeToRun;
	}
	
	public void setTimeToRun(long timeToRun) {
		this.timeToRun = timeToRun;
	}
	
	public int getQueryLength() {
		return queryLength;
	}
	
	public void setQueryLength(int queryLength) {
		this.queryLength = queryLength;
	} 
}
