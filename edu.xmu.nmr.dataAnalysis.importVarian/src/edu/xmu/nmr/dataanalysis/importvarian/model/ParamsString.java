package edu.xmu.nmr.dataanalysis.importvarian.model;

import java.util.ArrayList;

/**
 * VNMRJ参数中，该参数为字符串时，参数类，如果numEnd参数为0 ，enumValues为empty
 * 
 * @author Jevin
 *
 */
public class ParamsString extends ParameterObject {

	private ArrayList<String> currentValues;
	public ArrayList<String> enumValues = new ArrayList<String>();

	public ParamsString() {
	}

	public ArrayList<String> getCurrentValues() {
		return currentValues;
	}

	public void setCurrentValues(ArrayList<String> currentValues) {
		this.currentValues = currentValues;
	}

	public String toString() {
		StringBuffer strBuf = new StringBuffer(super.toString());
		strBuf.append("currentValues: ");
		if (this.currentValues != null) {
			for (String value : this.currentValues) {
				strBuf.append(value).append(" ");
			}
		}
		strBuf.append("\r");
		strBuf.append("enumValues: ");
		if (this.enumValues != null) {
			for (String value : this.enumValues) {
				strBuf.append(value).append(" ");
			}
		}
		strBuf.append("\r");
		return strBuf.toString();
	}

	@Override
	public String getCurrentValuesTrim() {
		if (currentValues != null) {
			String value = currentValues.toString();
			return value.substring(1, value.length() - 1);
		}
		return null;

	}

	@Override
	public void setCurrentValues(String value) {
		if (value != null && value != "") {
			String[] vs = value.split(",");
			currentValues = new ArrayList<String>();
			for (String s : vs) {
				currentValues.add(s);
			}
		}
	}

	@Override
	public int hashCode() {
		if (currentValues == null) {
			return 0;
		}
		int result = 0;
		for (String d : currentValues) {
			result = 31 * result + (d == null ? 0 : d.hashCode());
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
		ParamsString one = ((ParamsString) obj);
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
		// TODO Auto-generated method stub
		ParamsString one = (ParamsString) super.clone();
		one.enumValues = (ArrayList<String>) this.currentValues.clone();
		return one;
	}
}
