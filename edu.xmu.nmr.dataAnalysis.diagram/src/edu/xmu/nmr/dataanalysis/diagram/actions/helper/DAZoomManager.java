package edu.xmu.nmr.dataanalysis.diagram.actions.helper;

/**
 * 缩放管理器，通过操作模型interval的缩放比例，来影响figure的变化
 * 
 * @author software
 *         
 */
public class DAZoomManager {
    
    /**
     * 记录当前缩放比例，
     */
    private double zoom = 1.0;
    private double[] zoomLevels = { 1.0, 1.25, 1.5, 1.75, 2.0, 3.0, 4.0 };
    
    /**
     * 总的缩放比例
     */
    private double totalScale = 1.0;
    private double factor = 1;
    /**
     * 标志totalScale 当前zoom下是否被相乘factor
     */
    private boolean isMultiFactor = false;
    
    public DAZoomManager() {
    
    }
    
    public double getMaxZoom() {
        return getZoomLevels()[getZoomLevels().length - 1];
    }
    
    public double getMinZoom() {
        return getZoomLevels()[0];
    }
    
    public double[] getZoomLevels() {
        return zoomLevels;
    }
    
    /**
     * 找出下一个缩放比例，当没有比当前缩放比例大的zoom时，选取原始缩放比例
     * 
     * @return
     */
    public double getNextZoomLevel() {
        for (int i = 0; i < zoomLevels.length; i++) {
            if (zoomLevels[i] > zoom)
                return zoomLevels[i];
        }
        return getMinZoom();
    }
    
    public double getPreviousZoomLevel() {
        for (int i = 0; i < zoomLevels.length; i++)
            if (zoomLevels[i] >= zoom) {
                if (i == 0) {
                    break;
                }
                return zoomLevels[i - 1];
            }
        return getMaxZoom();
    }
    
    public double getZoom() {
        return zoom;
    }
    
    /**
     * 设置zoom level给指定的value，
     * 
     * @param zoom
     */
    protected void primSetZoom(double zoom) {
        double prevZoom = this.zoom;
        this.zoom = zoom;
        // 根据zoom值自行判断是zoom in还是zoom out，方便以后使用setZoom()
        // zoom in时计算总的totalScale，用于fid的显示,此处边界值极为重要
        factor = zoom / prevZoom;
        if ((zoom == getMinZoom() && prevZoom == getMaxZoom())
                || (zoom != getMaxZoom() && zoom > prevZoom)
                || (zoom == getMaxZoom() && prevZoom != getMinZoom())) {
            if (zoom != getMinZoom()) {
                totalScale = totalScale * factor;
                isMultiFactor = true;
            } else {
                isMultiFactor = false;
            }
        } else {// zoom out 时计算totalScale，用于fid显示
            double preTemp = prevZoom / getMaxZoom();
            double temp = zoom / getMaxZoom();
            if (temp != 1) {
                totalScale = totalScale * temp / preTemp;
                isMultiFactor = true;
            } else {
                isMultiFactor = false;
            }
        }
    }
    
    /**
     * 设置zoom level到指定值，如果zoom 出了范围，它会被忽略
     * 
     * @param zoom
     *            新的zoom level
     */
    public void setZoomAndPrim(double zoom) {
        zoom = Math.min(getMaxZoom(), zoom);
        zoom = Math.max(getMinZoom(), zoom);
        if (this.zoom != zoom)
            primSetZoom(zoom);
    }
    
    public void setZoom(double zoom) {
        zoom = Math.min(getMaxZoom(), zoom);
        zoom = Math.max(getMinZoom(), zoom);
        if (this.zoom != zoom)
            this.zoom = zoom;
    }
    
    public void setZoomLevels(double[] zoomLevels) {
        this.zoomLevels = zoomLevels;
    }
    
    public boolean canZoomIn() {
        return getZoom() <= getMaxZoom();
    }
    
    public boolean canZoomOut() {
        return getZoom() >= getMinZoom();
    }
    
    /**
     * 放大
     */
    public void zoomIn() {
        setZoomAndPrim(getNextZoomLevel());
    }
    
    /**
     * 缩小
     */
    public void zoomOut() {
        setZoomAndPrim(getPreviousZoomLevel());
    }
    
    public double getTotalScale() {
        return totalScale;
    }
    
    public void setTotalScale(double totalScale) {
        this.totalScale = totalScale;
    }
    
    public double getFactor() {
        return factor;
    }
    
    public boolean isMultiFactor() {
        return isMultiFactor;
    }
    
}
