package edu.xmu.nmrdataanalysis.diagram.model;

import java.util.ArrayList;

/**
 * 
 * NMRDataSets
 * <p>
 * NMR数据处理所需要的数据集的 bean
 * </p>
 * 
 * @see
 */
public class NMRDataSetBean {
    
    /**
     * fid 数据集
     */
    private ArrayList<Float> fidDataSet;
    
    /**
     * spec 数据集
     */
    private ArrayList<Float> spceDataSet;
    
    public ArrayList<Float> getFidDataSet() {
        return fidDataSet;
    }
    
    public void setFidDataSet(ArrayList<Float> fidDataSet) {
        this.fidDataSet = fidDataSet;
    }
    
    public ArrayList<Float> getSpceDataSet() {
        return spceDataSet;
    }
    
    public void setSpceDataSet(ArrayList<Float> spceDataSet) {
        this.spceDataSet = spceDataSet;
    }
}
