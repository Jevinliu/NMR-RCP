package edu.xmu.dataanalysis.explorer.model;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import edu.xmu.nmr.dataanalysis.logic.explorermodel.AbstractDataNode;

public class ExplorerContentProvider implements ITreeContentProvider,
		IStructuredContentProvider {

	private static final Object[] NO_CHILDREN = new Object[0];

	// private FolderNode trRoot;

	public ExplorerContentProvider() {

	}

	// private void init() {
	// trRoot = NMRDataCenter.getInstance().getNmrData();
	// }

	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getElements(Object inputElement) {
		// if (trRoot == null) {
		// init();
		// }
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof AbstractDataNode) {
			if (((AbstractDataNode) parentElement).hasChildren()) {
				return ((AbstractDataNode) parentElement).getChildren();
			}
		}
		return NO_CHILDREN;
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof AbstractDataNode) {
			return ((AbstractDataNode) element).getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof AbstractDataNode) {
			return ((AbstractDataNode) element).hasChildren();
		}
		return false;
	}

}
