package edu.xmu.nmr.dataanalysis.diagram.editparts;

import java.beans.PropertyChangeEvent;

import org.eclipse.draw2d.IFigure;

import edu.xmu.nmr.dataanalysis.diagram.figures.RulerFigure;
import edu.xmu.nmrdataanalysis.diagram.model.FElement;
import edu.xmu.nmrdataanalysis.diagram.model.Ruler;

public class RulerEditPart extends NMRAbstractEditPart {

	public RulerEditPart() {
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(Ruler.PRO_RULER_STEPSIZE)
				|| evt.getPropertyName().equals(Ruler.PRO_RULER_INTERVAL)) {
			refreshVisuals();
		}
		if (evt.getPropertyName().equals(FElement.NMR_PRO_LAYOUT)) {
			refreshVisuals();
		}
	}

	@Override
	protected IFigure createFigure() {
		IFigure rulerFigure = new RulerFigure();
		return rulerFigure;
	}

	@Override
	protected void refreshVisuals() {
		RulerFigure figure = (RulerFigure) getFigure();
		Ruler ruler = (Ruler) getModel();
		figure.setOrient(ruler.getOrient());
		figure.setInterval((int) ruler.getInterval());
		figure.setStepSize(ruler.getStepSize());
		figure.setLayout(ruler.getLayout());
	}

	@Override
	protected void createEditPolicies() {

	}

}
