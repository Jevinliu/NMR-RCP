package edu.xmu.nmr.dataanalysis.diagram.editpolicys;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import edu.xmu.nmr.dataanalysis.diagram.commands.ContainerChangeLayoutCommand;
import edu.xmu.nmr.dataanalysis.diagram.commands.DAAbstractLayoutCommand;
import edu.xmu.nmr.dataanalysis.diagram.editparts.FidContainerEditPart;

public class EditLayoutPolicy extends XYLayoutEditPolicy {

	public EditLayoutPolicy() {
	}

	@Override
	protected Command createChangeConstraintCommand(
			ChangeBoundsRequest request, EditPart child, Object constraint) {
		DAAbstractLayoutCommand command = null;
		if (child instanceof FidContainerEditPart) {
			command = new ContainerChangeLayoutCommand();
		}
		if (command != null) {
			command.setModel(child.getModel());
			command.setConstraint((Rectangle) constraint);
		}

		return command;
	}

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
