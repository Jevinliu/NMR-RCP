package edu.xmu.nmr.dataanalysis.diagram.multieditor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class ParamsEditorInput implements IEditorInput {

	private String name = "Paramster";
	private String nodePath;

	public ParamsEditorInput() {
	}

	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

	@Override
	public boolean exists() {
		return name != null && !name.trim().equals("");
	}

	public String getNodePath() {
		return nodePath;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ParamsEditorInput))
			return false;
		return ((ParamsEditorInput) obj).getNodePath().equals(getNodePath());
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return nodePath;
	}

}
