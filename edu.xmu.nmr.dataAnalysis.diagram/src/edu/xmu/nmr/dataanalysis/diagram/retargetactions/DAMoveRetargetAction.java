package edu.xmu.nmr.dataanalysis.diagram.retargetactions;

import org.eclipse.jface.action.IAction;

import edu.xmu.nmr.dataanalysis.diagram.actions.helper.DAActionConstants;
import edu.xmu.nmr.dataanalysis.diagram.others.DASharedImages;

public class DAMoveRetargetAction extends DAAbstractRetargetAction {
    
    public DAMoveRetargetAction() {
        super(null, null, IAction.AS_CHECK_BOX);
        setId(DAActionConstants.DA_MOVE_IMG);
        setText("Move");
        setActionDefinitionId(DAActionConstants.DA_MOVE_IMG);
        setImageDescriptor(DASharedImages.MOVE_V);
    }
    
}
