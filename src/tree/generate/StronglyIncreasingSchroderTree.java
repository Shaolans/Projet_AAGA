package tree.generate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tree.graph.MakeGraph;
import tree.implementation.SchroderTreeImpl;
import tree.implementation.SchroderTreeUtils;
import tree.interfaces.SchroderTree;

public class StronglyIncreasingSchroderTree {
	private BigInteger[] coeffs;
	private int l;
	
	public StronglyIncreasingSchroderTree(int nb_comptage) {
		coeffs = countCoeff(nb_comptage);
		l = 1;
	}
	
	
	public BigInteger[] countCoeff(int n) {
		if(n <= 0) return null;
		if(n == 1) {
			BigInteger[] count = new BigInteger[n+1];
			count[0] = BigInteger.ZERO;
			count[1] = BigInteger.ONE;
			return count;
		}
		if(n == 2) {
			BigInteger[] count = new BigInteger[n+1];
			count[0] = BigInteger.ZERO;
			count[1] = BigInteger.ONE;
			count[2] = BigInteger.ONE;
			return count;
		}
		BigInteger[] count = new BigInteger[n+1];
		count[0] = BigInteger.ZERO;
		count[1] = BigInteger.ONE;
		count[2] = BigInteger.ONE;
		for(int i = 3; i <= n; i++) {
			count[i] = count[i-1].multiply(new BigInteger(i+"")); 
		}
		return count;
	}
	
	public SchroderTree treeBuilder(int n) {
		if(n == 1) return new SchroderTreeImpl();
		List<SchroderTree> leafs = new ArrayList<>();
		List<SchroderTree> children = new ArrayList<>();
		for(int i = 0; i < 2; i++) {
			children.add(new SchroderTreeImpl());
		}
		SchroderTree root = new SchroderTreeImpl(false, 1, children);
		leafs.addAll(children);
		
		SchroderTree lastnode = root;
		int l = 2;
		int k;
		SchroderTree lf;
		Random r = new Random();
		for(int i = 3; i <= n; i++) {
			k = r.nextInt(i);
			if(k+1 == i) {
				lf = new SchroderTreeImpl();
				lastnode.getChildren().add(lf);
				leafs.add(lf);
			}else {
				lastnode = leafs.remove(k);
				lastnode.setisLeaf(false);
				lastnode.setLabel(l);
				children = new ArrayList<>();
				for(int m = 0; m < 2; m++) {
					children.add(new SchroderTreeImpl());
				}
				lastnode.getChildren().addAll(children);
				leafs.addAll(children);
				l++;
				
			}
		}
		
		return root;
	}
	
	
	
	public SchroderTree unrankTree(int n, BigInteger s) {
		l = 1;
		SchroderTree t = unrankTreeRec(n, s);
		l = 1;
		return t;
	}
	
	public SchroderTree unrankTreeRec(int n, BigInteger s) {
		if(n == 1) return new SchroderTreeImpl();
		int k = n-1;
		BigInteger r = s;
		while(r.compareTo(BigInteger.ZERO) >= 0) {
			r = r.subtract(new BigInteger(k+"").multiply(coeffs[k]));
			k--;
		}
		k++;
		r = r.add(new BigInteger(k+"").multiply(coeffs[k]));
		BigInteger[] euclidian = r.divideAndRemainder(coeffs[k]);
		SchroderTree t = unrankTreeRec(k, euclidian[1]);
		SchroderTree node = SchroderTreeUtils.getLeaves(t).get(euclidian[0].intValue());
		node.setisLeaf(false);
		node.setLabel(l++);
		for(int i = 0; i < (n-k)+1; i++) {
			node.getChildren().add(new SchroderTreeImpl());
		}
		return t;
	}
	
	public BigInteger[] getComptage() {
		return coeffs;
	}
	
	
	
	public static void main(String[] args) {
		//Arrays.stream(countCoeff(20)).forEach(x -> System.out.println(x));
		//MakeGraph.makeGraph("tree/test4.dot", treeBuilder(30));
		StronglyIncreasingSchroderTree incSchroderTree = new StronglyIncreasingSchroderTree(1000);
		MakeGraph.makeGraph("tree/test6.dot", incSchroderTree.unrankTree(30, new BigInteger("999999999992131231239")));
	}
	
}
