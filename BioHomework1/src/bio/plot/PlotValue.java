package bio.plot;

public class PlotValue {
	private int score;
	private PlotValue diagnol;
	private PlotValue horizontal;
	private PlotValue vertical;
	
	private int row;
	private int col;
	
	public PlotValue() {
		
	}
	
	public PlotValue (int score) {
		this.score = score;
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public PlotValue getDiagnol() {
		return diagnol;
	}

	public void setDiagnol(PlotValue diagnol) {
		this.diagnol = diagnol;
	}

	public PlotValue getHorizontal() {
		return horizontal;
	}

	public void setHorizontal(PlotValue horizontal) {
		this.horizontal = horizontal;
	}

	public PlotValue getVertical() {
		return vertical;
	}

	public void setVertical(PlotValue vertical) {
		this.vertical = vertical;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
}
