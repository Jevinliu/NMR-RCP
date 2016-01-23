package edu.xmu.nmr.dataanalysis.diagram.editpolicys;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.AbstractEditPolicy;

import edu.xmu.nmr.dataanalysis.diagram.commands.ZoomCommand;
import edu.xmu.nmr.dataanalysis.diagram.requests.DARequestConstants;
import edu.xmu.nmr.dataanalysis.diagram.requests.ZoomRequst;
import edu.xmu.nmrdataanalysis.diagram.model.Container;
import edu.xmu.nmrdataanalysis.diagram.model.FElement;
import edu.xmu.nmrdataanalysis.diagram.model.FidData;

public class ZoomPolicy extends AbstractEditPolicy {
    
    public static final String ROLE = "ZoomPolicy_Role";
    
    @Override public Command getCommand(Request request) {
        if (request.getType().equals(DARequestConstants.DA_REQ_ZOOM_IN)) {
            return createMoveCommand(request);
        }
        return null;
    }
    
    protected Command createMoveCommand(Request request) {
        ZoomCommand cmd = new ZoomCommand();
        Object parent = getHost().getModel();
        if (!(parent instanceof Container)) {
            return null;
        }
        if (!((Container) parent).hasChildren()) {
            return null;
        }
        List<FElement> children = ((Container) parent).getChildren();
        for (FElement child : children) {
            if (child instanceof FidData) {
                cmd.setModel((FidData) child);
                ZoomRequst req = (ZoomRequst) request;
                cmd.setTotalScale(req.getTotalScale());
                cmd.setFactor(req.getFactor());
                cmd.setMultiFactor(req.isMultiFactor());
                return cmd;
            }
        }
        return null;
    }
    
    /**
     * 根据请求获得目标EditPart
     * 
     * @param request
     * @return
     * @see org.eclipse.gef.editpolicies.AbstractEditPolicy#getTargetEditPart(org.eclipse.gef.Request)
     */
    @Override public EditPart getTargetEditPart(Request request) {
        if (DARequestConstants.DA_REQ_ZOOM_IN.equals(request.getType())) {
            return getHost();
        }
        return super.getTargetEditPart(request);
    }
}
