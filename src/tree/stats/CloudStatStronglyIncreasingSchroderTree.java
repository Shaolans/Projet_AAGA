package tree.stats;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;

import tree.generate.StronglyIncreasingSchroderTree;
import tree.implementation.SchroderTreeUtils;
import tree.interfaces.SchroderTree;

public class CloudStatStronglyIncreasingSchroderTree {
	public static void main(String[] args) {
		final int AVG = 50;
		final int MAX_NODES = 2000;
		final int PADDING = 10;
		StronglyIncreasingSchroderTree sist = new StronglyIncreasingSchroderTree(2001);
		StringBuilder statAvgNodesTreeBuilder = new StringBuilder();
		StringBuilder statAvgNodesUnrank = new StringBuilder();
		StringBuilder statAvgHeightTreeBuilder = new StringBuilder();
		StringBuilder statAvgHeightUnrank = new StringBuilder();
		for(int i = PADDING; i <= MAX_NODES; i += PADDING) {
			System.out.println("taille: "+i+" "+(i<=MAX_NODES));
			int avgNodesTreeBuilder = 0;
			int avgNodesUnrank = 0;
			int avgHeightTreeBuilder = 0;
			int avgHeightUnrank = 0;
			for(int j = 0; j < AVG; j++) {
				SchroderTree treeBuilder = sist.treeBuilder(i);
				avgNodesTreeBuilder = SchroderTreeUtils.countInternalNode(treeBuilder);
				avgHeightTreeBuilder = SchroderTreeUtils.getHeight(treeBuilder);
				
				SchroderTree unrank = sist.unrankTree(i,
						SchroderTreeUtils.nextRandomBigInteger(sist.getComptage()[i].subtract(BigInteger.ONE)));
				avgNodesUnrank = SchroderTreeUtils.countInternalNode(unrank);;
				avgHeightUnrank = SchroderTreeUtils.getHeight(unrank);
				statAvgNodesTreeBuilder.append(i+" "+avgNodesTreeBuilder+"\n");
				statAvgNodesUnrank.append(i+" "+avgNodesUnrank+"\n");
				statAvgHeightTreeBuilder.append(i+" "+avgHeightTreeBuilder+"\n");
				statAvgHeightUnrank.append(i+" "+avgHeightUnrank+"\n");
			}

		}
		printData("stats/cloudstatStrongAvgNodesTreeBuilder.txt", statAvgNodesTreeBuilder.toString());
		printData("stats/cloudstatStrongAvgNodesUnrank.txt", statAvgNodesUnrank.toString());
		printData("stats/cloudstatStrongAvgHeightTreeBuilder.txt", statAvgHeightTreeBuilder.toString());
		printData("stats/cloudstatStrongAvgHeightUnrank.txt", statAvgHeightUnrank.toString());
		
	}
	
	public static void printData(String path, String content) {
		FileWriter fileWriter = null;
		PrintWriter printWriter = null;
		
		try {
			fileWriter = new FileWriter(path);
			printWriter = new PrintWriter(fileWriter);
			printWriter.print(content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			printWriter.close();
		}
		
	}
	
}
