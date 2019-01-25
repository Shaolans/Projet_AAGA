package tree.stats;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;

import tree.generate.StronglyIncreasingSchroderTree;
import tree.generate.WeaklyInscreasingSchroderTree;
import tree.implementation.SchroderTreeUtils;
import tree.interfaces.SchroderTree;

public class StatWeaklyIncreasingSchroderTree {
	public static void main(String[] args) {
		final int AVG = 50;
		final int MAX_NODES = 2000;
		final int PADDING = 10;
		WeaklyInscreasingSchroderTree sist = new WeaklyInscreasingSchroderTree(2001);
		StringBuilder statAvgNodesTreeBuilder = new StringBuilder();
		StringBuilder statAvgNodesUnrank = new StringBuilder();
		StringBuilder statAvgHeightTreeBuilder = new StringBuilder();
		StringBuilder statAvgHeightUnrank = new StringBuilder();
		for(int i = PADDING; i <= MAX_NODES; i += PADDING) {
			System.out.println("taille: "+i);
			int avgNodesTreeBuilder = 0;
			int avgNodesUnrank = 0;
			int avgHeightTreeBuilder = 0;
			int avgHeightUnrank = 0;
			for(int j = 0; j < AVG; j++) {
				SchroderTree treeBuilder = sist.treeBuilder(i);
				avgNodesTreeBuilder += SchroderTreeUtils.countInternalNode(treeBuilder);
				avgHeightTreeBuilder += SchroderTreeUtils.getHeight(treeBuilder);
				
				SchroderTree unrank = sist.unrankTree(i,
						SchroderTreeUtils.nextRandomBigInteger(sist.getComptage()[i].subtract(BigInteger.ONE)));
				avgNodesUnrank += SchroderTreeUtils.countInternalNode(unrank);;
				avgHeightUnrank += SchroderTreeUtils.getHeight(unrank);
			}
			statAvgNodesTreeBuilder.append(i+" "+((double)avgNodesTreeBuilder/(double)AVG)+"\n");
			statAvgNodesUnrank.append(i+" "+((double)avgNodesUnrank/(double)AVG)+"\n");
			statAvgHeightTreeBuilder.append(i+" "+((double)avgHeightTreeBuilder/(double)AVG)+"\n");
			statAvgHeightUnrank.append(i+" "+((double)avgHeightUnrank/(double)AVG)+"\n");
		}
		printData("stats/statWeakAvgNodesTreeBuilder.txt", statAvgNodesTreeBuilder.toString());
		printData("stats/statWeakAvgNodesUnrank.txt", statAvgNodesUnrank.toString());
		printData("stats/statWeakAvgHeightTreeBuilder.txt", statAvgHeightTreeBuilder.toString());
		printData("stats/statWeakAvgHeightUnrank.txt", statAvgHeightUnrank.toString());
		
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
