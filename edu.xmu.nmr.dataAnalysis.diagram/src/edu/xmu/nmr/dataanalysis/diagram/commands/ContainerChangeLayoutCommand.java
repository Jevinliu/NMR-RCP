package edu.xmu.nmr.dataanalysis.diagram.commands;

import org.eclipse.draw2d.geometry.Rectangle;

import edu.xmu.nmrdataanalysis.diagram.model.Container;

public class ContainerChangeLayoutCommand extends AbstractLayoutCommand {

	private Container container;
	private Rectangle layout;
	private Rectangle oldLayout;

	@Override
	public void execute() {
		container.setLayout(layout);
	}

	public ContainerChangeLayoutCommand() {

	}

	@Override
	public void setConstraint(Rectangle rect) {
		this.layout = rect;
	}

	@Override
	public void setModel(Object model) {
		this.container = (Container) model;
		this.oldLayout = this.container.getLayout();
	}

	@Override
	public void undo() {
		this.container.setLayout(this.oldLayout);
	}
}
