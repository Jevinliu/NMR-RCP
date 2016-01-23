package edu.xmu.nmr.dataanalysis.diagram.requests;

import org.eclipse.gef.Request;

public class ZoomRequst extends Request {
    
    private double totalScale;
    private double factor;
    private boolean isMultiFactor;
    
    public ZoomRequst() {
        setType(DARequestConstants.DA_REQ_ZOOM);
    }
    
    public double getTotalScale() {
        return totalScale;
    }
    
    public double getFactor() {
        return factor;
    }
    
    public void setTotalScale(double totalScale) {
        this.totalScale = totalScale;
    }
    
    public void setFactor(double factor) {
        this.factor = factor;
    }
    
    public boolean isMultiFactor() {
        return isMultiFactor;
    }
    
    public void setMultiFactor(boolean isMultiFactor) {
        this.isMultiFactor = isMultiFactor;
    }
    
}
