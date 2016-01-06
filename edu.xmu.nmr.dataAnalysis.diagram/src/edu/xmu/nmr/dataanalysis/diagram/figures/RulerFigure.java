package edu.xmu.nmr.dataanalysis.diagram.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.TextLayout;

import edu.xmu.nmr.dataanalysis.diagram.layouts.FormatXY;
import edu.xmu.nmrdataanalysis.diagram.model.Ruler;
import edu.xmu.nmrdataanalysis.diagram.model.RulerOrient;

public class RulerFigure extends Figure {

	/**
	 * 每个interval对应的实际的坐标间隔
	 */
	private float stepSize;
	/**
	 * 坐标节点短画线的长度
	 */
	private int tall = 10;
	/**
	 * 当前坐标系的坐标轴的坐落方向
	 */
	private RulerOrient orient;
	/**
	 * 坐标系的像素间隔
	 */
	private int interval;

	public RulerFigure() {
		setForegroundColor(ColorConstants.black);
		setOpaque(false);
		setFont(new Font(null, "Arial", 10, SWT.NORMAL));
	}

	public RulerFigure(float stepSize, RulerOrient orient, int interval) {
		this();
		this.stepSize = stepSize;
		this.orient = orient;
		this.interval = interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public void setStepSize(float stepSize) {
		this.stepSize = stepSize;
	}

	public void setOrient(RulerOrient orient) {
		this.orient = orient;
	}

	public RulerOrient getOrient() {
		return orient;
	}

	public void setLayout(Rectangle rect) {
		getParent().setConstraint(this, rect); // 设置子figure在父figure中的位置
	}

	@Override
	protected void paintFigure(Graphics graphics) {
		super.paintFigure(graphics);
		graphics.pushState();

		Rectangle bounds = getBounds();
		int rulerLabL = Ruler.AXISLL - 5;
		int rightEndX = bounds.x + bounds.width - 1;
		int bottomEndY = bounds.y + bounds.height;
		int centerY = bounds.y + bounds.height() / 2;
		int num = bounds.height / interval;
		switch (orient) {
		case LEFT:
			graphics.drawLine(rightEndX - tall, centerY, rightEndX, centerY);
			TextLayout layout = new TextLayout(null);
			layout.setText("0");
			layout.setAlignment(SWT.RIGHT);
			layout.setWidth(rulerLabL - tall - 1); // 设置文字的宽度，该宽度要去除坐标轴的宽度
			graphics.drawTextLayout(layout, rightEndX - rulerLabL, centerY - 6);
			int i = 1;
			while (i < num / 2 + 1) {
				int aboveY = centerY - interval * i;
				int belowY = centerY + interval * i;
				if (aboveY >= bounds.y || belowY <= bottomEndY) {
					graphics.drawLine(rightEndX, centerY + interval * (i - 1),
							rightEndX, belowY);
					graphics.drawLine(rightEndX - tall, belowY, rightEndX,
							belowY); // 绘制坐标短线
					float y1 = -1 * i * stepSize;
					String sy1 = String.valueOf(y1);
					if (sy1.length() > 7) {
						sy1 = FormatXY.getENotation(y1);
					}
					layout.setText(sy1);
					graphics.drawTextLayout(layout, rightEndX - rulerLabL,
							belowY - 6);
					graphics.drawLine(rightEndX, centerY - interval * (i - 1),
							rightEndX, aboveY);
					graphics.drawLine(rightEndX - tall, aboveY, rightEndX,
							aboveY);
					float y2 = i * stepSize;
					String sy2 = String.valueOf(y2);
					if (sy1.length() > 7) {
						sy2 = FormatXY.getENotation(y2);
					}
					layout.setText(sy2);
					graphics.drawTextLayout(layout, rightEndX - rulerLabL,
							aboveY - 6);
				}
				i++;
			}
			graphics.drawLine(rightEndX, centerY + interval * (i - 2),
					rightEndX, bottomEndY + 1);
			graphics.drawLine(rightEndX, centerY - interval * (i - 1),
					rightEndX, bounds.y);
			break;
		case BOTTOM:
			int j = 1;
			while (j * interval <= bounds.width) {
				int pX = bounds.x + interval * j;
				graphics.drawLine(bounds.x + interval * (j - 1), bounds.y, pX,
						bounds.y);
				graphics.drawLine(bounds.x + interval * j, bounds.y, pX,
						bounds.y + tall);
				float b = j * stepSize;
				String sb = String.valueOf(b);
				if (sb.length() > 7) {
					sb = FormatXY.getENotation(b);
				}
				TextLayout bLayout = new TextLayout(null);
				bLayout.setText(sb);
				bLayout.setWidth(interval - 10);
				bLayout.setAlignment(SWT.CENTER);
				graphics.drawTextLayout(bLayout, pX - interval / 2 + 5,
						bounds.y + tall + 1);
				j++;
			}
			graphics.drawLine(bounds.x + interval * (j - 2), bounds.y,
					rightEndX, bounds.y);
			break;
		}
		graphics.popState();
	}
}
