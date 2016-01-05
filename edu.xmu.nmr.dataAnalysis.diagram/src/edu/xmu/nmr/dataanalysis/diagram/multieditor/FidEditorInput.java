package edu.xmu.nmr.dataanalysis.diagram.multieditor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class FidEditorInput implements IEditorInput {

	private String name = "Fid";
	private String nodePath; // 记录FidNode的nodePath

	public FidEditorInput() {

	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

	public String getNodePath() {
		return nodePath;
	}

	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

	@Override
	public boolean exists() {
		return name != null && !name.trim().equals("");
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof FidEditorInput))
			return false;
		return ((FidEditorInput) obj).getNodePath().equals(getNodePath());
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return ImageDescriptor.getMissingImageDescriptor();
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
