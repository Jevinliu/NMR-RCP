package edu.xmu.nmr.dataanalysis.diagram.pref.helper;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.RGB;

import edu.xmu.nmr.dataanalysis.diagram.Activator;

public class DAPrefPageUtil {
    private static IPreferenceStore store = Activator.getDefault()
            .getPreferenceStore();
            
    // ///////////////////
    // fid 页面
    // ///////////////////
    
    public static int getValueOfFidColorListIndex() {
        return store.getInt(DAPrefConstants.FID_PREF_COLORLIST_SELECT);
    }
    
    public static void setValueOfFidColorListIndex(int colorListIndex) {
        store.setValue(DAPrefConstants.FID_PREF_COLORLIST_SELECT,
                colorListIndex);
    }
    
    public static RGB getValueOfFidForeColor() {
        return PreferenceConverter.getColor(store,
                DAPrefConstants.FID_PREF_FORE_COLOR);
    }
    
    public static void setValueOfFidForeColor(RGB fidForeColor) {
        PreferenceConverter.setValue(store, DAPrefConstants.FID_PREF_FORE_COLOR,
                fidForeColor);
    }
    
    public static RGB getValueOfFidBackColor() {
        return PreferenceConverter.getColor(store,
                DAPrefConstants.FID_PREF_BACK_COLOR);
    }
    
    public static void setValueOfFidBackColor(RGB fidBackColor) {
        PreferenceConverter.setValue(store, DAPrefConstants.FID_PREF_BACK_COLOR,
                fidBackColor);
    }
    
    public static boolean getValueOfFidBorderCheck() {
        return store.getBoolean(DAPrefConstants.FID_PREF_HAS_BORDER);
    }
    
    public static void setValueOfFidBorderCheck(boolean borderChecked) {
        store.setValue(DAPrefConstants.FID_PREF_HAS_BORDER, borderChecked);
    }
    
    public static boolean getValueOfFidGridCheck() {
        return store.getBoolean(DAPrefConstants.FID_PREF_HAS_GRID);
    }
    
    public static void setValueOfFidGridCheck(boolean gridChecked) {
        store.setValue(DAPrefConstants.FID_PREF_HAS_GRID, gridChecked);
    }
    
    public static int getValueOfFidLineWidth() {
        return store.getInt(DAPrefConstants.FID_PREF_LINE_WIDTH);
    }
    
    public static void setValueOfFidLineWidth(int lineWidth) {
        store.setValue(DAPrefConstants.FID_PREF_LINE_WIDTH, lineWidth);
    }
}
