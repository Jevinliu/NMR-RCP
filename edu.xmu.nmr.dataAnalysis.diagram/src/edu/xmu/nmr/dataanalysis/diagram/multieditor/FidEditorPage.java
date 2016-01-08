package edu.xmu.nmr.dataanalysis.diagram.multieditor;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.FreeformFigure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
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
import edu.xmu.nmr.dataanalysis.diagram.figures.BackgroundFigure;
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
	// private GraphicalViewer viewer;
	private Container container;
	private ZoomManager zoomManager;

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
		GraphicalViewer viewer = getGraphicalViewer();
		viewer.getControl().setBackground(ColorConstants.gray);

		ScalableRootEditPart srep = (ScalableRootEditPart) viewer
				.getRootEditPart();
		srep.getContentPane().setLayoutManager(new XYLayout());
		zoomManager = srep.getZoomManager();
		zoomManager.setZoomLevels(new double[] { 0.01, 7 });
		viewer.getControl().addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				System.out.println("Resize");
				FigureCanvas fc = (FigureCanvas) e.getSource();
				double scale = getFitXZoomLevel(1);
				System.out.println("scale:" + scale);
				zoomManager.setZoom(getFitXZoomLevel(1));
				Viewport vp = zoomManager.getViewport();
				// 将滚动条放到中间
				int wsWidthHalf = LayoutUtils.WORKSPACE_CONSTRAINS.width / 2;
				int wsHeightHalf = LayoutUtils.WORKSPACE_CONSTRAINS.height / 2;
				fc.getViewport().setViewLocation(
						(int) (wsWidthHalf * zoomManager.getZoom() - vp
								.getSize().width / 2),
						(int) (wsHeightHalf * zoomManager.getZoom() - vp
								.getSize().height / 2));
			}
		});
		viewer.setEditPartFactory(new NMREditPartFactory()); // 添加editpart工厂，通过工厂创建editpart
	}

	@Override
	protected void createActions() {
		super.createActions();
	}

	/**
	 * 根据当前{@link ZoomManager}
	 * 中的zoom的缩放比例，获取下次如果缩放要缩放的比例值，该值可以应用在判断是否该缩放值满足最小缩放比例的条件
	 * 
	 * @param which
	 *            0 代表{@link ZoomManager.FIT_WIDTH} ，即横向缩放；1 代表
	 *            {@link ZoomManager.FIT_HEIGHT}，即 纵向缩放；2 代表
	 *            {@link ZoomManager.FIT_ALL}
	 * @return 即将缩放值
	 */
	private double getFitXZoomLevel(int which) {
		IFigure fig = zoomManager.getScalableFigure();
		Dimension avaliable = zoomManager.getViewport().getClientArea()
				.getSize();
		Dimension desired;
		if (fig instanceof FreeformFigure)
			desired = ((FreeformFigure) fig).getFreeformExtent().getCopy()
					.union(0, 0).getSize();
		else
			desired = fig.getPreferredSize().getCopy();
		desired.width -= fig.getInsets().getWidth();
		desired.width -= fig.getInsets().getHeight();
		while (fig != zoomManager.getViewport()) {
			avaliable.width -= fig.getInsets().getWidth();
			avaliable.height -= fig.getInsets().getHeight();
			fig = fig.getParent();
		}
		Rectangle r = LayoutUtils.getContainerBounds();
		double scaleX = Math.min(((double) avaliable.width) / r.width,
				zoomManager.getMaxZoom());
		double scaleY = Math.min(((double) avaliable.height) / r.height,
				zoomManager.getMaxZoom());
		if (which == 0)
			return scaleX;
		if (which == 1)
			return scaleY;
		return Math.min(scaleX, scaleY);
	}

	/**
	 * 设置model层的fid数据
	 * 
	 * @param rawData
	 *            原始数据，
	 * @param rawStepSize
	 *            原始数据点间的原始间隔，如采样时间
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
		GraphicalViewer viewer = getGraphicalViewer();
		viewer.setContents(createContainer()); // 设置model
	}

	/**
	 * 创建绘图所需要的model层
	 */
	private Container createContainer() {
		Container workspace = new Container();
		workspace.setcType(ContainerType.WORKSPACE);
		workspace.setLayout(LayoutUtils.WORKSPACE_CONSTRAINS);
		Rectangle backBounds = LayoutUtils.getContainerBounds();
		Container background = new Container();
		background.setcType(ContainerType.BACKGROUND);
		background.setParent(workspace);
		background.setLayout(backBounds);
		container = new Container();
		container.setcType(ContainerType.FIDCONTAINER);
		container.setParent(workspace);
		int backSpan = BackgroundFigure.SPAN;
		Rectangle conBounds = new Rectangle(
				(LayoutUtils.WORKSPACE_CONSTRAINS.width - backBounds.width) / 2
						+ backSpan,
				(LayoutUtils.WORKSPACE_CONSTRAINS.height - backBounds.height)
						/ 2 + backSpan, backBounds.width - 2 * backSpan,
				backBounds.height - 2 * backSpan);
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

	@Override
	public void doSave(IProgressMonitor monitor) {

	}
}
