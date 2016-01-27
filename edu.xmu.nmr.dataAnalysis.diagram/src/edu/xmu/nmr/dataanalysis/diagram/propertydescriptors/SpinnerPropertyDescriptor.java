package edu.xmu.nmr.dataanalysis.diagram.propertydescriptors;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import edu.xmu.nmr.dataanalysis.diagram.celleditors.SpinnerCellEditor;

/**
 * 
 * SpinnerPropertyDescriptor
 * <p>
 * 实现spinner的 propertyDescriptor
 * </p>
 * 
 * @see
 */
public class SpinnerPropertyDescriptor extends PropertyDescriptor {
    
    private int minimum;
    
    private int maximum;
    
    private int selection;
    
    private int increment;
    
    private int pageIncrement;
    
    public SpinnerPropertyDescriptor(Object id, String displayName) {
        super(id, displayName);
    }
    
    public SpinnerPropertyDescriptor(Object id, String displayName, int minimum,
            int maximum, int selection, int increment, int pageIncrement) {
        this(id, displayName);
        this.minimum = minimum;
        this.maximum = maximum;
        this.selection = selection;
        this.increment = increment;
        this.pageIncrement = pageIncrement;
    }
    
    @Override public CellEditor createPropertyEditor(Composite parent) {
        SpinnerCellEditor editor = new SpinnerCellEditor(parent);
        editor.setMinimum(minimum);
        editor.setMaximum(maximum);
        editor.setIncrement(increment);
        editor.setPageIncrement(pageIncrement);
        editor.setSelection(selection);
        if (getValidator() != null) {
            editor.setValidator(getValidator());
        }
        return editor;
    }
    
    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }
    
    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }
    
    public void setSelection(int selection) {
        this.selection = selection;
    }
    
    public void setIncrement(int increment) {
        this.increment = increment;
    }
    
    public void setPageIncrement(int pageIncrement) {
        this.pageIncrement = pageIncrement;
    }
    
}
