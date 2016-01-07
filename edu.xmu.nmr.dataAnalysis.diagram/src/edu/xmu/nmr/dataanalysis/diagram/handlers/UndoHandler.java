package edu.xmu.nmr.dataanalysis.diagram.handlers;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.ui.actions.UndoAction;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class UndoHandler extends AbstractHandler {

	private Logger log = Logger.getLogger(this.getClass());

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		IEditorPart ep = HandlerUtil.getActiveEditor(event);
		// 利用适配器获取当前GraphicalViewer
		GraphicalViewer gv = (GraphicalViewer) ep
				.getAdapter(GraphicalViewer.class);
		if (gv == null) {
			log.warn("There is no can used editor.");
			return null;
		}
		if (!gv.getEditDomain().getCommandStack().canUndo()) {
			return null;
		}
		UndoAction undo = new UndoAction(HandlerUtil.getActiveEditor(event));
		undo.run();
		return null;
	}

}
