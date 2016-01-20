package edu.xmu.nmr.dataanalysis.diagram.multieditor;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;

import edu.xmu.nmr.dataanalysis.diagram.actions.DAMoveAction;
import edu.xmu.nmr.dataanalysis.diagram.actions.DAPartZoomInAction;
import edu.xmu.nmr.dataanalysis.diagram.actions.DAZoomInAction;
import edu.xmu.nmr.dataanalysis.diagram.actions.DAZoomOutAction;
import edu.xmu.nmr.dataanalysis.diagram.actions.helper.DAActionConstants;
import edu.xmu.nmr.dataanalysis.diagram.actions.helper.DADefaultEditDomain;
import edu.xmu.nmr.dataanalysis.diagram.actions.helper.DAZoomManager;
import edu.xmu.nmr.dataanalysis.diagram.editparts.DAEditPartFactory;
import edu.xmu.nmr.dataanalysis.diagram.figures.PointsTools;
import edu.xmu.nmr.dataanalysis.diagram.layouts.LayoutUtils;
import edu.xmu.nmr.dataanalysis.diagram.tool.ZoomTool;
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
public class FidEditorPage extends GraphicalEditor {
    
    private Logger log = Logger.getLogger(this.getClass());
    public static final String ID = "edu.xmu.nmr.dataAnalysis.diagram.editorparts.fidEditorPage";
    private FidData fidData; // 模型节点
    private VerticalRuler leftRuler;
    private HorizontalRuler bottomRuler;
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
        setEditDomain(new DADefaultEditDomain(this));
        ZoomTool zoomTool = new ZoomTool();
        getEditDomain().setDefaultTool(zoomTool);
        getEditDomain().setActiveTool(zoomTool);
        
        fidData = new FidData();
        leftRuler = new VerticalRuler();
    }
    
    @Override public GraphicalViewer getGraphicalViewer() {
        return super.getGraphicalViewer();
    }
    
    @Override protected void configureGraphicalViewer() {
        GraphicalViewer viewer = getGraphicalViewer();
        ScalableRootEditPart rootPart = (ScalableRootEditPart) viewer
                .getRootEditPart();
        rootPart.getContentPane().setLayoutManager(new XYLayout());
        viewer.getControl().addControlListener(new ControlAdapter() {
            public void controlResized(ControlEvent e) {
                FigureCanvas fc = (FigureCanvas) e.getSource();
                fc.setScrollBarVisibility(FigureCanvas.NEVER);
                fidContainer.setLayout(fc.getViewport().getBounds().getCopy());
            }
        });
        viewer.setEditPartFactory(new DAEditPartFactory()); // 添加editpart工厂，通过工厂创建editpart
        viewer.setProperty(DAZoomManager.class.toString(), new DAZoomManager()); // 注册DAZoomManager
        setupKeyHandler();
    }
    
    /**
     * 设置keyHandler
     */
    private void setupKeyHandler() {
        KeyHandler keyHandler = new KeyHandler();
        keyHandler.put(KeyStroke.getPressed(SWT.F3, 0),
                getActionRegistry().getAction(DAActionConstants.DA_ZOOM_IN));
        keyHandler.put(KeyStroke.getPressed(SWT.F4, 0),
                getActionRegistry().getAction(DAActionConstants.DA_ZOOM_OUT));
        keyHandler.put(KeyStroke.getPressed((char) 0x19, 0x79, SWT.CTRL),
                getActionRegistry().getAction(ActionFactory.REDO.getId()));
        keyHandler.put(KeyStroke.getPressed((char) 0x1a, 0x7a, SWT.CTRL),
                getActionRegistry().getAction(ActionFactory.UNDO.getId()));
        getGraphicalViewer().setKeyHandler(keyHandler);
    }
    
    @Override public Object getAdapter(Class type) {
        
        return super.getAdapter(type);
    }
    
    @Override protected void createActions() {
        super.createActions();
        ActionRegistry registry = getActionRegistry();
        IAction moveAction = new DAMoveAction(this);
        registry.registerAction(moveAction);
        getSelectionActions().add(moveAction.getId());
        
        IAction partZoominAction = new DAPartZoomInAction(this);
        registry.registerAction(partZoominAction);
        getSelectionActions().add(partZoominAction.getId());
        
        IAction zoomInAction = new DAZoomInAction(this);
        registry.registerAction(zoomInAction);
        getSelectionActions().add(zoomInAction.getId());
        
        IAction zoomOutAction = new DAZoomOutAction(this);
        registry.registerAction(zoomOutAction);
        getSelectionActions().add(zoomOutAction.getId());
        
    }
    
    /**
     * 设置model层的fid数据
     * 
     * @param rawData
     *            原始数据，
     * @param rawStepSize
     *            原始数据点间的原始间隔，如采样时间
     */
    public void setFidData(ArrayList<Float> rawData, float rawStepSize) {
        
        float absMax = PointsTools.getAbsMax(rawData);
        if (fidData == null || leftRuler == null || bottomRuler == null) {
            log.error("Fid or Axis' model is error.");
            return;
        }
        fidData.setAbsMax(absMax);
        fidData.setRawData(rawData);
        leftRuler.setAbsMax(absMax);
        bottomRuler.setBasicInfo(rawData.size(), rawStepSize);
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
        fidData.setParent(fidContainer);
        fidData.setVInterval(leftRuler.getInterval());
        fidData.addPropertyChangeListener(leftRuler);
        Container placeholderContainer = new Container();
        placeholderContainer.setCType(ContainerType.PLACEHOLDER);
        placeholderContainer.setParent(fidContainer);
        bottomRuler = new HorizontalRuler();
        bottomRuler.setOrient(RulerOrient.BOTTOM);
        bottomRuler.setParent(fidContainer);
        fidData.setHInterval(bottomRuler.getInterval());
        fidData.addPropertyChangeListener(bottomRuler);
        return fidContainer;
    }
    
    @Override public void dispose() {
        if (fidData != null && leftRuler != null) {
            fidData.removePropertyChangeListener(leftRuler);
        }
        super.dispose();
    }
    
    @Override public void doSave(IProgressMonitor monitor) {
    
    }
}
