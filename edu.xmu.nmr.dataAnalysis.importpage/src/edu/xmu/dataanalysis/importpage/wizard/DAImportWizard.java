package edu.xmu.dataanalysis.importpage.wizard;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import edu.xmu.dataanalysis.explorer.view.NMRExplorer;
import edu.xmu.dataanalysis.importpage.tree.TreeObject;
import edu.xmu.dataanalysis.importpage.tree.TreeParent;
import edu.xmu.nmr.dataanalysis.logic.datacenter.FileType;
import edu.xmu.nmr.dataanalysis.logic.datacenter.NMRDataCenter;
import edu.xmu.nmr.dataanalysis.logic.explorermodel.AbstractDataNode;
import edu.xmu.nmr.dataanalysis.logic.explorermodel.DotNMRNode;
import edu.xmu.nmr.dataanalysis.logic.explorermodel.FidNode;
import edu.xmu.nmr.dataanalysis.logic.explorermodel.FolderNode;
import edu.xmu.nmr.dataanalysis.logic.explorermodel.ParamtersNode;
import edu.xmu.nmr.dataanalysis.logic.explorermodel.VersionNode;

public class DAImportWizard extends Wizard implements IImportWizard {

	private Logger log = Logger.getLogger(this.getClass());
	private ImportPage importPage;
	private String subDir; // 源文件的被选中的根目录
	protected static final String NMR_EXT = ".nmr";
	protected String selectedDirectory;
	protected Map<String, TreeParent> leftToRight;
	protected TreeParent leftRoot;
	protected FileType fileType;

	@Override
	public void addPages() {
		super.addPages();
		addPage(importPage);
	}

	@Override
	public boolean performFinish() {
		if (!checkAvailable()) {
			return false;
		}
		subDir = selectedDirectory.substring(0,
				selectedDirectory.lastIndexOf(File.separator));
		if (leftRoot.hasChildren()) {
			TreeObject child = leftRoot.getChildren()[0];
			try {
				NMRExplorer explorer = (NMRExplorer) PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.showView(NMRExplorer.ID);
				FolderNode trRoot = NMRDataCenter.getInstance().getNmrData();
				this.setupImportProgress(child, subDir, leftToRight, trRoot);

				explorer.getCommonViewer().setInput(trRoot);
				explorer.getCommonViewer().refresh(trRoot);
				return true;
			} catch (PartInitException e) {
				e.printStackTrace();
			}

		}
		return false;
	}

