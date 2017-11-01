package bio.plot;

public class Plot {
	
	private PlotValue[][] plotMatrix;
	private int finalScore;
	private String alignedQuery;
	private String alignedDatabase;
	
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
	
}
