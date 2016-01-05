package edu.xmu.nmr.dataanalysis.diagram.pref;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class DataAnalysisCategory extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	@Override
	public void init(IWorkbench workbench) {
		// 添加偏好页面描述语句，显示在页面区域最上方
		setDescription("This section is used to configure the DataAnalysis preference pages.");
	}

	@Override
	protected void createFieldEditors() {

	}

}
