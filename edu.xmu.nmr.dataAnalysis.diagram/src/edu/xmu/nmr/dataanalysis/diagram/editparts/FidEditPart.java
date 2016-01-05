package edu.xmu.nmr.dataanalysis.diagram.editparts;

import java.beans.PropertyChangeEvent;

import org.eclipse.draw2d.IFigure;

import edu.xmu.nmr.dataanalysis.diagram.figures.LineFigure;
import edu.xmu.nmrdataanalysis.diagram.model.FidData;

public class FidEditPart extends NMRAbstractEditPart {

	public FidEditPart() {
	}

	@Override
	protected IFigure createFigure() {
		IFigure fidFigure = new LineFigure();
		return fidFigure;
	}

	@Override
	public void refreshVisuals() {
		LineFigure figure = (LineFigure) getFigure();
		FidData fidData = (FidData) getModel();
		figure.setRawData(fidData.getRawData()); // 模型层与view层结合，装填数据
		figure.setAbsMax(fidData.getAbsMax());
		figure.setCoordinatetf(fidData.getCoordinateTf());
		figure.setLayout(fidData.getLayout()); // 初始化
	}

	@Override
	protected void createEditPolicies() {

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		if (evt.getPropertyName().equals(FidData.PRO_LS_FIDDATA)) {
			System.out.println("fiddata 刷新");
			refreshVisuals();
		}
		if (evt.getPropertyName().equals(FidData.PRO_LS_STEPSIZE)) {
			System.out.println("stepsize 刷新");
			refreshVisuals();
		}
	}

}
