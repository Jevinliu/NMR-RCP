package edu.xmu.nmr.dataanalysis.diagram.multieditor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import edu.xmu.nmr.dataanalysis.diagram.others.ParamsTCProvider;
import edu.xmu.nmr.dataanalysis.diagram.others.ParamsTLProvider;
import edu.xmu.nmr.dataanalysis.diagram.others.ParamsValueEditingSupport;

public class ParamsEditorPage extends EditorPart implements
		PropertyChangeListener {

	public static final String ID = "edu.xmu.nmr.dataAnalysis.diagram.editorparts.paramseditorpage";
	private TableViewer tViewer;
	private boolean isParamsPageDirty = false;
	private ParamsValueEditingSupport pes;
	private NMRDiagEditor parent;
	public static final String PRO_PARAM_DIRTY = "isParamsPageDirty";

	public ParamsEditorPage() {
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		if (!(input instanceof ParamsEditorInput))
			return;
		setSite(site); // 单独一个editorpart时，该语句必不可少
		setInput(input);
	}

	@Override
	public void addPropertyListener(IPropertyListener listener) {

	}

	@Override
	public void createPartControl(Composite parent) {
		tViewer = new TableViewer(parent, SWT.FULL_SELECTION | SWT.BORDER);
		tViewer.getTable().setLinesVisible(true);
		tViewer.getTable().setHeaderVisible(true);

		TableViewerColumn columnName = new TableViewerColumn(tViewer, SWT.NONE);
		columnName.getColumn().setAlignment(SWT.LEFT);
		columnName.getColumn().setText("Name");

		TableViewerColumn columnValue = new TableViewerColumn(tViewer, SWT.NONE);
		columnValue.getColumn().setAlignment(SWT.LEFT);
		columnValue.getColumn().setText("Value");
		pes = new ParamsValueEditingSupport(tViewer);
		columnValue.setEditingSupport(pes);
		pes.addPropertyChangeListener(this);
		TableLayout layout = new TableLayout();
		tViewer.getTable().setLayout(layout);
		layout.addColumnData(new ColumnWeightData(30));
		layout.addColumnData(new ColumnWeightData(70));

		tViewer.setContentProvider(new ParamsTCProvider());
		tViewer.setLabelProvider(new ParamsTLProvider());

	}

	public TableViewer gettViewer() {
		return tViewer;
	}

	public NMRDiagEditor getParent() {
		return parent;
	}

	public void setParent(NMRDiagEditor parent) {
		this.parent = parent;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(ParamsValueEditingSupport.PRO_DIRTY)) {
			isParamsPageDirty = (boolean) evt.getNewValue();
			firePropertyChange(PROP_DIRTY);
			if (parent != null)
				parent.handlePropertyChange(PROP_DIRTY);
		}

	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		isParamsPageDirty = false;
	}

	@Override
	public void doSaveAs() {
		isParamsPageDirty = false;
	}

	@Override
	public boolean isDirty() {
		return isParamsPageDirty;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setFocus() {
		tViewer.getTable().setFocus();
	}

}
