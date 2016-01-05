package edu.xmu.nmr.dataanalysis.importvarian.logic;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import edu.xmu.nmr.dataanalysis.importvarian.model.DataFileHeader;


public class VarianFidReader {

	private Logger log = Logger.getLogger(this.getClass());
	private DataFileHeader dataFileHeader;
		private File file;
		private List<ArrayList<Float>> traces = new ArrayList<ArrayList<Float>>(); 

	public VarianFidReader(String filePath) {
		file = new File(filePath);
		if (!file.exists()) {
			log.error("The file(" + filePath + ") is not exists.");
		}
	}

	public VarianFidReader(File file) {
		this.file = file;
		if (!this.file.exists()) {
			log.error(this.file.getPath() + "is not exists.");
		}
	}

	
	public VarianFidReader(URI uri) {
		file = new File(uri);
		if (!file.exists()) {
			log.error("The file(" + file.getAbsolutePath() + ") is not exists.");
		}
	}

	
	public boolean parser() {
		try {
			FileInputStream fis = new FileInputStream(file);
			DataInputStream dis = new DataInputStream(fis);
			dataFileHeader = this.getDataFileHeader(dis);
			this.getFidData(dis);
			Utiles.closeStream(fis, dis);
			log.info("file(" + file.getPath()
					+ ") has been parsed successfully.");
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	private DataFileHeader getDataFileHeader(DataInputStream dis) {
		DataFileHeader tempFileHeader = new DataFileHeader();
		try {
			tempFileHeader.nblocks = dis.readInt();
			tempFileHeader.ntraces = dis.readInt();
			tempFileHeader.np = dis.readInt();
			tempFileHeader.ebytes = dis.readInt();
			tempFileHeader.tbytes = dis.readInt();
			tempFileHeader.bbytes = dis.readInt();
			tempFileHeader.vers_id = dis.readShort();
			tempFileHeader.status = dis.readShort();
			tempFileHeader.nbheaders = dis.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tempFileHeader;
	}

	
	private void getFidData(DataInputStream dis) {
		for (int b = 0; b < dataFileHeader.nblocks; b++) {
			try {
				dis.skip(dataFileHeader.nbheaders * 28);
						for (int i = 0; i < dataFileHeader.ntraces; i++) {
					traces.add((ArrayList<Float>) readIntegerOrFloat(
							dataFileHeader.tbytes, dis));
										}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	private List<Float> readIntegerOrFloat(int numByte, DataInputStream dis)
			throws IOException {
		List<Float> data = new ArrayList<Float>();
		if (isFloat()) {
			for (int i = 0; i < numByte; i += 4) {
				data.add(dis.readFloat());
			}
		} else {
			if (is32Bit()) {
				for (int i = 0; i < numByte; i += 4) {
					data.add((float) dis.readInt());
				}
			} else {
				for (int i = 0; i < numByte; i += 2) {
					data.add((float) dis.readShort());
				}
			}
		}
		return data;
	}

	public boolean isFloat() {
		return (this.dataFileHeader.status & 0x0008) == 0x0008;
	}

	public boolean is32Bit() {
		return (this.dataFileHeader.status & 0x0004) == 0x0004;
	}

	public boolean isComplex() {
		return (dataFileHeader.status & 0x0010) == 0x0010;
	}

	public DataFileHeader getDataFileHeader() {
		return dataFileHeader;
	}

	public File getFile() {
		return file;
	}

	public ArrayList<ArrayList<Float>> getTraces() {
		return (ArrayList<ArrayList<Float>>) traces;
	}
}
