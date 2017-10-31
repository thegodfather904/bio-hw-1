package bio.scanner;

import java.util.Scanner;

import bio.sequence.Alphabet;
import bio.sequence.ScoringMatrix;

public class ScoringMatrixBuilder {
	
	/**
	 * 
	 * @param smOriginal
	 * @param alphabet
	 * @return
	 */
	public static ScoringMatrix buildScoringMatrix(String smOriginal, Alphabet alphabet) throws Exception {
		ScoringMatrix sm = new ScoringMatrix();
		sm.setOriginalMatrix(smOriginal);
		sm.setScoringMatrix(build2dMatrix(smOriginal, alphabet));
		return sm;
	}
	
	/**
	 * 
	 * @param smOriginal
	 * @param alphabet
	 * @return
	 */
	private static int[][] build2dMatrix(String smOriginal, Alphabet alphabet) throws Exception {
		int[][] scoringMatrix = new int[alphabet.getAlphabetArray().length][alphabet.getAlphabetArray().length];
		Scanner scan = new Scanner(smOriginal);
		
		int row = 0;
		int col = 0;
		
		try{
			String[] currentLineArray;
			while(scan.hasNextLine()){
				currentLineArray = scan.nextLine().split(" ");
				for(String score : currentLineArray)
					if(score.length() > 0)
						scoringMatrix[row][col++] = Integer.parseInt(score.trim());	
				row++;
				col = 0;
			}
		}catch(Exception e){
			System.err.println("Error building scoring matrix. " + e.getMessage());
			throw new Exception();
		}finally{
			scan.close();
		}
		
		return scoringMatrix;
	}
}
