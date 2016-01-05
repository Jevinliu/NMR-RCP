package edu.xmu.dataanalysis.importpage.wizard;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.progress.ProgressMonitorJobsDialog;

import edu.xmu.dataanalysis.importpage.tree.NavTreeContentProvider;
import edu.xmu.dataanalysis.importpage.tree.NavTreeLabelProvider;
import edu.xmu.dataanalysis.importpage.tree.TreeObject;
import edu.xmu.dataanalysis.importpage.tree.TreeParent;

public class ImportPage extends WizardPage {

	private Combo directoryCombo; // 目录下拉框
	private CheckboxTreeViewer ctvLeft; // 左边文件夹级别的树视图
	private CheckboxTreeViewer ctvRight; // 右边文件级别的树视图
	public Map<String, TreeParent> leftToRight = new HashMap<String, TreeParent>(); // HashMap<文件路径，TreeParent("")->TreeObjects(文件名)>

	private String selectedDirectory;
	private TreeParent leftRoot;
	private ISelection selection;

	public String getSelectedDirectory() {
		return selectedDirectory;
	}

	public void setSelectedDirectory(String selectedDirectory) {
		this.selectedDirectory = selectedDirectory;
	}

	public Map<String, TreeParent> getLeftToRight() {
		return leftToRight;
	}

	public void setLeftToRight(Map<String, TreeParent> leftToRight) {
		this.leftToRight = leftToRight;
	}

	public TreeParent getLeftRoot() {
		return leftRoot;
	}

	public void setLeftRoot(TreeParent leftRoot) {
		this.leftRoot = leftRoot;
	}

	public ImportPage(ISelection selection, String message) {
		super("Select");
		setTitle("Select");
		setMessage(message);
		this.selection = selection;
	}

