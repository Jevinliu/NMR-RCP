/**
 * @Title ShowFullRetargetAction.java
 * @Package edu.xmu.nmr.dataanalysis.diagram.retargetactions
 * @brief TODO
 * Copyright Copyright (c) 2015
 * Wuhan Zhongke Magnetic Resonance
 * Technology Co., Ltd.
 *
 * @author Jevin
 * @date 2016年1月23日 上午11:32:37
 * @version V1.0
 */
package edu.xmu.nmr.dataanalysis.diagram.retargetactions;

import org.eclipse.ui.actions.RetargetAction;

import edu.xmu.nmr.dataanalysis.diagram.actions.helper.DAActionConstants;
import edu.xmu.nmr.dataanalysis.diagram.others.DASharedImages;

/**
 * ShowFullRetargetAction
 * <p>
 * TODO
 * </p>
 * 
 * @see
 */
public class ShowFullRetargetAction extends RetargetAction {
    
    /**
     * TODO
     * 
     * @param actionID
     * @param text
     * @param style
     */
    public ShowFullRetargetAction() {
        super(null, null);
        setId(DAActionConstants.DA_SHOW_FULL);
        setActionDefinitionId(DAActionConstants.DA_SHOW_FULL);
        setToolTipText("Show Full");
        setText("Full");
        setImageDescriptor(DASharedImages.FULL);
    }
    
}
