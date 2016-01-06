package edu.xmu.nmr.dataanalysis.diagram.editparts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import edu.xmu.nmrdataanalysis.diagram.model.Container;
import edu.xmu.nmrdataanalysis.diagram.model.FidData;
import edu.xmu.nmrdataanalysis.diagram.model.Ruler;

public class NMREditPartFactory implements EditPartFactory {

	public NMREditPartFactory() {
	}

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		NMRAbstractEditPart part = null;
		if (model instanceof Container) {
			switch (((Container) model).getcType()) {
			case WORKSPACE:
				part = new WorkspaceEditPart();
				break;
			case FIDCONTAINER:
				part = new FidContainerEditPart();
				break;
			case BACKGROUND:
				part = new BackgroundEditPart();
				break;
			}
		} else if (model instanceof FidData) {
			part = new FidEditPart();
		} else if (model instanceof Ruler) {
			part = new RulerEditPart();
		}
		if (part != null) {
			part.setModel(model);
		}
		return part;
	}

}
