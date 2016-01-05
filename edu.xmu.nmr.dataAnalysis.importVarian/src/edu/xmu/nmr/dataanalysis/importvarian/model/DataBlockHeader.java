package edu.xmu.nmr.dataanalysis.importvarian.model;

public class DataBlockHeader implements Cloneable {

	public short scale;
	public short status;
	public short index;
	public short mode;
	public int ctcount;
	public float lpval;
	public float rpval;
	public float lvl;
	public float tlt;

	public DataBlockHeader clone() {
		DataBlockHeader dataBlockHeader = null;
		try {
			dataBlockHeader = (DataBlockHeader) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return dataBlockHeader;
	}

	@Override
	public String toString() {
		StringBuffer strBuf = new StringBuffer("=== DataBlockHeader ===")
				.append("\r");
		strBuf.append("scale: ").append(scale).append("\r");
		strBuf.append("status: ").append(status).append("\r");
		strBuf.append("index: ").append(index).append("\r");
		strBuf.append("mode: ").append(mode).append("\r");
		strBuf.append("ctcount: ").append(ctcount).append("\r");
		strBuf.append("lpval: ").append(lpval).append("\r");
		strBuf.append("rpval: ").append(rpval).append("\r");
		strBuf.append("lvl: ").append(lvl).append("\r");
		strBuf.append("tlt: ").append(tlt).append("\r");
		return strBuf.toString();

	}
}
