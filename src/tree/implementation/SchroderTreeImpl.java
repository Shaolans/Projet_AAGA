package tree.implementation;

import java.util.ArrayList;
import java.util.List;

import tree.interfaces.SchroderTree;

public class SchroderTreeImpl implements SchroderTree {
	private int label;
	private List<SchroderTree> children;
	private boolean isLeaf;
	
	public SchroderTreeImpl() {
		this.isLeaf = false;
		this.label = -1;
		this.children = new ArrayList<>();
	}
	
	public SchroderTreeImpl(boolean isLeaf, int label, List<SchroderTree> children) {
		this.isLeaf = isLeaf;
		this.label = label;
		this.children = children;
	}
	
	
	@Override
	public int getLabel() {
		return label;
	}

	@Override
	public boolean isLeaf() {
		return isLeaf;
	}

	@Override
	public List<SchroderTree> getChildren() {
		return children;
	}


	@Override
	public void setLabel(int lbl) {
		label = lbl;
	}


	@Override
	public void setisLeaf(boolean b) {
		isLeaf = b;
	}

	@Override
	public String getUniqueId() {
		String name = super.toString();
		name = name.replaceAll("[.,@]", "");
		return name;
	}

}
