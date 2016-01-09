package edu.xmu.nmr.dataanalysis.diagram.editparts;

import java.beans.PropertyChangeEvent;

import org.eclipse.draw2d.IFigure;

import edu.xmu.nmr.dataanalysis.diagram.figures.RulerFigure;
import edu.xmu.nmrdataanalysis.diagram.model.Ruler;

public class RulerEditPart extends DAAbstractEditPart {

	public RulerEditPart() {
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String[] eventsName = new String[] { Ruler.PRO_RULER_STEPSIZE,
				Ruler.PRO_RULER_INTERVAL, Ruler.PRO_RULER_TOTALSIZE };
		for (String eventName : eventsName) {
			if (evt.getPropertyName().equals(eventName)) {
				refreshVisuals();
				return;
			}
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
		figure.setGridLayout();
		figure.setOrient(ruler.getOrient());
		figure.setInterval((int) ruler.getInterval());
		figure.setTotalSize(ruler.getTotalSize());
		// figure.setStepSize(ruler.getStepSize());
		// figure.setLayout(ruler.getLayout());

	}

	@Override
	protected void createEditPolicies() {

	}

}
