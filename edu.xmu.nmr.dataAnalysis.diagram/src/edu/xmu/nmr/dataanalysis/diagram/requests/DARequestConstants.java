package edu.xmu.nmr.dataanalysis.diagram.requests;

/**
 * 
 * DARequestConstants
 * <p>
 * 自定义的request类型
 * </p>
 * 
 * @see
 */
public interface DARequestConstants {
    /**
     * 图像上下移动
     */
    String DA_REQ_MOVE_V_IMG = "Move_v_image";
    
    /**
     * 图像水平移动
     */
    String DA_REQ_MOVE_H_IMG = "Move_h_image";
    
    /**
     * 图像部分缩放
     */
    String DA_REQ_PART_ZOOM = "Part_zoom";
    
    /**
     * 缩放
     */
    String DA_REQ_ZOOM = "Req_zoom";
    
    /**
     * 显示全谱
     */
    String DA_REQ_SHOW_FULL = "Req_show_full";
}
