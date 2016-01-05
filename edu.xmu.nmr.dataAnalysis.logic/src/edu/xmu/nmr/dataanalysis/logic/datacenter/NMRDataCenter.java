package edu.xmu.nmr.dataanalysis.logic.datacenter;

import edu.xmu.nmr.dataanalysis.logic.explorermodel.FolderNode;

/**
 * 使用单例模式，将读取的数据缓存
 * 
 * @author Jevin
 *
 */
public class NMRDataCenter {
	private static final NMRDataCenter dataCenter = new NMRDataCenter();
	private final FolderNode nmrData; // 其中
										// key代表路径，AbstractDataModel代表数据模型

	private NMRDataCenter() {
		nmrData = new FolderNode("", null, "");
	}

	public static NMRDataCenter getInstance() {
		return dataCenter;
	}

	public FolderNode getNmrData() {
		return nmrData;
	}
}
