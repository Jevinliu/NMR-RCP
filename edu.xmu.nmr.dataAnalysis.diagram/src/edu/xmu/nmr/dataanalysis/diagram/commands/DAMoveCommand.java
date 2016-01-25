package edu.xmu.nmr.dataanalysis.diagram.commands;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import edu.xmu.nmrdataanalysis.diagram.model.FidData;

public abstract class DAMoveCommand extends Command {
    
    private FidData model;
    
    private Point startLocation;
    private Point endLocation;
    
    public void setModel(Object model) {
        this.model = (FidData) model;
    }
    
    public void setStartLocation(Point startLocation) {
        this.startLocation = startLocation;
    }
    
    public void setEndLocation(Point endLocation) {
        this.endLocation = endLocation;
    }
    
    public FidData getModel() {
        return model;
    }
    
    public Point getStartLocation() {
        return startLocation;
    }
    
    public Point getEndLocation() {
        return endLocation;
    }
}
