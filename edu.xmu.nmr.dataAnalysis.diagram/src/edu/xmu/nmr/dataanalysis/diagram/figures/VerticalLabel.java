package edu.xmu.nmr.dataanalysis.diagram.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.swt.SWT;

public class VerticalLabel extends Figure {
    
    private String text;
    private int alignment = SWT.LEFT;
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        if (this.text == null || !this.text.equals(text)) {
            this.text = text;
            repaint();
        }
    }
    
    public VerticalLabel() {
        setSize(40, 40);
        setOpaque(false);
    }
    
    public void setAlignment(int alignment) {
        this.alignment = alignment;
        repaint();
    }
    
    @Override protected void paintFigure(Graphics graphics) {
        super.paintFigure(graphics);
        if (text == null || text.equals("")) {
            return;
        }
        graphics.pushState();
        graphics.translate(bounds.x, bounds.y);
        graphics.rotate(90);
        int y = -14;
        if (alignment == SWT.RIGHT) {
            y = -14 - getBounds().width / 2;
        }
        graphics.drawString(text, 5, y);
        graphics.popState();
    }
}
