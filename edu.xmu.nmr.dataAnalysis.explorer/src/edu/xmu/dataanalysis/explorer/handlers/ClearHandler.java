package edu.xmu.dataanalysis.explorer.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import edu.xmu.dataanalysis.explorer.view.NMRExplorer;
import edu.xmu.nmr.dataanalysis.logic.datacenter.NMRDataCenter;
import edu.xmu.nmr.dataanalysis.logic.explorermodel.FolderNode;

public class ClearHandler extends AbstractHandler {

	public ClearHandler() {
	}

	@Override
	public boolean isEnabled() {
		return isAvaliable();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if (!isAvaliable())
			return null;
		if (MessageDialog.openQuestion(new Shell(), "Clear confirm",
				"Dou you confirm clear all data?")) {
			NMRDataCenter.getInstance().getNmrData().removeChildren();
			NMRExplorer ne = (NMRExplorer) PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.findView(NMRExplorer.ID);
			ne.getCommonViewer().refresh();
		}
		return null;
	}

	/**
	 * 根据nmr数据中心是否持有数据，判断clear是否可用
	 * 
	 * @return
	 */
	private boolean isAvaliable() {
		FolderNode fn = NMRDataCenter.getInstance().getNmrData();
		if (fn == null || !fn.hasChildren()) {
			return false;
		}
		return true;
	}
}
