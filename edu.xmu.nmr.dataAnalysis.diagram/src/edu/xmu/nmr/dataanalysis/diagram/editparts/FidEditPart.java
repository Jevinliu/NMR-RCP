package edu.xmu.nmr.dataanalysis.diagram.editparts;

import java.beans.PropertyChangeEvent;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Layer;
import org.eclipse.draw2d.LayeredPane;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.LayerConstants;

import edu.xmu.nmr.dataanalysis.diagram.editpolicys.DAPartZoomInPolicy;
import edu.xmu.nmr.dataanalysis.diagram.figures.DAGridLayer;
import edu.xmu.nmr.dataanalysis.diagram.figures.LineFigure;
import edu.xmu.nmr.dataanalysis.diagram.pref.helper.DAPrefConstants;
import edu.xmu.nmrdataanalysis.diagram.model.FElement;
import edu.xmu.nmrdataanalysis.diagram.model.FidData;

public class FidEditPart extends DAAbstractEditPart implements LayerConstants {
    
    class FeedbackLayer extends Layer {
        FeedbackLayer() {
            setEnabled(false);
        }
        
        /**
         * @see org.eclipse.draw2d.Figure#getPreferredSize(int, int)
         */
        public Dimension getPreferredSize(int wHint, int hHint) {
            Rectangle rect = new Rectangle();
            for (int i = 0; i < getChildren().size(); i++)
                rect.union(((IFigure) getChildren().get(i)).getBounds());
            return rect.getSize();
        }
        
    }
    
    private LayeredPane innerLayers;
    private LayeredPane printableLayers;
    private IFigure primaryFigure;
    
    public FidEditPart() {
    }
    
    @Override protected IFigure createFigure() {
        innerLayers = new LayeredPane();
        createLayers(innerLayers);
        IFigure fidFigure = getPrimaryFigure();
        getLayer(PRIMARY_LAYER).add(fidFigure, 0);
        return innerLayers;
    }
    
    public IFigure getPrimaryFigure() {
        if (primaryFigure == null) {
            primaryFigure = new LineFigure();
        }
        return primaryFigure;
    }
    
    private void createLayers(LayeredPane layeredPane) {
        layeredPane.add(new DAGridLayer(), GRID_LAYER);
        layeredPane.add(getPrintableLayers(), PRINTABLE_LAYERS);
        layeredPane.add(new FeedbackLayer(), FEEDBACK_LAYER);
    }
    
    private LayeredPane createPrintableLayers() {
        LayeredPane pane = new LayeredPane();
        Layer layer = new Layer();
        layer.setLayoutManager(new StackLayout());
        pane.add(layer, PRIMARY_LAYER);
        return pane;
    }
    
    protected LayeredPane getPrintableLayers() {
        if (printableLayers == null) {
            printableLayers = createPrintableLayers();
        }
        return printableLayers;
    }
    
    @Override public void refreshVisuals() {
        IFigure baseFigure = getFigure();
        baseFigure.getParent().add(baseFigure,
                new GridData(GridData.FILL, GridData.FILL, true, true, 4, 4),
                1);
                
        LineFigure figure = (LineFigure) getPrimaryFigure();
        FidData fidData = (FidData) getModel();
        figure.setRawData(fidData.getRawData()); // 模型层与view层结合，装填数据
        figure.setAbsMax(fidData.getAbsMax());
        figure.setVScale(fidData.getVScale());
        figure.setOffsetY(fidData.getOffsetY());
        figure.setOffsetX(fidData.getOffsetX());
        figure.setHScale(fidData.getHScale());
        figure.setForegroundColor(fidData.getForegroundColor());
        figure.setLineWidth(fidData.getLineWidth());
        if (fidData.isHasBorder()) {
            figure.setBorder(new LineBorder(ColorConstants.lightGray, 1));
        } else
            figure.setBorder(null);
        figure.repaint();
        refreshGrid(fidData);
    }
    
    /**
     * 刷新网格
     * 
     * @param model
     */
    private void refreshGrid(FidData model) {
        DAGridLayer gridLayer = (DAGridLayer) getLayer(GRID_LAYER);
        gridLayer.setBackgroundColor(model.getBackgroundColor());
        if (model.getYAxis() != null) {
            gridLayer.setVInterval(
                    (int) Math.floor(model.getYAxis().get("gap")));
        }
        if (model.getXAxis() != null) {
            gridLayer.setHInterval(
                    (int) Math.floor(model.getXAxis().get("gap")));
        }
        gridLayer.setOffsetX(model.getOffsetX());
        gridLayer.setOffsetY(model.getOffsetY());
        gridLayer.setHasGrid(model.isHasGird());
        gridLayer.repaint();
    }
    
    @Override protected void createEditPolicies() {
        installEditPolicy(DAPartZoomInPolicy.ROLE, new DAPartZoomInPolicy());
    }
    
    @Override public void propertyChange(PropertyChangeEvent evt) {
        String[] eventsName = new String[] { FidData.PRO_FD_FIDDATA,
                FElement.PRO_FE_LAYOUT, FidData.PRO_FD_OFFSETY,
                FidData.PRO_FD_OFFSETX, FidData.PRO_FD_YAXIS,
                FidData.PRO_FD_XAXIS, DAPrefConstants.FID_PREF_FORE_COLOR,
                DAPrefConstants.FID_PREF_BACK_COLOR,
                DAPrefConstants.FID_PREF_HAS_BORDER,
                DAPrefConstants.FID_PREF_HAS_GRID,
                DAPrefConstants.FID_PREF_LINE_WIDTH };
        for (String eventName : eventsName) {
            if (evt.getPropertyName().equals(eventName)) {
                refreshVisuals();
                return;
            }
        }
    }
    
    @Override public IFigure getLayer(Object key) {
        if (innerLayers == null)
            return null;
        IFigure layer = printableLayers.getLayer(key);
        if (layer != null)
            return layer;
        return innerLayers.getLayer(key);
    }
    
    public IFigure getContentPane() {
        return getPrimaryFigure();
    }
}
