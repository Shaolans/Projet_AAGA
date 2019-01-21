package tree.implementation;

import java.util.ArrayList;
import java.util.List;

import tree.interfaces.SchroderTree;

public class SchroderTreeUtils {
	
	public static List<SchroderTree> getLeaves(SchroderTree T){
		List<SchroderTree> leaves = new ArrayList<>();
		getLeavesRec(T, leaves);
		return leaves;
	}
	
	private static void getLeavesRec(SchroderTree T, List<SchroderTree>leaves) {
		
		if(T.isLeaf())
			leaves.add(T);
		else
			for(SchroderTree child : T.getChildren())
				getLeavesRec(child, leaves);
		
	}
	
	
	public static int getCurrentLabel(SchroderTree T) {
		
		int child_max=T.getLabel();
		for(SchroderTree child : T.getChildren()) {
			int child_label = getCurrentLabel(child);
			if(child_max < child_label){
				child_max = child_label;
			}
			
		}
		return child_max;
	}

}
