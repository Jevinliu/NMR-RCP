package edu.xmu.nmr.dataanalysis.diagram.retargetactions;

import org.eclipse.ui.actions.RetargetAction;

import edu.xmu.nmr.dataanalysis.diagram.actions.helper.DAActionConstants;
import edu.xmu.nmr.dataanalysis.diagram.others.DASharedImages;

public class FFTRetargetAction extends RetargetAction {
    
    public FFTRetargetAction() {
        super(null, null);
        setId(DAActionConstants.DA_FFT);
        setActionDefinitionId(DAActionConstants.DA_FFT);
        setText("FFT");
        setImageDescriptor(DASharedImages.FFT);
    }
    
}
