package edu.xmu.nmr.dataanalysis.diagram.commands;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import edu.xmu.nmrdataanalysis.diagram.model.FidData;

public class DAMoveCommand extends Command {
    private FidData model;
    private int appendOffsetY;
    
    private Point startLocation;
    private Point endLocation;
    
    public void setModel(Object model) {
        this.model = (FidData) model;
    }
    
    @Override public void execute() {
        this.model.appendOffsetY(appendOffsetY);
    }
    
    public void setAppendOffsetY(int appendOffsetY) {
        this.appendOffsetY = appendOffsetY;
    }
    
    public void setStartLocation(Point startLocation) {
        this.startLocation = startLocation;
    }
    
    public void setEndLocation(Point endLocation) {
        this.endLocation = endLocation;
    }
    
    @Override public void undo() {
        this.model.appendOffsetY(startLocation.y - endLocation.y);
    }
    
}
