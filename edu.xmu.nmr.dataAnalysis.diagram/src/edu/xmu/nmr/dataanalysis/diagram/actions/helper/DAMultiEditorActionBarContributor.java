package edu.xmu.nmr.dataanalysis.diagram.actions.helper;

import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.actions.ActionFactory;

import edu.xmu.nmr.dataanalysis.diagram.retargetactions.DAPartZoomInRetargetAction;
import edu.xmu.nmr.dataanalysis.diagram.retargetactions.DAXMoveRetargetAction;
import edu.xmu.nmr.dataanalysis.diagram.retargetactions.DAYMoveRetargetAction;
import edu.xmu.nmr.dataanalysis.diagram.retargetactions.DAZoomInRetargetAction;
import edu.xmu.nmr.dataanalysis.diagram.retargetactions.DAZoomOutRetargetAction;
import edu.xmu.nmr.dataanalysis.diagram.retargetactions.FFTRetargetAction;
import edu.xmu.nmr.dataanalysis.diagram.retargetactions.ShowFullRetargetAction;

public class DAMultiEditorActionBarContributor
        extends AbstractMultiActionBarContributor {
        
    public DAMultiEditorActionBarContributor() {
    }
    
    @Override public void buildActions() {
        addRetargetAction(new UndoRetargetAction());
        addRetargetAction(new RedoRetargetAction());
        
        addRetargetAction(new DAZoomInRetargetAction());
        addRetargetAction(new DAZoomOutRetargetAction());
        
        DAYMoveRetargetAction yMoveAction = new DAYMoveRetargetAction();
        addRetargetAction(yMoveAction);
        
        DAXMoveRetargetAction xMoveAction = new DAXMoveRetargetAction();
        addRetargetAction(xMoveAction);
        
        DAPartZoomInRetargetAction partAction = new DAPartZoomInRetargetAction();
        partAction.addListener(yMoveAction);
        partAction.addListener(xMoveAction);
        yMoveAction.addListener(partAction);
        yMoveAction.addListener(xMoveAction);
        xMoveAction.addListener(partAction);
        xMoveAction.addListener(yMoveAction);
        addRetargetAction(partAction);
        
        addRetargetAction(new ShowFullRetargetAction());
        addRetargetAction(new FFTRetargetAction());
    }
    
    @Override public void declareGlobalActionKeys() {
    
    }
    
    @Override public void contributeToToolBar(IToolBarManager toolBarManager) {
        toolBarManager.add(getAction(ActionFactory.UNDO.getId()));
        toolBarManager.add(getAction(ActionFactory.REDO.getId()));
        toolBarManager.add(getAction(DAActionConstants.DA_ZOOM_IN));
        toolBarManager.add(getAction(DAActionConstants.DA_ZOOM_OUT));
        toolBarManager.add(getAction(DAActionConstants.DA_MOVE_V_IMG));
        toolBarManager.add(getAction(DAActionConstants.DA_MOVE_H_IMG));
        toolBarManager.add(getAction(DAActionConstants.DA_PART_ZOOM_IN));
        toolBarManager.add(getAction(DAActionConstants.DA_SHOW_FULL));
        toolBarManager.add(getAction(DAActionConstants.DA_FFT));
    }
}
