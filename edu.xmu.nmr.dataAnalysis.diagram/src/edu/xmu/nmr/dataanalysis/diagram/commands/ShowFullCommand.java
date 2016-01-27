package edu.xmu.nmr.dataanalysis.diagram.commands;

import org.eclipse.gef.commands.Command;

import edu.xmu.nmr.dataanalysis.diagram.actions.helper.DAZoomManager;
import edu.xmu.nmrdataanalysis.diagram.model.FidData;

public class ShowFullCommand extends Command {
    
    private FidData model;
    
    private DAZoomManager zoomMgr;
    
    private int oldOffsetY;
    
    private int oldOffsetX;
    
    private double oldVScale;
    
    private double oldHScale;
    
    private double oldZoom;
    
    public void setModel(FidData model) {
        this.model = model;
        this.oldOffsetY = this.model.getOffsetY();
        this.oldOffsetX = this.model.getOffsetX();
        this.oldVScale = this.model.getVScale();
        this.oldHScale = this.model.getHScale();
    }
    
    public void setZoomManager(DAZoomManager zoomManager) {
        this.zoomMgr = zoomManager;
        this.oldZoom = this.zoomMgr.getZoom();
    }
    
    public boolean checkNeedReset() {
        return this.model.checkNeedReset();
    }
    
    @Override public void execute() {
        this.model.reset();
        zoomMgr.setTotalScale(1);
    }
    
    @Override public void undo() {
        this.model.setOffsetX(this.oldOffsetX);
        this.model.setOffsetY(this.oldOffsetY);
        this.model.setHScale(this.oldHScale);
        this.model.setVScale(this.oldVScale);
        zoomMgr.setTotalScale(this.oldVScale);
        zoomMgr.setZoom(this.oldZoom);
    }
}
