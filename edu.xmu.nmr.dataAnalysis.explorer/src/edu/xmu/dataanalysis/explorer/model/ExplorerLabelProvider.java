package edu.xmu.dataanalysis.explorer.model;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.IDescriptionProvider;

import edu.xmu.nmr.dataanalysis.logic.explorermodel.AbstractDataNode;
import edu.xmu.nmr.dataanalysis.logic.explorermodel.FidNode;
import edu.xmu.nmr.dataanalysis.logic.explorermodel.FolderNode;
import edu.xmu.nmr.dataanalysis.logic.explorermodel.ParamtersNode;
import edu.xmu.nmr.dataanalysis.logic.explorermodel.VersionNode;

public class ExplorerLabelProvider extends LabelProvider implements
		ILabelProvider, IDescriptionProvider {

	@Override
	public String getDescription(Object anElement) {
		if (anElement instanceof AbstractDataNode) {
			return ((AbstractDataNode) anElement).getNodePath();
		}
		return null;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof AbstractDataNode) {
			return ((AbstractDataNode) element).getName();
		}
		return null;
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof FidNode) {
			return PlatformUI.getWorkbench().getSharedImages()
					.getImage(ISharedImages.IMG_OBJ_ELEMENT);
		} else if (element instanceof ParamtersNode) {
			return PlatformUI.getWorkbench().getSharedImages()
					.getImage(ISharedImages.IMG_OBJ_FILE);
		} else if (element instanceof VersionNode) {
			return PlatformUI.getWorkbench().getSharedImages()
					.getImage(ISharedImages.IMG_OBJ_FOLDER);
		} else if (element instanceof FolderNode) {
			return PlatformUI.getWorkbench().getSharedImages()
					.getImage(ISharedImages.IMG_OBJ_FOLDER);
		}
		return null;
	}
}
