package edu.xmu.nmr.dataanalysis.diagram.actions;

import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.gef.ui.actions.ZoomInRetargetAction;
import org.eclipse.gef.ui.actions.ZoomOutRetargetAction;
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
        addRetargetAction(new ZoomInRetargetAction());
        addRetargetAction(new ZoomOutRetargetAction());
        addRetargetAction(new DAMoveRetargetAction());
    }
    
    @Override public void declareGlobalActionKeys() {
        
    }
    
    @Override public void contributeToToolBar(IToolBarManager toolBarManager) {
        toolBarManager.add(getAction(ActionFactory.UNDO.getId()));
        toolBarManager.add(getAction(ActionFactory.REDO.getId()));
        toolBarManager.add(getAction(GEFActionConstants.ZOOM_IN));
        toolBarManager.add(getAction(GEFActionConstants.ZOOM_OUT));
        toolBarManager.add(getAction(DAActionConstants.DA_MOVE_IMG));
    }
}
