package edu.xmu.nmr.dataanalysis.diagram.pref;

import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import edu.xmu.nmr.dataanalysis.diagram.Activator;
import edu.xmu.nmr.dataanalysis.diagram.pref.helper.DataAnalysisPrefConstants;
import edu.xmu.nmr.dataanalysis.diagram.pref.helper.DataAnalysisPrefPageUtil;

public class FidPrefPage extends PreferencePage implements
		IWorkbenchPreferencePage {

	private Font font; // 面板上的字体设置
	private List list; // 相关的颜色设置列表
	private String[] colorLists = new String[] { "Fid figure foreground",
			"Fid figure background" };
	private ColorSelector colorSelector; // 颜色按钮
	private Button borderCheckBtn;
	private RGB[] selectedRGB = new RGB[2]; // 0: fid foreground color, 1: fid
											// background color

	public FidPrefPage() {
		setDescription("Fid editor page's references:");
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	@Override
	protected void setControl(Control newControl) {
		super.setControl(newControl);
	}

	@Override
	protected Control createContents(Composite parent) {
		font = parent.getFont();
		// The main composite
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.numColumns = 2;// 创建出来的对话框有两列
		composite.setLayout(layout);
		composite.setFont(font);

		createOthers(composite);
		createColorList(composite);

		doLoad();
		return composite;
	}

	/**
	 * 创建一些属性，如是否添加border的选项
	 * 
	 * @param parent
	 */
	private void createOthers(Composite parent) {
		borderCheckBtn = new Button(parent, SWT.CHECK);
		borderCheckBtn.setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL,
				false, false, 2, 1));
		borderCheckBtn.setText("Figure Border");
		borderCheckBtn.setFont(font);
		borderCheckBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
	}

	/**
	 * 从preference store 里边加载该页面的数据
	 */
	private void doLoad() {
		IPreferenceStore ips = getPreferenceStore();
		borderCheckBtn.setSelection(ips
				.getBoolean(DataAnalysisPrefConstants.FID_PREF_HAVE_BORDER));
		int colorIndex = ips
				.getInt(DataAnalysisPrefConstants.FID_PREF_COLORLIST_SELECT);
		list.setSelection(colorIndex);
		selectedRGB[0] = PreferenceConverter.getColor(ips,
				DataAnalysisPrefConstants.FID_PREF_FOREGROUND_COLOR);
		selectedRGB[1] = PreferenceConverter.getColor(ips,
				DataAnalysisPrefConstants.FID_PREF_BACHGROUND_COLOR);
		setColorSelector(colorIndex);

	}

	/**
	 * 创建颜色列表
	 * 
	 * @param parent
	 */
	private void createColorList(Composite parent) {
		Composite listComp = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.numColumns = 2;
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.heightHint = 150;
		gridData.widthHint = 300;
		listComp.setLayout(layout);
		listComp.setLayoutData(gridData);
		listComp.setFont(font);
		list = new List(listComp, SWT.BORDER | SWT.SINGLE);
		list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		list.setFont(font);
		list.setItems(colorLists);
		list.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setColorSelector(list.getSelectionIndex());
			}
		});
		createColorBtn(listComp);

		Label span = new Label(parent, SWT.NONE);
		span.setText("\t");
		span.setLayoutData(new GridData(SWT.BEGINNING, SWT.TOP, false, false,
				1, 1));
	}

	/**
	 * 根据颜色列表框的选择，设置颜色按钮的显示颜色
	 * 
	 * @param colorIndex
	 *            选择的颜色列表框按钮
	 */
	private void setColorSelector(int colorIndex) {
		switch (colorIndex) {
		case 0:
			colorSelector.setColorValue(selectedRGB[0]);
			break;
		case 1:
			colorSelector.setColorValue(selectedRGB[1]);
		}
	}

	private void createColorBtn(Composite parent) {
		colorSelector = new ColorSelector(parent);
		colorSelector.getButton().setLayoutData(
				new GridData(SWT.BEGINNING, SWT.TOP, false, false, 1, 1));
		colorSelector.getButton().setFont(font);
		colorSelector.addListener(new IPropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent event) {
				switch (list.getSelectionIndex()) {
				case 0:
					selectedRGB[0] = (RGB) event.getNewValue();
					break;
				case 1:
					selectedRGB[1] = (RGB) event.getNewValue();
					break;
				}
			}
		});
	}

	@Override
	protected void performDefaults() {
		borderCheckBtn.setSelection(true);
		list.setSelection(0);
		selectedRGB[0] = new RGB(0, 0, 255);
		selectedRGB[1] = new RGB(255, 255, 255);
		colorSelector.setColorValue(selectedRGB[0]);
		super.performDefaults();
	}

	@Override
	public boolean performOk() {

		DataAnalysisPrefPageUtil.setValueOfFidBorderCheck(borderCheckBtn
				.getSelection());
		DataAnalysisPrefPageUtil.setValueOfFidColorListIndex(list
				.getSelectionIndex());
		DataAnalysisPrefPageUtil.setValueOfFidForeColor(selectedRGB[0]);
		DataAnalysisPrefPageUtil.setValueOfFidBackColor(selectedRGB[1]);

		return super.performOk();

	}

}