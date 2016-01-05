package edu.xmu.nmr.dataanalysis.diagram.multieditor;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;

import edu.xmu.nmr.custom.extensions.beans.EditorPageBean;
import edu.xmu.nmr.custom.extensions.provider.EditorPageProvider;
import edu.xmu.nmr.dataanalysis.logic.datacenter.FileType;
import edu.xmu.nmr.dataanalysis.logic.explorermodel.FidNode;
import edu.xmu.nmr.dataanalysis.logic.explorermodel.ParamtersNode;

public class NMRDiagEditor extends MultiPageEditorPart {

	public static final String ID = "edu.xmu.nmr.dataAnalysis.diagram.nmreditor";
	private Logger log = Logger.getLogger(this.getClass());
	private FidEditorPage fidEditorPage;
	private ParamsEditorPage paramsEditorPage;
	private boolean isModify = false;

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		if (!(input instanceof FidNode || input instanceof ParamtersNode)) {
			log.error("Diagram editor's input is error");
		}
		super.init(site, input);
	}

	public NMRDiagEditor() {
		super();
	}

	@Override
	protected void createPages() {
		createNMRDiagPage();
	}

	/**
	 * 由自定义插件，获取multipage editor的多个页面
	 */
	private void createNMRDiagPage() {
		List<EditorPageBean> editorPageBeans = EditorPageProvider.getInstance()
				.loadExtensions();
		try {
			if (editorPageBeans != null && !editorPageBeans.isEmpty()) {
				for (EditorPageBean bean : editorPageBeans) {
					IEditorPart part = bean.getEditorPart();
					IEditorInput input = bean.getEditorInput();
					this.initEditorPage(part, input);
					int index = addPage(part, input);
					setPageText(index, bean.getName());
				}
			}
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 初始化editorpage的一些配置
	 * 
	 * @param part
	 * @param input
	 */
	private void initEditorPage(IEditorPart part, IEditorInput input) {
		if (part instanceof FidEditorPage) {
			fidEditorPage = (FidEditorPage) part;
		} else if (part instanceof ParamsEditorPage) {
			paramsEditorPage = (ParamsEditorPage) part;
			paramsEditorPage.setParent(this);
		}
		if (input instanceof FidEditorInput) {
			FidEditorInput fidEditorInput = (FidEditorInput) input;
			fidEditorInput.setNodePath(((ParamtersNode) getEditorInput())
					.getNodePath());
		} else if (input instanceof ParamsEditorInput) {
			ParamsEditorInput paramsEditorInput = (ParamsEditorInput) input;
			paramsEditorInput.setNodePath(((ParamtersNode) getEditorInput())
					.getNodePath());
		}
	}

	/**
	 * <p>
	 * 设置FidEditorPage中的fid data和采样时间即stepSize,用于图形显示时获取数据
	 * </p>
	 * 
	 * @param fidNode
	 *            选中的导航树中的fid节点
	 * @param paramsNode
	 *            选中的导航树中的参数节点
	 */
	public void setFidData(FidNode fidNode, ParamtersNode paramsNode) {
		if (fidNode == null || paramsNode == null || fidEditorPage == null) {
			return;
		}
		float rawStepSize = -1f;
		if (paramsNode.getFileType().equals(FileType.BRUKER)) {
			rawStepSize = 5;
		} else if (paramsNode.getFileType().equals(FileType.VARIAN)) {
			rawStepSize = 3; // 采样间隔
		}
		fidEditorPage.setFidData(fidNode.getData().get("1"), rawStepSize);
	}

	/**
	 * 设置参数页的参数
	 */
	public void setParameters() {
		if (paramsEditorPage == null)
			return;
		paramsEditorPage.gettViewer().setInput(getEditorInput());
		paramsEditorPage.gettViewer().refresh();
	}

	/**
	 * 由内嵌的editor page调用，主要用于操纵property的改变
	 */
	@Override
	protected void handlePropertyChange(int propertyId) {
		if (paramsEditorPage != null && propertyId == IEditorPart.PROP_DIRTY) {
			isModify = paramsEditorPage.isDirty();
		}
		super.handlePropertyChange(propertyId);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		isModify = false;
	}

	@Override
	public boolean isDirty() {
		return isModify || super.isDirty();
	}

	@Override
	public void doSaveAs() {
		isModify = false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	@Override
	public void setFocus() {
		switch (getActivePage()) {
		case 0:
			// tViewer.getTable().setFocus();
			if (paramsEditorPage != null)
				paramsEditorPage.setFocus();
			break;
		case 1:
			if (fidEditorPage != null)
				fidEditorPage.setFocus();
			break;
		}

	}
}
