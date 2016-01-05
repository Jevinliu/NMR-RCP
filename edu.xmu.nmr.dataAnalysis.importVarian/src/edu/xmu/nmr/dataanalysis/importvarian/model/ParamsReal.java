package edu.xmu.nmr.dataanalysis.importvarian.model;

import java.util.ArrayList;

/**
 * VNMRJ参数中，该参数为实数时，参数类，如果numEnd参数为0 ，enumValues为empty
 * 
 * @author Jevin
 *
 */
public class ParamsReal extends ParameterObject {

	private ArrayList<Double> currentValues;

	public ParamsReal() {

	}

	public ArrayList<Double> getCurrentValues() {
		return currentValues;
	}

	/**
	 * 将逗号分隔符的字符串转换为 currentValues
	 * 
	 * @param value
	 *            String 设置的currentValues的字符串
	 */
	@Override
	public void setCurrentValues(String value) {
		if (value != null && value != "") {
			String[] vs = value.split(",");
			currentValues = new ArrayList<Double>();
			for (String s : vs) {
				currentValues.add(Double.parseDouble(s.trim()));
			}
		}
	}

	/**
	 * 获取currentValue的当前值得字符串形式 ，如 [1.0,3.0] -> 1.0,3.0
	 * 
	 * @return String 返回trim掉“[]”的字符串
	 */
	@Override
	public String getCurrentValuesTrim() {
		if (currentValues != null) {
			String value = currentValues.toString();
			return value.substring(1, value.length() - 1);
		}
		return null;
	}

	public void setCurrentValues(ArrayList<Double> currentValues) {
		this.currentValues = currentValues;
	}

	public String toString() {
		StringBuffer strBuf = new StringBuffer(super.toString());
		strBuf.append("currentValues: ");
		if (this.currentValues != null) {
			for (Double value : this.currentValues) {
				strBuf.append(value).append(" ");
			}
		}
		strBuf.append("\r");
		return strBuf.toString();
	}

	@Override
	public int hashCode() {
		if (currentValues == null) {
			return 0;
		}
		int result = 0;
		for (Double d : currentValues) {
			result = (int) (31 * result + (d == null ? 0 : d));
		}
		return result + name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this)
			return true;
		if (this.getClass() != obj.getClass())
			return false;
		ParamsReal one = ((ParamsReal) obj);
		if (one.getCurrentValues() == null) {
			return false;
		}
		if (currentValues.size() != one.getCurrentValues().size()) {
			return false;
		}
		for (int i = 0; i < currentValues.size(); i++) {
			if (!currentValues.get(i).equals(one.getCurrentValues().get(i))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
