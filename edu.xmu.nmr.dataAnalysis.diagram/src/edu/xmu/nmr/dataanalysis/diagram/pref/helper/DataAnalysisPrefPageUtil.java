package edu.xmu.nmr.dataanalysis.diagram.pref.helper;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.RGB;

import edu.xmu.nmr.dataanalysis.diagram.Activator;

public class DataAnalysisPrefPageUtil {
	private static IPreferenceStore store = Activator.getDefault()
			.getPreferenceStore();

	// ///////////////////
	// fid 页面
	// ///////////////////

	public static int getValueOfFidColorListIndex() {
		return store
				.getInt(DataAnalysisPrefConstants.FID_PREF_COLORLIST_SELECT);
	}

	public static void setValueOfFidColorListIndex(int colorListIndex) {
		store.setValue(DataAnalysisPrefConstants.FID_PREF_COLORLIST_SELECT,
				colorListIndex);
	}

	public static RGB getValueOfFidForeColor() {
		return PreferenceConverter.getColor(store,
				DataAnalysisPrefConstants.FID_PREF_FOREGROUND_COLOR);
	}

	public static void setValueOfFidForeColor(RGB fidForeColor) {
		PreferenceConverter.setValue(store,
				DataAnalysisPrefConstants.FID_PREF_FOREGROUND_COLOR,
				fidForeColor);
	}

	public static RGB getValueOfFidBackColor() {
		return PreferenceConverter.getColor(store,
				DataAnalysisPrefConstants.FID_PREF_BACHGROUND_COLOR);
	}

	public static void setValueOfFidBackColor(RGB fidBackColor) {
		PreferenceConverter.setValue(store,
				DataAnalysisPrefConstants.FID_PREF_BACHGROUND_COLOR,
				fidBackColor);
	}

	public static boolean getValueOfFidBorderCheck() {
		return store.getBoolean(DataAnalysisPrefConstants.FID_PREF_HAVE_BORDER);
	}

	public static void setValueOfFidBorderCheck(boolean borderChecked) {
		store.setValue(DataAnalysisPrefConstants.FID_PREF_HAVE_BORDER,
				borderChecked);
	}
}
