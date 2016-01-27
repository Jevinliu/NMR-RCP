package edu.xmu.nmr.dataanalysis.diagram.editparts;

import java.beans.PropertyChangeListener;

import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import edu.xmu.nmrdataanalysis.diagram.model.FElement;

public abstract class DAAbstractEditPart extends AbstractGraphicalEditPart
        implements PropertyChangeListener {
        
    public DAAbstractEditPart() {
    
    }
    
    @Override public void activate() {
        super.activate();
        ((FElement) getModel()).addPropertyChangeListener(this);
    }
    
    @Override public void deactivate() {
        super.deactivate();
        ((FElement) getModel()).addPropertyChangeListener(this);
    }
    
    /**
     * 双击操作关联到editpart打开焦点的属性页，对graphicalEditpart双击事实上生成了一个REQ_OPEN类型的Request。
     * 这个请求没有转化为EditPolicy，而是被EditPart中的performRequest()方法处理。
     * 
     * @param req
     * @see org.eclipse.gef.editparts.AbstractEditPart#performRequest(org.eclipse.gef.Request)
     */
    @Override public void performRequest(Request req) {
        if (req.getType().equals(RequestConstants.REQ_OPEN)) {
            IWorkbenchPage page = PlatformUI.getWorkbench()
                    .getActiveWorkbenchWindow().getActivePage();
            try {
                page.showView(IPageLayout.ID_PROP_SHEET);
            } catch (PartInitException e) {
                e.printStackTrace();
            }
        }
    }
}
