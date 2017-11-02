package bio.localalignment;

import java.util.ArrayList;
import java.util.List;

import bio.output.Graph2;
import bio.output.NeededForPrint;
import bio.output.OutputHolder;
import bio.plot.Plot;
import bio.plot.PlotValue;
import bio.sequence.Alphabet;
import bio.sequence.ScoringMatrix;
import bio.sequence.Sequence;

public class LocalAlignment {

	public static OutputHolder runLocalAlignment(List<Sequence> queryList, List<Sequence> dbList, Alphabet alphabet,
			ScoringMatrix sm, int gapPenalty) {
		OutputHolder holder = new OutputHolder();

		List<NeededForPrint> printList = new ArrayList<>();

		Plot plot;
		NeededForPrint nfp;

		List<Graph2> graph2Stuff = new ArrayList<>();
		long startTime;
		long endTime;

		for (Sequence s : queryList) {

			System.out.println(s.getHsa());

			startTime = System.currentTimeMillis();

			for (Sequence d : dbList) {

				plot = plotInit(s.getSequence().length(), d.getSequence().length());
				fillOutPlot(plot, s, d, alphabet, sm, gapPenalty);
				backTrackForAlignment(plot, s, d);

				nfp = new NeededForPrint();
				nfp.setFinalScore(new Integer(plot.getFinalScore()));
				nfp.setQueryId(s.getHsa());
				nfp.setDbId(d.getHsa());
				nfp.setAlignedQuery(plot.getAlignedQuery());
				nfp.setAlignedDatabase(plot.getAlignedDatabase());
				nfp.setAlignedQueryStart(plot.getAlignedQueryStart());
				nfp.setAlignedDatabaseStart(plot.getAlignedDatabaseStart());

				printList.add(nfp);

				plot = null;
			}

			endTime = System.currentTimeMillis();
			graph2Stuff.add(new Graph2(endTime - startTime, s.getCharSequence().length));
		}

		holder.setPrintList(printList);
		holder.setGraph2Stuff(graph2Stuff);

		return holder;
	}
	
	/**
	 * 
	 * Initializes the first row/column of the plot with the correct values
	 * using the 0 for local alignment
	 * 
	 * @param queryListSize
	 * @param dbListSize
	 * @param gapPenalty
	 */
	private static Plot plotInit(int querySequenceSize, int dbSequenceSize) {
		Plot plot = new Plot();
		
		PlotValue[][] plotMatrix = new PlotValue[++dbSequenceSize][++querySequenceSize];
		
		//set values in first row
		PlotValue pv;
		for(int col = 0; col < querySequenceSize; col++){
			pv = new PlotValue(0);
			plotMatrix[0][col] = pv;
		}
		
		//set values in first column
		for(int row = 1; row < dbSequenceSize; row++){
			pv = new PlotValue(0);
			plotMatrix[row][0] = pv;
		}
		
		plot.setPlotMatrix(plotMatrix);
		
		return plot;
	}
	
	/**
	 * Fills out the plot using local sequence alignment
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
		
		PlotValue maxScorePlotValue = plotMatrix[0][0];
		
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
				
				//set to 0 for local alignment if negative
				if(maxScore < 0)
					maxScore = 0;
				
				//keep track of max score in matrix
				if(maxScorePlotValue.getScore() < maxScore)
					maxScorePlotValue = currentPlotValue;
				
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
		plot.setMaxScorePlotValue(maxScorePlotValue);
		
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
		
		//start at max score
		PlotValue currentPlotValue = plot.getMaxScorePlotValue();
		
		StringBuilder querySb = new StringBuilder();
		StringBuilder databaseSb = new StringBuilder();
		
		String DASH = "-";
		
		//trace up until you reach a 0 or a null
		while((currentPlotValue.getDiagnol() != null && currentPlotValue.getDiagnol().getScore() != 0) 
				|| (currentPlotValue.getVertical() != null && currentPlotValue.getVertical().getScore() != 0)
				|| (currentPlotValue.getHorizontal() != null && currentPlotValue.getHorizontal().getScore() != 0)){
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
		
		if(currentPlotValue != null && currentPlotValue.getScore() != 0){
			querySb.append(qSequence.getCharSequence()[qCharPosition]);
			databaseSb.append(dbSequence.getCharSequence()[dbCharPosition]);
		}
		
		plot.setAlignedQuery(querySb.reverse().toString());
		plot.setAlignedDatabase(databaseSb.reverse().toString());
		
		//query/db start not zero based so add 1
		plot.setAlignedQueryStart(currentPlotValue.getCol() + 1);
		plot.setAlignedDatabaseStart(currentPlotValue.getRow() + 1);
	}
	

}
