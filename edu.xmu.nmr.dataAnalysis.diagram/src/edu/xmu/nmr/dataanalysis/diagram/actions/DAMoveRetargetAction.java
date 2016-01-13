package edu.xmu.nmr.dataanalysis.diagram.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.actions.RetargetAction;

import edu.xmu.nmr.dataanalysis.diagram.actions.helper.DAActionConstants;

public class DAMoveRetargetAction extends RetargetAction {
    
    public DAMoveRetargetAction() {
        super(null, null, IAction.AS_CHECK_BOX);
        setId(DAActionConstants.DA_MOVE_IMG);
        setText("Move");
        setActionDefinitionId(DAActionConstants.DA_MOVE_IMG);
    }
    
}