	@Override
	public void createControl(Composite parent) {
		Composite topComp = new Composite(parent, SWT.NONE);
		GridLayout topLayout = new GridLayout(12, true);
		topComp.setLayout(topLayout);

		GridData laGrid2 = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		GridData txGrid8 = new GridData(SWT.FILL, SWT.FILL, true, false, 8, 1);
		Label titleLabel = new Label(topComp, SWT.NONE);
		titleLabel.setText("From directory:");
		titleLabel.setLayoutData(laGrid2);
		directoryCombo = new Combo(topComp, SWT.NONE);
		directoryCombo.setLayoutData(txGrid8);
		directoryCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateSource(directoryCombo.getText());
			}
		});
		Button browserBtn = new Button(topComp, SWT.NONE);
		browserBtn.setText("Browser");
		browserBtn.setLayoutData(laGrid2);
		browserBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Shell shell = new Shell();
				DirectoryDialog directoryDialog = new DirectoryDialog(shell);
				directoryDialog.setMessage("Select the folder");
				directoryDialog.setText("selected folder");
				directoryDialog.setFilterPath("D:\\");
				selectedDirectory = directoryDialog.open();
				if (selectedDirectory != null) {
					String selectedPath = directoryDialog.getFilterPath();
					// directoryCombo.add(selectedPath);
					// directoryCombo.setText(selectedPath);
					// setDirectoryComboSource(selectedPath);
					// reset(); // 各种缓存复位，
					// importDirectory(selectedPath);
					// setupImportDirProgress(selectedPath);
					// refreshTree();
					updateSource(selectedPath);
				}
			}
		});

		setLeftDirectory(topComp);
		setRightDirectory(topComp);
		Button selectAllBtn = new Button(topComp, SWT.NONE);
		selectAllBtn.setText("Select all");
		selectAllBtn.setLayoutData(laGrid2);
		selectAllBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// ctvLeft.setCheckStateProvider(new CheckStateProvider(true));
				TreeParent root = ((NavTreeContentProvider) ctvLeft
						.getContentProvider()).invisibleRoot;
				checkChildren(root.getChildren()[0], true);
				TreeParent rightRoot = ((NavTreeContentProvider) ctvRight
						.getContentProvider()).invisibleRoot;
				for (TreeObject rightObj : rightRoot.getChildren()) {
					ctvRight.setChecked(rightObj, rightObj.isChecked());
				}
			}
		});
		Button deSelectAllBtn = new Button(topComp, SWT.NONE);
		deSelectAllBtn.setText("Deselect all");
		deSelectAllBtn.setLayoutData(laGrid2);
		deSelectAllBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// ctvLeft.setCheckStateProvider(new CheckStateProvider(false));
				TreeParent root = ((NavTreeContentProvider) ctvLeft
						.getContentProvider()).invisibleRoot;
				checkChildren(root.getChildren()[0], false);
				TreeParent rightRoot = ((NavTreeContentProvider) ctvRight
						.getContentProvider()).invisibleRoot;
				for (TreeObject rightObj : rightRoot.getChildren()) {
					ctvRight.setChecked(rightObj, rightObj.isChecked());
				}
			}
		});
		new Label(topComp, SWT.NONE).setLayoutData(txGrid8);
		ModifyListener importModifyListener = new ImportModifyListener();
		directoryCombo.addModifyListener(importModifyListener);
		setControl(topComp);
	}

	/**
	 * 根据选择的文件夹路径，将文件夹，文件分别导入到左边和右边树视图中
	 * 
	 * @param selectedPath
	 *            选中的文件夹路径
	 */
	private void importDirectory(String selectedPath) {
		NavTreeContentProvider contentProvider = (NavTreeContentProvider) ctvLeft
				.getContentProvider();
		leftRoot = contentProvider.invisibleRoot;
		if (leftRoot.hasChildren()) {
			leftRoot.removeChildren();
		}
		File file = new File(selectedPath);
		scanDirectory(file, leftRoot);
		// ctvLeft.refresh();
		// ctvLeft.setCheckStateProvider(new CheckStateProvider(false));
		NavTreeContentProvider rightCont = (NavTreeContentProvider) ctvRight
				.getContentProvider();
		TreeParent rightRoot = rightCont.invisibleRoot;
		if (rightRoot.hasChildren()) {
			rightRoot.removeChildren();
		}
		rightRoot = leftToRight.get(file.getPath());
		// ctvRight.refresh();
	}

	/**
	 * 在遍历完文件夹后，刷新树节点使用
	 */
	private void refreshTree() {
		ctvLeft.refresh();
		ctvLeft.setCheckStateProvider(new CheckStateProvider(false));
		ctvRight.refresh();
	}

	/**
	 * 根据选择的目录，进行过滤操作并更新显示
	 * 
	 * @param selectedPath
	 *            选择的目录路径
	 */
	private void updateSource(String selectedPath) {
		setDirectoryComboSource(selectedPath);
		reset(); // 各种缓存复位，
		setupImportDirProgress(selectedPath);
		refreshTree();
	}

	/**
	 * 创建导入进程，因为有过滤掉不需要的文件的操作，所以要再选择完文件后遍历所有的文件夹，此时遍历时可能耗时较长，使用进程监视器，
	 * 
	 * @param selectedPath
	 *            选择的导入路径
	 */
	private void setupImportDirProgress(final String selectedPath) {
		@SuppressWarnings("restriction")
		ProgressMonitorDialog dialog = new ProgressMonitorJobsDialog(
				getContainer().getShell());
		IRunnableWithProgress runnable = new IRunnableWithProgress() {

			@Override
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				monitor.beginTask("Select dir progress.",
						IProgressMonitor.UNKNOWN);
				importDirectory(selectedPath);
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
	 * 设置目录下拉框的选项，
	 * 
	 * @param path
	 *            本地目录路径
	 */
	private void setDirectoryComboSource(String path) {
		if (path.length() > 0) {
			String[] currentItems = this.directoryCombo.getItems();
			int selectedIndex = -1;
			for (int i = 0; i < currentItems.length; i++) {
				if (currentItems[i].equals(path))
					selectedIndex = i;
			}
			if (selectedIndex < 0) {
				int oldLength = currentItems.length;
				String[] newItems = new String[oldLength + 1];
				System.arraycopy(currentItems, 0, newItems, 0, oldLength);
				newItems[oldLength] = path;
				this.directoryCombo.setItems(newItems);
				selectedIndex = oldLength;
			}
			this.directoryCombo.select(selectedIndex);
			selectedDirectory = this.directoryCombo.getText();
		}
	}

	/**
	 * 左边树视图的布局
	 * 
	 * @param parent
	 *            父视图
	 */
	private void setLeftDirectory(Composite parent) {
		ScrolledComposite scomp = new ScrolledComposite(parent, SWT.BORDER
				| SWT.H_SCROLL | SWT.V_SCROLL);
		scomp.setExpandHorizontal(true);
		scomp.setExpandVertical(true);
		scomp.setAlwaysShowScrollBars(false);
		scomp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 6, 1));
		scomp.setLayout(new FillLayout());
		Composite comp = new Composite(scomp, SWT.SMOOTH);
		comp.setLayout(new FillLayout());
		ctvLeft = new CheckboxTreeViewer(comp);
		NavTreeContentProvider contentProvider = new NavTreeContentProvider();
		ctvLeft.setContentProvider(contentProvider);
		ctvLeft.setLabelProvider(new NavTreeLabelProvider());
		ctvLeft.setInput(contentProvider.invisibleRoot);
		ctvLeft.addCheckStateListener(new ICheckStateListener() {

			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				checkChildren(event.getElement(), event.getChecked());
				judgeParentAndCheck(event.getElement(), event.getChecked());
				TreeParent rightRoot = ((NavTreeContentProvider) ctvRight
						.getContentProvider()).invisibleRoot;
				for (TreeObject rightObj : rightRoot.getChildren()) {
					ctvRight.setChecked(rightObj, rightObj.isChecked());
				}
			}
		});
		ctvLeft.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {

				Object element = ((StructuredSelection) event.getSelection())
						.getFirstElement();
				if (element == null) {
					return;
				}
				String path = getDirectoryPath(element);
				NavTreeContentProvider navContentProvider = (NavTreeContentProvider) ctvRight
						.getContentProvider();
				final Boolean isChecked = ctvLeft.getChecked(element);

				navContentProvider.invisibleRoot = leftToRight.get(path);
				ctvRight.setCheckStateProvider(new CheckStateProvider(isChecked));
				ctvRight.refresh();
			}
		});
		scomp.setContent(comp);
		scomp.setMinSize(comp.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	/**
	 * 右边树视图布局
	 * 
	 * @param parent
	 *            父视图
	 */
	private void setRightDirectory(Composite parent) {
		ScrolledComposite scomp = new ScrolledComposite(parent, SWT.BORDER
				| SWT.H_SCROLL | SWT.V_SCROLL);
		scomp.setExpandHorizontal(true);
		scomp.setExpandVertical(true);
		scomp.setAlwaysShowScrollBars(false);
		scomp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 6, 1));
		scomp.setLayout(new FillLayout());
		Composite comp = new Composite(scomp, SWT.NONE);
		comp.setLayout(new FillLayout());
		ctvRight = new CheckboxTreeViewer(comp);
		NavTreeContentProvider contentProvider = new NavTreeContentProvider();
		ctvRight.setContentProvider(contentProvider);
		ctvRight.setLabelProvider(new NavTreeLabelProvider());
		ctvRight.setCheckStateProvider(new CheckStateProvider(false));
		ctvRight.setInput(contentProvider.invisibleRoot);
		ctvRight.addCheckStateListener(new ICheckStateListener() {

			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				TreeObject rightObj = (TreeObject) event.getElement();
				rightObj.setChecked(event.getChecked());
				ctvRight.setChecked(rightObj, event.getChecked());
			}

		});
		scomp.setContent(comp);
		scomp.setMinSize(comp.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	/**
	 * 递归方式扫描指定文件夹下的所有文件，并添加到树节点，同时记录文件夹和文件对应关系 ;右边树的模型为 TreeParent
	 * ->TreeObject(s)(文件名)
	 * 
	 * 其中成员变量leftToRight还 用来存储还没有确定文件夹类型的文件夹和文件的对应关系，如，要只留bruker数据，
	 * 先将文件夹节点添加到左边树视图中
	 * ，将该文件夹下地文件节点全部添加到leftToRight中，通过返回值判断该文件是否问bruker文件，结合所有文件的判断情况
	 * ，如果是bruker文件夹，则保留结点， 如果不是bruker文件夹则删除左边树视图的结点，和右边leftToRight的对应关系
	 * 
	 * @param parentFile
	 *            指定文件夹
	 * @param treeParent
	 *            该文件夹对应的树的节点
	 * 
	 */
	public boolean scanDirectory(File parentFile, TreeParent treeParent) {
		String path = parentFile.getPath();
		if (parentFile.isDirectory()) {
			if (!leftToRight.containsKey(path)) {
				TreeParent root = new TreeParent("");
				leftToRight.put(path, root);
			}
			TreeParent tParent;
			if (parentFile.getPath().length() == 3) { // 防止直接选择磁盘
				tParent = new TreeParent(parentFile.getPath().substring(0, 2));
			} else {
				tParent = new TreeParent(parentFile.getName());
			}
			treeParent.addChild(tParent);
			File[] files = parentFile.listFiles();
			if (files == null || files.length == 0) { // 防止文件夹下没有文件，为空
				return false;
			}
			boolean directoryFlag = false;// 标识整个文件夹是否包含需要的文件，如果包含则，保留文件夹；
			boolean filesFlag = false; // 标识该文件夹下既包含文件夹又包含文件的情况，如果包含的文件中不包含需要的文件，则文件需要删除，视情况保留文件夹
			for (int i = 0; i < files.length; i++) {
				boolean res = scanDirectory(files[i], tParent);
				directoryFlag = res || directoryFlag;
				filesFlag = (files[i].isFile() && res) || filesFlag;
			}
			if (!directoryFlag) { // 如果不是需要的文件类型，则删除该文件夹对的结点信息
				treeParent.removeChild(tParent);
				leftToRight.remove(path);
			} else if (!filesFlag) {
				leftToRight.remove(path);
			}
			return directoryFlag;
		} else {
			String pathStr = parentFile.getParentFile().getPath();
			if (leftToRight.containsKey(pathStr)) {
				TreeParent root = leftToRight.get(pathStr);
				root.addChild(new TreeObject(parentFile.getName()));
			}
			return this.judgeFileTypeIsNeed(parentFile.getName());
		}
	}

	/**
	 * 判断文件类型，通过文件名判断该文件夹是否是需要的文件类型，如果是返回true，否则返回false,默认是bruker过滤
	 * 
	 * @param fileName
	 *            当前文件名
	 */
	public boolean judgeFileTypeIsNeed(String fileName) {
		if (fileName.equals("acqus") || fileName.equals("procs")) {
			return true;
		}
		return false;
	}

	/**
	 * 设置当前节点下的所有子节点的选中状态，子节点选中状态要与当前节点选中状态一致
	 * 
	 * @param obj
	 *            TreeParent or TreeObject
	 * @param checked
	 *            当前的节点状态
	 */
	private void checkChildren(Object obj, Boolean checked) {
		if (obj instanceof TreeParent) {
			for (TreeObject treeObject : ((TreeParent) obj).getChildren()) {
				checkChildren(treeObject, checked);
			}
		}
		ctvLeft.setChecked(obj, checked);
		TreeObject treeObj = (TreeObject) obj;
		treeObj.setChecked(checked);
		// 设置右边树中对应节点的状态
		String path = getDirectoryPath(treeObj);
		if (leftToRight.containsKey(path)) {
			TreeParent rightRoot = leftToRight.get(path);
			for (TreeObject rightObj : rightRoot.getChildren()) {
				rightObj.setChecked(checked);
			}
		}

	}

	/**
	 * 设置当前节点的父节点选中状态
	 * 
	 * @param obj
	 *            树节点
	 * @param checked
	 *            树节点选中状态
	 */
	private void checkParent(Object obj, Boolean checked) {
		TreeObject element = (TreeObject) obj;
		element.setChecked(checked);
		if (element.getName() != "") {
			// ctv.setGrayChecked(element, checked);
			TreeParent parent = element.getParent();
			ctvLeft.setChecked(parent, checked);
			checkParent(parent, checked);
		}
	}

	/**
	 * 该方法采用递归方法实现，如果当前选中状态为假，父节点状态为真，若其所有兄弟节点的状态为假，则父节点状态设为假；
	 * 
	 * @param obj
	 *            TreeParent or TreeObject
	 * @param checked
	 *            节点选中状态
	 */
	private void checkParentFalse(Object obj, Boolean checked) {
		TreeObject element = (TreeObject) obj;
		element.setChecked(checked);
		if (element.getName() != "") {
			TreeParent parent = element.getParent();
			if (!checked && ctvLeft.getChecked(parent)) {
				for (TreeObject child : parent.getChildren()) {
					if (ctvLeft.getChecked(child)) {
						return;
					}
				}
				ctvLeft.setChecked(parent, checked);
				checkParentFalse(parent, checked);
			}
		}
	}

	/**
	 * 
	 * 判断选中节点和父节点的选中状态是否相符，如果不相符，改变父节点选中状态
	 * 
	 * @param obj
	 *            TreeParent or TreeObject 选中的树节点
	 * @param checked
	 *            当前节点的选中状态
	 */
	private void judgeParentAndCheck(Object obj, Boolean checked) {
		TreeObject element = (TreeObject) obj;
		if (element.getName() != "") {
			TreeParent parent = element.getParent();
			if (checked && !ctvLeft.getChecked(parent)) { // 如果当前节点为选中状态，其父节点没有选中，设置父节点选中
				checkParent(obj, checked);
			}
			checkParentFalse(obj, checked);
		}
	}

	/**
	 * 根据选中节点，通过遍历，找到当前节点对应的树路径
	 * 
	 * @param obj
	 *            TreeParent or TreeObject
	 * 
	 * @param path
	 *            当前节点的路径
	 * 
	 */
	private StringBuffer getTreePath(Object obj, StringBuffer path) {
		if (obj instanceof TreeParent) {
			if (path.length() == 0) {
				path.insert(0, ((TreeParent) obj).getName());
			} else {
				path.insert(0, File.separator).insert(0,
						((TreeParent) obj).getName());
			}
			TreeParent parent = ((TreeParent) obj).getParent();
			String parentName = parent.getName();
			if (parentName == "") {
				return path;
			}
			return getTreePath(parent, path);
		}
		return null;
	}

	/**
	 * 获取当前节点的文件路径
	 * 
	 * @param element
	 *            当前节点对象
	 * @return 当前节点的文件路径信息
	 */
	private String getDirectoryPath(Object element) {
		String dirText = directoryCombo.getText();
		String path;
		if (dirText.length() == 3) { // 针对如：“E:\”的形式
			path = getTreePath(element, new StringBuffer("")).toString();
		} else {
			String subDir = dirText.substring(0,
					dirText.lastIndexOf(File.separator));
			String subPath = getTreePath(element, new StringBuffer(""))
					.toString();
			path = subDir + File.separator + subPath;
		}
		return path;
	}

	/**
	 * 
	 * 导入监听器
	 * 
	 * @author
	 *
	 */
	private class ImportModifyListener implements ModifyListener {

		@Override
		public void modifyText(ModifyEvent e) {
			setPageComplete(false);
			if (directoryCombo.getText() == "") {
				setErrorMessage("Please input the directory and select your workspace.");
				return;
			}
			setErrorMessage(null);
			setPageComplete(true);
		}
	}

	/**
	 * 私有内部类，勾选状态提供类
	 * 
	 * @author Administrator
	 *
	 */
	private class CheckStateProvider implements ICheckStateProvider {

		private Boolean isCheck;

		public CheckStateProvider(Boolean isCheck) {
			this.isCheck = isCheck;
		}

		@Override
		public boolean isChecked(Object element) {
			TreeObject treeObject = (TreeObject) element;
			treeObject.setChecked(isCheck);
			return isCheck;
		}

		@Override
		public boolean isGrayed(Object element) {
			return false;
		}

	}

	/**
	 * 复位，将左边和右边的树视图等都设为null
	 */
	private void reset() {

		TreeParent leftRoot = ((NavTreeContentProvider) ctvLeft
				.getContentProvider()).invisibleRoot;
		if (leftRoot.hasChildren()) {
			leftRoot.removeChildren();
		}
		TreeParent rightRoot = ((NavTreeContentProvider) ctvRight
				.getContentProvider()).invisibleRoot;
		if (rightRoot.hasChildren()) {
			rightRoot.removeChildren();
		}
		if (!leftToRight.isEmpty()) {
			leftToRight.clear();
		}
	}
}
