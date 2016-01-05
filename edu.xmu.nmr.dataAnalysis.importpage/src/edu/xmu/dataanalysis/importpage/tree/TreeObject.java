package edu.xmu.dataanalysis.importpage.tree;


public class TreeObject {

	private String name;
	private TreeParent parent;
	private boolean isChecked = false; // 记录树节点的checked状态

	public TreeObject(String name) {
		this.name = name;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public String getName() {
		return name;
	}

	public void setParent(TreeParent parent) {
		this.parent = parent;
	}

	public TreeParent getParent() {
		return parent;
	}

	public String toString() {
		return getName();
	}
}
