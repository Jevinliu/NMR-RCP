package edu.xmu.nmr.dataanalysis.diagram.editpolicys;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.AbstractEditPolicy;

import edu.xmu.nmr.dataanalysis.diagram.commands.DAMoveCommand;
import edu.xmu.nmr.dataanalysis.diagram.commands.DAXMoveCommand;
import edu.xmu.nmr.dataanalysis.diagram.commands.DAYMoveCommand;
import edu.xmu.nmr.dataanalysis.diagram.requests.DAMoveRequest;
import edu.xmu.nmr.dataanalysis.diagram.requests.DARequestConstants;
import edu.xmu.nmrdataanalysis.diagram.model.Container;
import edu.xmu.nmrdataanalysis.diagram.model.FElement;
import edu.xmu.nmrdataanalysis.diagram.model.FidData;

/**
 * 
 * DAYMovePolicy
 * <p>
 * Y轴方向上移动fid等图形的policy
 * </p>
 * 
 * @see
 */
public class DAYMovePolicy extends AbstractEditPolicy {
    
    public static final String ROLE = "DAMovePolicy_Role";
    
    @Override public Command getCommand(Request request) {
        if (request.getType().equals(DARequestConstants.DA_REQ_MOVE_V_IMG)
                || request.getType()
                        .equals(DARequestConstants.DA_REQ_MOVE_H_IMG)) {
            return createMoveCommand(request);
        }
        return null;
    }
    
    protected Command createMoveCommand(Request moveRequest) {
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
                DAMoveRequest req = (DAMoveRequest) moveRequest;
                DAMoveCommand cmd;
                if (moveRequest.getType()
                        .equals(DARequestConstants.DA_REQ_MOVE_V_IMG)) {
                    cmd = new DAYMoveCommand();
                    int offsetY = req.getCurrentLocation().y
                            - req.getStartLocation().y;
                    ((DAYMoveCommand) cmd).setAppendOffsetY(offsetY);
                } else {
                    cmd = new DAXMoveCommand();
                    int offsetX = req.getCurrentLocation().x
                            - req.getStartLocation().x;
                    ((DAXMoveCommand) cmd).setAppendOffsetX(offsetX);
                }
                cmd.setModel(child);
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
        if (DARequestConstants.DA_REQ_MOVE_V_IMG.equals(request.getType())
                || DARequestConstants.DA_REQ_MOVE_H_IMG
                        .equals(request.getType())) {
            return getHost();
        }
        return super.getTargetEditPart(request);
    }
}
