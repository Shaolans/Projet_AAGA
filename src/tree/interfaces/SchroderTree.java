package tree.interfaces;

import java.util.List;

public interface SchroderTree {
	public int getLabel();
	public boolean isLeaf();
	public List<SchroderTree> getChildren();
	public void setLabel(int lbl);
	public void setisLeaf(boolean b);
	public String getUniqueId();
}
