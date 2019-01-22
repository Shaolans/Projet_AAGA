package tree.stats;

import java.math.BigInteger;

import tree.generate.WeaklyInscreasingSchroderTree;
import tree.graph.MakeGraph;
import tree.implementation.SchroderTreeUtils;

public class StatStronglyIncreasingSchroderTree {
	public static void main(String[] args) {
		WeaklyInscreasingSchroderTree winst = new WeaklyInscreasingSchroderTree(1001);
		MakeGraph.makeGraph("tree/test9.dot", winst.treeBuilder(30));
		MakeGraph.makeGraph("tree/test10.dot", winst.unrankTree(30, 
				SchroderTreeUtils.nextRandomBigInteger(
						winst.getComptage()[30].subtract(BigInteger.ONE))));
	}
	
}
