package tree.generate;

import java.math.BigInteger;
import java.util.Arrays;

import org.apache.commons.math3.util.CombinatoricsUtils;

import tree.implementation.SchroderTreeImpl;
import tree.interfaces.SchroderTree;

public class WeaklyInscreasingSchroderTree {

	public static void main(String[] args) {
		Arrays.stream(countCoeff(20)).forEach(x -> System.out.println(x));

	}
	
	public static BigInteger[] countCoeff(int n) {
		BigInteger count[] = new BigInteger[n];
		count[0] = BigInteger.ZERO;
		count[1] = BigInteger.ONE;
		BigInteger tmp;
		for(int i = 2; i < n; i++) {
			tmp = BigInteger.ZERO;
			for(int k = 1; k < i; k++) {
				tmp = tmp.add(new BigInteger(CombinatoricsUtils.binomialCoefficient(i-1, k-1)+"").multiply(count[k]));
			}
			count[i] = tmp;
		}
		return count;
	}
	
	public static SchroderTree unrankTree(int n, int s) {
		if(n == 1) return new SchroderTreeImpl();
		BigInteger[] coeffs = countCoeff(n);
		int k = n;
		int r = s;
		while(r >= 0) {
			k--;
			r -= CombinatoricsUtils.binomialCoefficient(n-1, k-1)*coeffs[k].intValue();
		}
		r += CombinatoricsUtils.binomialCoefficient(n-1, k-1)*coeffs[k].intValue();
		int sp = r%coeffs[k].intValue();
		SchroderTree t = unrankTree(k, sp);
		
		return null;
	}
	

}
