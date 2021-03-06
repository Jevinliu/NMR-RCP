package edu.xmu.nmr.dataanalysis.diagram.handlers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import edu.xmu.nmr.dataanalysis.diagram.multieditor.NMRDiagEditor;
import edu.xmu.nmr.dataanalysis.logic.explorermodel.AbstractDataNode;
import edu.xmu.nmr.dataanalysis.logic.explorermodel.FidNode;
import edu.xmu.nmr.dataanalysis.logic.explorermodel.ParamtersNode;

public class OpenEditorHandler extends AbstractHandler
        implements PropertyChangeListener {
        
    public static final String ID = "edu.xmu.nmr.dataAnalysis.diagram.commands.opennmreditor";
    
    public OpenEditorHandler() {
    }
    
    @Override public Object execute(ExecutionEvent event)
            throws ExecutionException {
        IWorkbenchPage page = HandlerUtil.getActiveWorkbenchWindow(event)
                .getActivePage();
        ISelection selection = HandlerUtil.getCurrentSelection(event);
        if (selection != null && selection instanceof IStructuredSelection) {
            Object sel = ((IStructuredSelection) selection).getFirstElement();
            if (sel == null) {
                return null;
            }
            if (sel instanceof ParamtersNode || sel instanceof FidNode) {
                AbstractDataNode[] adms = ((AbstractDataNode) sel).getParent()
                        .getChildren();
                try {
                    NMRDiagEditor editor = null;
                    ParamtersNode pn = null;
                    FidNode fn = null;
                    for (AbstractDataNode adm : adms) {
                        if (adm instanceof ParamtersNode) {
                            pn = (ParamtersNode) adm;
                            editor = (NMRDiagEditor) page.openEditor(
                                    (IEditorInput) pn, NMRDiagEditor.ID); // 只有pn作为输入的时候才打开，防止因为EditorInput不同，打开多个editor
                            pn.addPropertyChangeListener("params", this);
                        } else if (adm instanceof FidNode) {
                            fn = (FidNode) adm;
                        }
                    }
                    if (editor != null) {
                        // 打开multipage editor后装填数据
                        editor.setFidData(fn, pn);
                        editor.setParameters();
                        editor.setAcitvePage((AbstractDataNode) sel);
                    }
                } catch (PartInitException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }
    
    /**
     * 监听参数是否已经改变，参数改变时进行动作
     */
    @Override public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("参数已经改变,为以后的谱图运算做准备");
    }
}
