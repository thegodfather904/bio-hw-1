package bio.main;

import java.util.ArrayList;
import java.util.List;

import bio.dovetailalignment.DovetailAlignment;
import bio.globalalignment.GlobalAlignment;
import bio.localalignment.LocalAlignment;
import bio.output.NeededForPrint;
import bio.output.Output;
import bio.plot.Plot;
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
		int numToDisplay = 1; //TODO read from command line
		int gapPenalty = -3; //TODO read from command line
		
		/*TODO read files from command line*/
//		if (args.length !=7 0) {
//			System.err.println("Incorrect number of args - ending program");
//			return;
//		}
		
		FileScanner fscanner = new FileScanner();

		try {
			userSelection = fscanner.getUserSelection("1");
			queryList = fscanner.createSequenceList("query.txt");
			dbList = fscanner.createSequenceList("database2.txt");
			alphabet = fscanner.getAlphabet("alphabet.txt");
			scoringMatrix = fscanner.getScoringMatrix("scoringmatrix.txt", alphabet);
		} catch (Exception e) {
			System.err.println("Error parsing files - program ending");
			return;
		}
		
		List<NeededForPrint> alignmentList = new ArrayList<>();
		
		if(userSelection == 1) 
			alignmentList = GlobalAlignment.runGlobalAlignment(queryList, dbList, alphabet, scoringMatrix, gapPenalty);
		else if(userSelection == 2)
			LocalAlignment.runLocalAlignment();
		else
			DovetailAlignment.runDovetailAlignment();
		
		Output.printResults(alignmentList, numToDisplay);
		
		System.out.println("DONE!");
	}

}
