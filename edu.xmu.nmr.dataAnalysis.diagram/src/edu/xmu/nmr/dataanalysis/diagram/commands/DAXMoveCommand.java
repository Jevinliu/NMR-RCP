package edu.xmu.nmr.dataanalysis.diagram.commands;

public class DAXMoveCommand extends DAMoveCommand {
    
    private int appendOffsetX;
    
    public void setAppendOffsetX(int appendOffsetX) {
        this.appendOffsetX = appendOffsetX;
    }
    
    @Override public void execute() {
        this.getModel().appendOffsetX(appendOffsetX);
    }
    
    @Override public void undo() {
        this.getModel()
                .appendOffsetY(getStartLocation().x - getEndLocation().x);
    }
    
}
