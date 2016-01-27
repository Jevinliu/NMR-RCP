/**
 * @Title DAAbstractGraphicalEditor.java
 * @Package edu.xmu.nmr.dataanalysis.diagram.multieditor
 * @brief 绘图编辑页面的父类
 * Copyright Copyright (c) 2015
 * Wuhan Zhongke Magnetic Resonance
 * Technology Co., Ltd.
 *
 * @author Jevin
 * @date 2016年1月21日 下午3:16:11
 * @version V1.0
 */
package edu.xmu.nmr.dataanalysis.diagram.multieditor;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.SWT;
import org.eclipse.ui.actions.ActionFactory;

import edu.xmu.nmr.dataanalysis.diagram.actions.DAPartZoomInAction;
import edu.xmu.nmr.dataanalysis.diagram.actions.DAXMoveAction;
import edu.xmu.nmr.dataanalysis.diagram.actions.DAYMoveAction;
import edu.xmu.nmr.dataanalysis.diagram.actions.DAZoomInAction;
import edu.xmu.nmr.dataanalysis.diagram.actions.DAZoomOutAction;
import edu.xmu.nmr.dataanalysis.diagram.actions.ShowFullAction;
import edu.xmu.nmr.dataanalysis.diagram.actions.helper.DAActionConstants;
import edu.xmu.nmr.dataanalysis.diagram.actions.helper.DADefaultEditDomain;
import edu.xmu.nmr.dataanalysis.diagram.actions.helper.DAZoomManager;
import edu.xmu.nmr.dataanalysis.diagram.editparts.DAEditPartFactory;

/**
 * DAAbstractGraphicalEditor
 * <p>
 * 继承自{@link GraphicalEditor},作为{@link FidEditorPage}与{@link SpecEditorPage}
 * 的父类，提供公共的接口。
 * </p>
 * 
 * @see
 */
public abstract class DAAbstractGraphicalEditor extends GraphicalEditor {
    
    public DAAbstractGraphicalEditor() {
        setEditDomain(new DADefaultEditDomain(this));
    }
    
    @Override protected void configureGraphicalViewer() {
        GraphicalViewer viewer = getGraphicalViewer();
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
    
    @Override public GraphicalViewer getGraphicalViewer() {
        return super.getGraphicalViewer();
    }
    
    @Override protected void createActions() {
        super.createActions();
        ActionRegistry registry = getActionRegistry();
        IAction yMoveAction = new DAYMoveAction(this);
        registry.registerAction(yMoveAction);
        getSelectionActions().add(yMoveAction.getId());
        
        IAction xMoveAction = new DAXMoveAction(this);
        registry.registerAction(xMoveAction);
        getSelectionActions().add(xMoveAction.getId());
        
        IAction partZoominAction = new DAPartZoomInAction(this);
        registry.registerAction(partZoominAction);
        getSelectionActions().add(partZoominAction.getId());
        
        IAction zoomInAction = new DAZoomInAction(this);
        registry.registerAction(zoomInAction);
        getSelectionActions().add(zoomInAction.getId());
        
        IAction zoomOutAction = new DAZoomOutAction(this);
        registry.registerAction(zoomOutAction);
        getSelectionActions().add(zoomOutAction.getId());
        
        IAction showFullAction = new ShowFullAction(this);
        registry.registerAction(showFullAction);
        getSelectionActions().add(showFullAction.getId());
    }
    
}
