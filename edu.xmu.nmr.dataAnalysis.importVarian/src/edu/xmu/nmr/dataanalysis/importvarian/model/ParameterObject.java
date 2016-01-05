package edu.xmu.nmr.dataanalysis.importvarian.model;

/**
 * VNMRJ参数 的抽象父类，VNMRJ的所有参数都继承自该父类，根据 basictype， numValues， numEnd，产生不同的子类
 * 
 * @author Jevin
 *
 */
public abstract class ParameterObject implements Cloneable {

	// line 1
	/**
	 * the paramter name
	 */
	public String name;
	/**
	 * an integer value for the paramter type: 0 (undefined), 1 (real), 2
	 * (string), 3 (delay), 4 (flag), 5 (frequency), 6 (pulse), 7 (integer).
	 */
	public int subtype;
	/**
	 * an integer value: 0 (undefined), 1 (real), 2 (string).
	 */
	public int basictype;
	/**
	 * is a real number for the maximum value that the parameter can contain, or
	 * an index to a maximum value in the parameter parmax (found in
	 * /vnmr/conpar).
	 */
	public double maxvalue;
	/**
	 * a real number for the minimum value that the parameter can contain or an
	 * index to a minimum value in the parameter parmin
	 */
	public double minvalue;
	/**
	 * a real number for the step size in which parameters can be entered or
	 * index to a step size in the parameter parstep (found in /vnmr/conpar). If
	 * stepsize is 0, it is ignored. Applies to real types only.
	 */
	public double stepsize;
	/**
	 * an integer value: 0 (ALL), 1 (SAMPLE), 2 (ACQUISITION), 3 (PROCESSING), 4
	 * (DISPLAY), 5 (SPIN).
	 */
	public int Ggroup;
	/**
	 * an integer value.
	 */
	public int Dgroup;
	/**
	 * a 32-bit word made up of the following bit masks, which are summed to
	 * form the full mask
	 */
	public int protection;
	/**
	 * an integer value: 0 (not active), 1 (active).
	 */
	public int active;
	public int intptr;

	// line 2
	/**
	 * the number of values the paramters is set to.
	 */
	public int numValues;

	// line 3
	/**
	 * the number of enumerabler values.
	 */
	public int numEnd;

	/**
	 * 获取currentValue的当前值得字符串形式 ，如 [1.0,3.0] -> 1.0,3.0
	 * 
	 * @return String 返回trim掉“[]”的字符串
	 */
	public abstract String getCurrentValuesTrim();

	/**
	 * 将逗号分隔符的字符串转换为 currentValues
	 * 
	 * @param value
	 *            String 设置的currentValues的字符串
	 */
	public abstract void setCurrentValues(String value);

	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("===== ").append(this.name).append(" =====\r");
		strBuf.append("subtype: ").append(this.subtype).append(" \r");
		strBuf.append("basictype: ").append(this.basictype).append(" \r");
		strBuf.append("maxvalue: ").append(this.maxvalue).append(" \r");
		strBuf.append("minvalue: ").append(this.minvalue).append(" \r");
		strBuf.append("stepsize: ").append(this.stepsize).append(" \r");
		strBuf.append("Ggroup: ").append(this.Ggroup).append(" \r");
		strBuf.append("Dgroup: ").append(this.Dgroup).append(" \r");
		strBuf.append("protection: ").append(this.protection).append(" \r");
		strBuf.append("active: ").append(this.active).append(" \r");
		strBuf.append("intptr: ").append(this.intptr).append(" \r");
		strBuf.append("numValues: ").append(this.numValues).append(" \r");
		strBuf.append("numEnd: ").append(this.numEnd).append(" \r");
		return strBuf.toString();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
}
