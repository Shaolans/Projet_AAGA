package tree.generate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.google.common.math.BigIntegerMath;

import tree.graph.MakeGraph;
import tree.implementation.SchroderTreeImpl;
import tree.implementation.SchroderTreeUtils;
import tree.interfaces.SchroderTree;

public class InscreasingSchroderTree {
	public static BigInteger[] coeffs = countCoeff(100);
	
	public static BigInteger[] countCoeff(int n) {
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
	
	public static SchroderTree treeBuilder(int n) {
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
	
	
	
	
	
	
	public static SchroderTree unrankTree(int n, BigInteger s) {
		System.out.println("call: "+n+" "+s);
		if(n == 1) return new SchroderTreeImpl();
		
		int k = n-1;
		BigInteger r = s;
		while(r.compareTo(BigInteger.ZERO) >= 0) {
			System.out.println("1: "+r);
			r = r.subtract(new BigInteger(k+"").multiply(coeffs[k]));
			System.out.println("2: " + new BigInteger(k+"").multiply(coeffs[k]));
			System.out.println("3: " + r);
			k--;
		}
		k++;
		r = r.add(new BigInteger(k+"").multiply(coeffs[k]));
		BigInteger sp = r.mod(coeffs[k]);
		SchroderTree t = unrankTree(k, sp);
		List<BigInteger> C = unrankComposition(n,k,r.divideAndRemainder(coeffs[k])[0]);
		substituteTree(t,C);
		return t;
	}
	
	
	public static List<BigInteger> unrankComposition(int n, int k, BigInteger s){
		List<BigInteger> C = new ArrayList<>();
		unrankCompositionRec(n ,k, s, C);
		return C;
	}
	
	public static void unrankCompositionRec(int n, int k, BigInteger s, List<BigInteger> C){
		if(n == k) {
			for(int i = 0; i < k; i++) {
				C.add(BigInteger.ONE);
			}
			return;
		}
				
		BigInteger s_prime = s;
		BigInteger combinaison = BigIntegerMath.binomial(n-2, k-1);
		if(s_prime.compareTo(combinaison) == -1) {
			unrankCompositionRec(n-1, k, s_prime, C);
			BigInteger newVal = C.get(C.size()-1).add(BigInteger.ONE);
			C.remove(C.get(C.size()-1));
			C.add(newVal);
			
		}else {
			s_prime = s_prime.subtract(combinaison);
			unrankCompositionRec(n-1, k-1, s_prime, C);
			C.add(BigInteger.ONE);
			
		}
		
	}
	private static int label = 1;
	public static void  substituteTree(SchroderTree T, List<BigInteger> C) {
		
		if(C.size()!=0) {
			List<SchroderTree> leaves = SchroderTreeUtils.getLeaves(T);
			int position=0;
			
			for(SchroderTree leaf : leaves) {
				BigInteger val = C.get(position);
				if(val.compareTo(BigInteger.ONE)!=0) {
					leaf.setisLeaf(false);
					leaf.setLabel(label++);
					List<SchroderTree> children = leaf.getChildren();
					for(BigInteger i=BigInteger.ZERO; i.compareTo(val)<0; i=i.add(BigInteger.ONE)) {
						SchroderTree s = new SchroderTreeImpl();
						children.add(s);
					}
				}
				position++;
			
				
			}
			
		}else {
			System.out.println("Vide");
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		Arrays.stream(countCoeff(20)).forEach(x -> System.out.println(x));
		//MakeGraph.makeGraph("tree/test4.dot", treeBuilder(30));
		MakeGraph.makeGraph("tree/test6.dot", unrankTree(25, new BigInteger("9874532999879983234")));
	}
	
}
