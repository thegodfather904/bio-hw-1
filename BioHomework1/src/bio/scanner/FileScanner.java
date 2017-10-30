package bio.scanner;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import bio.sequence.Sequence;

public class FileScanner {
	
	/**
	 * Gets the first argumemnt which is the users selection of the algorithm to run 
	 * (must be between 1-3)
	 * 
	 * @param args0
	 * @return
	 * @throws Exception
	 */
	public int getUserSelection(String args0) throws Exception{
		int userSelection;
		try{
			userSelection = Integer.parseInt(args0);
			if(userSelection < 0 || userSelection> 3)
				throw new Exception();
			return userSelection;
		}catch(Exception e){
			System.err.println("First argument must be a number and be between 1 and 3");
			throw new Exception();
		}
	}
	
	/**
	 * 
	 * Creates a list of sequences from a given file (query or db file)
	 * 
	 * @param filename
	 * @return
	 */
	public List<Sequence> createSequenceList(String filename) throws Exception{
		List<Sequence> sequenceList = new ArrayList<>();
		
		try{
			String fullFile = new String(Files.readAllBytes(Paths.get(filename)));
			
			String[] split = fullFile.split(Pattern.quote(">"));
			
			for(String sequenceString : split)
				if(sequenceString.length() > 0)
					sequenceList.add(buildSequenceFromString(sequenceString));
			
		}catch(Exception e){
			System.err.println("Error reading file " + filename);
			throw new Exception();
		}
		
		return sequenceList;
	}
	
	/**
	 * Builds a Sequence object from the sequence string
	 * 
	 * @param sequenceString
	 * @return
	 */
	private Sequence buildSequenceFromString(String sequenceString){
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
	private Sequence sequenceIntialSetup(String sequenceString){
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
	private void buildFullSequenceFromString(Sequence sequence){
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
