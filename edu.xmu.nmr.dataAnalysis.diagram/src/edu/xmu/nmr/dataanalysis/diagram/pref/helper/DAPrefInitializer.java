package edu.xmu.nmr.dataanalysis.diagram.pref.helper;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.RGB;

import edu.xmu.nmr.dataanalysis.diagram.Activator;

public class DAPrefInitializer extends AbstractPreferenceInitializer {
    
    public DAPrefInitializer() {
        // TODO Auto-generated constructor stub
    }
    
    @Override public void initializeDefaultPreferences() {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        // fid 页面
        store.setDefault(DAPrefConstants.FID_PREF_COLORLIST_SELECT, 0);
        PreferenceConverter.setDefault(store,
                DAPrefConstants.FID_PREF_FORE_COLOR, new RGB(0, 0, 255));
        PreferenceConverter.setDefault(store,
                DAPrefConstants.FID_PREF_BACK_COLOR, new RGB(255, 255, 255));
        store.setDefault(DAPrefConstants.FID_PREF_HAS_BORDER, true);
        store.setDefault(DAPrefConstants.FID_PREF_LINE_WIDTH, 1);
        store.setDefault(DAPrefConstants.FID_PREF_HAS_GRID, true);
        PreferenceConverter.setDefault(store,
                DAPrefConstants.FID_PREF_GRID_COLOR,
                ColorConstants.lightGray.getRGB());
        PreferenceConverter.setDefault(store,
                DAPrefConstants.FID_PREF_BORDER_COLOR,
                ColorConstants.lightGray.getRGB());
                
        // Axis 页面
        
        store.setDefault(DAPrefConstants.L_RULER_PREF_IS_VISIABLE, true);
        store.setDefault(DAPrefConstants.R_RULER_PREF_IS_VISIABLE, false);
        store.setDefault(DAPrefConstants.T_RULER_PREF_IS_VISIABLE, false);
        store.setDefault(DAPrefConstants.B_RULER_PREF_IS_VISIABLE, true);
        String[] colorStrs = new String[] {
                DAPrefConstants.L_RULER_PREF_FORE_COLOR,
                DAPrefConstants.L_RULER_PREF_BACK_COLOR,
                DAPrefConstants.R_RULER_PREF_FORE_COLOR,
                DAPrefConstants.R_RULER_PREF_BACK_COLOR,
                DAPrefConstants.T_RULER_PREF_FORE_COLOR,
                DAPrefConstants.T_RULER_PREF_BACK_COLOR,
                DAPrefConstants.B_RULER_PREF_FORE_COLOR,
                DAPrefConstants.B_RULER_PREF_BACK_COLOR };
        for (int i = 0; i < 8; i++) {
            PreferenceConverter.setDefault(store, colorStrs[i],
                    ColorConstants.white.getRGB());
            PreferenceConverter.setDefault(store, colorStrs[i + 1],
                    ColorConstants.black.getRGB());
            i++;
        }
    }
}
