package edu.xmu.nmrdataanalysis.diagram.model;

import java.util.ArrayList;
import java.util.Map.Entry;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import edu.xmu.nmr.dataanalysis.importvarian.model.ParameterObject;

public class ParamsTLProvider implements ITableLabelProvider {

	@Override
	public void addListener(ILabelProviderListener listener) {

	}

	@Override
	public void dispose() {

	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {

	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		Entry<String, Object> param = (Entry<String, Object>) element;
		if (columnIndex == 0) {
			return param.getKey();
		} else if (columnIndex == 1) {
			Object obj = param.getValue();
			if (obj instanceof ParameterObject) {
				return ((ParameterObject) obj).getCurrentValuesTrim();
			} else if (obj instanceof ArrayList) {
				return Utils.getArrayListToStringTrim((ArrayList) obj);
			} else {
				return obj.toString();
			}
		}
		return null;
	}
}
