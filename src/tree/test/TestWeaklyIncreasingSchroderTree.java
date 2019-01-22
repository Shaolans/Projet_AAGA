package tree.test;

import java.math.BigInteger;

import tree.generate.StronglyIncreasingSchroderTree;
import tree.graph.MakeGraph;
import tree.implementation.SchroderTreeUtils;
import tree.interfaces.SchroderTree;

public class TestWeaklyIncreasingSchroderTree {

	public static void main(String[] args) {
		StronglyIncreasingSchroderTree incst = new StronglyIncreasingSchroderTree(1000);
		SchroderTree treebuilder = incst.treeBuilder(30);
		MakeGraph.makeGraph("tree/test7.dot", treebuilder);
		MakeGraph.makeGraph("tree/test8.dot", incst.unrankTree(30, 
				SchroderTreeUtils.nextRandomBigInteger(new BigInteger("9876543567894532456786453"))));
	}

}
