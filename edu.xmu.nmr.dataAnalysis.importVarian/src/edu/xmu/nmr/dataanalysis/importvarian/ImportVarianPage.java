package edu.xmu.nmr.dataanalysis.importvarian;

import org.eclipse.jface.viewers.ISelection;

import edu.xmu.dataanalysis.importpage.wizard.ImportPage;

public class ImportVarianPage extends ImportPage {

	public ImportVarianPage(ISelection selection, String message) {
		super(selection, message);
	}

	@Override
	public boolean judgeFileTypeIsNeed(String fileName) {
		return fileName.equalsIgnoreCase("procpar");
	}

}
