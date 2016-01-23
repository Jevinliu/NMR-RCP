package edu.xmu.nmr.dataanalysis.diagram.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.geometry.Rectangle;

public class FidContainerFigure extends Figure {
    
    public FidContainerFigure() {
        
        GridLayout layout = new GridLayout(5, false);
        layout.marginHeight = 5;
        layout.marginWidth = 5;
        layout.horizontalSpacing = 3;
        layout.verticalSpacing = 3;
        setLayoutManager(layout);
        setBorder(new LineBorder(ColorConstants.gray, 2));
        setBackgroundColor(ColorConstants.white);
        setOpaque(true);
    }
    
    public void setLayout(Rectangle rect) {
        getParent().setConstraint(this, rect); // 设置子figure在父figure中的位置
    }
    
}
