package edu.xmu.nmr.dataanalysis.diagram.editparts;

import java.beans.PropertyChangeListener;

import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import edu.xmu.nmrdataanalysis.diagram.model.FElement;

public abstract class DAAbstractEditPart extends AbstractGraphicalEditPart
		implements PropertyChangeListener {

	public DAAbstractEditPart() {

	}

	@Override
	public void activate() {
		super.activate();
		((FElement) getModel()).addPropertyChangeListener(this);
	}

	@Override
	public void deactivate() {
		super.deactivate();
		((FElement) getModel()).addPropertyChangeListener(this);
	}
}
