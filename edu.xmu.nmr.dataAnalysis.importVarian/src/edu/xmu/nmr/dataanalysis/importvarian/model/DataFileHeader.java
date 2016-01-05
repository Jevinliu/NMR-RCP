package edu.xmu.nmr.dataanalysis.importvarian.model;

public class DataFileHeader {

	// number of blocks in file
	public int nblocks;
	// number of traces per block
	public int ntraces;
	// number of elements per trace
	public int np;
	// number of bytes per element
	public int ebytes;
	// number of bytes per trace
	public int tbytes;
	// number of bytes per block
	public int bbytes;
	// software version, file_id status bits
	public short vers_id;
	// status of whole file
	public short status;
	// number of block headers per block
	public int nbheaders;

	@Override
	public String toString() {

		StringBuffer strBuf = new StringBuffer("=== DataFileHeader ===")
				.append("\r");
		strBuf.append("nblocks: ").append(nblocks).append("\r");
		strBuf.append("ntraces: ").append(ntraces).append("\r");
		strBuf.append("np: ").append(np).append("\r");
		strBuf.append("ebytes: ").append(ebytes).append("\r");
		strBuf.append("tbytes: ").append(tbytes).append("\r");
		strBuf.append("bbytes: ").append(bbytes).append("\r");
		strBuf.append("vers_id: ").append(vers_id).append("\r");
		strBuf.append("status: ").append(status).append("\r");
		strBuf.append("nbheaders: ").append(nbheaders).append("\r");
		return strBuf.toString();
	}
}
