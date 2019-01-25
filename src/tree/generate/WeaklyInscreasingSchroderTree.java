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
	private BigInteger[] coeffs;
	
	public WeaklyInscreasingSchroderTree(int nb_comptage) {
		coeffs = countCoeff(nb_comptage);
	}
	
	
	/*
	 * Fonction qui ne sert pas, calcul le nombre de Bell
	 * mais ne calcul pas le nombre ORDONNEE de Bell...
	 */
	public static BigInteger[] bellTriangle(int n) {
		BigInteger[] bell = new BigInteger[n+1];
		bell[0] = BigInteger.ZERO;
		bell[1] = BigInteger.ONE;
		bell[2] = BigInteger.ONE;
		BigInteger[] firstrow = new BigInteger[1];
		firstrow[0] = BigInteger.ONE;
		BigInteger[] secondrow = new BigInteger[2];
		secondrow[0] = BigInteger.ONE;
		BigInteger[] tmp;
		for(int i = 3; i < n+1; i++) {
			for(int j = 0; j < firstrow.length; j++) {
				secondrow[j+1] = firstrow[j].add(secondrow[j]);
				if(j == firstrow.length-1) {
					tmp = new BigInteger[secondrow.length+1];
					firstrow = secondrow;
					tmp[0] = secondrow[j+1];
					secondrow = tmp;
					bell[i] = tmp[0];
					break;
				}
			}
		}
		return bell;
	}
	
	
	public BigInteger[] countCoeff(int n) {
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
	
	public SchroderTree unrankTree(int n, BigInteger s) {
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
	

	public SchroderTree treeBuilder(int n){
		if(n == 1){
			return new SchroderTreeImpl();
		}
		List<SchroderTree> children = new ArrayList<>();
		children.add(new SchroderTreeImpl(true, 2, new ArrayList<>()));
		children.add(new SchroderTreeImpl(true, 2, new ArrayList<>()));
		SchroderTree t = new SchroderTreeImpl(false, 1, children);
		if(n == 2) {
			return t;
		}
		List<SchroderTree> leaves = new ArrayList<>(children);
		List<SchroderTree> tmp;
		SchroderTree lastadded = t;
		int l = 2;
		for(int i = 3; i <= n; i++) {
			BigInteger s = BigIntegerMath.binomial(i-1, i-2);
			BigInteger value = SchroderTreeUtils.nextRandomBigInteger(s.add(BigInteger.ONE));
			tmp = new ArrayList<>();
			if(value.compareTo(s) == 0) {
				lastadded.getChildren().add(new SchroderTreeImpl(true, l, new ArrayList<>()));
				leaves = SchroderTreeUtils.getLeaves(t);
			}else {
				List<BigInteger> C = unrankComposition(i, i-1, value);
				for(int j = 0; j < leaves.size(); j++) {
					BigInteger nb = C.get(j);
					if(nb.compareTo(BigInteger.ONE) != 0) {
						l++;
						SchroderTree tmptree = leaves.get(j);
						lastadded = tmptree;
						tmptree.setisLeaf(false);
						for(BigInteger k = BigInteger.ZERO; k.compareTo(nb) == -1; k = k.add(BigInteger.ONE)) {
							SchroderTree leaf = new SchroderTreeImpl();
							tmptree.getChildren().add(leaf);
							tmp.add(leaf);
							leaf.setLabel(l);
						}
					}else {
						leaves.get(j).setLabel(l);
						tmp.add(leaves.get(j));
					}
				}
				leaves = tmp;
			}
		}
		return t;
	}
	
	
	public BigInteger[] getComptage() {
		return coeffs;
	}

	public static void main(String[] args) {
		
		WeaklyInscreasingSchroderTree winst = new WeaklyInscreasingSchroderTree(1001);
		MakeGraph.makeGraph("tree/test9.dot", winst.treeBuilder(30));
		MakeGraph.makeGraph("tree/test10.dot", winst.unrankTree(30, 
				SchroderTreeUtils.nextRandomBigInteger(
						winst.getComptage()[30].subtract(BigInteger.ONE))));
		
	}


	
	

}
