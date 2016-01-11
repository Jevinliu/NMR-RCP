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
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import edu.xmu.nmr.dataanalysis.diagram.actions.DAZoomInAction;
import edu.xmu.nmr.dataanalysis.diagram.actions.DAZoomManager;
import edu.xmu.nmr.dataanalysis.diagram.actions.DAZoomOutAction;
import edu.xmu.nmr.dataanalysis.diagram.editparts.DAEditPartFactory;
import edu.xmu.nmr.dataanalysis.diagram.figures.PointsTools;
import edu.xmu.nmr.dataanalysis.diagram.handlers.DAMouseWheelZoomHandler;
import edu.xmu.nmr.dataanalysis.diagram.layouts.LayoutUtils;
import edu.xmu.nmrdataanalysis.diagram.model.Container;
import edu.xmu.nmrdataanalysis.diagram.model.ContainerType;
import edu.xmu.nmrdataanalysis.diagram.model.FidData;
import edu.xmu.nmrdataanalysis.diagram.model.HorizontalRuler;
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
	private FidData fidData; // 模型节点
	private VerticalRuler leftRuler;
	private HorizontalRuler bottomRuler;
	private ZoomManager zoomManager;
	private DAZoomManager daZoomMgr;

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
		fidData = new FidData();
		daZoomMgr = new DAZoomManager(fidData);
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
				FigureCanvas fc = (FigureCanvas) e.getSource();
				double scale;
				if (fc.getBounds().width > fc.getBounds().height) {
					scale = getFitXZoomLevel(1);
				} else {
					scale = getFitXZoomLevel(0);
				}
				zoomManager.setZoom(scale);
				Viewport vp = zoomManager.getViewport(); // 获取可视化区域
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
		viewer.setEditPartFactory(new DAEditPartFactory()); // 添加editpart工厂，通过工厂创建editpart
		viewer.setProperty(DAZoomManager.class.toString(), daZoomMgr); // 注册DAZoomManager
		setupDAZoomAction();
	}

	/**
	 * 设置DAZoomAction
	 */
	private void setupDAZoomAction() {
		getActionRegistry().registerAction(new DAZoomInAction(daZoomMgr));
		getActionRegistry().registerAction(new DAZoomOutAction(daZoomMgr));
		getGraphicalViewer().setProperty(
				MouseWheelHandler.KeyGenerator.getKey(SWT.NONE),
				DAMouseWheelZoomHandler.SINGLETON);
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
	 *            0 代表{@link ZoomManager#FIT_WIDTH} ，即横向缩放；1 代表
	 *            {@link ZoomManager#FIT_HEIGHT}，即 纵向缩放；2 代表
	 *            {@link ZoomManager#FIT_ALL}
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
		Rectangle r = LayoutUtils.getClientArea();
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
		Container fidContainer = new Container();
		fidContainer.setcType(ContainerType.FIDCONTAINER);
		fidContainer.setParent(workspace);
		int backSpan = LayoutUtils.TEN;
		Rectangle conBounds = new Rectangle(backBounds.x + backSpan,
				backBounds.y + backSpan, backBounds.width - 2 * backSpan,
				backBounds.height - 2 * backSpan);
		fidContainer.setLayout(conBounds);
		leftRuler = new VerticalRuler();
		leftRuler.setOrient(RulerOrient.LEFT);
		leftRuler.setParent(fidContainer);
		fidData.setParent(fidContainer);
		fidData.setVInterval(leftRuler.getInterval());
		Container placeholderContainer = new Container();
		placeholderContainer.setcType(ContainerType.BACKGROUND);
		placeholderContainer.setParent(fidContainer);
		bottomRuler = new HorizontalRuler();
		bottomRuler.setOrient(RulerOrient.BOTTOM);
		bottomRuler.setParent(fidContainer);
		fidData.setHInterval(bottomRuler.getInterval());
		return workspace;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {

	}
}
