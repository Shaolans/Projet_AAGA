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

public class WeaklyInscreasingSchroderTree {
	public static BigInteger[] coeffs = countCoeff(100);
	
	public static void main(String[] args) {
		//Arrays.stream(countCoeff(100)).forEach(x -> System.out.println(x));
		System.out.println(coeffs[15]);
		MakeGraph.makeGraph("tree/test4.dot", unrankTree(15, new BigInteger("77777")));
		
		
	}
	
	public static BigInteger[] countCoeff(int n) {
		BigInteger count[] = new BigInteger[n+1];
		count[0] = BigInteger.ZERO;
		count[1] = BigInteger.ONE;
		BigInteger tmp;
		for(int i = 2; i < n+1; i++) {
			tmp = BigInteger.ZERO;
			for(int k = 1; k < i; k++) {
				tmp = tmp.add(BigIntegerMath.binomial(i-1, k-1).multiply(count[k]));
			}
			count[i] = tmp;
		}
		return count;
	}
	
	public static SchroderTree unrankTree(int n, BigInteger s) {
		if(n == 1) return new SchroderTreeImpl();
		
		int k = n-1;
		BigInteger r = s;
		while(r.compareTo(BigInteger.ZERO) >= 0) {
			
			r = r.subtract(BigIntegerMath.binomial(n-1, k-1).multiply(coeffs[k]));
			k--;
		}
		k++;
		r = r.add(BigIntegerMath.binomial(n-1, k-1).multiply(coeffs[k]));
		BigInteger sp = r.mod(coeffs[k]);
		SchroderTree t = unrankTree(k, sp);
		List<BigInteger> C = unrankComposition(n,k,r.divideAndRemainder(coeffs[k])[0]);
		System.out.println(C);
		substituteTree(t,C);
		return t;
	}
	
	public static List<BigInteger> unrankComposition(int n, int k, BigInteger s){
		List<BigInteger> C = new ArrayList<>();
		//System.out.println(n+" "+k+" "+s);
		unrankCompositionRec(n ,k, s, C);
		return C;
	}
	
	public static void unrankCompositionRec(int n, int k, BigInteger s, List<BigInteger> C){
		if(n == k ) {
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
	
	public static void  substituteTree(SchroderTree T, List<BigInteger> C) {
		
		if(C.size()!=0) {
			List<SchroderTree> leaves = SchroderTreeUtils.getLeaves(T);
			int position=0;
			int currentLabel = SchroderTreeUtils.getCurrentLabel(T);
			
			for(SchroderTree leaf : leaves) {
				BigInteger val = C.get(position);
				if(val.compareTo(BigInteger.ONE)!=0) {
					leaf.setisLeaf(false);
					leaf.setLabel(currentLabel+1);
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
	
	private static void substituteTreeRec(SchroderTree T, List<BigInteger> C, int label, int position) {
		List<SchroderTree> children = T.getChildren();
		if(T.isLeaf()) {
			BigInteger val = C.get(position);
			if(val.compareTo(BigInteger.ONE)==0) return;
			T.setLabel(label);
			T.setisLeaf(false);
						
			
			for(BigInteger i=BigInteger.ZERO; i.compareTo(val)<0; i=i.add(BigInteger.ONE)) {
				SchroderTree s = new SchroderTreeImpl();
				children.add(s);
			}
			return;
		}
		else {
			for(SchroderTree child : children) {
				substituteTreeRec(child, C, T.getLabel()+1, position++);
			}
		}
	}

	public static SchroderTree genArbre(int n){
		if(n == 1){
			return new SchroderTreeImpl();
		}
		if(n == 2){
			List<SchroderTree> children = new ArrayList<>();
			children.add(new SchroderTreeImpl());
			children.add(new SchroderTreeImpl());
			return new SchroderTreeImpl(false, 1, children);
		}
		BigInteger s = nextRandomBigInteger(coeffs[n]);
		SchroderTree t = genArbre(n-1);
		List<BigInteger> C = unrankComposition(n, n-1, s);
		substituteTree(t, C);
		return t;
	}
	
	public static BigInteger nextRandomBigInteger(BigInteger n) {
	    Random rand = new Random();
	    BigInteger result = new BigInteger(n.bitLength(), rand);
	    while( result.compareTo(n) >= 0 ) {
	        result = new BigInteger(n.bitLength(), rand);
	    }
	    return result;
	}
	
	
	

}
