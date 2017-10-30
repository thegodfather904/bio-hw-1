package bio.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import bio.scanner.FileScanner;
import bio.sequence.Sequence;

public class SequenceAlignmentMain {

	public static void main(String[] args) {
		int userSelection;
		List<Sequence> queryList;
		
		/*TODO read files from command line*/
//		if (args.length !=7 0) {
//			System.err.println("Incorrect number of args - ending program");
//			return;
//		}
		
		FileScanner fscanner = new FileScanner();

		try {
			userSelection = fscanner.getUserSelection("1");
			
			queryList = fscanner.createSequenceList("query.txt");

		} catch (Exception e) {
			System.err.println("Error parsing files - program ending");
			return;
		}
	}

}
