package edu.xmu.nmr.dataanalysis.diagram.actions;

import org.eclipse.gef.EditDomain;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import edu.xmu.nmr.dataanalysis.diagram.actions.helper.DAActionConstants;
import edu.xmu.nmr.dataanalysis.diagram.multieditor.DAAbstractGraphicalEditor;
import edu.xmu.nmr.dataanalysis.diagram.multieditor.FidEditorPage;
import edu.xmu.nmr.dataanalysis.diagram.multieditor.SpecEditorPage;
import edu.xmu.nmr.dataanalysis.diagram.others.DAUI;
import edu.xmu.nmr.dataanalysis.diagram.others.DASharedImages;
import edu.xmu.nmr.dataanalysis.diagram.tool.DAMoveTool;

public class DAYMoveAction extends SelectionAction {
    
    private boolean checked = false;
    
    public DAYMoveAction(IWorkbenchPart part) {
        super(part);
        setId(DAActionConstants.DA_MOVE_V_IMG);
        setText("Move Vertical");
        setImageDescriptor(DASharedImages.MOVE_V);
    }
    
    @Override protected boolean calculateEnabled() {
        IWorkbenchPart part = getWorkbenchPart();
        if (part instanceof FidEditorPage || part instanceof SpecEditorPage) {
            return true;
        }
        return false;
    }
    
    @Override public boolean isChecked() {
        return checked;
    }
    
    @Override public void setChecked(boolean checked) {
        this.checked = checked;
    }
    
    @Override public void run() {
        DAAbstractGraphicalEditor editorPage = (DAAbstractGraphicalEditor) getWorkbenchPart();
        EditDomain editDomain = editorPage.getGraphicalViewer().getEditDomain();
        if (isChecked()) {
            DAMoveTool mvTool = new DAMoveTool();
            mvTool.setType(DAUI.VERTICAL);
            editDomain.setActiveTool(mvTool);
        } else {
            editDomain.setActiveTool(editDomain.getDefaultTool());
        }
    }
    
}