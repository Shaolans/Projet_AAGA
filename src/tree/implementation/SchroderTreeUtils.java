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
	
	public static int countInternalNode(SchroderTree T) {
		if(T == null || T.isLeaf()) return 0;
		int size = 1;
		for(SchroderTree child: T.getChildren()) {
			size += countInternalNode(child);
		}
		return size;
	}
	
	public static int countLeafNode(SchroderTree T) {
		if(T == null) return 0;
		if(T.isLeaf()) return 1;
		int size = 0;
		for(SchroderTree child: T.getChildren()) {
			size += countLeafNode(child);
		}
		return size;
	}

}
