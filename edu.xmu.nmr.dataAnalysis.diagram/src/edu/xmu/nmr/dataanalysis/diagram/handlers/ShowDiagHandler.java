package edu.xmu.nmr.dataanalysis.diagram.handlers;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.handlers.RadioState;

import edu.xmu.nmr.dataanalysis.diagram.math.FFTMathUtils;
import edu.xmu.nmr.dataanalysis.diagram.multieditor.FidEditorPage;
import edu.xmu.nmr.dataanalysis.diagram.multieditor.NMRDiagEditor;
import edu.xmu.nmrdataanalysis.diagram.model.NMRDataSetBean;

/**
 * 
 * ShowDiagHandler
 * <p>
 * 显示Fid或者Spec的command的handler
 * </p>
 * 
 * @see
 */
public class ShowDiagHandler extends AbstractHandler {
    
    private Logger log = Logger.getLogger(this.getClass());
    
    @Override public boolean isEnabled() {
        IWorkbenchPart page = PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getActivePage().getActivePart();
        if (page instanceof NMRDiagEditor) {
            if (((NMRDiagEditor) page)
                    .getActiveEditor() instanceof FidEditorPage)
                setBaseEnabled(true);
            else
                setBaseEnabled(false);
        } else {
            setBaseEnabled(false);
        }
        return super.isEnabled();
    }
    
    @Override public Object execute(ExecutionEvent event)
            throws ExecutionException {
        if (HandlerUtil.matchesRadioState(event))
            return null;
        String currentState = event.getParameter(RadioState.PARAMETER_ID);
        HandlerUtil.updateRadioState(event.getCommand(), currentState);
        IWorkbenchPart page = HandlerUtil.getActiveWorkbenchWindow(event)
                .getActivePage().getActivePart();
        FidEditorPage fep = (FidEditorPage) ((NMRDiagEditor) page)
                .getActiveEditor();
        NMRDataSetBean dataSetBean = fep.getNmrDataSetBean();
        if (currentState.trim().equalsIgnoreCase("fid")) {
            log.info(": Chose fid.");
            fep.getDataSetNode()
                    .setRawData(fep.getNmrDataSetBean().getFidDataSet());
            fep.getDataSetNode().setStride(3);
        } else {
            log.info(": Chose spec");
            
            if (dataSetBean.getSpceDataSet() == null) {
                dataSetBean.setSpceDataSet(FFTMathUtils
                        .getComplexFloatFFT_1D(dataSetBean.getFidDataSet()));
            }
            fep.getDataSetNode().setRawData(dataSetBean.getSpceDataSet());
            fep.getDataSetNode().setStride(3);
        }
        return null;
    }
    
}
