package edu.xmu.nmr.dataanalysis.diagram.actions;

import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.actions.ActionFactory;

import edu.xmu.nmr.dataanalysis.diagram.actions.helper.DAActionConstants;

public class DAMultiEditorActionBarContributor extends
        AbstractMultiActionBarContributor {
    
    public DAMultiEditorActionBarContributor() {
    }
    
    @Override public void buildActions() {
        addRetargetAction(new UndoRetargetAction());
        addRetargetAction(new RedoRetargetAction());
        addRetargetAction(new DAZoomInRetargetAction());
        addRetargetAction(new DAZoomOutRetargetAction());
        addRetargetAction(new DAMoveRetargetAction());
    }
    
    @Override public void declareGlobalActionKeys() {
        
    }
    
    @Override public void contributeToToolBar(IToolBarManager toolBarManager) {
        toolBarManager.add(getAction(ActionFactory.UNDO.getId()));
        toolBarManager.add(getAction(ActionFactory.REDO.getId()));
        toolBarManager.add(getAction(DAActionConstants.ZOOM_IN));
        toolBarManager.add(getAction(DAActionConstants.ZOOM_OUT));
        toolBarManager.add(getAction(DAActionConstants.DA_MOVE_IMG));
    }
}
