package edu.xmu.nmr.dataanalysis.importvarian.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import edu.xmu.nmr.dataanalysis.importvarian.model.ParameterObject;
import edu.xmu.nmr.dataanalysis.importvarian.model.ParamsReal;
import edu.xmu.nmr.dataanalysis.importvarian.model.ParamsString;

public class VarianParamsReader {

	private Logger log = Logger.getLogger(this.getClass());
	private File file;
	private TreeMap<String, Object> params;

	public VarianParamsReader(String filePath) {
		file = new File(filePath);
	}

	public VarianParamsReader(URI uri) {
		file = new File(uri);
	}

	public VarianParamsReader(File file) {
		this.file = file;
	}

	public boolean parser() {
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			params = this.read(br);
			Utiles.closeStream(isr, br);
			if (params == null) {
				return false;
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.error("This file(" + file.getAbsolutePath() + ") is failure.");
		return false;
	}

	private TreeMap<String, Object> read(BufferedReader br) throws IOException {
		TreeMap<String, Object> params = new TreeMap<String, Object>();
		String line = br.readLine();
		while (line != null) {
			String[] strs = line.split("\\s+");
			if (strs.length < 3) {
				log.error("file(" + file.getPath() + ") cannot be read.");
				return null;
			}
			switch (strs[2]) {
			case "1":
				ParameterObject real = new ParamsReal();
				real.name = strs[0];
				real.subtype = Integer.parseInt(strs[1]);
				real.basictype = Integer.parseInt(strs[2]);
				real.maxvalue = Double.parseDouble(strs[3]);
				real.minvalue = Double.parseDouble(strs[4]);
				real.stepsize = Double.parseDouble(strs[5]);
				real.Ggroup = Integer.parseInt(strs[6]);
				real.Dgroup = Integer.parseInt(strs[7]);
				real.protection = Integer.parseInt(strs[8]);
				real.active = Integer.parseInt(strs[9]);
				real.intptr = Integer.parseInt(strs[10]);
				line = br.readLine();
				if (line != null) {
					String[] line2 = line.split("\\s+");
					real.numValues = Integer.parseInt(line2[0]);
					ArrayList<Double> cValues = new ArrayList<Double>();
					for (int i = 1; i < line2.length; i++) {
						// ((ParamsReal) real).currentValues.add(Double
						// .parseDouble(line2[i]));
						cValues.add(Double.parseDouble(line2[i]));
					}
					((ParamsReal) real).setCurrentValues(cValues);
				}
				line = br.readLine();
				if (line != null) {
					String[] line3 = line.split("\\s+");
					real.numEnd = Integer.parseInt(line3[0]);
				}
				params.put(real.name, real);
				line = br.readLine();
				break;
			case "2":
				ParameterObject str = new ParamsString(); // 用到向上转型
				str.name = strs[0];
				str.subtype = Integer.parseInt(strs[1]);
				str.subtype = Integer.parseInt(strs[1]);
				str.basictype = Integer.parseInt(strs[2]);
				str.maxvalue = Double.parseDouble(strs[3]);
				str.minvalue = Double.parseDouble(strs[4]);
				str.stepsize = Double.parseDouble(strs[5]);
				str.Ggroup = Integer.parseInt(strs[6]);
				str.Dgroup = Integer.parseInt(strs[7]);
				str.protection = Integer.parseInt(strs[8]);
				str.active = Integer.parseInt(strs[9]);
				str.intptr = Integer.parseInt(strs[10]);
				line = br.readLine();
				if (line != null) {
					String[] line2 = this.readLine2(line);
					str.numValues = Integer.parseInt(line2[0]);
					ArrayList<String> cValue = new ArrayList<String>();
					cValue.add(line2[1]);
					if (str.numValues > 1) {
						for (int i = 1; i < str.numValues; i++) {
							line = br.readLine();
							cValue.add(trimQuotation(line));
						}
					}
					((ParamsString) str).setCurrentValues(cValue);
				}
				line = br.readLine();
				if (line != null) {
					String[] line3 = line.split("\\s+");
					str.numEnd = Integer.parseInt(line3[0]);
					if (str.numEnd > 0) {
						for (int i = 1; i <= str.numEnd; i++) {
							((ParamsString) str).enumValues
									.add(trimQuotation(line3[i]));
						}
					}
				}
				params.put(str.name, str);
				line = br.readLine();
				break;
			}
		}
		log.info("file(" + file.getPath() + ") has been read successfully.");
		return params;
	}

	/**
	 * 截取字符串，去除字符串两边的双引号
	 * 
	 * @param str
	 *            指定的原始字符串
	 * @return String 返回截取后的字符串
	 */
	private String trimQuotation(String str) {
		if (str.length() <= 1) {
			return str;
		}
		return str.substring(1, str.length() - 1);
	}

	/**
	 * 解析第二行数据，获取字符串数量，和第一个字符串值
	 * 
	 * @param str
	 * @return
	 */
	private String[] readLine2(String str) {
		List<String> strs = new ArrayList<String>();
		Pattern pattern = Pattern.compile("(\\d+)\\s+\"(.*)\"");
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) {
			for (int i = 1; i <= matcher.groupCount(); i++) {
				strs.add(matcher.group(i));
			}
		}
		return strs.toArray(new String[] {});
	}

	public File getFile() {
		return file;
	}

	/**
	 * 获得参数map
	 * 
	 * @return
	 */
	public TreeMap<String, Object> getParams() {
		return params;
	}
}
