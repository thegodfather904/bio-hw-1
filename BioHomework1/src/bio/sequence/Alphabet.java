package bio.sequence;

import java.util.Hashtable;

public class Alphabet {
	
	private String alphabet;
	private char[] alphabetArray;
	private Hashtable<Character, Integer> alphabetHash;

	public String getAlphabet() {
		return alphabet;
	}

	public void setAlphabet(String alphabet) {
		this.alphabet = alphabet;
	}

	public char[] getAlphabetArray() {
		return alphabetArray;
	}

	public void setAlphabetArray(char[] alphabetArray) {
		this.alphabetArray = alphabetArray;
	}

	public Hashtable<Character, Integer> getAlphabetHash() {
		return alphabetHash;
	}

	public void setAlphabetHash(Hashtable<Character, Integer> alphabetHash) {
		this.alphabetHash = alphabetHash;
	}
}
