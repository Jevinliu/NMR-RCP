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
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import edu.xmu.nmr.dataanalysis.diagram.Activator;
import edu.xmu.nmr.dataanalysis.diagram.pref.helper.DAPrefConstants;
import edu.xmu.nmr.dataanalysis.diagram.pref.helper.DAPrefPageUtil;

public class FidPrefPage extends PreferencePage
        implements IWorkbenchPreferencePage {
        
    private Font font; // 面板上的字体设置
    private List list; // 相关的颜色设置列表
    private String[] colorLists = new String[] { "Fid figure foreground",
            "Fid figure background" };
    private ColorSelector colorSelector; // 颜色按钮
    private Button borderCheckBtn, gridCheckBtn;
    private RGB[] selectedRGB = new RGB[2]; // 0: fid foreground color, 1: fid
                                            // background color
    private Spinner lineWidthSpn;
    
    public FidPrefPage() {
        setDescription("Fid editor page's references:");
    }
    
    @Override public void init(IWorkbench workbench) {
        setPreferenceStore(Activator.getDefault().getPreferenceStore());
    }
    
    @Override protected void setControl(Control newControl) {
        super.setControl(newControl);
    }
    
    @Override protected Control createContents(Composite parent) {
        font = parent.getFont();
        // The main composite
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(2, false);
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        composite.setLayout(layout);
        composite.setFont(font);
        
        createOthers(composite);
        createColorList(composite);
        
        doLoad();
        return composite;
    }
    
    /**
     * 创建一些属性，如是否添加border与 grid的选项
     * 
     * @param parent
     */
    private void createOthers(Composite parent) {
        borderCheckBtn = new Button(parent, SWT.CHECK);
        GridData gridData2_1 = new GridData(SWT.FILL, SWT.FILL, true, false, 2,
                1);
        borderCheckBtn.setLayoutData(gridData2_1);
        borderCheckBtn.setText("Figure Border");
        borderCheckBtn.setFont(font);
        
        gridCheckBtn = new Button(parent, SWT.CHECK);
        gridCheckBtn.setLayoutData(gridData2_1);
        gridCheckBtn.setText("Grid");
        gridCheckBtn.setFont(font);
        
        GridData gridData1_1 = new GridData(SWT.BEGINNING, SWT.FILL, true,
                false, 1, 1);
        Label label = new Label(parent, SWT.NONE);
        label.setText("Line Width: ");
        label.setLayoutData(
                new GridData(SWT.BEGINNING, SWT.FILL, false, false, 1, 1));
        lineWidthSpn = new Spinner(parent, SWT.BORDER);
        lineWidthSpn.setLayoutData(gridData1_1);
        lineWidthSpn.setMinimum(1);
        lineWidthSpn.setMaximum(50);
        lineWidthSpn.setIncrement(1);
        lineWidthSpn.setPageIncrement(10);
    }
    
    /**
     * 从preference store 里边加载该页面的数据
     */
    private void doLoad() {
        IPreferenceStore ips = getPreferenceStore();
        borderCheckBtn.setSelection(
                ips.getBoolean(DAPrefConstants.FID_PREF_HAS_BORDER));
        gridCheckBtn.setSelection(
                ips.getBoolean(DAPrefConstants.FID_PREF_HAS_GRID));
        lineWidthSpn
                .setSelection(ips.getInt(DAPrefConstants.FID_PREF_LINE_WIDTH));
        int colorIndex = ips.getInt(DAPrefConstants.FID_PREF_COLORLIST_SELECT);
        list.setSelection(colorIndex);
        selectedRGB[0] = PreferenceConverter.getColor(ips,
                DAPrefConstants.FID_PREF_FORE_COLOR);
        selectedRGB[1] = PreferenceConverter.getColor(ips,
                DAPrefConstants.FID_PREF_BACK_COLOR);
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
        layout.numColumns = 3;
        GridData gridData = new GridData(GridData.FILL_BOTH);
        gridData.horizontalSpan = 2;
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
            @Override public void widgetSelected(SelectionEvent e) {
                setColorSelector(list.getSelectionIndex());
            }
        });
        createColorBtn(listComp);
        
        Label span = new Label(listComp, SWT.NONE);
        span.setText("\t");
        span.setLayoutData(
                new GridData(SWT.BEGINNING, SWT.TOP, false, false, 1, 1));
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
            
            @Override public void propertyChange(PropertyChangeEvent event) {
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
    
    @Override protected void performDefaults() {
        borderCheckBtn.setSelection(true);
        gridCheckBtn.setSelection(true);
        lineWidthSpn.setSelection(1);
        list.setSelection(0);
        selectedRGB[0] = new RGB(0, 0, 255);
        selectedRGB[1] = new RGB(255, 255, 255);
        colorSelector.setColorValue(selectedRGB[0]);
        super.performDefaults();
    }
    
    @Override public boolean performOk() {
        
        DAPrefPageUtil.setValueOfFidBorderCheck(borderCheckBtn.getSelection());
        DAPrefPageUtil.setValueOfFidGridCheck(gridCheckBtn.getSelection());
        DAPrefPageUtil.setValueOfFidLineWidth(lineWidthSpn.getSelection());
        DAPrefPageUtil.setValueOfFidColorListIndex(list.getSelectionIndex());
        DAPrefPageUtil.setValueOfFidForeColor(selectedRGB[0]);
        DAPrefPageUtil.setValueOfFidBackColor(selectedRGB[1]);
        return super.performOk();
    }
    
}