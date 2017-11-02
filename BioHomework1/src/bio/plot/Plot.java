package bio.plot;

public class Plot {
	
	private PlotValue[][] plotMatrix;
	private int finalScore;
	private String alignedQuery;
	private String alignedDatabase;
	private int alignedQueryStart = 1;
	private int alignedDatabaseStart = 1;
	
	//for local
	private PlotValue maxScorePlotValue;
	
	public PlotValue[][] getPlotMatrix() {
		return plotMatrix;
	}

	public void setPlotMatrix(PlotValue[][] plotMatrix) {
		this.plotMatrix = plotMatrix;
	}

	public int getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(int finalScore) {
		this.finalScore = finalScore;
	}

	public String getAlignedQuery() {
		return alignedQuery;
	}

	public void setAlignedQuery(String alignedQuery) {
		this.alignedQuery = alignedQuery;
	}

	public String getAlignedDatabase() {
		return alignedDatabase;
	}

	public void setAlignedDatabase(String alignedDatabase) {
		this.alignedDatabase = alignedDatabase;
	}

	public int getAlignedQueryStart() {
		return alignedQueryStart;
	}

	public void setAlignedQueryStart(int alignedQueryStart) {
		this.alignedQueryStart = alignedQueryStart;
	}

	public int getAlignedDatabaseStart() {
		return alignedDatabaseStart;
	}

	public void setAlignedDatabaseStart(int alignedDatabaseStart) {
		this.alignedDatabaseStart = alignedDatabaseStart;
	}

	public PlotValue getMaxScorePlotValue() {
		return maxScorePlotValue;
	}

	public void setMaxScorePlotValue(PlotValue maxScorePlotValue) {
		this.maxScorePlotValue = maxScorePlotValue;
	}
}
