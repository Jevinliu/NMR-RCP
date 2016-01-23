package edu.xmu.nmr.dataanalysis.diagram.others;

import org.eclipse.jface.resource.ImageDescriptor;

import edu.xmu.nmr.dataanalysis.diagram.Activator;

public final class DASharedImages {
    
    /**
     * 移动图像
     */
    public static final ImageDescriptor MOVE_V = get(
            "/resources/images/move_v.png");
    /**
     * 局部放大
     */
    public static final ImageDescriptor PART_ZOOM_IN = get(
            "/resources/images/part_zoom_in.png");
    /**
     * 局部缩小
     */
    public static final ImageDescriptor PART_ZOOM_OUT = get(
            "/resources/images/part_zoom_out.png");
            
    public static final ImageDescriptor FFT = get("/resources/images/fft.png");
    
    public static final ImageDescriptor FULL = get(
            "/resources/images/full.png");
            
    private static final ImageDescriptor get(String path) {
        return ImageDescriptor.createFromFile(Activator.class, path);
    }
}
