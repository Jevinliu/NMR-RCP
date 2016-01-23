package edu.xmu.nmrdataanalysis.diagram.model;

import java.beans.PropertyChangeEvent;
import java.util.Map;

public class VerticalRuler extends Ruler {
    
    public VerticalRuler() {
        super();
    }
    
    @Override public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
        case FidData.PRO_FD_OFFSETY:
            setOffset((int) evt.getNewValue());
            break;
        case FidData.PRO_FD_YAXIS:
            setAxis((Map<String, Float>) evt.getNewValue());
        }
    }
    
}
