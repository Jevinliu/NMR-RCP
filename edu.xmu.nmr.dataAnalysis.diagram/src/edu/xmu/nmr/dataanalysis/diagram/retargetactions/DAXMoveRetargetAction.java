package edu.xmu.nmr.dataanalysis.diagram.retargetactions;

import org.eclipse.jface.action.IAction;

import edu.xmu.nmr.dataanalysis.diagram.actions.helper.DAActionConstants;
import edu.xmu.nmr.dataanalysis.diagram.others.DASharedImages;

public class DAXMoveRetargetAction extends DAAbstractRetargetAction {
    
    public DAXMoveRetargetAction() {
        super(null, null, IAction.AS_CHECK_BOX);
        setId(DAActionConstants.DA_MOVE_H_IMG);
        setText("Move Horizontal");
        setActionDefinitionId(DAActionConstants.DA_MOVE_H_IMG);
        setImageDescriptor(DASharedImages.MOVE_H);
    }
}
