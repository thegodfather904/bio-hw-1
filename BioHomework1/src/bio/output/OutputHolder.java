package bio.output;

import java.util.List;

public class OutputHolder {
	private List<NeededForPrint> printList;
	private List<Graph2> graph2Stuff;
	
	public List<NeededForPrint> getPrintList() {
		return printList;
	}

	public void setPrintList(List<NeededForPrint> printList) {
		this.printList = printList;
	}

	public List<Graph2> getGraph2Stuff() {
		return graph2Stuff;
	}

	public void setGraph2Stuff(List<Graph2> graph2Stuff) {
		this.graph2Stuff = graph2Stuff;
	}
}
