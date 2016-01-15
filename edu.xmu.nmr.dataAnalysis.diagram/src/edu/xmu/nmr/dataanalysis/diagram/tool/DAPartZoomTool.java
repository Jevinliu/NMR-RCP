package edu.xmu.nmr.dataanalysis.diagram.tool;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.Handle;
import org.eclipse.gef.Request;
import org.eclipse.gef.tools.SelectionTool;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Cursor;

import edu.xmu.nmr.dataanalysis.diagram.editparts.FidContainerEditPart;
import edu.xmu.nmr.dataanalysis.diagram.figures.LineFigure;
import edu.xmu.nmr.dataanalysis.diagram.requests.DAPartZoomRequest;
import edu.xmu.nmr.dataanalysis.diagram.requests.DARequestConstants;

public class DAPartZoomTool extends SelectionTool {
    
    /**
     * 选择框figure
     */
    private Figure selectionFigure;
    /**
     * 记录当前选中的figure，方便在此figure上完成addFeedback与removeFeedback的整套操作，该figure必须为
     * fid层的figure，
     */
    private Figure fidFigure;
    
    public DAPartZoomTool() {
        setDefaultCursor(Cursors.HAND);
        setDisabledCursor(Cursors.ARROW);
    }
    
    @Override protected String getCommandName() {
        return DARequestConstants.DA_REQ_PART_ZOOM;
    }
    
    @Override protected Request createTargetRequest() {
        Request request = new DAPartZoomRequest();
        request.setType(getCommandName());
        return request;
    }
    
    @Override protected void updateTargetRequest() {
        DAPartZoomRequest request = (DAPartZoomRequest) getTargetRequest();
        if (selectionFigure != null) {
            request.setOffsetX(
                    fidFigure.getBounds().x - selectionFigure.getBounds().x);
            request.setHScale(fidFigure.getBounds().width
                    / (double) selectionFigure.getBounds().width);
        }
    }
    
    @Override protected boolean handleButtonDown(int button) {
        if (button != 1) {
            setState(STATE_INVALID);
            handleInvalidInput();
        }
        if (!stateTransition(STATE_INITIAL, STATE_DRAG)) {
            resetHover();
            return true;
        }
        
        resetHover();
        EditPartViewer viewer = getCurrentViewer();
        Point p = getLocation();
        if (getDragTracker() != null)
            getDragTracker().deactivate();
            
        if (viewer instanceof GraphicalViewer) {
            Handle handle = ((GraphicalViewer) viewer).findHandleAt(p);
            if (handle != null) {
                setDragTracker(handle.getDragTracker());
                return true;
            }
        }
        updateTargetUnderMouse();
        EditPart editpart = getTargetEditPart();
        if (editpart != null) {
            setDragTracker(editpart.getDragTracker(getTargetRequest()));
            lockTargetEditPart(editpart);
            return true;
        }
        return false;
    }
    
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
    
    @Override protected boolean handleInvalidInput() {
        eraseSelectionFeedback();
        return true;
    }
    
    @Override protected boolean handleDragStarted() {
        return stateTransition(STATE_DRAG, STATE_DRAG_IN_PROGRESS);
    }
    
    boolean isInDragInProgress() {
        return isInState(
                STATE_DRAG_IN_PROGRESS | STATE_ACCESSIBLE_DRAG_IN_PROGRESS);
    }
    
    protected boolean handleDragInProgress() {
        if (isInDragInProgress()) {
            showSelectionFeedback();
        }
        return true;
    }
    
    private IFigure getSelectionFeedbackFigure() {
        if (selectionFigure == null) {
            selectionFigure = new RectangleFigure();
            selectionFigure.setBorder(new LineBorder(ColorConstants.red, 1));
            selectionFigure.setBackgroundColor(ColorConstants.yellow);
            ((RectangleFigure) selectionFigure).setAlpha(128);
            addFeedback(selectionFigure);
        }
        return selectionFigure;
    }
    
    private void showSelectionFeedback() {
        fidFigure = getFidFigure();
        if (fidFigure == null) {
            return;
        }
        Rectangle rect = getSelectionRectangle();
        IFigure selectionFigure = getSelectionFeedbackFigure();
        selectionFigure.translateToRelative(rect);
        rect.y = fidFigure.getBounds().y + 2;
        rect.height = fidFigure.getBounds().height - 4;
        int maxX = fidFigure.getBounds().x + fidFigure.getBounds().width;
        if (rect.x + rect.width > maxX)
            rect.width = maxX - rect.x - 1;
        selectionFigure.setBounds(rect);
        selectionFigure.validate();
    }
    
    @Override protected void addFeedback(IFigure figure) {
        if (fidFigure != null) {
            fidFigure.add(figure);
        }
    }
    
    private void eraseSelectionFeedback() {
        if (selectionFigure != null) {
            removeFeedback(selectionFigure);
            selectionFigure = null;
        }
    }
    
    @Override protected void removeFeedback(IFigure figure) {
        if (fidFigure != null) {
            fidFigure.remove(figure);
        }
    }
    
    private Figure getFidFigure() {
        EditPart editPart = getTargetEditPart();
        if (editPart instanceof FidContainerEditPart) {
            for (Object f : ((FidContainerEditPart) editPart).getFigure()
                    .getChildren()) {
                if (f instanceof LineFigure) {
                    return (Figure) f;
                }
            }
        }
        return null;
    }
    
    private Rectangle getSelectionRectangle() {
        return new Rectangle(getStartLocation(), getLocation());
    }
    
    protected boolean handleButtonUp(int button) {
        if (stateTransition(STATE_DRAG | STATE_DRAG_IN_PROGRESS,
                STATE_TERMINAL)) {
            updateTargetRequest();
            unlockTargetEditPart();
            eraseSelectionFeedback();
            setCurrentCommand(getCommand());
            performSelect();
        }
        setState(STATE_TERMINAL);
        handleFinished();
        return true;
    }
    
    private void performSelect() {
        executeCurrentCommand();
    }
    
    @Override protected void handleFinished() {
        reactivate();
    }
    
    @Override public void deactivate() {
        fidFigure = null;
        super.deactivate();
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
