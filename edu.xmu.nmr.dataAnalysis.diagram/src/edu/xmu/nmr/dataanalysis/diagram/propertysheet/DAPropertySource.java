package edu.xmu.nmr.dataanalysis.diagram.propertysheet;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.views.properties.ColorPropertyDescriptor;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import edu.xmu.nmr.dataanalysis.diagram.pref.helper.DAPrefConstants;
import edu.xmu.nmr.dataanalysis.diagram.propertydescriptors.CheckBoxPropertyDescriptor;
import edu.xmu.nmr.dataanalysis.diagram.propertydescriptors.SpinnerPropertyDescriptor;
import edu.xmu.nmrdataanalysis.diagram.model.FElement;
import edu.xmu.nmrdataanalysis.diagram.model.FidData;
import edu.xmu.nmrdataanalysis.diagram.model.Ruler;

public class DAPropertySource implements IPropertySource {
    
    private FElement fElement;
    private String[] scales = new String[] { "s", "ms", "us" };
    
    public DAPropertySource(FElement fElement) {
        this.fElement = fElement;
    }
    
    @Override public Object getEditableValue() {
        return null;
    }
    
    @Override public IPropertyDescriptor[] getPropertyDescriptors() {
        ArrayList<IPropertyDescriptor> properties = new ArrayList<IPropertyDescriptor>();
        if (fElement instanceof FidData || fElement instanceof Ruler) {
            properties.add(new ColorPropertyDescriptor(
                    DAPrefConstants.FID_PREF_FORE_COLOR, "Foreground Color"));
            properties.add(new ColorPropertyDescriptor(
                    DAPrefConstants.FID_PREF_BACK_COLOR, "Background Color"));
        }
        if (fElement instanceof FidData) {
            properties.add(new CheckBoxPropertyDescriptor(
                    DAPrefConstants.FID_PREF_HAS_GRID, "Has Grid"));
            properties.add(new SpinnerPropertyDescriptor(
                    DAPrefConstants.FID_PREF_LINE_WIDTH, "Line Width", 1, 40, 1,
                    1, 5));
            properties.add(new CheckBoxPropertyDescriptor(
                    DAPrefConstants.FID_PREF_HAS_BORDER, "Has Border"));
            properties.add(new ColorPropertyDescriptor(
                    DAPrefConstants.FID_PREF_GRID_COLOR, "Grid Color"));
            properties.add(new ColorPropertyDescriptor(
                    DAPrefConstants.FID_PREF_BORDER_COLOR, "Border Color"));
        }
        if (fElement instanceof Ruler) {
            properties.add(new CheckBoxPropertyDescriptor(
                    DAPrefConstants.L_RULER_PREF_IS_VISIABLE, "Visiable"));
            properties.add(new ComboBoxPropertyDescriptor(
                    DAPrefConstants.L_RULER_PREF_SCALE, "Scale", scales));
                    
        }
        return properties.toArray(new IPropertyDescriptor[0]);
    }
    
    @Override public Object getPropertyValue(Object id) {
        if (fElement instanceof FidData || fElement instanceof Ruler) {
            switch ((String) id) {
            case DAPrefConstants.FID_PREF_FORE_COLOR:
                return fElement.getForegroundColor().getRGB();
            case DAPrefConstants.FID_PREF_BACK_COLOR:
                return fElement.getBackgroundColor().getRGB();
            }
        }
        if (fElement instanceof FidData) {
            switch ((String) id) {
            case DAPrefConstants.FID_PREF_HAS_GRID:
                return ((FidData) fElement).isHasGird();
            case DAPrefConstants.FID_PREF_HAS_BORDER:
                return ((FidData) fElement).isHasBorder();
            case DAPrefConstants.FID_PREF_LINE_WIDTH:
                return ((FidData) fElement).getLineWidth();
            case DAPrefConstants.FID_PREF_GRID_COLOR:
                return ((FidData) fElement).getFidGridColor().getRGB();
            case DAPrefConstants.FID_PREF_BORDER_COLOR:
                return ((FidData) fElement).getFidBorderColor().getRGB();
            }
        }
        if (fElement instanceof Ruler) {
            switch ((String) id) {
            case DAPrefConstants.L_RULER_PREF_IS_VISIABLE:
                return ((Ruler) fElement).isVisiable();
            case DAPrefConstants.L_RULER_PREF_SCALE:
                return 1;
            }
        }
        return null;
    }
    
    @Override public boolean isPropertySet(Object id) {
        return false;
    }
    
    @Override public void resetPropertyValue(Object id) {
    
    }
    
    @Override public void setPropertyValue(Object id, Object value) {
        if (fElement instanceof FidData || fElement instanceof Ruler) {
            switch ((String) id) {
            case DAPrefConstants.FID_PREF_FORE_COLOR:
                fElement.setForegroundColor(new Color(null, (RGB) value));
                break;
            case DAPrefConstants.FID_PREF_BACK_COLOR:
                fElement.setBackgroundColor(new Color(null, (RGB) value));
                break;
            }
        }
        if (fElement instanceof FidData) {
            switch ((String) id) {
            case DAPrefConstants.FID_PREF_HAS_GRID:
                ((FidData) fElement).setHasGird((boolean) value);
                break;
            case DAPrefConstants.FID_PREF_LINE_WIDTH:
                ((FidData) fElement).setLineWidth((int) value);
                break;
            case DAPrefConstants.FID_PREF_HAS_BORDER:
                ((FidData) fElement).setHasBorder((boolean) value);
                break;
            case DAPrefConstants.FID_PREF_BORDER_COLOR:
                ((FidData) fElement)
                        .setFidBorderColor(new Color(null, (RGB) value));
                break;
            case DAPrefConstants.FID_PREF_GRID_COLOR:
                ((FidData) fElement)
                        .setFidGridColor(new Color(null, (RGB) value));
                break;
            }
        }
        if (fElement instanceof Ruler) {
            switch ((String) id) {
            case DAPrefConstants.L_RULER_PREF_IS_VISIABLE:
                ((Ruler) fElement).setVisiable((boolean) value);
                break;
            case DAPrefConstants.L_RULER_PREF_SCALE:
                ((Ruler) fElement).setScale("cm");
                break;
            }
        }
    }
}
