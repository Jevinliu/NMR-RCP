package edu.xmu.nmr.dataanalysis.diagram.tool;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.Request;

import edu.xmu.nmr.dataanalysis.diagram.editparts.FidEditPart;
import edu.xmu.nmr.dataanalysis.diagram.requests.DAPartZoomInRequest;
import edu.xmu.nmr.dataanalysis.diagram.requests.DARequestConstants;

public class DAPartZoomInTool extends DAAbstractDragTool {
    
    /**
     * 选择框figure
     */
    private Figure selectionFigure;
    /**
     * 记录当前选中的figure，方便在此figure上完成addFeedback与removeFeedback的整套操作，该figure必须为
     * fid层的figure，
     */
    private Figure feedbackLayer;
    
    public DAPartZoomInTool() {
        setDefaultCursor(Cursors.HAND);
        setDisabledCursor(Cursors.ARROW);
    }
    
    @Override protected String getCommandName() {
        return DARequestConstants.DA_REQ_PART_ZOOM;
    }
    
    @Override protected Request createTargetRequest() {
        Request request = new DAPartZoomInRequest();
        request.setType(getCommandName());
        return request;
    }
    
    @Override protected void updateTargetRequest() {
        DAPartZoomInRequest request = (DAPartZoomInRequest) getTargetRequest();
        if (selectionFigure != null && selectionFigure.getBounds().width != 0) {
            request.setOffsetX(feedbackLayer.getBounds().x
                    - selectionFigure.getBounds().x);
            request.setHScale(feedbackLayer.getBounds().width
                    / (double) selectionFigure.getBounds().width);
        }
    }
    
    @Override protected boolean handleInvalidInput() {
        eraseSelectionFeedback();
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
    
    /**
     * 显示选择的回显
     */
    private void showSelectionFeedback() {
        feedbackLayer = getFeedback();
        if (feedbackLayer == null) {
            return;
        }
        Rectangle rect = getSelectionRectangle();
        IFigure selectionFigure = getSelectionFeedbackFigure();
        selectionFigure.translateToRelative(rect);
        rect.y = feedbackLayer.getBounds().y + 2;
        rect.height = feedbackLayer.getBounds().height - 4;
        int maxX = feedbackLayer.getBounds().x
                + feedbackLayer.getBounds().width;
        if (rect.x + rect.width > maxX)
            rect.width = maxX - rect.x - 1;
        if (rect.x < feedbackLayer.getBounds().x) {
            rect.width = rect.x + rect.width - feedbackLayer.getBounds().x;
            rect.x = feedbackLayer.getBounds().x;
        }
        selectionFigure.setBounds(rect);
        selectionFigure.validate();
    }
    
    @Override protected void addFeedback(IFigure figure) {
        if (feedbackLayer != null) {
            feedbackLayer.add(figure);
        }
    }
    
    private void eraseSelectionFeedback() {
        if (selectionFigure != null) {
            removeFeedback(selectionFigure);
            selectionFigure = null;
        }
    }
    
    @Override protected void removeFeedback(IFigure figure) {
        if (feedbackLayer != null) {
            feedbackLayer.remove(figure);
        }
    }
    
    private Figure getFeedback() {
        EditPart editPart = getTargetEditPart();
        if (editPart instanceof FidEditPart) {
            return (Figure) ((FidEditPart) editPart)
                    .getLayer(LayerConstants.FEEDBACK_LAYER);
        }
        return null;
    }
    
    private Rectangle getSelectionRectangle() {
        if (getStartLocation().x > getLocation().x) {
            return new Rectangle(getLocation(), getStartLocation());
        }
        return new Rectangle(getStartLocation(), getLocation());
    }
    
    @Override public void deactivate() {
        feedbackLayer = null;
        super.deactivate();
    }
    
    @Override public void performHandleDown() {
    
    }
    
    @Override public void performDragInProgress() {
        showSelectionFeedback();
    }
    
    @Override public void performHandleUp() {
        updateTargetRequest();
        unlockTargetEditPart();
        eraseSelectionFeedback();
        setCurrentCommand(getCommand());
        executeCurrentCommand();
        
    }
    
}
