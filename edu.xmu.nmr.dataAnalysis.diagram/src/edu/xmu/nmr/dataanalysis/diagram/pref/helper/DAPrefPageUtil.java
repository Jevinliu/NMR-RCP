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
    
    public static RGB getValueOfFidGridColor() {
        return PreferenceConverter.getColor(store,
                DAPrefConstants.FID_PREF_GRID_COLOR);
    }
    
    public static void setValueOfFidGridColor(RGB fidGridColor) {
        PreferenceConverter.setValue(store, DAPrefConstants.FID_PREF_GRID_COLOR,
                fidGridColor);
    }
    
    public static RGB getValueOfFidBorderColor() {
        return PreferenceConverter.getColor(store,
                DAPrefConstants.FID_PREF_BORDER_COLOR);
    }
    
    public static void setValueOfFidBorderColor(RGB fidBorderColor) {
        PreferenceConverter.setValue(store,
                DAPrefConstants.FID_PREF_BORDER_COLOR, fidBorderColor);
    }
    
    //////////////////////////
    /// ruler page
    //////////////////////
    public static RGB getValueOfLRulerForeColor() {
        return PreferenceConverter.getColor(store,
                DAPrefConstants.L_RULER_PREF_FORE_COLOR);
    }
    
    public static void setValueOfLRulerForeColor(RGB lRulerForeColor) {
        PreferenceConverter.setValue(store,
                DAPrefConstants.L_RULER_PREF_FORE_COLOR, lRulerForeColor);
    }
    
    public static RGB getValueOfLRulerBackColor() {
        return PreferenceConverter.getColor(store,
                DAPrefConstants.L_RULER_PREF_BACK_COLOR);
    }
    
    public static void setValueOfLRulerBackColor(RGB lRulerBackColor) {
        PreferenceConverter.setValue(store,
                DAPrefConstants.L_RULER_PREF_BACK_COLOR, lRulerBackColor);
    }
    
    public static boolean getLRulerVisiable() {
        return store.getBoolean(DAPrefConstants.L_RULER_PREF_IS_VISIABLE);
    }
    
    public static void setLRulerVisiable(boolean lRulerVisiable) {
        store.setValue(DAPrefConstants.L_RULER_PREF_IS_VISIABLE,
                lRulerVisiable);
    }
    
    public static RGB getValueOfRRulerForeColor() {
        return PreferenceConverter.getColor(store,
                DAPrefConstants.R_RULER_PREF_FORE_COLOR);
    }
    
    public static void setValueOfRRulerForeColor(RGB rRulerForeColor) {
        PreferenceConverter.setValue(store,
                DAPrefConstants.R_RULER_PREF_FORE_COLOR, rRulerForeColor);
    }
    
    public static RGB getValueOfRRulerBackColor() {
        return PreferenceConverter.getColor(store,
                DAPrefConstants.R_RULER_PREF_BACK_COLOR);
    }
    
    public static void setValueOfRRulerBackColor(RGB rRulerBackColor) {
        PreferenceConverter.setValue(store,
                DAPrefConstants.R_RULER_PREF_BACK_COLOR, rRulerBackColor);
    }
    
    public static boolean getRRulerVisiable() {
        return store.getBoolean(DAPrefConstants.R_RULER_PREF_IS_VISIABLE);
    }
    
    public static void setRRulerVisiable(boolean rRulerVisiable) {
        store.setValue(DAPrefConstants.R_RULER_PREF_IS_VISIABLE,
                rRulerVisiable);
    }
    
    public static RGB getValueOfTRulerForeColor() {
        return PreferenceConverter.getColor(store,
                DAPrefConstants.T_RULER_PREF_FORE_COLOR);
    }
    
    public static void setValueOfTRulerForeColor(RGB tRulerForeColor) {
        PreferenceConverter.setValue(store,
                DAPrefConstants.T_RULER_PREF_FORE_COLOR, tRulerForeColor);
    }
    
    public static RGB getValueOfTRulerBackColor() {
        return PreferenceConverter.getColor(store,
                DAPrefConstants.T_RULER_PREF_BACK_COLOR);
    }
    
    public static void setValueOfTRulerBackColor(RGB tRulerBackColor) {
        PreferenceConverter.setValue(store,
                DAPrefConstants.T_RULER_PREF_BACK_COLOR, tRulerBackColor);
    }
    
    public static boolean getTRulerVisiable() {
        return store.getBoolean(DAPrefConstants.T_RULER_PREF_IS_VISIABLE);
    }
    
    public static void setTRulerVisiable(boolean tRulerVisiable) {
        store.setValue(DAPrefConstants.T_RULER_PREF_IS_VISIABLE,
                tRulerVisiable);
    }
    
    public static RGB getValueOfBRulerForeColor() {
        return PreferenceConverter.getColor(store,
                DAPrefConstants.B_RULER_PREF_FORE_COLOR);
    }
    
    public static void setValueOfBRulerForeColor(RGB lRulerForeColor) {
        PreferenceConverter.setValue(store,
                DAPrefConstants.B_RULER_PREF_FORE_COLOR, lRulerForeColor);
    }
    
    public static RGB getValueOfBRulerBackColor() {
        return PreferenceConverter.getColor(store,
                DAPrefConstants.B_RULER_PREF_BACK_COLOR);
    }
    
    public static void setValueOfBRulerBackColor(RGB bRulerBackColor) {
        PreferenceConverter.setValue(store,
                DAPrefConstants.B_RULER_PREF_BACK_COLOR, bRulerBackColor);
    }
    
    public static boolean getBRulerVisiable() {
        return store.getBoolean(DAPrefConstants.B_RULER_PREF_IS_VISIABLE);
    }
    
    public static void setBRulerVisiable(boolean bRulerVisiable) {
        store.setValue(DAPrefConstants.B_RULER_PREF_IS_VISIABLE,
                bRulerVisiable);
    }
}
