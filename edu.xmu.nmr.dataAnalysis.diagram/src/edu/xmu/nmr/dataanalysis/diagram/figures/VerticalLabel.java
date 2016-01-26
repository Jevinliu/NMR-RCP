package edu.xmu.nmr.dataanalysis.diagram.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;

public class VerticalLabel extends Figure {
    
    private String text;
    
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
    
    @Override protected void paintFigure(Graphics graphics) {
        super.paintFigure(graphics);
        if (text == null || text.equals("")) {
            return;
        }
        graphics.pushState();
        graphics.translate(bounds.x, bounds.y);
        graphics.rotate(90);
        graphics.drawString(text, 5, -14);
        graphics.popState();
    }
}
