package bio.main;

import java.util.List;

import bio.dovetailalignment.DovetailAlignment;
import bio.globalalignment.GlobalAlignment;
import bio.localalignment.LocalAlignment;
import bio.scanner.FileScanner;
import bio.sequence.Alphabet;
import bio.sequence.ScoringMatrix;
import bio.sequence.Sequence;

public class SequenceAlignmentMain {

	public static void main(String[] args) {
		int userSelection;
		List<Sequence> queryList;
		List<Sequence> dbList;
		Alphabet alphabet;
		ScoringMatrix scoringMatrix;
		
		/*TODO read files from command line*/
//		if (args.length !=7 0) {
//			System.err.println("Incorrect number of args - ending program");
//			return;
//		}
		
		FileScanner fscanner = new FileScanner();

		try {
			userSelection = fscanner.getUserSelection("1");
			queryList = fscanner.createSequenceList("query.txt");
			dbList = fscanner.createSequenceList("database.txt");
			alphabet = fscanner.getAlphabet("alphabet.txt");
			scoringMatrix = fscanner.getScoringMatrix("scoringmatrix.txt");
		} catch (Exception e) {
			System.err.println("Error parsing files - program ending");
			return;
		}
		
		if(userSelection == 1) 
			GlobalAlignment.runGlobalAlignment();
		else if(userSelection == 2)
			LocalAlignment.runLocalAlignment();
		else
			DovetailAlignment.runDovetailAlignment();
	}

}
