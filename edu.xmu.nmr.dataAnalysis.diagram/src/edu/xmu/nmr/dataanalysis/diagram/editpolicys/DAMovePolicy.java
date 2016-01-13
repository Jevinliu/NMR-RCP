package edu.xmu.nmr.dataanalysis.diagram.editpolicys;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.AbstractEditPolicy;

import edu.xmu.nmr.dataanalysis.diagram.commands.DAMoveCommand;
import edu.xmu.nmr.dataanalysis.diagram.requests.DAMoveRequest;
import edu.xmu.nmr.dataanalysis.diagram.requests.DARequestConstants;
import edu.xmu.nmrdataanalysis.diagram.model.Container;
import edu.xmu.nmrdataanalysis.diagram.model.FElement;
import edu.xmu.nmrdataanalysis.diagram.model.FidData;

/**
 * 
 * DAMovePolicy
 * <p>
 * 移动fid等图形的policy
 * </p>
 * 
 * @see
 */
public class DAMovePolicy extends AbstractEditPolicy {
    
    public static final String ROLE = "DAMovePolicy_Role";
    
    @Override public Command getCommand(Request request) {
        if (request.getType().equals(DARequestConstants.DA_REQ_MOVE_IMG)) {
            return createMoveCommand(request);
        }
        return null;
    }
    
    protected Command createMoveCommand(Request moveRequest) {
        DAMoveCommand cmd = new DAMoveCommand();
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
                cmd.setModel(child);
                DAMoveRequest req = (DAMoveRequest) moveRequest;
                int offsetY = req.getCurrentLocation().y
                        - req.getStartLocation().y;
                cmd.setAppendOffsetY(offsetY);
                cmd.setStartLocation(req.getDefaultLocation());
                cmd.setEndLocation(req.getCurrentLocation());
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
        if (DARequestConstants.DA_REQ_MOVE_IMG.equals(request.getType())) {
            return getHost();
        }
        return super.getTargetEditPart(request);
    }
}
