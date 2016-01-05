package edu.xmu.nmr.dataanalysis.diagram.multieditor;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import edu.xmu.nmr.dataanalysis.diagram.editparts.NMREditPartFactory;
import edu.xmu.nmr.dataanalysis.diagram.figures.PointsTools;
import edu.xmu.nmrdataanalysis.diagram.model.Container;
import edu.xmu.nmrdataanalysis.diagram.model.FidData;
import edu.xmu.nmrdataanalysis.diagram.model.Ruler;
import edu.xmu.nmrdataanalysis.diagram.model.RulerOrient;

/**
 * <p>
 * {@link NMRDiagEditor}的Fid页面，主要用来显示Fid图形
 * </p>
 * 
 * @author Jevin
 *
 */
public class FidEditorPage extends GraphicalEditor {

	private Logger log = Logger.getLogger(this.getClass());
	public static final String ID = "edu.xmu.nmr.dataAnalysis.diagram.editorparts.fidEditorPage";
	private FidData fidData = new FidData(); // 模型节点
	private Ruler leftRuler;
	private Ruler bottomRuler;
	private GraphicalViewer viewer;

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		if (!(input instanceof FidEditorInput)) {
			log.error("FidEditorPage's editorInput is error.");
			return;
		}
		super.init(site, input);
	}

	public FidEditorPage() {
		setEditDomain(new DefaultEditDomain(this));
	}

	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		viewer = getGraphicalViewer();
		viewer.getControl().addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				FigureCanvas fc = (FigureCanvas) e.getSource();
				org.eclipse.swt.graphics.Rectangle rect = fc.getBounds();
				float scale = rect.height
						/ (float) leftRuler.getLayout().height;
				ScalableRootEditPart srep = (ScalableRootEditPart) viewer
						.getRootEditPart();
				ZoomManager zm = srep.getZoomManager();
				zm.setZoomAsText(ZoomManager.FIT_HEIGHT);

				// if (scale != 1.0) {
				// leftRuler.setLayout(new Rectangle(rect.x, rect.y,
				// rect.width, rect.height));
				// // leftRuler.setInterval(leftRuler.getInterval() * scale);
				// zm.setZoom(scale);
				// }
			}
		});
		viewer.setEditPartFactory(new NMREditPartFactory()); // 添加editpart工厂，通过工厂创建editpart
	}

	/**
	 * 设置model层的fid数据
	 * 
	 * @param rawData
	 * @param rawStepSize
	 */
	public void setFidData(ArrayList<Float> rawData, float rawStepSize) {
		fidData.setRawData(rawData);
		fidData.setRawStepSize(rawStepSize);
		float absMax = PointsTools.getAbsMax(rawData);
		// leftRuler.setAbsMax(absMax);
		// bottomRuler.setDataSize(rawData.size());
		// bottomRuler.setRawStepSize(rawStepSize);

		org.eclipse.swt.graphics.Rectangle rect = Display.getDefault()
				.getClientArea();
		Rectangle bounds = new Rectangle(rect.x + 10, rect.y + 10, 59,
				rect.height - 80);
		leftRuler.setLayout(bounds);
		leftRuler.setStepSize(2 * absMax / rect.height);

		// bounds = new Rectangle(rect.x + 70, rect.y + rect.height - 69,
		// rect.width - 80, 59);
	}

	@Override
	protected void initializeGraphicalViewer() {
		viewer = getGraphicalViewer();
		viewer.setContents(createContainer()); // 设置model
	}

	/**
	 * 创建绘图所需要的model层
	 */
	private Container createContainer() {
		Container container = new Container();
		fidData.setParent(container);
		leftRuler = new Ruler();
		leftRuler.setOrient(RulerOrient.LEFT);
		// bottomRuler = new Ruler();
		// bottomRuler.setOrient(RulerOrient.BOTTOM);
		leftRuler.setParent(container);
		// bottomRuler.setParent(container);
		return container;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {

	}

	@Override
	public void setFocus() {
		super.setFocus();
	}
}
