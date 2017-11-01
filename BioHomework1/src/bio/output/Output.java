package bio.output;

import java.util.List;

public class Output {
	
	public static void printResults(List<NeededForPrint> printList, int numToPrint){
		
		for(int x = 0; x < numToPrint; x++){
			System.out.println("Score = " + printList.get(x).getFinalScore());
			System.out.println(printList.get(x).getQueryId() + " " 
					+ printList.get(x).getAlignedQueryStart() + " " +
					printList.get(x).getAlignedQuery());
			System.out.println(printList.get(x).getDbId() + " " 
					+ printList.get(x).getAlignedDatabaseStart() + " " +
					printList.get(x).getAlignedDatabase());
		}
		
	}
	
}
