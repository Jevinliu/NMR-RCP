package edu.xmu.nmr.dataanalysis.diagram.multieditor;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import edu.xmu.nmr.dataanalysis.diagram.layouts.LayoutUtils;
import edu.xmu.nmrdataanalysis.diagram.model.Container;
import edu.xmu.nmrdataanalysis.diagram.model.ContainerType;
import edu.xmu.nmrdataanalysis.diagram.model.FidData;
import edu.xmu.nmrdataanalysis.diagram.model.HorizontalRuler;
import edu.xmu.nmrdataanalysis.diagram.model.RulerOrient;
import edu.xmu.nmrdataanalysis.diagram.model.VerticalRuler;

/**
 * <p>
 * {@link NMRDiagEditor}的Fid页面，主要用来显示Fid图形
 * </p>
 * 
 * @author Jevin
 *         
 */
public class FidEditorPage extends DAAbstractGraphicalEditor {
    
    private Logger log = Logger.getLogger(this.getClass());
    public static final String ID = "edu.xmu.nmr.dataAnalysis.diagram.editorparts.fidEditorPage";
    private FidData fidData; // 模型节点
    private VerticalRuler leftRuler, rightRuler;
    private HorizontalRuler bottomRuler, topRuler;
    private Container fidContainer;
    
    @Override public void init(IEditorSite site, IEditorInput input)
            throws PartInitException {
        if (!(input instanceof FidEditorInput)) {
            log.error("FidEditorPage's editorInput is error.");
            return;
        }
        super.init(site, input);
    }
    
    public FidEditorPage() {
        super();
        fidData = new FidData();
        leftRuler = new VerticalRuler();
    }
    
    @Override protected void configureGraphicalViewer() {
        super.configureGraphicalViewer();
        GraphicalViewer viewer = getGraphicalViewer();
        ScalableRootEditPart rootPart = (ScalableRootEditPart) viewer
                .getRootEditPart();
        rootPart.getContentPane().setLayoutManager(new XYLayout());
        viewer.getControl().addControlListener(new ControlAdapter() {
            public void controlResized(ControlEvent e) {
                FigureCanvas fc = (FigureCanvas) e.getSource();
                fc.setScrollBarVisibility(FigureCanvas.NEVER);
                fidContainer.setLayout(fc.getViewport().getBounds().getCopy());
                fidData.setSize(new Dimension(
                        fc.getViewport().getBounds().width - 4 - 13 - 40,
                        fc.getViewport().getBounds().height - 4 - 13 - 40));
            }
        });
    }
    
    /**
     * 设置model层的fid数据
     * 
     * @param rawData
     *            原始数据，
     * @param stride
     *            原始数据点间的原始间隔，如采样时间
     */
    public void setFidData(ArrayList<Float> rawData, float stride) {
        
        if (fidData == null) {
            log.error("Fid' model is error.");
            return;
        }
        fidData.setRawData(rawData);
        fidData.setStride(stride);
    }
    
    @Override protected void createActions() {
        super.createActions();
    }
    
    @Override protected void initializeGraphicalViewer() {
        GraphicalViewer viewer = getGraphicalViewer();
        viewer.setContents(createContainer()); // 设置model
    }
    
    /**
     * 创建绘图所需要的model层
     */
    private Container createContainer() {
        
        fidContainer = new Container();
        fidContainer.setCType(ContainerType.DIAGCONTAINER);
        fidContainer.setLayout(LayoutUtils.getClientArea());
        leftRuler.setOrient(RulerOrient.LEFT);
        leftRuler.setParent(fidContainer);
        rightRuler = new VerticalRuler();
        rightRuler.setOrient(RulerOrient.RIGHT);
        rightRuler.setParent(fidContainer);
        fidData.setParent(fidContainer);
        fidData.addPropertyChangeListener(leftRuler);
        fidData.addPropertyChangeListener(rightRuler);
        bottomRuler = new HorizontalRuler();
        bottomRuler.setOrient(RulerOrient.BOTTOM);
        bottomRuler.setParent(fidContainer);
        topRuler = new HorizontalRuler();
        topRuler.setOrient(RulerOrient.TOP);
        topRuler.setParent(fidContainer);
        fidData.addPropertyChangeListener(topRuler);
        fidData.addPropertyChangeListener(bottomRuler);
        return fidContainer;
    }
    
    @Override public void dispose() {
        if (fidData != null && leftRuler != null && bottomRuler != null) {
            fidData.removePropertyChangeListener(leftRuler);
            fidData.removePropertyChangeListener(bottomRuler);
            fidData.removePropertyChangeListener(rightRuler);
            fidData.removePropertyChangeListener(topRuler);
        }
        super.dispose();
    }
    
    @Override public void doSave(IProgressMonitor monitor) {
    
    }
}
