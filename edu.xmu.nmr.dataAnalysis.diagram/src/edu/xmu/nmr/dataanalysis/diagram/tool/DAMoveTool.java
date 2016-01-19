package edu.xmu.nmr.dataanalysis.diagram.tool;

import org.eclipse.draw2d.Cursors;
import org.eclipse.gef.Request;

import edu.xmu.nmr.dataanalysis.diagram.requests.DAMoveRequest;
import edu.xmu.nmr.dataanalysis.diagram.requests.DARequestConstants;

public class DAMoveTool extends DAAbstractDragTool {
    
    public DAMoveTool() {
        setDefaultCursor(Cursors.HAND);
        setDisabledCursor(Cursors.ARROW);
    }
    
    @Override protected String getCommandName() {
        return DARequestConstants.DA_REQ_MOVE_IMG;
    }
    
    protected Request createTargetRequest() {
        Request request = new DAMoveRequest();
        request.setType(getCommandName());
        return request;
    }
    
    @Override protected void updateTargetRequest() {
        DAMoveRequest request = (DAMoveRequest) getTargetRequest();
        request.setStartLocation(request.getCurrentLocation());
        request.setCurrentLocation(getLocation());
    }
    
    // @Override protected void eraseTargetFeedback() {
    // return;
    // }
    //
    // @Override protected void showTargetFeedback() {
    // return;
    // }
    //
    // @Override protected void showHoverFeedback() {
    // return;
    // }
    //
    // @Override protected void eraseHoverFeedback() {
    // return;
    // }
    
    @Override public void performHandleDown() {
        ((DAMoveRequest) getTargetRequest()).setDefaultLocation(getLocation());
        updateTargetUnderMouse();
    }
    
    @Override public void performDragInProgress() {
        updateTargetRequest();
        setCurrentCommand(getCommand());
        if (getCurrentCommand() != null)
            getCurrentCommand().execute();
    }
    
    @Override public void performHandleUp() {
        unlockTargetEditPart();
        executeCurrentCommand();
    }
    
}
