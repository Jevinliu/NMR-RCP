package edu.xmu.nmrdataanalysis.diagram.model;

import java.util.Map.Entry;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import edu.xmu.nmr.dataanalysis.logic.explorermodel.ParamtersNode;

public class ParamsTCProvider implements IStructuredContentProvider {

	private static final Object[] NO_ELEMENTS = new Object[1];

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof ParamtersNode) {
			return ((ParamtersNode) inputElement).getParams().entrySet()
					.toArray(new Entry[] {});
		}
		return NO_ELEMENTS;
		// TreeMap<String, Object> elements = (TreeMap<String, Object>)
		// inputElement;
		// return elements.entrySet().toArray(new Entry[] {});
	}
}
