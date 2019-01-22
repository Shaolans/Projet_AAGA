package tree.stats;

import java.math.BigInteger;

import tree.generate.StronglyIncreasingSchroderTree;
import tree.graph.MakeGraph;
import tree.implementation.SchroderTreeUtils;

public class StatWeaklyIncreasingSchroderTree {

	public static void main(String[] args) {
		StronglyIncreasingSchroderTree incst = new StronglyIncreasingSchroderTree(1000);
		MakeGraph.makeGraph("tree/test7.dot", incst.treeBuilder(30));
		MakeGraph.makeGraph("tree/test8.dot", incst.unrankTree(30, 
				SchroderTreeUtils.nextRandomBigInteger(new BigInteger("9876543567894532456786453"))));
	}

}
