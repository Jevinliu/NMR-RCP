package edu.xmu.nmr.custom.extensions.provider;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.EditorPart;

import edu.xmu.nmr.custom.extensions.beans.EditorPageBean;
import edu.xmu.nmr.custom.extensions.constants.EditorPageConstants;

public class EditorPageProvider {
	private String ID = EditorPageConstants.EXTENSION_ID;
	public static EditorPageProvider instance;

	private EditorPageProvider() {
	}

	public static synchronized EditorPageProvider getInstance() {
		if (instance == null) {
			instance = new EditorPageProvider();
		}
		return instance;
	}

	public List<EditorPageBean> loadExtensions() {
		IConfigurationElement[] elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(ID);
		if (elements == null || elements.length == 0) {
			return null;
		}
		List<EditorPageBean> beans = new ArrayList<EditorPageBean>();
		List<String> ids = new ArrayList<String>();
		for (int i = 0; i < elements.length; i++) {
			IConfigurationElement element = elements[i];
			EditorPageBean bean = new EditorPageBean();
			bean.setId(element.getAttribute(EditorPageConstants.ATTR_ID));
			if (ids.contains(bean.getId())) { // 判断是否有重复的ID
				continue;
			}
			ids.add(bean.getId());
			bean.setName(element.getAttribute(EditorPageConstants.ATTR_NAME));
			String pageClassPath = element
					.getAttribute(EditorPageConstants.ATTR_CLASS);
			String inputClassPath = element
					.getAttribute(EditorPageConstants.ATTR_EDITORINPUT);
			Object pageClass = null;
			Object inputClass = null;
			try {
				if (!pageClassPath.isEmpty()) {
					pageClass = element
							.createExecutableExtension(EditorPageConstants.ATTR_CLASS);
				} else {
					continue;
				}
				if (!inputClassPath.isEmpty()) {
					inputClass = element
							.createExecutableExtension(EditorPageConstants.ATTR_EDITORINPUT);
				}
				if ((pageClass instanceof IEditorPart)
						|| (inputClass == null)
						|| (inputClass != null && inputClass instanceof IEditorInput)) {
					bean.setEditorInput((IEditorInput) inputClass);
					bean.setEditorPart((EditorPart) pageClass);
					beans.add(bean);
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return beans;
	}
}
