package edu.xmu.nmr.dataanalysis.diagram.tool;

import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.Handle;
import org.eclipse.gef.Request;
import org.eclipse.gef.tools.SelectionTool;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Cursor;

import edu.xmu.nmr.dataanalysis.diagram.editparts.FidContainerEditPart;
import edu.xmu.nmr.dataanalysis.diagram.requests.DAMoveRequest;
import edu.xmu.nmr.dataanalysis.diagram.requests.DARequestConstants;

public class DAMoveTool extends SelectionTool {
    
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
    
    @Override protected boolean handleButtonDown(int button) {
        if (!stateTransition(STATE_INITIAL, STATE_DRAG)) {
            resetHover();
            return true;
        }
        
        resetHover();
        EditPartViewer viewer = getCurrentViewer();
        Point p = getLocation();
        ((DAMoveRequest) getTargetRequest()).setDefaultLocation(p);
        if (getDragTracker() != null)
            getDragTracker().deactivate();
        
        if (viewer instanceof GraphicalViewer) {
            Handle handle = ((GraphicalViewer) viewer).findHandleAt(p);
            if (handle != null) {
                setDragTracker(handle.getDragTracker());
                return true;
            }
        }
        updateTargetRequest();
        updateTargetUnderMouse();
        EditPart editpart = getTargetEditPart();
        if (editpart != null) {
            setDragTracker(editpart.getDragTracker(getTargetRequest()));
            lockTargetEditPart(editpart);
            return true;
        }
        return false;
    }
    
    /**
     * 
     * 重写父类该方法，禁止获取<code>getDragTracker()</code>进行drag
     * 
     * @param me
     * @param viewer
     * @see org.eclipse.gef.tools.SelectionTool#mouseDrag(org.eclipse.swt.events.MouseEvent,
     *      org.eclipse.gef.EditPartViewer)
     */
    @Override public void mouseDrag(MouseEvent me, EditPartViewer viewer) {
        if (!isViewerImportant(viewer))
            return;
        setViewer(viewer);
        boolean wasDragging = movedPastThreshold();
        getCurrentInput().setInput(me);
        handleDrag();
        if (movedPastThreshold()) {
            if (!wasDragging)
                handleDragStarted();
            handleDragInProgress();
        }
    }
    
    @Override protected boolean handleDragStarted() {
        return stateTransition(STATE_DRAG, STATE_DRAG_IN_PROGRESS);
    }
    
    protected boolean handleDragInProgress() {
        if (isInDragInProgress()) {
            updateTargetRequest();
            setCurrentCommand(getCommand());
            performMove();
        }
        return true;
    }
    
    boolean isInDragInProgress() {
        return isInState(STATE_DRAG_IN_PROGRESS
                | STATE_ACCESSIBLE_DRAG_IN_PROGRESS);
    }
    
    protected boolean handleButtonUp(int button) {
        if (stateTransition(STATE_DRAG | STATE_DRAG_IN_PROGRESS, STATE_TERMINAL)) {
            unlockTargetEditPart();
            executeCurrentCommand();
        }
        setState(STATE_TERMINAL);
        handleFinished();
        return true;
    }
    
    /**
     * 
     * 重写该方法，防止在mouse up时，调用{@link EditDomain#getDefaultTool()}，使当前active tool
     * 失效
     * 
     * @see org.eclipse.gef.tools.AbstractTool#handleFinished()
     */
    @Override protected void handleFinished() {
        reactivate();
    }
    
    protected void performMove() {
        getCurrentCommand().execute();
    }
    
    @Override protected void eraseTargetFeedback() {
        return;
    }
    
    @Override protected void showTargetFeedback() {
        return;
    }
    
    @Override protected void showHoverFeedback() {
        return;
    }
    
    @Override protected void eraseHoverFeedback() {
        return;
    }
    
    protected void refreshCursor() {
        if (isActive())
            setCursor(calculateCursor());
    }
    
    protected boolean handleMove() {
        refreshCursor();
        if (stateTransition(STATE_ACCESSIBLE_DRAG, STATE_INITIAL))
            setDragTracker(null);
        if (isInState(STATE_INITIAL)) {
            updateTargetRequest();
            updateTargetUnderMouse();
            return true;
        } else if (isInState(STATE_TRAVERSE_HANDLE)) {
            EditPartViewer viewer = getCurrentViewer();
            if (viewer instanceof GraphicalViewer) {
                Handle handle = ((GraphicalViewer) viewer)
                        .findHandleAt(getLocation());
                if (handle != null) {
                    setState(STATE_ACCESSIBLE_DRAG);
                    setStartLocation(getLocation());
                    setDragTracker(handle.getDragTracker());
                    return true;
                } else {
                    setState(STATE_INITIAL);
                }
            }
        }
        return false;
    }
    
    protected Cursor calculateCursor() {
        updateTargetUnderMouse();
        EditPart tarEditPart = getTargetEditPart();
        if (tarEditPart instanceof FidContainerEditPart) {
            return getDefaultCursor();
        }
        return getDisabledCursor();
    }
    
}
