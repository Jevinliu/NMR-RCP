package edu.xmu.dataanalysis.explorer.handlers;

import java.util.Iterator;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import edu.xmu.dataanalysis.explorer.view.NMRExplorer;
import edu.xmu.nmr.dataanalysis.logic.explorermodel.AbstractDataNode;
import edu.xmu.nmr.dataanalysis.logic.explorermodel.FidNode;
import edu.xmu.nmr.dataanalysis.logic.explorermodel.ParamtersNode;

public class DeleteHandler implements IHandler {

	public DeleteHandler() {
	}

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {

	}

	@Override
	public void dispose() {

	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage page = window.getActivePage();
		// get the selection
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection != null && selection instanceof IStructuredSelection) {
			Iterator<Object> it = ((IStructuredSelection) selection).iterator();
			while (it.hasNext()) {
				Object sel = it.next();
				if (sel == null || sel instanceof ParamtersNode
						|| sel instanceof FidNode) {
					return null;
				}
				if (sel instanceof AbstractDataNode) {
					((AbstractDataNode) sel).getParent().removeChild(
							(AbstractDataNode) sel);
				}
			}
			NMRExplorer ne = (NMRExplorer) PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.findView(NMRExplorer.ID);
			ne.getCommonViewer().refresh();
		}
		return null;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isHandled() {
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {

	}

}
