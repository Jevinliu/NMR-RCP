package edu.xmu.nmr.dataanalysis.logic.explorermodel;

import java.util.TreeMap;

import org.apache.log4j.Logger;

import edu.xmu.nmr.dataanalysis.logic.datacenter.FileType;

public class ParamtersNode extends AbstractDataNode {

	public static final String PARAMS = "params";
	private TreeMap<String, Object> params;
	private Logger log = Logger.getLogger(this.getClass());

	public ParamtersNode(String nodePath, FileType fileType) {
		super(nodePath, fileType, "paramters");
	}

	public TreeMap<String, Object> getParams() {
		return params;
	}

	public void setParams(TreeMap<String, Object> params) {
		this.params = params;
	}

	/**
	 * 该方法添加或者修改一个参数的值，如果已经包含这个参数，则修改该参数值，如果没有该参数为添加
	 * 
	 * @param isOpenFire
	 *            boolean 是否开启 开启属性改变监听器
	 * @param key
	 *            要添加的key
	 * @param value
	 *            要添加的value
	 */
	public void putParam(boolean isOpenFire, String key, Object value) {
		Object oldValue = params.get(key);
		if (oldValue != null && oldValue.equals(value)) {
			return;
		}
		params.put(key, value);
		if (isOpenFire) {
			log.debug("Has changed params' value, old value: " + oldValue
					+ ", new value: " + value.toString());
			firePropertyChange(PARAMS, oldValue, value);
		}
	}

	@Override
	public boolean hasChildren() {
		return false;
	}

	@Override
	public void addChild(AbstractDataNode child) {
		return;
	}

	@Override
	public void removeChild(AbstractDataNode child) {
		return;
	}

	@Override
	public void removeChildren() {
		return;
	}
}
