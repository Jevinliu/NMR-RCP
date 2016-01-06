package edu.xmu.nmr.dataanalysis.diagram.editparts;

import java.beans.PropertyChangeEvent;

import org.eclipse.draw2d.IFigure;

import edu.xmu.nmr.dataanalysis.diagram.figures.BackgroundFigure;
import edu.xmu.nmrdataanalysis.diagram.model.Container;

public class BackgroundEditPart extends NMRAbstractEditPart {

	public BackgroundEditPart() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

	}

	@Override
	protected IFigure createFigure() {
		BackgroundFigure bf = new BackgroundFigure();
		return bf;
	}

	@Override
	protected void refreshVisuals() {
		BackgroundFigure figure = (BackgroundFigure) getFigure();
		Container container = (Container) getModel();
		figure.setLayout(container.getLayout());
	}

	@Override
	protected void createEditPolicies() {

	}

}
