package edu.xmu.nmr.dataanalysis.diagram.commands;

import org.eclipse.gef.commands.Command;

import edu.xmu.nmrdataanalysis.diagram.model.FidData;

/**
 * DAPartZoomIn
 * <p>
 * 局部放大{@link Command}
 * </p>
 * 
 * @see
 */
public class DAPartZoomInCommand extends Command {
    
    private FidData model;
    /**
     * 选中拖动时的起点，
     */
    private int startX;
    /**
     * 选中拖动时的终点，可能包括选中过程中和结束时的终点
     */
    private int endX;
    
    private int oldStartX;
    private int oldEndX;
    
    public void setModel(Object model) {
        this.model = (FidData) model;
    }
    
    public void setModel(FidData model) {
        this.model = model;
    }
    
    public void setStartX(int startX) {
        this.oldStartX = this.startX;
        this.startX = startX;
    }
    
    public void setEndX(int endX) {
        this.oldEndX = this.endX;
        this.endX = endX;
    }
    
    @Override public void execute() {
        this.model.setZoomStartAndEndX(startX, endX);
    }
    
    @Override public void undo() {
        this.model.setZoomStartAndEndX(oldStartX, oldEndX);
    }
}
