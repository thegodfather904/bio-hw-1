package bio.sequence;

public class Sequence {
	private String originalFromFile;
	private String descriptionLine;
	private String hsa;
	private String refSequence;
	private String sequence;
	
	public String getDescriptionLine() {
		return descriptionLine;
	}
	
	public void setDescriptionLine(String descriptionLine) {
		this.descriptionLine = descriptionLine;
	}

	public String getHsa() {
		return hsa;
	}
	
	public void setHsa(String hsa) {
		this.hsa = hsa;
	}
	
	public String getRefSequence() {
		return refSequence;
	}
	
	public void setRefSequence(String refSequence) {
		this.refSequence = refSequence;
	}
	
	public String getSequence() {
		return sequence;
	}
	
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getOriginalFromFile() {
		return originalFromFile;
	}

	public void setOriginalFromFile(String originalFromFile) {
		this.originalFromFile = originalFromFile;
	}
}
