package edu.xmu.nmr.dataanalysis.diagram.propertysheet;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.views.properties.ColorPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import edu.xmu.nmr.dataanalysis.diagram.propertydescriptors.CheckBoxPropertyDescriptor;
import edu.xmu.nmrdataanalysis.diagram.model.FElement;
import edu.xmu.nmrdataanalysis.diagram.model.FidData;

public class DAPropertySource implements IPropertySource {
    
    private FElement fElement;
    
    public DAPropertySource(FElement fElement) {
        this.fElement = fElement;
    }
    
    @Override public Object getEditableValue() {
        return null;
    }
    
    @Override public IPropertyDescriptor[] getPropertyDescriptors() {
        ArrayList<IPropertyDescriptor> properties = new ArrayList<IPropertyDescriptor>();
        if (fElement instanceof FidData) {
            properties.add(new ColorPropertyDescriptor(
                    FigureProperty.PROPERTY_FORE_COLOR, "Foreground Color"));
            properties.add(new ColorPropertyDescriptor(
                    FigureProperty.PROPERTY_BACK_COLOR, "Background Color"));
            properties.add(new CheckBoxPropertyDescriptor(
                    FigureProperty.PROPERTY_HAS_GRID, "Has Grid"));
        }
        return properties.toArray(new IPropertyDescriptor[0]);
    }
    
    @Override public Object getPropertyValue(Object id) {
        switch ((String) id) {
        case FigureProperty.PROPERTY_FORE_COLOR:
            return ((FidData) fElement).getForegroundColor().getRGB();
        case FigureProperty.PROPERTY_BACK_COLOR:
            return ((FidData) fElement).getBackgroundColor().getRGB();
        case FigureProperty.PROPERTY_HAS_GRID:
            return ((FidData) fElement).isHasGird();
        default:
            return null;
        }
    }
    
    @Override public boolean isPropertySet(Object id) {
        return false;
    }
    
    @Override public void resetPropertyValue(Object id) {
    
    }
    
    @Override public void setPropertyValue(Object id, Object value) {
        switch ((String) id) {
        case FigureProperty.PROPERTY_FORE_COLOR:
            ((FidData) fElement)
                    .setForegroundColor(new Color(null, (RGB) value));
            break;
        case FigureProperty.PROPERTY_BACK_COLOR:
            ((FidData) fElement)
                    .setBackgroundColor(new Color(null, (RGB) value));
            break;
        case FigureProperty.PROPERTY_HAS_GRID:
            ((FidData) fElement).setHasGird((boolean) value);
        }
    }
    
}
