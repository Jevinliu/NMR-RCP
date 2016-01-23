/**
 * @Title ShowFullTool.java
 * @Package edu.xmu.nmr.dataanalysis.diagram.actions
 * @brief TODO
 * Copyright Copyright (c) 2015
 * Wuhan Zhongke Magnetic Resonance
 * Technology Co., Ltd.
 *
 * @author Jevin
 * @date 2016年1月23日 下午1:49:14
 * @version V1.0
 */
package edu.xmu.nmr.dataanalysis.diagram.tool;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.tools.TargetingTool;
import org.eclipse.swt.events.MouseEvent;

import edu.xmu.nmr.dataanalysis.diagram.commands.ShowFullCommand;
import edu.xmu.nmr.dataanalysis.diagram.editparts.FidContainerEditPart;
import edu.xmu.nmr.dataanalysis.diagram.requests.DARequestConstants;
import edu.xmu.nmr.dataanalysis.diagram.requests.ShowFullRequest;

/**
 * ShowFullTool
 * <p>
 * 显示全谱的Tool
 * </p>
 * 
 * @see
 */
public class ShowFullTool extends TargetingTool {
    
    @Override protected String getCommandName() {
        return DARequestConstants.DA_REQ_SHOW_FULL;
    }
    
    @Override protected Request createTargetRequest() {
        Request request = new ShowFullRequest();
        return request;
    }
    
    @Override protected void updateTargetRequest() {
        super.updateTargetRequest();
    }
    
    @Override public void mouseDown(MouseEvent me, EditPartViewer viewer) {
        if (!isViewerImportant(viewer))
            return;
        setViewer(viewer);
        updateTargetUnderMouse();
        performShowFull(me, viewer);
    }
    
    private void performShowFull(MouseEvent me, EditPartViewer viewer) {
        EditPart tarEditPart = getTargetEditPart();
        if (!(tarEditPart instanceof FidContainerEditPart)) {
            return;
        }
        updateTargetRequest();
        unlockTargetEditPart();
        Command cmd = getCommand();
        if ((cmd != null) && cmd instanceof ShowFullCommand) {
            if (((ShowFullCommand) cmd).checkNeedReset()) {
                setCurrentCommand(getCommand());
                executeCurrentCommand();
            }
        }
        lockTargetEditPart(tarEditPart);
    }
    
}
