package edu.xmu.nmr.dataanalysis.diagram.editpolicys;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.AbstractEditPolicy;

import edu.xmu.nmr.dataanalysis.diagram.commands.ShowFullCommand;
import edu.xmu.nmr.dataanalysis.diagram.requests.DARequestConstants;
import edu.xmu.nmr.dataanalysis.diagram.requests.ShowFullRequest;
import edu.xmu.nmrdataanalysis.diagram.model.Container;
import edu.xmu.nmrdataanalysis.diagram.model.FElement;
import edu.xmu.nmrdataanalysis.diagram.model.FidData;

public class ShowFullPolicy extends AbstractEditPolicy {
    
    public static String ROLE = "ShowFullPolicy_Role";
    
    @Override public Command getCommand(Request request) {
        if (request.getType().equals(DARequestConstants.DA_REQ_SHOW_FULL)) {
            return createCommand(request);
        }
        return null;
    }
    
    private Command createCommand(Request request) {
        Object parent = getHost().getModel();
        if (!(parent instanceof Container)) {
            return null;
        }
        if (!((Container) parent).hasChildren()) {
            return null;
        }
        List<FElement> children = ((Container) parent).getChildren();
        ShowFullCommand cmd = new ShowFullCommand();
        for (FElement child : children) {
            if (child instanceof FidData) {
                cmd.setModel((FidData) child);
                cmd.setZoomManager(
                        ((ShowFullRequest) request).getZoomManager());
                return cmd;
            }
        }
        return null;
    }
    
    @Override public EditPart getTargetEditPart(Request request) {
        if (DARequestConstants.DA_REQ_SHOW_FULL.equals(request.getType())) {
            return getHost();
        }
        return super.getTargetEditPart(request);
    }
}
