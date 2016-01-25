package edu.xmu.nmr.dataanalysis.diagram.commands;

public class DAYMoveCommand extends DAMoveCommand {
    
    private int appendOffsetY;
    
    @Override public void execute() {
        this.getModel().appendOffsetY(appendOffsetY);
    }
    
    public void setAppendOffsetY(int appendOffsetY) {
        this.appendOffsetY = appendOffsetY;
    }
    
    @Override public void undo() {
        this.getModel()
                .appendOffsetY(getStartLocation().y - getEndLocation().y);
    }
    
}
