package edu.xmu.nmr.dataanalysis.diagram.commands;

import org.eclipse.gef.commands.Command;

import edu.xmu.nmrdataanalysis.diagram.model.FidData;

public class ShowFullCommand extends Command {
    
    private FidData model;
    
    private int oldOffsetY;
    
    private int oldOffsetX;
    
    private double oldVScale;
    
    private double oldHScale;
    
    public void setModel(FidData model) {
        this.model = model;
        this.oldOffsetY = this.model.getOffsetY();
        this.oldOffsetX = this.model.getOffsetX();
        this.oldVScale = this.model.getVScale();
        this.oldHScale = this.model.getHScale();
    }
    
    public boolean checkNeedReset() {
        return this.model.checkNeedReset();
    }
    
    @Override public void execute() {
        this.model.reset();
    }
    
    @Override public void undo() {
        this.model.setOffsetX(this.oldOffsetX);
        this.model.setOffsetY(this.oldOffsetY);
        this.model.setHScale(this.oldHScale);
        this.model.setVScale(this.oldVScale);
    }
}
