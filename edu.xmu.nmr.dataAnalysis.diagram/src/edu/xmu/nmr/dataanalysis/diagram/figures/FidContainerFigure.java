package edu.xmu.nmr.dataanalysis.diagram.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.geometry.Rectangle;

import edu.xmu.nmr.dataanalysis.diagram.layouts.DABorderLayout;

public class FidContainerFigure extends Figure {
    
    public FidContainerFigure() {
        
        DABorderLayout layout = new DABorderLayout();
        setLayoutManager(layout);
        setBorder(new LineBorder(ColorConstants.darkGreen, 2));
        setBackgroundColor(ColorConstants.white);
        setOpaque(true);
    }
    
    public void setLayout(Rectangle rect) {
        getParent().setConstraint(this, rect); // 设置子figure在父figure中的位置
    }
    
}
