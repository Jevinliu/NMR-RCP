package edu.xmu.nmr.dataanalysis.diagram.actions;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import edu.xmu.nmr.dataanalysis.diagram.actions.helper.DAActionConstants;
import edu.xmu.nmr.dataanalysis.diagram.multieditor.SpecEditorPage;
import edu.xmu.nmr.dataanalysis.diagram.others.DASharedImages;
import edu.xmu.nmrdataanalysis.diagram.model.FidData;

public class FFTAction extends SelectionAction {
    
    public FFTAction(IWorkbenchPart part) {
        super(part);
        setId(DAActionConstants.DA_FFT);
        setText("FFT");
        setImageDescriptor(DASharedImages.FFT);
    }
    
    @Override protected boolean calculateEnabled() {
        IWorkbenchPart part = getWorkbenchPart();
        if (part instanceof SpecEditorPage) {
            return true;
        }
        return false;
    }
    
    @Override public void run() {
        SpecEditorPage specEditorPage = (SpecEditorPage) getWorkbenchPart();
        FidData specNode = specEditorPage.getSpecNode();
    }
}
