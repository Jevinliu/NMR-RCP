package edu.xmu.nmr.dataanalysis.diagram.commands;

import org.eclipse.gef.commands.Command;

import edu.xmu.nmrdataanalysis.diagram.model.FidData;

public class ZoomCommand extends Command {
    
    private FidData model;
    
    private double totalScale;
    
    private double factor;
    
    private boolean isMultiFactor;
    
    public void setModel(FidData model) {
        this.model = model;
    }
    
    public void setTotalScale(double totalScale) {
        this.totalScale = totalScale;
    }
    
    public void setFactor(double factor) {
        this.factor = factor;
    }
    
    public void setMultiFactor(boolean isMultiFactor) {
        this.isMultiFactor = isMultiFactor;
    }
    
    @Override public void execute() {
        this.model.setVIntervalScale(totalScale, factor);
    }
    
    @Override public void undo() {
        if (isMultiFactor) {
            this.model.setVIntervalScale(totalScale * 1 / factor, 1 / factor);
        } else {
            this.model.setVIntervalScale(totalScale, 1 / factor);
        }
    }
}
