package bio.sequence;

public class ScoringMatrix {
	
	private String originalMatrix;
	private int[][] scoringMatrix;
	
	public int[][] getScoringMatrix() {
		return scoringMatrix;
	}

	public void setScoringMatrix(int[][] scoringMatrix) {
		this.scoringMatrix = scoringMatrix;
	}

	public String getOriginalMatrix() {
		return originalMatrix;
	}

	public void setOriginalMatrix(String originalMatrix) {
		this.originalMatrix = originalMatrix;
	}
}
