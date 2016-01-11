package edu.xmu.nmr.dataanalysis.diagram.others;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

import edu.xmu.nmr.dataanalysis.importvarian.model.ParameterObject;
import edu.xmu.nmr.dataanalysis.importvarian.model.ParamsReal;
import edu.xmu.nmr.dataanalysis.importvarian.model.ParamsString;
import edu.xmu.nmr.dataanalysis.logic.explorermodel.ParamtersNode;

public class ParamsValueEditingSupport extends EditingSupport {

	public static final String PRO_DIRTY = "pro_dirty";
	private final TableViewer viewer;
	private final CellEditor editor;
	private Logger log = Logger.getLogger(this.getClass());
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public ParamsValueEditingSupport(TableViewer viewer) {
		super(viewer);
		this.viewer = viewer;
		this.editor = new TextCellEditor(viewer.getTable());
		this.editor.addListener(new ICellEditorListener() {

			@Override
			public void editorValueChanged(boolean oldValidState,
					boolean newValidState) {
				pcs.firePropertyChange(PRO_DIRTY, null,
						ParamsValueEditingSupport.this.editor.isDirty());

			}

			@Override
			public void cancelEditor() {
				// TODO Auto-generated method stub

			}

			@Override
			public void applyEditorValue() {
				// TODO Auto-generated method stub

			}
		});

	}

	@Override
	public CellEditor getCellEditor(Object element) {
		// TODO Auto-generated method stub
		return editor;
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		if (element instanceof Entry) {
			Object value = ((Entry<String, Object>) element).getValue();
			if (value instanceof ParameterObject) {
				return ((ParameterObject) value).getCurrentValuesTrim();
			} else if (value instanceof ArrayList) {
				return StringArrayUtils.getArrayListToStringTrim((ArrayList) value);
			} else {
				return value.toString();
			}
		}
		return null;
	}

	/**
	 * 根据设置的单元格的值，更新Entry的值
	 */
	@Override
	protected void setValue(Object element, Object value) {
		// cellEditor 刷脏 操作
		editor.setValue(value);
		pcs.firePropertyChange(PRO_DIRTY, null, editor.isDirty());

		Entry<String, Object> input = (Entry<String, Object>) element;
		Object oldObject = input.getValue();
		Object newObject = null;
		try {
			if (oldObject instanceof ParamsString) {
				newObject = ((ParamsString) oldObject).clone();
				((ParamsString) newObject).setCurrentValues(StringArrayUtils
						.setStringTrimToArrayList(value.toString()));
			} else if (oldObject instanceof ParamsReal) {
				newObject = ((ParamsReal) oldObject).clone();

				((ParamsReal) newObject).setCurrentValues(StringArrayUtils
						.setStringTrimToArrayListDouble(value.toString()));
			} else if (oldObject instanceof ArrayList) {
				newObject = StringArrayUtils.setStringTrimToArrayList(value.toString());
			} else {
				newObject = value;
			}
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		log.debug("Change Value, new value: " + value.toString());
		((ParamtersNode) viewer.getInput()).putParam(true, input.getKey(),
				newObject);
		input.setValue(newObject);
		viewer.update(input, null);
	}

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		pcs.addPropertyChangeListener(pcl);
	}

	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		pcs.removePropertyChangeListener(pcl);
	}
}
