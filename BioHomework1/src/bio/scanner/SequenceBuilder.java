package bio.scanner;

import java.util.Scanner;

import bio.sequence.Sequence;

public class SequenceBuilder {
	
	/**
	 * Builds a Sequence object from the sequence string
	 * 
	 * @param sequenceString
	 * @return
	 */
	public static Sequence buildSequenceFromString(String sequenceString){
		Sequence sequence = sequenceIntialSetup(sequenceString);
		buildFullSequenceFromString(sequence);
		return sequence;
	}
	
	/**
	 * Takes the first row (the description) and builds the initial values for the sequence
	 * from it (hsa, refSequence, etc)
	 * 
	 * ex. ">hsa:100287010 no KO assigned | (RefSeq) uncharacterized LOC100287010 (N)"
	 * 
	 * 
	 * @param firstRow
	 * @return
	 */
	private static Sequence sequenceIntialSetup(String sequenceString){
		Sequence sequence = new Sequence();
		
		sequence.setOriginalFromFile(sequenceString);
		
		Scanner scan = new Scanner(sequenceString);
		String firstRow = scan.nextLine();
		scan.close();
		sequence.setDescriptionLine(firstRow);
		
		//get rid of hsa part before colon
		firstRow = firstRow.split(":")[1];

		//set the id (now the first word)
		sequence.setHsa(firstRow.split(" " , 2)[0]);
		
		//set the refSequence
		sequence.setRefSequence(firstRow.split("\\|")[1]);
		
		return sequence;
	}
	
	/**
	 * Builds the actual sequence from the given sequence in the file
	 * @param sequence
	 */
	private static void buildFullSequenceFromString(Sequence sequence){
		Scanner scan = new Scanner(sequence.getOriginalFromFile());
		
		//skip descriptor row
		scan.nextLine();
		
		StringBuilder builder = new StringBuilder();
		while(scan.hasNextLine())
			builder.append(scan.nextLine());
		
		sequence.setSequence(builder.toString());
		
		scan.close();	
	}
}
