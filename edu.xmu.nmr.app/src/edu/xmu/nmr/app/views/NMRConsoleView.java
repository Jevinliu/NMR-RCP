package edu.xmu.nmr.app.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.internal.console.ConsoleView;

import edu.xmu.nmr.app.utils.DateTimeUtils;

public class NMRConsoleView extends ConsoleView {
    
    public static final String ID = "edu.xmu.nmr.app.views.NMRConsoleView";
    
    public NMRConsoleView() {
    
    }
    
    @Override public void createPartControl(Composite parent) {
        final SashForm sashForm = new SashForm(parent, SWT.VERTICAL);
        super.createPartControl(sashForm);
        // 命令行输入面板布局
        Composite comp = new Composite(sashForm, SWT.NONE);
        GridLayout layout = new GridLayout(2, false);
        layout.horizontalSpacing = 5;
        comp.setLayout(layout);
        new Label(comp, SWT.NONE).setLayoutData(
                new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
        final Text cmdText = new Text(comp, SWT.BORDER);
        cmdText.setLayoutData(
                new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3));
        cmdText.setFont(new Font(Display.getDefault(), "Arial", 14,
                SWT.BOLD | SWT.ITALIC));
        Label cmdLabel = new Label(comp, SWT.NONE);
        cmdLabel.setText("CMD : ");
        cmdLabel.setLayoutData(
                new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
        new Label(comp, SWT.NONE).setLayoutData(
                new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
        sashForm.setWeights(new int[] { 8, 2 });
        sashForm.addControlListener(new ControlAdapter() {
            @Override public void controlResized(ControlEvent e) {
                int height = sashForm.getClientArea().height;
                int[] weights = sashForm.getWeights();
                if (height >= 40) {
                    weights[0] = height - 40;
                    weights[1] = 40;
                }
                sashForm.setWeights(weights);
            }
        });
        // 进行输入判定
        cmdText.addVerifyListener(new VerifyListener() {
            
            @Override public void verifyText(VerifyEvent e) {
                e.doit = true;
            }
        });
        
        cmdText.addKeyListener(new KeyListener() {
            
            @Override public void keyReleased(KeyEvent e) {
            }
            
            @Override public void keyPressed(KeyEvent e) {
                if ((e.keyCode == SWT.CR) || (e.keyCode == SWT.KEYPAD_CR)) {
                    StringBuffer sb = new StringBuffer();
                    sb.append("[").append(DateTimeUtils.getCurrentTime())
                            .append("] : ");
                    sb.append(cmdText.getText());
                    System.out.println(sb.toString());
                    cmdText.setText("");
                }
            }
        });
    }
}