	/**
	 * 创建进程监视器
	 * 
	 * @param child
	 * @param subDir
	 * @param leftToRight
	 * @param trRoot
	 */
	private void setupImportProgress(final TreeObject child,
			final String subDir, final Map<String, TreeParent> leftToRight,
			final FolderNode trRoot) {
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(getContainer()
				.getShell());
		IRunnableWithProgress runnable = new IRunnableWithProgress() {

			@Override
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				// boolean isCanAdded = isCanAddExplorerNode(child);
				monitor.beginTask("Import Progress", IProgressMonitor.UNKNOWN);
				getCheckedFilesToExplorer(child, subDir, leftToRight, trRoot,
						false, false);
			}
		};
		try {
			dialog.run(true, true, runnable);
		} catch (InvocationTargetException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 检查传入文件结点是否合法
	 */
	protected Boolean checkAvailable() {
		initSomeParams();
		if (selectedDirectory == null || selectedDirectory.equals("")
				|| leftToRight.isEmpty() || leftRoot == null) {
			log.error("File is not illegal，you should check file type.");
			return false;
		}
		return true;
	}

	/**
	 * 获得选中的文件，并将相应地文件结点添加的数据导航结点中，添加时要转换结点结构为： 用户主目录-实验-abc.nmr-版本号-fid的结点结构
	 * 
	 * @param obj
	 *            左边树视图的节点
	 * @param path
	 *            当前文件的路径 filePath = （subDir + getName）
	 * @param leftToRight
	 *            右边树视图的节点
	 * @param isCanAdded
	 *            标识是否开始添加导航结点
	 * @param isAdded
	 *            标识是否已经添加导航节点
	 */
	private void getCheckedFilesToExplorer(TreeObject treeObj, String path,
			Map<String, TreeParent> leftToRight, AbstractDataNode parent,
			boolean isCanAdded, boolean isAdded) {

		AbstractDataNode temp = null;
		if (!treeObj.isChecked()) {
			treeObj.getParent().removeChild(treeObj); // 移除没有选中的本节点
			return;
		}
		if (!isAdded) {
			isCanAdded = isCanAdded || isCanAddExplorerNode(treeObj);
		}
		String directoryPath = path + File.separator + treeObj.getName();
		String nodeName = treeObj.getName();
		if (canConvertToDotNmr(treeObj)) {
			AbstractDataNode tParent = parent;
			if (!isAdded) { // 当检测到文件夹的输入路径直接从abc.nmr开始时，要直接创建父节点，然后解析文件
				tParent = (AbstractDataNode) judgeNodeExistsAndAddFolderNode(
						parent, path);
			}
			nodeName = trimExtension(nodeName) + NMR_EXT;
			if (isThisNodeExists(tParent, nodeName) != null) {
				log.info("Import files are same.");
				return;
			}
			DotNMRNode p = new DotNMRNode(getNodePath(tParent, nodeName),
					fileType, nodeName);
			log.debug(p.toString());
			tParent.addChild(p);
			findKeySet(directoryPath, leftToRight, p);
			return;// 该条结点路径添加完毕，返回
		} else if (isAdded) { // 如果已经添加了父目录节点，而此时还没有到.nmr节点，则添加FolderNode节点
			temp = new FolderNode(getNodePath(parent, nodeName), fileType,
					nodeName);
			parent.addChild(temp);
		}

		// 判断是否可以在此处开始添加导航结点
		if (!isAdded && isCanAdded) {
			AbstractDataNode tParent = (AbstractDataNode) judgeNodeExistsAndAddFolderNode(
					parent, path);
			temp = (AbstractDataNode) judgeNodeExistsAndAddFolderNode(tParent,
					nodeName);
			log.debug(temp.toString());
			isAdded = true;
		} else {
			if (temp == null)
				temp = parent;
		}
		if (treeObj instanceof TreeParent) {
			for (TreeObject treeObject : ((TreeParent) treeObj).getChildren()) {
				getCheckedFilesToExplorer(treeObject, directoryPath,
						leftToRight, temp, isCanAdded, isAdded);
			}
		}
	}

	/**
	 * 去除文件结点扩展名，主要对varian文件夹上得.fid文件进行处理
	 * 
	 * @param nodeName
	 * @return
	 */
	private String trimExtension(String nodeName) {
		if (nodeName.contains(".")) {
			return nodeName.substring(0, nodeName.lastIndexOf("."));
		}
		return nodeName;
	}

	/**
	 * 判断该父节点下是否已经有要加入的结点,如果有，将该结点返回，如果没有创建新节点，
	 * 
	 * @param parent
	 * @param nodeName
	 * @return
	 */
	private AbstractDataNode judgeNodeExistsAndAddFolderNode(
			AbstractDataNode parent, String nodeName) {
		AbstractDataNode ab = isThisNodeExists(parent, nodeName);
		if (ab != null) {
			return ab;
		}
		FolderNode etp = new FolderNode(getNodePath(parent, nodeName),
				fileType, nodeName);
		parent.addChild(etp);
		return etp;
	}

	private String getNodePath(AbstractDataNode parent, String nodeName) {
		if (parent.getNodePath().trim().equals("")) {
			return nodeName;
		}
		return parent.getNodePath().trim() + File.separator + nodeName;
	}

	/**
	 * 判断该父节点下是否已经有要加入的结点
	 * 
	 * @param parent
	 * @param nodeName
	 * @return
	 */
	private AbstractDataNode isThisNodeExists(AbstractDataNode parent,
			String nodeName) {
		if (parent.hasChildren()) { // 如果该导航结点已经有相应地文件夹结点，则直接添加其子节点
			for (AbstractDataNode eto : parent.getChildren()) {
				if (eto.getName().equals(nodeName)) {
					return eto;
				}
			}
		}
		return null;
	}

	// private AbstractDataModel judgeNodeExistsAndAddDotNMRNode(
	// AbstractDataModel parent, String nodeName) {
	// AbstractDataModel ab = isThisNodeExists(parent, nodeName);
	// if (ab != null) {
	// return ab;
	// }
	// DotNMRNode etp = new DotNMRNode(getNodePath(parent, nodeName),
	// fileType, nodeName);
	// parent.addChild(etp);
	// return etp;
	// }

	/**
	 * 找到要解析的文件 （继承该类时重写该方法）
	 * 
	 * @param directoryPath
	 *            导入数据文件的直接父目录，
	 * @param leftToRight
	 * @param parent
	 *            数据导航节点，该结点名为：abc.nmr形式
	 */
	protected void findKeySet(String directoryPath,
			Map<String, TreeParent> leftToRight, AbstractDataNode parent) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		for (String key : leftToRight.keySet()) {
			String path = directoryPath + File.separator + "pdata";
			if (key.contains(path)) {
				TreeParent rightRoot = leftToRight.get(key);
				for (TreeObject rightObj : rightRoot.getChildren()) {
					if (rightObj.isChecked()
							&& judgeFileIsNeed(rightObj.getName())) {
						if (rightObj.getName().equalsIgnoreCase("procs")) {
							File file = new File(key + File.separator
									+ rightObj.getName());
							long millis = file.lastModified();
							cal.setTimeInMillis(millis);
							String version = "version "
									+ formatter.format(cal.getTime());
							VersionNode etp = new VersionNode(
									parent.getNodePath() + File.separator
											+ version, fileType, version);
							parent.addChild(etp);
							FidNode fid = new FidNode(etp.getNodePath()
									+ File.separator + "fid", fileType, "fid");
							ParamtersNode params = new ParamtersNode(
									etp.getNodePath() + File.separator
											+ "paramters", fileType);
							etp.addChild(fid);
							etp.addChild(params);
							this.parserFile(directoryPath, key, version, etp);
						}
					}
				}
			}
		}
	}

