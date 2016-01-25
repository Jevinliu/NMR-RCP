package edu.xmu.nmr.dataanalysis.diagram.tool;

import org.eclipse.draw2d.Cursors;
import org.eclipse.gef.Request;

import edu.xmu.nmr.dataanalysis.diagram.others.DA;
import edu.xmu.nmr.dataanalysis.diagram.requests.DAMoveRequest;
import edu.xmu.nmr.dataanalysis.diagram.requests.DARequestConstants;

public class DAMoveTool extends DAAbstractDragTool {
    
    private int type = -1;
    
    public DAMoveTool() {
        setDefaultCursor(Cursors.HAND);
        setDisabledCursor(Cursors.ARROW);
    }
    
    @Override protected String getCommandName() {
        if (type == DA.HORIZONTAL) {
            return DARequestConstants.DA_REQ_MOVE_H_IMG;
        } else {
            return DARequestConstants.DA_REQ_MOVE_V_IMG;
        }
    }
    
    /**
     * 该MoveTool对应的 移动类型，该值只能为<code>DA.VERTICAL</code>或
     * <code>DA.HORIZONTAL</code>.
     * 
     * @param type
     *            如果为<code>DA.VERTICAL</code>表示为竖直方向移动，反之如果为
     *            <code>DA.HORIZONTAL</code>则为水平方向移动。
     */
    public void setType(int type) {
        if (type != DA.HORIZONTAL && type != DA.VERTICAL) {
            throw new IllegalArgumentException(
                    "Type must be 'DA.VERTICAL' or 'DA.HORIZONTAL'");
        }
        this.type = type;
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
