package edu.xmu.nmr.dataanalysis.diagram.pref;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import edu.xmu.nmr.dataanalysis.diagram.Activator;
import edu.xmu.nmr.dataanalysis.diagram.pref.helper.DAPrefConstants;
import edu.xmu.nmr.dataanalysis.diagram.pref.helper.DAPrefPageUtil;

public class AxisPrefPage extends PreferencePage
        implements IWorkbenchPreferencePage {
        
    private String[] colorDesps = new String[] { "Left axis foregound color",
            "Left axis background color", "Right axis foreground color",
            "Right axis background color", "Top axis forground color",
            "Top axis background color", "Bottom axis foreground color",
            "Bottom axis background color" };
    private ColorSelector colorSelector;
    private Button leftVSBtn, rightVSBtn, topVSBtn, bottomVSBtn;
    private List colorDespList;
    private RGB[] selectedRGB = new RGB[8];
    String[] colorStrs = new String[] { DAPrefConstants.L_RULER_PREF_FORE_COLOR,
            DAPrefConstants.L_RULER_PREF_BACK_COLOR,
            DAPrefConstants.R_RULER_PREF_FORE_COLOR,
            DAPrefConstants.R_RULER_PREF_BACK_COLOR,
            DAPrefConstants.T_RULER_PREF_FORE_COLOR,
            DAPrefConstants.T_RULER_PREF_BACK_COLOR,
            DAPrefConstants.B_RULER_PREF_FORE_COLOR,
            DAPrefConstants.B_RULER_PREF_BACK_COLOR };
            
    public AxisPrefPage() {
        setDescription("Axis editor page's references:");
    }
    
    @Override public void init(IWorkbench workbench) {
        setPreferenceStore(Activator.getDefault().getPreferenceStore());
    }
    
    @Override protected Control createContents(Composite parent) {
        Composite comp = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(1, false);
        comp.setLayout(layout);
        
        createVSGroup(comp);
        createColorList(comp);
        
        doLoad();
        return comp;
    }
    
    /**
     * 创建是否显示组
     * 
     * @param comp
     */
    private void createVSGroup(Composite comp) {
        Group group = new Group(comp, SWT.NONE);
        group.setText("Axis Visiable");
        group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        GridLayout layout = new GridLayout(1, false);
        layout.marginLeft = 4;
        layout.marginRight = 4;
        group.setLayout(layout);
        leftVSBtn = new Button(group, SWT.CHECK);
        GridData gd = new GridData(SWT.BEGINNING, SWT.FILL, true, false, 1, 1);
        leftVSBtn.setLayoutData(gd);
        leftVSBtn.setText("Left axis visiable");
        rightVSBtn = new Button(group, SWT.CHECK);
        rightVSBtn.setLayoutData(gd);
        rightVSBtn.setText("Right axis visiable");
        topVSBtn = new Button(group, SWT.CHECK);
        topVSBtn.setLayoutData(gd);
        topVSBtn.setText("Top axis visiable");
        bottomVSBtn = new Button(group, SWT.CHECK);
        bottomVSBtn.setLayoutData(gd);
        bottomVSBtn.setText("Bottom axis visiable");
    }
    
    private void createColorList(Composite parent) {
        Composite listComp = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        layout.numColumns = 3;
        GridData gridData = new GridData(GridData.FILL_BOTH);
        gridData.horizontalSpan = 1;
        gridData.heightHint = 150;
        gridData.widthHint = 300;
        listComp.setLayout(layout);
        listComp.setLayoutData(gridData);
        colorDespList = new List(listComp, SWT.BORDER | SWT.SINGLE);
        colorDespList.setLayoutData(
                new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        colorDespList.setItems(colorDesps);
        colorDespList.addSelectionListener(new SelectionAdapter() {
            @Override public void widgetSelected(SelectionEvent e) {
                setColorSelector(colorDespList.getSelectionIndex());
            }
        });
        createColorBtn(listComp);
        
        Label span = new Label(listComp, SWT.NONE);
        span.setText("\t");
        span.setLayoutData(
                new GridData(SWT.BEGINNING, SWT.TOP, false, false, 1, 1));
    }
    
    private void createColorBtn(Composite parent) {
        colorSelector = new ColorSelector(parent);
        colorSelector.getButton().setLayoutData(
                new GridData(SWT.BEGINNING, SWT.TOP, false, false, 1, 1));
        colorSelector.addListener(new IPropertyChangeListener() {
            
            @Override public void propertyChange(PropertyChangeEvent event) {
                selectedRGB[colorDespList.getSelectionIndex()] = (RGB) event
                        .getNewValue();
            }
        });
    }
    
    /**
     * 根据颜色列表框的选择，设置颜色按钮的显示颜色
     * 
     * @param colorIndex
     *            选择的颜色列表框按钮
     */
    private void setColorSelector(int colorIndex) {
        colorSelector.setColorValue(selectedRGB[colorIndex]);
    }
    
    private void doLoad() {
        IPreferenceStore ips = getPreferenceStore();
        leftVSBtn.setSelection(
                ips.getBoolean(DAPrefConstants.L_RULER_PREF_IS_VISIABLE));
        rightVSBtn.setSelection(
                ips.getBoolean(DAPrefConstants.R_RULER_PREF_IS_VISIABLE));
        topVSBtn.setSelection(
                ips.getBoolean(DAPrefConstants.T_RULER_PREF_IS_VISIABLE));
        bottomVSBtn.setSelection(
                ips.getBoolean(DAPrefConstants.B_RULER_PREF_IS_VISIABLE));
        for (int i = 0; i < 8; i++) {
            selectedRGB[i] = PreferenceConverter.getColor(ips, colorStrs[i]);
        }
        setColorSelector(0);
    }
    
    @Override protected void performDefaults() {
        leftVSBtn.setSelection(true);
        rightVSBtn.setSelection(false);
        topVSBtn.setSelection(false);
        bottomVSBtn.setSelection(true);
        colorDespList.setSelection(0);
        for (int i = 0; i < 8; i++) {
            selectedRGB[i] = ColorConstants.black.getRGB();
            selectedRGB[i + 1] = ColorConstants.white.getRGB();
            i++;
        }
        colorSelector.setColorValue(selectedRGB[0]);
        super.performDefaults();
    }
    
    @Override public boolean performOk() {
        DAPrefPageUtil.setLRulerVisiable(leftVSBtn.getSelection());
        DAPrefPageUtil.setRRulerVisiable(rightVSBtn.getSelection());
        DAPrefPageUtil.setTRulerVisiable(topVSBtn.getSelection());
        DAPrefPageUtil.setBRulerVisiable(bottomVSBtn.getSelection());
        for (int i = 0; i < 8; i++) {
            PreferenceConverter.setValue(getPreferenceStore(), colorStrs[i],
                    selectedRGB[i]);
        }
        return super.performOk();
    }
}
