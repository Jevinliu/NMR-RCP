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
     * 要追加的水平偏移量,该偏移量是在figure上一个缩放比例下的偏移量，新的偏移量要根据追加的放大倍数进行计算
     */
    private int offsetX;
    
    /**
     * 要追加的水平缩放比例
     */
    private double hScale;
    
    public void setModel(FidData model) {
        this.model = model;
    }
    
    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }
    
    public void setHScale(double hScale) {
        this.hScale = hScale;
    }
    
    @Override public void execute() {
        this.model.setHScaleAndOffset(offsetX, hScale);
    }
    
    @Override public void undo() {
        this.model
                .setOffsetX((int) (this.model.getOffsetX() / hScale - offsetX));
        this.model.appendHScale(1 / hScale);
    }
}
