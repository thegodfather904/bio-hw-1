package bio.globalalignment;

import java.util.ArrayList;
import java.util.List;

import bio.output.NeededForPrint;
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
	public static List<NeededForPrint> runGlobalAlignment(List<Sequence> queryList, List<Sequence> dbList,
			Alphabet alphabet, ScoringMatrix sm, int gapPenalty) {
		
		List<NeededForPrint> printList = new ArrayList<>();
		
		Plot plot;
		NeededForPrint nfp;
		
		int sCount = 0;
		int dbCount = 0;
		
		for(Sequence s : queryList){
			
			System.out.println("Query " + sCount++);
			dbCount = 0;
			
			for(Sequence d : dbList){
				
				System.out.println("Database " + dbCount++);
				
				plot = plotInit(s.getSequence().length(), d.getSequence().length(), gapPenalty);
				fillOutPlot(plot, s, d, alphabet, sm, gapPenalty);
				backTrackForAlignment(plot, s, d);
				
				nfp = new NeededForPrint();
				nfp.setFinalScore(plot.getFinalScore());
				nfp.setQueryId(s.getHsa());
				nfp.setDbId(d.getHsa());
				nfp.setAlignedQuery(plot.getAlignedQuery());
				nfp.setAlignedDatabase(plot.getAlignedDatabase());
				
				printList.add(nfp);
				
				plot = null;
			}
		}
			
		
		return printList;
		
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
		
		PlotValue[][] plotMatrix = new PlotValue[++dbSequenceSize][++querySequenceSize];
		
		//set values in first row
		PlotValue pv;
		int currentPenatly = 0;
		for(int col = 0; col < querySequenceSize; col++){
			pv = new PlotValue(currentPenatly);
			currentPenatly += gapPenalty;
			plotMatrix[0][col] = pv;
		}
		
		//set values in first column
		currentPenatly = gapPenalty;
		for(int row = 1; row < dbSequenceSize; row++){
			pv = new PlotValue(currentPenatly);
			currentPenatly += gapPenalty;
			plotMatrix[row][0] = pv;
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
		for(int row = 1; row < dbSequence.getCharSequence().length + 1; row++){
			
			dbChar = dbSequence.getCharSequence()[row - 1];
			
			for(int col = 1; col < qSequence.getCharSequence().length + 1; col++){
				
				currentPlotValue = new PlotValue();
				
				qChar = qSequence.getCharSequence()[col - 1];
				
				diagnol = calcScoreFromScoringMatrix(qChar, dbChar, alphabet, sm) + plotMatrix[row - 1][col - 1].getScore();
				vertical = plotMatrix[row - 1][col].getScore() + gapPenalty;
				horizontal = plotMatrix[row][col - 1].getScore() + gapPenalty;
				
				//pick max score
				maxScore = Math.max(Math.max(diagnol, vertical), horizontal);
				
				currentPlotValue.setScore(maxScore);
				currentPlotValue.setRow(row);
				currentPlotValue.setCol(col);
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
		
		int finalScore = plotMatrix[dbSequence.getCharSequence().length][qSequence.getCharSequence().length].getScore();
		
		plot.setFinalScore(finalScore);
		
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
	
	private static void backTrackForAlignment(Plot plot, Sequence qSequence, Sequence dbSequence) {
		
		//start at last position in char array
		int qCharPosition = qSequence.getCharSequence().length - 1;
		int dbCharPosition = dbSequence.getCharSequence().length - 1;
		
		//start at bottom right corner (add 1 because we added a row/col)
		PlotValue currentPlotValue = plot.getPlotMatrix()[dbCharPosition + 1][qCharPosition + 1];
		
		StringBuilder querySb = new StringBuilder();
		StringBuilder databaseSb = new StringBuilder();
		
		String DASH = "-";
		
		//go until at starting position
		while(currentPlotValue.getDiagnol() != null || currentPlotValue.getVertical() != null || 
				currentPlotValue.getHorizontal() != null){
			if(currentPlotValue.getDiagnol() != null){
				querySb.append(qSequence.getCharSequence()[qCharPosition--]);
				databaseSb.append(dbSequence.getCharSequence()[dbCharPosition--]);
				currentPlotValue = currentPlotValue.getDiagnol();
			}else if (currentPlotValue.getVertical() != null){
				querySb.append(DASH);
				databaseSb.append(dbSequence.getCharSequence()[dbCharPosition--]);
				currentPlotValue = currentPlotValue.getVertical();
			}else if (currentPlotValue.getHorizontal() != null){
				querySb.append(qSequence.getCharSequence()[qCharPosition--]);
				databaseSb.append(DASH);
				currentPlotValue = currentPlotValue.getHorizontal();
			}
		}
		
		plot.setAlignedQuery(querySb.reverse().toString());
		plot.setAlignedDatabase(databaseSb.reverse().toString());
		
			
	}
	
}
