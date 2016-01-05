package edu.xmu.dataanalysis.explorer.view;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.navigator.CommonNavigator;

import edu.xmu.nmr.dataanalysis.logic.datacenter.NMRDataCenter;

public class NMRExplorer extends CommonNavigator {

	public static final String ID = "edu.xmu.dataAnalysis.explorer.view";

	public NMRExplorer() {
	}

	@Override
	protected Object getInitialInput() {
		return NMRDataCenter.getInstance().getNmrData();
	}

	@Override
	public void createPartControl(Composite aParent) {
		super.createPartControl(aParent);
		getCommonViewer().addDoubleClickListener(new DoubleClickListener());
	}

	class DoubleClickListener implements IDoubleClickListener {

		@Override
		public void doubleClick(DoubleClickEvent event) {
			IHandlerService handlerService = (IHandlerService) getSite()
					.getService(IHandlerService.class);
			try {
				handlerService
						.executeCommand(
								"edu.xmu.nmr.dataAnalysis.diagram.commands.opennmreditor",
								null);
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
	}
}
