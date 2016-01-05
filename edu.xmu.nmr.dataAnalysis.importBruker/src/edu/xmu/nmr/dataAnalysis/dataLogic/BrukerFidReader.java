package edu.xmu.nmr.dataAnalysis.dataLogic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class BrukerFidReader {

	private File file;
	private int bigOrLittle;
	private ArrayList<Float> fidData;
	private FileInputStream fis;

	public BrukerFidReader(String filePath, int bigOrLittle)
			throws FileNotFoundException {
		this(new File(filePath), bigOrLittle);
		this.file = new File(filePath);
	}

	public BrukerFidReader(URI uri, int bigOrLittle)
			throws FileNotFoundException {
		this(new File(uri), bigOrLittle);
		this.file = new File(uri);
	}

	public BrukerFidReader(File file, int bigOrLittle)
			throws FileNotFoundException {
		this(new FileInputStream(file), bigOrLittle);
		this.file = file;
	}

	public BrukerFidReader(FileInputStream fis, int bigOrLittle) {
		this.bigOrLittle = bigOrLittle;
		this.fis = fis;
		fidData = new ArrayList<Float>();
	}

	/**
	 * 获取当前字节序
	 * 
	 * @return
	 */
	public ByteOrder getByteOrder() {
		if (bigOrLittle == 1) {
			return ByteOrder.BIG_ENDIAN;
		}
		return ByteOrder.LITTLE_ENDIAN;
	}

	public boolean parser() {
		try {
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			ByteBuffer data = ByteBuffer.wrap(buffer);
			data.order(getByteOrder());
			while (data.hasRemaining()) {
				fidData.add(Float.valueOf(String.valueOf(data.getInt())));
			}
			fis.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 返回当前读取的文件
	 * 
	 * @return
	 */
	public File getFile() {
		return file;
	}

	/**
	 * 获取当前的fid数据
	 * 
	 * @return
	 */
	public ArrayList<Float> getFidData() {
		return fidData;
	}
}
