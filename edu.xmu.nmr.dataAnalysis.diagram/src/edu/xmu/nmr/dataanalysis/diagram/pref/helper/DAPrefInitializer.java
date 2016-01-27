package edu.xmu.nmr.dataanalysis.diagram.pref.helper;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
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
                DAPrefConstants.FID_PREF_BACH_COLOR, new RGB(255, 255, 255));
        store.setDefault(DAPrefConstants.FID_PREF_HAS_BORDER, true);
        store.setDefault(DAPrefConstants.FID_PREF_LINE_WIDTH, 1);
        store.setDefault(DAPrefConstants.FID_PREF_HAS_GRID, true);
    }
    
}
