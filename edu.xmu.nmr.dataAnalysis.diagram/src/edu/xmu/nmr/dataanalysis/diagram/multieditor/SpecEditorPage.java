/**
 * @Title SpecEditorPage.java
 * @Package edu.xmu.nmr.dataanalysis.diagram.multieditor
 * @brief TODO
 * Copyright Copyright (c) 2015
 * Wuhan Zhongke Magnetic Resonance
 * Technology Co., Ltd.
 *
 * @author Jevin
 * @date 2016年1月20日 下午1:42:53
 * @version V1.0
 */
package edu.xmu.nmr.dataanalysis.diagram.multieditor;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import edu.xmu.nmr.dataanalysis.diagram.actions.FFTAction;
import edu.xmu.nmr.dataanalysis.diagram.layouts.LayoutUtils;
import edu.xmu.nmrdataanalysis.diagram.model.Container;
import edu.xmu.nmrdataanalysis.diagram.model.ContainerType;
import edu.xmu.nmrdataanalysis.diagram.model.FidData;
import edu.xmu.nmrdataanalysis.diagram.model.HorizontalRuler;
import edu.xmu.nmrdataanalysis.diagram.model.Ruler;
import edu.xmu.nmrdataanalysis.diagram.model.RulerOrient;
import edu.xmu.nmrdataanalysis.diagram.model.VerticalRuler;

/**
 * SpecEditorPage
 * <p>
 * 添加谱图编辑区
 * </p>
 * 
 * @see
 */
public class SpecEditorPage extends DAAbstractGraphicalEditor {
    
    private Logger log = Logger.getLogger(this.getClass());
    private Container specContainer;
    private ArrayList<Float> fidDataSets;
    private FidData specNode;
    private boolean fidIsComplex = false;
    
    public SpecEditorPage() {
        super();
        specContainer = new Container();
    }
    
    @Override public void init(IEditorSite site, IEditorInput input)
            throws PartInitException {
        if (!(input instanceof SpecEditorInput)) {
            log.error("SpecEditorPage's editorInput is error.");
            return;
        }
        super.init(site, input);
        
    }
    
    public ArrayList<Float> getFidDataSets() {
        return fidDataSets;
    }
    
    public void setFidDataSets(ArrayList<Float> fidDataSets) {
        this.fidDataSets = fidDataSets;
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
                specContainer.setLayout(fc.getViewport().getBounds().getCopy());
                specNode.setSize(new Dimension(
                        fc.getViewport().getBounds().width - 4 - 13 - 40,
                        fc.getViewport().getBounds().height - 4 - 13 - 40));
            }
        });
    }
    
    /**
     * 
     * @see org.eclipse.gef.ui.parts.GraphicalEditor#initializeGraphicalViewer()
     */
    @Override protected void initializeGraphicalViewer() {
        GraphicalViewer viewer = getGraphicalViewer();
        viewer.setContents(createContainer()); // 设置model
    }
    
    /**
     * @param monitor
     * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
     */
    @Override public void doSave(IProgressMonitor monitor) {
    
    }
    
    @Override protected void createActions() {
        super.createActions();
        ActionRegistry registry = getActionRegistry();
        IAction fftAction = new FFTAction(this);
        registry.registerAction(fftAction);
        getSelectionActions().add(fftAction.getId());
    }
    
    private Container createContainer() {
        specContainer.setCType(ContainerType.DIAGCONTAINER);
        specContainer.setLayout(LayoutUtils.getClientArea());
        Ruler leftRuler = new VerticalRuler();
        leftRuler.setParent(specContainer);
        leftRuler.setOrient(RulerOrient.LEFT);
        specNode = new FidData();
        specNode.setParent(specContainer);
        specNode.addPropertyChangeListener(leftRuler);
        Container placeholder = new Container();
        placeholder.setCType(ContainerType.PLACEHOLDER);
        placeholder.setParent(specContainer);
        Ruler bottomRuler = new HorizontalRuler();
        bottomRuler.setOrient(RulerOrient.BOTTOM);
        bottomRuler.setParent(specContainer);
        specNode.addPropertyChangeListener(bottomRuler);
        return specContainer;
    }
    
    public Container getDiagContainer() {
        return specContainer;
    }
    
    public FidData getSpecNode() {
        return specNode;
    }
    
    public boolean isFidIsComplex() {
        return fidIsComplex;
    }
    
    public void setFidIsComplex(boolean fidIsComplex) {
        this.fidIsComplex = fidIsComplex;
    }
}
