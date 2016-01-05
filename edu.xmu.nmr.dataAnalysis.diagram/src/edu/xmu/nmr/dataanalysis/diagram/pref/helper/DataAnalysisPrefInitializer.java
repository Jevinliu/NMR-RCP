package edu.xmu.nmr.dataanalysis.diagram.pref.helper;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.RGB;

import edu.xmu.nmr.dataanalysis.diagram.Activator;

public class DataAnalysisPrefInitializer extends AbstractPreferenceInitializer {

	public DataAnalysisPrefInitializer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		// fid 页面
		store.setDefault(DataAnalysisPrefConstants.FID_PREF_COLORLIST_SELECT, 0);
		PreferenceConverter.setDefault(store,
				DataAnalysisPrefConstants.FID_PREF_FOREGROUND_COLOR, new RGB(0,
						0, 255));
		PreferenceConverter.setDefault(store,
				DataAnalysisPrefConstants.FID_PREF_BACHGROUND_COLOR, new RGB(
						255, 255, 255));
		store.setDefault(DataAnalysisPrefConstants.FID_PREF_HAVE_BORDER, true);
	}

}
