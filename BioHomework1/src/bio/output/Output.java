package bio.output;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Output {
	
	public static void printResults(OutputHolder holder, int numToPrint){
		
		List<NeededForPrint> printList = holder.getPrintList();
		
		sortList(printList);
		
		for(int x = 0; x < numToPrint; x++){
			System.out.println("Score = " + printList.get(x).getFinalScore());
			System.out.println(printList.get(x).getQueryId() + " " 
					+ printList.get(x).getAlignedQueryStart() + " " +
					printList.get(x).getAlignedQuery());
			System.out.println(printList.get(x).getDbId() + " " 
					+ printList.get(x).getAlignedDatabaseStart() + " " +
					printList.get(x).getAlignedDatabase());
			System.out.println();
		}
	}
	
	public static void printForGraph1(OutputHolder holder, int numToPrint) {
		sortList(holder.getPrintList());
		for(int x = 0; x < numToPrint; x++){
			System.out.print(holder.getPrintList().get(x).getFinalScore() + ", ");
		}
	}
	
	public static void printForGraph2(OutputHolder holder){
		for(Graph2 g : holder.getGraph2Stuff()){
			System.out.print("(" + g.getQueryLength() + ", " + g.getTimeToRun() + ")" + ", ");
		}
	}
	
	private static void sortList(List<NeededForPrint> printList) {
		Collections.sort(printList, new Comparator<NeededForPrint>() {
		    @Override
		    public int compare(NeededForPrint o1, NeededForPrint o2) {
		        return o2.getFinalScore().compareTo(o1.getFinalScore());
		    }
		});
	}
	
}
