package edu.xmu.nmr.dataanalysis.logic.explorermodel;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import edu.xmu.nmr.dataanalysis.logic.datacenter.FileType;

public class FidNode extends AbstractDataNode {

	private Map<String, ArrayList<Float>> data = new LinkedHashMap<String, ArrayList<Float>>();

	public FidNode(String nodePath, FileType fileType, String name) {
		super(nodePath, fileType, name);
	}

	public String toString() {
		return this.getNodePath() + File.separator + this.getName();
	}

	public Map<String, ArrayList<Float>> getData() {
		return data;
	}

	public void putData(String no, ArrayList<Float> fidData) {
		data.put(no, fidData);
	}

	@Override
	public boolean hasChildren() {
		return false;
	}

	@Override
	public void addChild(AbstractDataNode child) {
		return;
	}

	@Override
	public void removeChild(AbstractDataNode child) {
		return;
	}

	@Override
	public void removeChildren() {
		return;
	}

}
