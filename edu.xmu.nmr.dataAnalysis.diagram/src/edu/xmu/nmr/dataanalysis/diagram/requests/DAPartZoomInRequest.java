package edu.xmu.nmr.dataanalysis.diagram.requests;

import org.eclipse.gef.Request;

public class DAPartZoomInRequest extends Request {
    
    /**
     * 追加的偏移量
     */
    private int offsetX;
    /**
     * 追加的水平缩放比例
     */
    private double hScale;
    
    public DAPartZoomInRequest() {
        setType(DARequestConstants.DA_REQ_PART_ZOOM);
    }
    
    public int getOffsetX() {
        return offsetX;
    }
    
    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }
    
    public double getHScale() {
        return hScale;
    }
    
    public void setHScale(double hScale) {
        this.hScale = hScale;
    }
}
