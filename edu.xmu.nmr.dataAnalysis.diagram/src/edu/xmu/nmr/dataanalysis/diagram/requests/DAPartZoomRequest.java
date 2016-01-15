package edu.xmu.nmr.dataanalysis.diagram.requests;

import org.eclipse.gef.Request;

public class DAPartZoomRequest extends Request {
    
    /**
     * 起始横坐标
     */
    private int startX;
    /**
     * 终点横坐标
     */
    private int endX;
    
    public DAPartZoomRequest() {
        setType(DARequestConstants.DA_REQ_PART_ZOOM);
    }
    
    public int getStartX() {
        return startX;
    }
    
    public void setStartX(int startX) {
        this.startX = startX;
    }
    
    public int getEndX() {
        return endX;
    }
    
    public void setEndX(int endX) {
        this.endX = endX;
    }
}
