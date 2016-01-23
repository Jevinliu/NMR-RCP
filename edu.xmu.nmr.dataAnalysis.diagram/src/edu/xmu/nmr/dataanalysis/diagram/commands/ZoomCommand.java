package edu.xmu.nmr.dataanalysis.diagram.commands;

import org.eclipse.gef.commands.Command;

import edu.xmu.nmrdataanalysis.diagram.model.FidData;

public class ZoomCommand extends Command {
    
    private FidData model;
    
    private double totalScale;
    
    private double factor;
    
    /**
     * 标志位，表示<code>factor</code>是否已经被乘到当前的<code>totalScale</code>中，主要为了在Undo中使用
     */
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
        this.model.setVScale(totalScale);
    }
    
    @Override public void undo() {
        if (isMultiFactor) {
            this.model.setVScale(totalScale * 1 / factor);
        } else {
            this.model.setVScale(totalScale);
        }
    }
}
