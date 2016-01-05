package edu.xmu.dataanalysis.importpage.tree;

import java.util.ArrayList;

public class TreeParent extends TreeObject {

	private ArrayList<TreeObject> children;

	public TreeParent(String name) {
		super(name);
		children = new ArrayList<TreeObject>();
	}

	public void addChild(TreeObject child) {
		children.add(child);
		child.setParent(this);
	}

	public void removeChild(TreeObject child) {
		children.remove(child);
		child.setParent(null);
	}

	public void removeChildren() {
		for (int i = 0; i < children.size(); i++) {
			((TreeObject) children.get(i)).setParent(null);
		}
		children.clear();
	}

	public TreeObject[] getChildren() {
		return (TreeObject[]) children.toArray(new TreeObject[] {});
	}

	public boolean hasChildren() {
		return children.size() > 0;
	}
}