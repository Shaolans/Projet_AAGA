package tree.generate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tree.graph.MakeGraph;
import tree.implementation.SchroderTreeImpl;
import tree.interfaces.SchroderTree;

public class InscreasingSchroderTree {
	
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
	
	public static void main(String[] args) {
		MakeGraph.makeGraph("tree/test4.dot", treeBuilder(30));
	}
	
}
