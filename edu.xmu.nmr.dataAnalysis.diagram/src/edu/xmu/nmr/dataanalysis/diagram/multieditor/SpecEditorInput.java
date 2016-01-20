package edu.xmu.nmr.dataanalysis.diagram.multieditor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class SpecEditorInput implements IEditorInput {
    
    private String name = "Spectrum";
    private String nodePath; // 记录Spectrum节点的nodePath;
    
    public SpecEditorInput() {
    }
    
    @Override public <T> T getAdapter(Class<T> adapter) {
        return null;
    }
    
    @Override public boolean exists() {
        return name != null && !name.trim().equals("");
    }
    
    @Override public ImageDescriptor getImageDescriptor() {
        return ImageDescriptor.getMissingImageDescriptor();
    }
    
    @Override public String getName() {
        return name;
    }
    
    @Override public IPersistableElement getPersistable() {
        return null;
    }
    
    @Override public String getToolTipText() {
        return nodePath;
    }
    
    @Override public boolean equals(Object obj) {
        if (!(obj instanceof SpecEditorInput))
            return false;
        return ((SpecEditorInput) obj).getNodePath().equals(getNodePath());
    }
    
    public String getNodePath() {
        return nodePath;
    }
}
