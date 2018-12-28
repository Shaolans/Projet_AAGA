package tree.graph;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import tree.interfaces.SchroderTree;

public class MakeGraph {

	
	public static void makeGraph(String path, SchroderTree stree) {
		FileWriter fileWriter = null;
		PrintWriter printWriter = null;
		
		try {
			fileWriter = new FileWriter(path);
			printWriter = new PrintWriter(fileWriter);
			StringBuilder sb = new StringBuilder();
			sb.append("digraph {\n  edge [arrowhead = none, arrowtail=none];\n");
			sb.append(makeGraphAux(stree));
			sb.append("}");
			printWriter.print(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			printWriter.close();
		}
		
		
	}
	
	private static String makeGraphAux(SchroderTree stree) {
		if(stree.isLeaf()) {
			return stree.getUniqueId() +" [label=\"\", shape=point];\n";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(stree.getUniqueId() + " [label=\""+ stree.getLabel() +"\", shape=circle, width=0.1];\n");
		for(SchroderTree child: stree.getChildren()) {
			sb.append(stree.getUniqueId() + " -> " + child.getUniqueId() + ";\n");
		}
		for(SchroderTree child: stree.getChildren()) {
			sb.append(makeGraphAux(child));
		}
		return sb.toString();
		
	}
}
