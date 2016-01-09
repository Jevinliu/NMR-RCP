package edu.xmu.nmr.dataanalysis.diagram.commands;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

public abstract class DAAbstractLayoutCommand extends Command {

	/**
	 * 设置figure的约束
	 * 
	 * @param rect
	 */
	public abstract void setConstraint(Rectangle rect);

	/**
	 * 设置模型
	 * 
	 * @param model
	 */
	public abstract void setModel(Object model);

}
