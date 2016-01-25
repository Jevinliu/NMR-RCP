/**
 * @Title ShowFullAction.java
 * @Package edu.xmu.nmr.dataanalysis.diagram.actions
 * @brief TODO
 * Copyright Copyright (c) 2015
 * Wuhan Zhongke Magnetic Resonance
 * Technology Co., Ltd.
 *
 * @author Jevin
 * @date 2016年1月23日 上午11:03:56
 * @version V1.0
 */
package edu.xmu.nmr.dataanalysis.diagram.actions;

import org.eclipse.gef.EditDomain;
import org.eclipse.gef.Tool;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import edu.xmu.nmr.dataanalysis.diagram.actions.helper.DAActionConstants;
import edu.xmu.nmr.dataanalysis.diagram.multieditor.DAAbstractGraphicalEditor;
import edu.xmu.nmr.dataanalysis.diagram.multieditor.FidEditorPage;
import edu.xmu.nmr.dataanalysis.diagram.multieditor.SpecEditorPage;
import edu.xmu.nmr.dataanalysis.diagram.others.DASharedImages;
import edu.xmu.nmr.dataanalysis.diagram.tool.ShowFullTool;

/**
 * ShowFullAction
 * <p>
 * 显示全谱
 * </p>
 * 
 * @see
 */
public class ShowFullAction extends SelectionAction {
    
    /**
     * @param part
     */
    public ShowFullAction(IWorkbenchPart part) {
        super(part);
        initSome();
    }
    
    private void initSome() {
        setId(DAActionConstants.DA_SHOW_FULL);
        setText("Full");
        setImageDescriptor(DASharedImages.FULL);
    }
    
    /**
     * @param part
     * @param style
     */
    public ShowFullAction(IWorkbenchPart part, int style) {
        super(part, style);
        initSome();
    }
    
    /**
     * @return
     * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
     */
    @Override protected boolean calculateEnabled() {
        IWorkbenchPart part = getWorkbenchPart();
        if (part instanceof SpecEditorPage || part instanceof FidEditorPage) {
            return true;
        }
        return false;
    }
    
    @Override public void run() {
        DAAbstractGraphicalEditor part = (DAAbstractGraphicalEditor) getWorkbenchPart();
        EditDomain editDomain = part.getGraphicalViewer().getEditDomain();
        Tool oldTool = editDomain.getActiveTool();
        ShowFullTool tool = new ShowFullTool();
        editDomain.setActiveTool(tool);
        tool.mouseDown(null, part.getGraphicalViewer());
        editDomain.setActiveTool(oldTool);
    }
    
}
