package edu.xmu.nmr.dataanalysis.logic.explorermodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import edu.xmu.nmr.dataanalysis.logic.datacenter.FileType;

public class AbstractDataNode implements IEditorInput {

	private AbstractDataNode parent;
	private List<AbstractDataNode> children = new ArrayList<AbstractDataNode>();
	private String name;
	private String nodePath;
	private FileType fileType;
	private final PropertyChangeSupport listeners = new PropertyChangeSupport(
			this);

	public AbstractDataNode(String nodePath, FileType fileType, String name) {
		this.nodePath = nodePath;
		this.fileType = fileType;
		this.name = name;
	}

	public String getNodePath() {
		return nodePath;
	}

	public FileType getFileType() {
		return fileType;
	}

	@Override
	public String getName() {
		return name;
	}

	public String toString() {
		return nodePath;
	}

	public AbstractDataNode getParent() {
		return parent;
	}

	public void setParent(AbstractDataNode parent) {
		this.parent = parent;
	}

	public void removeChild(AbstractDataNode child) {
		if (children.contains(child)) {
			children.remove(child);
			child.setParent(null);
		}
	}

	public void removeChildren() {
		for (int i = 0; i < children.size(); i++) {
			children.get(i).setParent(null);
		}
		children.clear();
	}

	public boolean hasChildren() {
		return children.size() > 0;
	}

	public void addChild(AbstractDataNode child) {
		children.add(child);
		child.setParent(this);
	}

	public AbstractDataNode[] getChildren() {
		return (AbstractDataNode[]) children.toArray(new AbstractDataNode[] {});
	}

	public final void addPropertyChangeListener(PropertyChangeListener listener) {
		listeners.addPropertyChangeListener(listener);
	}

	public final void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		listeners.addPropertyChangeListener(propertyName, listener);
	}

	public final void removePropertyChangeListener(
			PropertyChangeListener listener) {
		listeners.removePropertyChangeListener(listener);
	}

	public final void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		listeners.removePropertyChangeListener(propertyName, listener);
	}

	final void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
		listeners.firePropertyChange(propertyName, oldValue, newValue);
	}

	public PropertyChangeSupport getListeners() {
		return listeners;
	}

	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

	@Override
	public boolean exists() {
		return this.name != null;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return ImageDescriptor.getMissingImageDescriptor();
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return getNodePath();
	}

	/**
	 * <p>
	 * 在作为IEditorInput时，使用该方法判断是否应该打开新的EditorPart
	 * </p>
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AbstractDataNode))
			return false;
		return ((AbstractDataNode) obj).getNodePath().equals(getNodePath());
	}
}
