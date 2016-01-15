package edu.xmu.nmr.dataanalysis.diagram.requests;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.Request;

import edu.xmu.nmr.dataanalysis.diagram.tool.DAMoveTool;

/**
 * 
 * DAMoveRequest
 * <p>
 * 移动图像显示时的request
 * </p>
 * 
 * @see DAMoveTool
 */
public class DAMoveRequest extends Request {
    
    /**
     * 鼠标按下时的点
     */
    private Point defaultLocation;
    private Point startLocation;
    private Point currentLocation;
    
    public DAMoveRequest() {
        setType(DARequestConstants.DA_REQ_MOVE_IMG);
    }
    
    public Point getStartLocation() {
        return startLocation;
    }
    
    public void setStartLocation(Point startLocation) {
        this.startLocation = startLocation;
    }
    
    public Point getCurrentLocation() {
        return currentLocation;
    }
    
    public void setCurrentLocation(Point currentLocation) {
        this.currentLocation = currentLocation;
    }
    
    public void setDefaultLocation(Point defaultLocation) {
        this.defaultLocation = defaultLocation;
    }
    
    public Point getDefaultLocation() {
        return defaultLocation;
    }
}
