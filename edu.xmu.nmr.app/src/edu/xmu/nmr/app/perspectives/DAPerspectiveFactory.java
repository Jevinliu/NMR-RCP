package edu.xmu.nmr.app.perspectives;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import edu.xmu.dataanalysis.explorer.view.NMRExplorer;
import edu.xmu.nmr.dataanalysis.diagram.multieditor.NMRDiagEditor;

public class DAPerspectiveFactory implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(IPageLayout layout) {
		String editor = layout.getEditorArea();
		layout.setFixed(false);
		layout.setEditorAreaVisible(false);

		layout.addView(NMRExplorer.ID, IPageLayout.LEFT, 0.3f, editor);
		layout.addView(NMRDiagEditor.ID, IPageLayout.RIGHT, 0.7f, editor);
	}

}
