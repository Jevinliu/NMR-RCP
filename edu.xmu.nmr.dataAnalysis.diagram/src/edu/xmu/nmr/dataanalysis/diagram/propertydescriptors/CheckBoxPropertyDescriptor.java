package edu.xmu.nmr.dataanalysis.diagram.propertydescriptors;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * 
 * CheckBoxPropertyDescriptor
 * <p>
 * 自定义复选框式PropertyDescriptor
 * </p>
 * 
 * @see
 */
public class CheckBoxPropertyDescriptor extends PropertyDescriptor {
    
    public CheckBoxPropertyDescriptor(Object id, String displayName) {
        super(id, displayName);
    }
    
    @Override public CellEditor createPropertyEditor(Composite parent) {
        CellEditor editor = new CheckboxCellEditor(parent);
        if (getValidator() != null) {
            editor.setValidator(getValidator());
        }
        return editor;
    }
}
