package bio.scanner;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import bio.sequence.Alphabet;
import bio.sequence.ScoringMatrix;
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

		String fullFile = convertFileToString(filename);
			
		String[] split = fullFile.split(Pattern.quote(">"));
			
		for(String sequenceString : split)
			if(sequenceString.length() > 0)
				sequenceList.add(SequenceBuilder.buildSequenceFromString(sequenceString));
		
		return sequenceList;
	}
	
	/**
	 * 
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public Alphabet getAlphabet(String filename) throws Exception {
		Alphabet a = new Alphabet();
		a.setAlphabet(convertFileToString(filename));
		a.setAlphabetArray(a.getAlphabet().toCharArray());
		return a;
	}
	
	/**
	 * 
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public ScoringMatrix getScoringMatrix(String filename, Alphabet alphabet) throws Exception {
		ScoringMatrix sm;
		String scoringMatrixFromFile = convertFileToString(filename);
		sm = ScoringMatrixBuilder.buildScoringMatrix(scoringMatrixFromFile, alphabet);
		return sm;
	}
	
	private String convertFileToString(String filename) throws Exception{
		try{
			return new String(Files.readAllBytes(Paths.get(filename)));
		}catch(Exception e){
			System.err.println("Error reading file " + filename);
			throw new Exception();
		}
	}

	

	

	
	
}
