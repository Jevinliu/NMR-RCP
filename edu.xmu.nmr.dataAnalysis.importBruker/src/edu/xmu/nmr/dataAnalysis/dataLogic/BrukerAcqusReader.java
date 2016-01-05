package edu.xmu.nmr.dataAnalysis.dataLogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class BrukerAcqusReader {

	private Logger log = Logger.getLogger(this.getClass());
	private File file;
	private TreeMap<String, Object> params = new TreeMap<String, Object>();

	public BrukerAcqusReader(String filePath) {
		file = new File(filePath);
	}

	public BrukerAcqusReader(URI uri) {
		file = new File(uri);
	}

	public BrukerAcqusReader(File file) {
		this.file = file;
	}

	public boolean parser() {
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			this.read(br);
			Utils.closeStream(fis, isr);
			br.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private void read(BufferedReader br) throws IOException {
		Pattern p1 = Pattern.compile("##((?!\\$).+)=[\\s]*(.*)");
		// Pattern p2 = Pattern.compile("\\$\\$[\\s]*(.*)");
		Pattern p3 = Pattern.compile("##\\$(.+)=[\\s]*(.*)");
		int lineNum = 0;
		if (!br.ready()) {
			return;
		}
		String line = br.readLine();
		lineNum++;
		while (line != null) {
			if (p1.matcher(line).find()) {
				Matcher m = p1.matcher(line);
				m.find();
				if (m.groupCount() < 2) {
					log.error("file(" + file.getPath() + "),line" + lineNum
							+ "parse failure.");
					continue;
				}
				String value = m.group(2);
				if (value.contains("$$")) { // 判断是否在同一行中有注释项，有则去掉
					value = value.substring(0, value.indexOf("$$"));
				}
				params.put(m.group(1), value);
			}
			// if (p2.matcher(line).find()) {
			// Matcher m = p2.matcher(line);
			// m.find();
			// String key = "line" + lineNum; // 当没有该行没有key时，使用line+ 行号作为key
			// params.put(key, m.group(1));
			// }
			if (p3.matcher(line).find()) {
				Matcher m = p3.matcher(line);
				m.find();
				if (m.groupCount() < 2) {
					log.error("file(" + file.getPath() + "),line" + lineNum
							+ "parse failure.");
					continue;
				}
				String value = m.group(2);
				if (value.contains("$$")) {
					value = value.substring(0, value.indexOf("$$"));
				}
				value = this.trimBrackets(value);
				Pattern manyValue = Pattern.compile("\\(0\\.\\.(\\d+)\\)$");
				Pattern oneLineMany = Pattern
						.compile("\\(0\\.\\.(\\d+)\\)(.+)");
				if (manyValue.matcher(value).find()) { // 判断如下类型： ##$TL= (0..7)
					Matcher m1 = manyValue.matcher(value);
					m1.find();
					int count = Integer.parseInt(m1.group(1));
					ArrayList<String> strs = new ArrayList<String>();
					while (count > 0) {
						line = br.readLine();
						String[] values = line.split("\\s");
						for (String s : values) {
							strs.add(s);
						}
						count -= values.length;
					}
					params.put(m.group(1), strs);
				} else if (oneLineMany.matcher(value).find()) {
					// 判断如下类型： (0..7)83 83 83 83 83 83 83 83
					Matcher m2 = oneLineMany.matcher(value);
					m2.find();
					ArrayList<String> strs = new ArrayList<String>();
					String[] values = m2.group(2).split("\\s");
					for (String s : values) {
						strs.add(s);
					}
					params.put(m.group(1), strs);
				} else {
					params.put(m.group(1), value);
				}
			}
			line = br.readLine();
			lineNum++;
		}
		log.info("Parsing file(" + file.getPath() + ") is finished.");
	}

	public File getFile() {
		return file;
	}

	public TreeMap<String, Object> getParams() {
		return params;
	}

	/**
	 * 判断是否找到的字符串为 <String> 形式，如果是，取出尖括号
	 * 
	 * @param str
	 *            源字符串
	 * @return
	 */
	private String trimBrackets(String str) {
		Pattern bracketPat = Pattern.compile("<(.*)>");
		Matcher m = bracketPat.matcher(str);
		if (m.find()) {
			return m.group(1);
		}
		return str;
	}

	/**
	 * 根据参数名（忽略大小写）,得到相应地value值，
	 * 
	 * @param key
	 *            参数名
	 * @return
	 */
	public String getString(String key) {
		return (String) params.get(key.toUpperCase());
	}

	public int getInt(String key) {
		Object value = params.get(key.toUpperCase());
		if (value instanceof ArrayList) {
			log.error("This " + key + "'s value is array.");
			return Integer.MIN_VALUE;
		} else {
			return Integer.parseInt((String) value);
		}
	}

	public float getFloat(String key) {
		Object value = params.get(key.toUpperCase());
		if (value instanceof ArrayList) {
			log.error("This " + key + "'s value is array.");
			return Float.MIN_VALUE;
		} else {
			return Float.parseFloat((String) value);
		}
	}

	public double getDouble(String key) {
		Object value = params.get(key.toUpperCase());
		if (value instanceof ArrayList) {
			log.error("This " + key + "'s value is array.");
			return Double.MIN_VALUE;
		} else {
			return Double.parseDouble((String) value);
		}
	}

	public ArrayList<Integer> getIntArray(String key) {
		Object value = params.get(key.toUpperCase());
		if (value instanceof ArrayList) {
			ArrayList<Integer> res = new ArrayList<Integer>();
			for (String s : (ArrayList<String>) value) {
				res.add(Integer.parseInt(s));
			}
			return res;
		}
		log.error("This " + key + "'s value is not array.");
		return null;
	}

	public ArrayList<Float> getFloatArray(String key) {
		Object value = params.get(key.toUpperCase());
		if (value instanceof ArrayList) {
			ArrayList<Float> res = new ArrayList<Float>();
			for (String s : (ArrayList<String>) value) {
				res.add(Float.parseFloat(s));
			}
			return res;
		}
		log.error("This " + key + "'s value is not array.");
		return null;
	}

	public ArrayList<Double> getDoubleArray(String key) {
		Object value = params.get(key.toUpperCase());
		if (value instanceof ArrayList) {
			ArrayList<Double> res = new ArrayList<Double>();
			for (String s : (ArrayList<String>) value) {
				res.add(Double.parseDouble(s));
			}
			return res;
		}
		log.error("This " + key + "'s value is not array.");
		return null;
	}
}
