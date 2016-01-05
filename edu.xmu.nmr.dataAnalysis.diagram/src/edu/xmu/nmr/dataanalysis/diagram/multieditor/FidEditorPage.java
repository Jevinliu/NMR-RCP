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
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import edu.xmu.nmr.dataanalysis.diagram.editparts.NMREditPartFactory;
import edu.xmu.nmr.dataanalysis.diagram.figures.PointsTools;
import edu.xmu.nmr.dataanalysis.diagram.layouts.LayoutUtils;
import edu.xmu.nmrdataanalysis.diagram.model.Container;
import edu.xmu.nmrdataanalysis.diagram.model.ContainerType;
import edu.xmu.nmrdataanalysis.diagram.model.FidData;
import edu.xmu.nmrdataanalysis.diagram.model.HorizontalRuler;
import edu.xmu.nmrdataanalysis.diagram.model.Ruler;
import edu.xmu.nmrdataanalysis.diagram.model.RulerOrient;
import edu.xmu.nmrdataanalysis.diagram.model.VerticalRuler;

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
	private VerticalRuler leftRuler;
	private HorizontalRuler bottomRuler;
	private GraphicalViewer viewer;
	private Rectangle clientArea;
	private Container container;

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
		clientArea = LayoutUtils.getClientArea();
	}

	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		viewer = getGraphicalViewer();
		viewer.getControl().addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				FigureCanvas fc = (FigureCanvas) e.getSource();
				ScalableRootEditPart srep = (ScalableRootEditPart) viewer
						.getRootEditPart();
				ZoomManager zm = srep.getZoomManager();
				org.eclipse.swt.graphics.Rectangle rect = fc.getBounds();
				if (rect.height > rect.width) {
					zm.setZoomAsText(ZoomManager.FIT_WIDTH);
				} else {
					zm.setZoomAsText(ZoomManager.FIT_HEIGHT);
				}
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

		float absMax = PointsTools.getAbsMax(rawData);
		if (fidData == null || leftRuler == null || bottomRuler == null) {
			log.error("Fid or Axis' model is error.");
			return;
		}
		fidData.setAbsMax(absMax);
		fidData.setRawData(rawData);
		leftRuler.setAbsMax(absMax);
		bottomRuler.setBasicInfo(rawData.size(), rawStepSize);
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
		Container workspace = new Container();
		workspace.setcType(ContainerType.WORKSPACE);
		container = new Container();
		container.setcType(ContainerType.FIDCONTAINER);
		container.setParent(workspace);
		Rectangle conBounds = getContainerBounds();
		container.setLayout(conBounds);
		fidData.setParent(container);
		int span = LayoutUtils.EIGHT;
		int rulerLabL = Ruler.AXISLL;
		int temp = span * 2 + rulerLabL;
		int fdWeight = conBounds.width - temp;
		int fdHeight = conBounds.height - temp;

		fidData.setLayout(new Rectangle(span + rulerLabL, span, fdWeight,
				fdHeight));
		leftRuler = new VerticalRuler();
		leftRuler.setOrient(RulerOrient.LEFT);
		leftRuler.setParent(container);
		leftRuler.setLayout(new Rectangle(span, span, rulerLabL - 2, fdHeight));
		bottomRuler = new HorizontalRuler();
		bottomRuler.setOrient(RulerOrient.BOTTOM);
		bottomRuler.setParent(container);
		bottomRuler.setLayout(new Rectangle(span + rulerLabL, conBounds.height
				- span - rulerLabL + 2, fdWeight, rulerLabL - 2));
		return workspace;
	}

	/**
	 * 根据需要container的长宽比例获取需要container的bounds
	 * 
	 * @return
	 */
	private Rectangle getContainerBounds() {
		if (clientArea == null) {
			return null;
		}
		int cHeight = clientArea.height - 20;
		int cWeight = clientArea.width - 12;
		float cWHRatio = container.getWHRatio();
		if (cHeight * cWHRatio > cWeight) {
			cHeight = (int) (cWeight / cWHRatio);
		} else {
			cWeight = (int) (cHeight * cWHRatio);
		}
		int hSpan = (clientArea.width - cWeight) / 2;
		int vSpan = (clientArea.height - cHeight) / 2;
		int cX = hSpan + clientArea.x;
		int cY = vSpan + clientArea.y;
		return new Rectangle(cX, cY, cWeight, cHeight);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {

	}

	@Override
	public void setFocus() {
		super.setFocus();
	}
}
