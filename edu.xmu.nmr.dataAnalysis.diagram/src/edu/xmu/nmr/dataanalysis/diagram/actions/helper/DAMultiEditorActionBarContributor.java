package edu.xmu.nmr.dataanalysis.diagram.actions.helper;

import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.actions.ActionFactory;

import edu.xmu.nmr.dataanalysis.diagram.retargetactions.DAMoveRetargetAction;
import edu.xmu.nmr.dataanalysis.diagram.retargetactions.DAPartZoomInRetargetAction;
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
        DAMoveRetargetAction moveAction = new DAMoveRetargetAction();
        addRetargetAction(moveAction);
        DAPartZoomInRetargetAction partAction = new DAPartZoomInRetargetAction();
        partAction.addListener(moveAction);
        moveAction.addListener(partAction);
        addRetargetAction(partAction);
        addRetargetAction(new FFTRetargetAction());
        
        addRetargetAction(new ShowFullRetargetAction());
    }
    
    @Override public void declareGlobalActionKeys() {
    
    }
    
    @Override public void contributeToToolBar(IToolBarManager toolBarManager) {
        toolBarManager.add(getAction(ActionFactory.UNDO.getId()));
        toolBarManager.add(getAction(ActionFactory.REDO.getId()));
        toolBarManager.add(getAction(DAActionConstants.DA_ZOOM_IN));
        toolBarManager.add(getAction(DAActionConstants.DA_ZOOM_OUT));
        toolBarManager.add(getAction(DAActionConstants.DA_MOVE_IMG));
        toolBarManager.add(getAction(DAActionConstants.DA_PART_ZOOM_IN));
        toolBarManager.add(getAction(DAActionConstants.DA_FFT));
        toolBarManager.add(getAction(DAActionConstants.DA_SHOW_FULL));
    }
}
