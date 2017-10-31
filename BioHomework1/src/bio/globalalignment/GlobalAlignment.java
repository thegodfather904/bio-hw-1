package bio.globalalignment;

import java.util.List;

import bio.plot.Plot;
import bio.plot.PlotValue;
import bio.sequence.Alphabet;
import bio.sequence.ScoringMatrix;
import bio.sequence.Sequence;

public class GlobalAlignment {
	
	/**
	 * 
	 * @param queryList
	 * @param dbList
	 * @param alphabet
	 * @param sm
	 * @param gapPenatly
	 */
	public static void runGlobalAlignment(List<Sequence> queryList, List<Sequence> dbList,
			Alphabet alphabet, ScoringMatrix sm, int gapPenalty) {
		
		Plot plot;
		for(Sequence s : queryList)
			for(Sequence d : dbList){
				plot = plotInit(s.getSequence().length(), d.getSequence().length(), gapPenalty);
				fillOutPlot(plot, s, d, alphabet, sm, gapPenalty);
			}
				
		
	}
	
	/**
	 * 
	 * Initializes the first row/column of the plot with the correct values
	 * using the gap penalty
	 * 
	 * @param queryListSize
	 * @param dbListSize
	 * @param gapPenalty
	 */
	private static Plot plotInit(int querySequenceSize, int dbSequenceSize, int gapPenalty) {
		Plot plot = new Plot();
		
		PlotValue[][] plotMatrix = new PlotValue[++querySequenceSize][++dbSequenceSize];
		
		//set values in first row
		PlotValue pv;
		int currentPenatly = 0;
		for(int x = 0; x < querySequenceSize; x++){
			pv = new PlotValue(currentPenatly);
			currentPenatly += gapPenalty;
			plotMatrix[0][x] = pv;
		}
		
		//set values in first column
		currentPenatly = gapPenalty;
		for(int y = 1; y < dbSequenceSize; y++){
			pv = new PlotValue(currentPenatly);
			currentPenatly += gapPenalty;
			plotMatrix[y][0] = pv;
		}
		
		plot.setPlotMatrix(plotMatrix);
		
		return plot;
	}
	
	/**
	 * Fills out the plot using global sequence alignment
	 * 
	 * @param plot
	 * @param qSequence
	 * @param dbSequence
	 * @param alphabet
	 * @param sm
	 * @param gapPenalty
	 */
	private static void fillOutPlot(Plot plot, Sequence qSequence, Sequence dbSequence, Alphabet alphabet,
			ScoringMatrix sm, int gapPenalty) {
		
		PlotValue[][] plotMatrix = plot.getPlotMatrix();
		
		int diagnol;
		int vertical;
		int horizontal;
		char qChar;
		char dbChar;
		int maxScore;
		PlotValue currentPlotValue;
		
		//start at row 1, col 1 because 0 is filled out
		for(int row = 1; row < qSequence.getCharSequence().length + 1; row++){
			
			dbChar = dbSequence.getCharSequence()[row - 1];
			
			for(int col = 1; col < dbSequence.getCharSequence().length + 1; col++){
				
				currentPlotValue = new PlotValue();
				
				qChar = qSequence.getCharSequence()[col - 1];
				
				diagnol = calcScoreFromScoringMatrix(qChar, dbChar, alphabet, sm) + plotMatrix[row - 1][col - 1].getScore();
				vertical = plotMatrix[row - 1][col].getScore() + gapPenalty;
				horizontal = plotMatrix[row][col - 1].getScore() + gapPenalty;
				
				//pick max score
				maxScore = Math.max(Math.max(diagnol, vertical), horizontal);
				
				currentPlotValue.setScore(maxScore);
				plotMatrix[row][col] = currentPlotValue;
				
				//set path so we can revert back later
				if(diagnol == maxScore)
					currentPlotValue.setDiagnol(plotMatrix[row - 1][col - 1]);
				
				if(vertical == maxScore)
					currentPlotValue.setVertical(plotMatrix[row - 1][col]);
				
				if(horizontal == maxScore)
					currentPlotValue.setHorizontal(plotMatrix[row][col - 1]);
				
			}
		}
	}
	
	/**
	 * Calculates the score for replacement using the scoring matrix
	 * 
	 * @param qChar
	 * @param dbChar
	 * @param alphabet
	 * @param sm
	 * @return
	 */
	private static int calcScoreFromScoringMatrix(char qChar, char dbChar, Alphabet alphabet,
			ScoringMatrix sm) {
		
		int score = 0;
		
		//get current query char and current db char index
		int qIndex = alphabet.getAlphabetHash().get(qChar);
		int dbIndex = alphabet.getAlphabetHash().get(dbChar);
		
		//look up in table to see score
		score = sm.getScoringMatrix()[qIndex][dbIndex];
		
		return score;
	}
	
}
