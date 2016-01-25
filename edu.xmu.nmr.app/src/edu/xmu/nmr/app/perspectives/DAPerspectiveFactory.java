package edu.xmu.nmr.app.perspectives;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;

import edu.xmu.dataanalysis.explorer.view.NMRExplorer;
import edu.xmu.nmr.app.views.NMRConsoleFactory;
import edu.xmu.nmr.dataanalysis.diagram.multieditor.NMRDiagEditor;

public class DAPerspectiveFactory implements IPerspectiveFactory {
    
    @Override public void createInitialLayout(IPageLayout layout) {
        String editor = layout.getEditorArea();
        layout.setFixed(false);
        layout.setEditorAreaVisible(false);
        
        NMRConsoleFactory consoleFactory = new NMRConsoleFactory();
        consoleFactory.openConsole();
        
        layout.addView(NMRExplorer.ID, IPageLayout.LEFT, 0.25f, editor);
        layout.addView(NMRDiagEditor.ID, IPageLayout.TOP, 0.25f, editor);
        
        layout.addView(IConsoleConstants.ID_CONSOLE_VIEW, IPageLayout.BOTTOM,
                0.75f, editor);
                
        layout.addPlaceholder(IPageLayout.ID_PROP_SHEET, IPageLayout.RIGHT,
                0.75f, editor);
    }
    
}
