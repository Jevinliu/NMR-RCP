package edu.xmu.nmr.dataanalysis.diagram.retargetactions;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.actions.RetargetAction;

public class DAAbstractRetargetAction extends RetargetAction
        implements PropertyChangeListener {
        
    private PropertyChangeSupport daListeners;
    public static final String PRO_ACTION_CHECKED = "pro_action_checked";
    
    public DAAbstractRetargetAction(String actionID, String text, int style) {
        super(actionID, text, style);
        daListeners = new PropertyChangeSupport(this);
    }
    
    public void addListener(PropertyChangeListener listener) {
        daListeners.addPropertyChangeListener(listener);
    }
    
    public void removeListener(PropertyChangeListener listener) {
        daListeners.removePropertyChangeListener(listener);
    }
    
    public PropertyChangeSupport getDAListeners() {
        return daListeners;
    }
    
    @Override public void dispose() {
        PropertyChangeListener[] lss = daListeners.getPropertyChangeListeners();
        if (lss != null && lss.length != 0) {
            for (PropertyChangeListener ls : lss) {
                daListeners.removePropertyChangeListener(ls);
            }
        }
        super.dispose();
    }
    
    @Override public void setChecked(boolean checked) {
        super.setChecked(checked);
        if (getActionHandler() != null) {
            getActionHandler().setChecked(checked);
            if (checked) {
                daListeners.firePropertyChange(PRO_ACTION_CHECKED, false, true);
            }
        }
    }
    
    @Override public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName()
                .equals(DAPartZoomInRetargetAction.PRO_ACTION_CHECKED)) {
            IAction handler = getActionHandler();
            if (handler != null && handler.isChecked()) {
                this.setChecked(false);
            }
        }
    }
}
