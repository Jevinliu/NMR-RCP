package edu.xmu.nmr.dataanalysis.diagram.editpolicys;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.AbstractEditPolicy;

import edu.xmu.nmr.dataanalysis.diagram.commands.DAPartZoomInCommand;
import edu.xmu.nmr.dataanalysis.diagram.requests.DAPartZoomRequest;
import edu.xmu.nmr.dataanalysis.diagram.requests.DARequestConstants;
import edu.xmu.nmrdataanalysis.diagram.model.Container;
import edu.xmu.nmrdataanalysis.diagram.model.FElement;
import edu.xmu.nmrdataanalysis.diagram.model.FidData;

public class DAPartZoomPolicy extends AbstractEditPolicy {
    
    public static final String ROLE = "DAPartZoomPolicy_Role";
    
    @Override public Command getCommand(Request request) {
        if (request.getType().equals(DARequestConstants.DA_REQ_PART_ZOOM)) {
            return createPartZoomCommand(request);
        }
        return null;
    }
    
    private Command createPartZoomCommand(Request request) {
        DAPartZoomInCommand cmd = new DAPartZoomInCommand();
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
                DAPartZoomRequest req = (DAPartZoomRequest) request;
                cmd.setOffsetX(req.getOffsetX());
                cmd.setHScale(req.getHScale());
                return cmd;
            }
        }
        return null;
    }
    
    @Override public EditPart getTargetEditPart(Request request) {
        
        if (DARequestConstants.DA_REQ_PART_ZOOM.equals(request.getType())) {
            return getHost();
        }
        return super.getTargetEditPart(request);
    }
}
