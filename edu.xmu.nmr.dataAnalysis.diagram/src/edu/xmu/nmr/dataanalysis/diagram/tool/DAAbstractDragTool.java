/**
 * @Title DAAbstractTool.java
 * @Package edu.xmu.nmr.dataanalysis.diagram.tool
 * @brief Tools的抽象类
 * Copyright Copyright (c) 2015
 * Wuhan Zhongke Magnetic Resonance
 * Technology Co., Ltd.
 *
 * @author Jevin
 * @date 2016年1月19日 上午8:39:40
 * @version V1.0
 */
package edu.xmu.nmr.dataanalysis.diagram.tool;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.Handle;
import org.eclipse.gef.tools.SelectionTool;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Cursor;

import edu.xmu.nmr.dataanalysis.diagram.editparts.FidContainerEditPart;

/**
 * DAAbstractTool
 * <p>
 * Tools的抽象类，该抽象类继承自SelectionTool，针对自己的特性进行重写
 * </p>
 * 
 * @see SelectionTool
 */
public abstract class DAAbstractDragTool extends SelectionTool {
    
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
    
    boolean isInDragInProgress() {
        return isInState(
                STATE_DRAG_IN_PROGRESS | STATE_ACCESSIBLE_DRAG_IN_PROGRESS);
    }
    
    protected void refreshCursor() {
        if (isActive())
            setCursor(calculateCursor());
    }
    
    protected Cursor calculateCursor() {
        updateTargetUnderMouse();
        EditPart tarEditPart = getTargetEditPart();
        if (tarEditPart instanceof FidContainerEditPart) {
            return getDefaultCursor();
        }
        return getDisabledCursor();
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
        performHandleDown();
        updateTargetUnderMouse();
        EditPart editpart = getTargetEditPart();
        if (editpart != null) {
            setDragTracker(editpart.getDragTracker(getTargetRequest()));
            lockTargetEditPart(editpart);
            return true;
        }
        return false;
    }
    
    public abstract void performHandleDown();
    
    @Override protected boolean handleDragStarted() {
        return stateTransition(STATE_DRAG, STATE_DRAG_IN_PROGRESS);
    }
    
    protected boolean handleDragInProgress() {
        if (isInDragInProgress()) {
            performDragInProgress();
        }
        return true;
    }
    
    public abstract void performDragInProgress();
    
    protected boolean handleButtonUp(int button) {
        if (stateTransition(STATE_DRAG | STATE_DRAG_IN_PROGRESS,
                STATE_TERMINAL)) {
            performHandleUp();
        }
        setState(STATE_TERMINAL);
        handleFinished();
        return true;
    }
    
    public abstract void performHandleUp();
    
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
}
