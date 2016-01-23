package edu.xmu.nmrdataanalysis.diagram.model;

import java.beans.PropertyChangeEvent;
import java.util.Map;

public class HorizontalRuler extends Ruler {
    
    public HorizontalRuler() {
        super();
    }
    
    @Override public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
        case FidData.PRO_FD_OFFSETX:
            setOffset((int) evt.getNewValue());
            break;
        case FidData.PRO_FD_XAXIS:
            setAxis((Map<String, Float>) evt.getNewValue());
        }
    }
}
