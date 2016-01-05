package edu.xmu.nmr.dataanalysis.diagram.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;

import edu.xmu.nmr.dataanalysis.diagram.layouts.FormatXY;
import edu.xmu.nmrdataanalysis.diagram.model.RulerOrient;

public class RulerFigure extends Figure {

	/**
	 * 每个interval对应的实际的坐标间隔
	 */
	private float stepSize;
	/**
	 * 输入的数据绝对值的最大值
	 */
	private float max;

	/**
	 * 主要针对x轴方向上两个点之间诸如采样间隔
	 */
	private float rawStepSize;

	private int dataSize;
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
		setOpaque(true);
		setFont(new Font(null, "Arial", 6, SWT.NORMAL));
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

	public void setRawStepSize(float rawStepSize) {
		this.rawStepSize = rawStepSize;
	}

	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public void setLayout(Rectangle rect) {
		getParent().setConstraint(this, rect); // 设置子figure在父figure中的位置
	}

	@Override
	protected void paintFigure(Graphics graphics) {
		super.paintFigure(graphics);
		graphics.pushState();

		Rectangle bounds = getBounds();
		int rightEndX = bounds.x + bounds.width - 2;
		int bottomEndY = bounds.y + bounds.height;
		int centerY = bounds.y + bounds.height() / 2;
		int num = bounds.height / interval;
		// System.out.println("num :" + num);
		switch (orient) {
		case LEFT:
			// stepSize = 2 * max * interval / bounds.height;
			graphics.drawLine(rightEndX - tall, centerY, rightEndX, centerY);
			graphics.drawText("0", rightEndX - 30, centerY - 4);
			int i = 1;
			while (i < num / 2 + 1) {
				int aboveY = centerY - interval * i;
				int belowY = centerY + interval * i;
				// System.out.println("i* stepsize" + i * stepSize);
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
					graphics.drawText(sy1, rightEndX - 40, belowY - 4); // 绘制坐标值
					graphics.drawLine(rightEndX, centerY - interval * (i - 1),
							rightEndX, aboveY);
					graphics.drawLine(rightEndX - tall, aboveY, rightEndX,
							aboveY);
					float y2 = i * stepSize;
					String sy2 = String.valueOf(y2);
					if (sy1.length() > 7) {
						sy2 = FormatXY.getENotation(y2);
					}
					graphics.drawText(sy2, rightEndX - 40, aboveY - 4);
				}
				i++;
			}
			graphics.drawLine(rightEndX, centerY + interval * (i - 2),
					rightEndX, bottomEndY + 1);
			graphics.drawLine(rightEndX, centerY - interval * (i - 1),
					rightEndX, bounds.y);
			break;
		case RIGHT:
			break;
		case BOTTOM:
			// stepSize = dataSize * rawStepSize / bounds.width;
			int j = 1;
			while (j * interval <= bounds.width) {
				int pX = bounds.x + interval * j;
				graphics.drawLine(bounds.x + interval * (j - 1), bounds.y, pX,
						bounds.y);
				graphics.drawLine(bounds.x + interval * j, bounds.y, pX,
						bounds.y + tall);
				graphics.drawText(String.valueOf(j * stepSize), pX, bounds.y
						+ tall + 30);
				j++;
			}
			graphics.drawLine(bounds.x + interval * (j - 2), bounds.y,
					rightEndX, bounds.y);
			break;
		}
		graphics.popState();
	}
}
