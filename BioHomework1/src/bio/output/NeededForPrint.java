package bio.output;

import java.util.List;

public class NeededForPrint {
	private Integer finalScore;
	private String alignedQuery;
	private String alignedDatabase;
	private int alignedQueryStart = 1;
	private int alignedDatabaseStart = 1;
	private String queryId;
	private String dbId;
	
	public String getAlignedQuery() {
		return alignedQuery;
	}
	public void setAlignedQuery(String alignedQuery) {
		this.alignedQuery = alignedQuery;
	}
	public String getAlignedDatabase() {
		return alignedDatabase;
	}
	public void setAlignedDatabase(String alignedDatabase) {
		this.alignedDatabase = alignedDatabase;
	}
	public int getAlignedQueryStart() {
		return alignedQueryStart;
	}
	public void setAlignedQueryStart(int alignedQueryStart) {
		this.alignedQueryStart = alignedQueryStart;
	}
	public int getAlignedDatabaseStart() {
		return alignedDatabaseStart;
	}
	public void setAlignedDatabaseStart(int alignedDatabaseStart) {
		this.alignedDatabaseStart = alignedDatabaseStart;
	}
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	public String getDbId() {
		return dbId;
	}
	public void setDbId(String dbId) {
		this.dbId = dbId;
	}
	public Integer getFinalScore() {
		return finalScore;
	}
	public void setFinalScore(Integer finalScore) {
		this.finalScore = finalScore;
	}
}
