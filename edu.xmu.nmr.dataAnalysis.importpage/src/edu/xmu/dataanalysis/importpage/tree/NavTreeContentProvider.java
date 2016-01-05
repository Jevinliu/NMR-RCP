package edu.xmu.dataanalysis.importpage.tree;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class NavTreeContentProvider implements IStructuredContentProvider,
		ITreeContentProvider {

	public TreeParent invisibleRoot;

	public NavTreeContentProvider() {
		initialize();
	}

	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	}

	public void dispose() {
	}

	public Object[] getElements(Object parent) {
		if (invisibleRoot == null)
			initialize();
		return getChildren(invisibleRoot);
	}

	public Object getParent(Object child) {
		if (child instanceof TreeObject) {
			return ((TreeObject) child).getParent();
		}
		return null;
	}

	public Object[] getChildren(Object parent) {
		if (parent instanceof TreeParent) {
			return ((TreeParent) parent).getChildren();
		}
		return new Object[0];
	}

	public boolean hasChildren(Object parent) {
		if (parent instanceof TreeParent)
			return ((TreeParent) parent).hasChildren();
		return false;
	}

	/*
	 * We will set up a dummy model to initialize tree heararchy. In a real
	 * code, you will connect to a real model and expose its hierarchy.
	 */
	public void initialize() {
		invisibleRoot = new TreeParent("");
	}

	public void removeItems(Object element) {
		if (element instanceof TreeParent) {
			TreeParent parent = ((TreeParent) element).getParent();
			parent.removeChild((TreeObject) element);
		} else {
			TreeParent parent = ((TreeObject) element).getParent();
			parent.removeChild((TreeObject) element);
		}
	}

	public void clear() {
		if (invisibleRoot.getChildren().length != 0) {
			for (TreeObject to : invisibleRoot.getChildren()) {
				invisibleRoot.removeChild(to);
			}
		}
	}
}