	/**
	 * 根据文件名判断该文件是否需要的文件类型 (继承该类时重写该方法)
	 * 
	 * @param fileName
	 *            文件名
	 * @return boolean 如果是需要的文件类型，返回true，否则返回false
	 */
	protected boolean judgeFileIsNeed(String fileName) {
		if (fileName.equals("acqus") || fileName.equals("fid")
				|| fileName.equals("procs") || fileName.equals("title")
				|| fileName.equals("ser")) {
			return true;
		}
		return false;
	}

	/**
	 * 判断该节点是否满足开始添加数据导航结点的条件，主要是为了满足：用户主目录-实验-abc.nmr-版本号-fid的结点结构
	 * 
	 * @param object
	 * @return boolean 如果该节点满足返回true
	 */
	// protected boolean canAddExplorerNode(TreeObject object) {
	// if (object instanceof TreeParent) {
	// if (((TreeParent) object).hasChildren()) {
	// return canConvertToDotNmr(((TreeParent) object).getChildren()[0]);
	// }
	// }
	// return false;
	// }

	/**
	 * 
	 * @param object
	 *            判断是否该节点下的子节点可以开始添加数据导航节点
	 * @return boolean 如果该节点下有一个子节点可以开始添加数据导航节点则返回true，若都不满足返回false
	 */
	protected boolean isCanAddExplorerNode(TreeObject object) {
		if (object instanceof TreeParent && ((TreeParent) object).hasChildren()) {
			for (TreeObject obj : ((TreeParent) object).getChildren()) {
				if (canConvertToDotNmr(obj)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 根据输入的结点，判断是否应该将该结点名转变为 实验名.nmr,如 abc.nmr （继承该类时可能要重写该方法）
	 * 
	 * @param object
	 *            当前结点名
	 * @return
	 */
	protected boolean canConvertToDotNmr(TreeObject object) {
		if (object instanceof TreeParent) {
			if (((TreeParent) object).hasChildren()) {
				if (isNumeric(object.getName())
						&& ((TreeParent) object).getChildren()[0].getName()
								.equalsIgnoreCase("pdata"))
					return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否全为数字
	 * 
	 * @param s
	 *            要判断的字符串
	 * @return
	 */
	private boolean isNumeric(String s) {
		if (s != null)
			return s.matches("^[0-9]+$");
		else
			return false;
	}

	/**
	 * 对数据文件或参数文件进行解析
	 * 
	 * @param fidFilePath
	 *            fid源文件路径
	 * @param fileProcsPath
	 *            procs源文件路径
	 * @param version
	 *            版本号
	 */
	protected void parserFile(String fidFilePath, String fileProcsPath,
			String version, VersionNode versionNode) {
		return;
	}

	@Override
	public boolean canFinish() {
		return super.canFinish();
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("Import Bruker");
		setNeedsProgressMonitor(true);
		importPage = new ImportPage(selection,
				"Select your bruker data's directory.");
	}

	/**
	 * 初始化变量
	 */
	protected void initSomeParams() {
		selectedDirectory = importPage.getSelectedDirectory();
		leftToRight = importPage.getLeftToRight();
		leftRoot = importPage.getLeftRoot();
	}
}
