package edu.xmu.nmrdataanalysis.diagram.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.geometry.Rectangle;

public class FElement implements IAdaptable {

	private List<FElement> children;
	private FElement parent;
	private PropertyChangeSupport listeners;
	private Rectangle layout;

	public FElement() {
		this.parent = null;
		this.children = new ArrayList<FElement>();
		this.listeners = new PropertyChangeSupport(this);
	}

	public List<FElement> getChildren() {
		return children;
	}

	public void addChild(FElement child) {
		this.children.add(child);
	}

	public FElement getParent() {
		return parent;
	}

	public void setParent(FElement parent) {
		this.parent = parent;
		parent.addChild(this);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		listeners.addPropertyChangeListener(listener);
	}

	public PropertyChangeSupport getListeners() {
		return listeners;
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		listeners.removePropertyChangeListener(listener);
	}

	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

	public Rectangle getLayout() {
		return layout;
	}

	public void setLayout(Rectangle layout) {
		this.layout = layout;
	}
}
