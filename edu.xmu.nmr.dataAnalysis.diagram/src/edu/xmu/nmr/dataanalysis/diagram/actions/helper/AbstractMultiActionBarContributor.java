package edu.xmu.nmr.dataanalysis.diagram.actions.helper;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.RetargetAction;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;

public abstract class AbstractMultiActionBarContributor extends
		MultiPageEditorActionBarContributor {

	private ActionRegistry registry = new ActionRegistry();
	private List retargetActions = new ArrayList();
	private List globalActionKeys = new ArrayList();

	protected void addAction(IAction action) {
		getActionRegistry().registerAction(action);
	}

	protected void addGlobalActionKey(String key) {
		globalActionKeys.add(key);
	}

	protected void addRetargetAction(RetargetAction action) {
		addAction(action);
		retargetActions.add(action);
		getPage().addPartListener(action);
		addGlobalActionKey(action.getId());
	}

	protected ActionRegistry getActionRegistry() {
		return registry;
	}

	@Override
	public void setActivePage(IEditorPart activeEditor) {
		ActionRegistry registry = (ActionRegistry) activeEditor
				.getAdapter(ActionRegistry.class);
		IActionBars bars = getActionBars();
		for (int i = 0; i < globalActionKeys.size(); i++) {
			String id = (String) globalActionKeys.get(i);
			IAction handler = registry != null ? registry.getAction(id) : null;
			bars.setGlobalActionHandler(id, handler);
		}
		// 此句必不可少，不然toolBar不可用
		bars.updateActionBars();
	}

	public void init(IActionBars bars) {
		buildActions();
		declareGlobalActionKeys();
		super.init(bars);
	}

	public void dispose() {
		for (int i = 0; i < retargetActions.size(); i++) {
			RetargetAction action = (RetargetAction) retargetActions.get(i);
			getPage().removePartListener(action);
			action.dispose();
		}
		registry.dispose();
		retargetActions = null;
		registry = null;
	}

	protected IAction getAction(String id) {
		return getActionRegistry().getAction(id);
	}

	public abstract void buildActions();

	public abstract void declareGlobalActionKeys();

}
