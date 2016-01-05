package edu.xmu.nmr.dataanalysis.logic.explorermodel;

import edu.xmu.nmr.dataanalysis.logic.datacenter.FileType;

public class FolderNode extends AbstractDataNode {

	public FolderNode(String nodePath, FileType fileType, String name) {
		super(nodePath, fileType, name);
	}

}
