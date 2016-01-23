package edu.xmu.nmr.dataanalysis.diagram.actions;

import org.eclipse.gef.EditDomain;
import org.eclipse.gef.internal.GEFMessages;
import org.eclipse.gef.internal.InternalImages;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IWorkbenchPart;

import edu.xmu.nmr.dataanalysis.diagram.actions.helper.DAActionConstants;
import edu.xmu.nmr.dataanalysis.diagram.multieditor.DAAbstractGraphicalEditor;
import edu.xmu.nmr.dataanalysis.diagram.multieditor.FidEditorPage;
import edu.xmu.nmr.dataanalysis.diagram.multieditor.SpecEditorPage;

public class DAZoomInAction extends SelectionAction {
    
    public DAZoomInAction(IWorkbenchPart part) {
        super(part);
        setText(GEFMessages.ZoomIn_Label);
        setId(DAActionConstants.DA_ZOOM_IN);
        setToolTipText(GEFMessages.ZoomIn_Tooltip);
        setImageDescriptor(InternalImages.DESC_ZOOM_IN);
        setActionDefinitionId(DAActionConstants.DA_ZOOM_IN);
    }
    
    @Override protected boolean calculateEnabled() {
        IWorkbenchPart part = getWorkbenchPart();
        if (part instanceof FidEditorPage || part instanceof SpecEditorPage) {
            return true;
        }
        return false;
    }
    
    @Override public void run() {
        DAAbstractGraphicalEditor editorPage = (DAAbstractGraphicalEditor) getWorkbenchPart();
        EditDomain editDomain = editorPage.getGraphicalViewer().getEditDomain();
        Event e = new Event();
        e.count = 2;
        editDomain.getDefaultTool().mouseWheelScrolled(e,
                editorPage.getGraphicalViewer());
    }
}
