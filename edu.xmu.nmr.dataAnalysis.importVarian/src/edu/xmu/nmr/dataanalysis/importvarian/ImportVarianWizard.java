package edu.xmu.nmr.dataanalysis.importvarian;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

import edu.xmu.dataanalysis.importpage.tree.TreeObject;
import edu.xmu.dataanalysis.importpage.tree.TreeParent;
import edu.xmu.dataanalysis.importpage.wizard.DAImportWizard;
import edu.xmu.nmr.dataanalysis.importvarian.logic.VarianFidReader;
import edu.xmu.nmr.dataanalysis.importvarian.logic.VarianParamsReader;
import edu.xmu.nmr.dataanalysis.logic.datacenter.FileType;
import edu.xmu.nmr.dataanalysis.logic.explorermodel.AbstractDataNode;
import edu.xmu.nmr.dataanalysis.logic.explorermodel.FidNode;
import edu.xmu.nmr.dataanalysis.logic.explorermodel.ParamtersNode;
import edu.xmu.nmr.dataanalysis.logic.explorermodel.VersionNode;

public class ImportVarianWizard extends DAImportWizard {
    
    private ImportVarianPage importPage;
    private Logger log = Logger.getLogger(this.getClass());
    
    @Override public void addPages() {
        addPage(importPage);
    }
    
    @Override public void init(IWorkbench workbench,
            IStructuredSelection selection) {
        setWindowTitle("Import Varian");
        setNeedsProgressMonitor(true);
        importPage = new ImportVarianPage(selection,
                "Select your varian data's directory.");
    }
    
    @Override protected void initSomeParams() {
        selectedDirectory = importPage.getSelectedDirectory();
        leftToRight = importPage.getLeftToRight();
        leftRoot = importPage.getLeftRoot();
        fileType = FileType.VARIAN;
    }
    
    @Override protected void findKeySet(String directoryPath,
            Map<String, TreeParent> leftToRight, AbstractDataNode parent) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        if (leftToRight.containsKey(directoryPath)) {
            TreeParent rightRoot = leftToRight.get(directoryPath);
            for (TreeObject rightObj : rightRoot.getChildren()) {
                if (rightObj.isChecked()
                        && judgeFileIsNeed(rightObj.getName())) {
                    if (rightObj.getName().equalsIgnoreCase("procpar")) {
                        File file = new File(directoryPath + File.separator
                                + rightObj.getName());
                        long millis = file.lastModified();
                        cal.setTimeInMillis(millis);
                        String version = "version "
                                + formatter.format(cal.getTime());
                        VersionNode etp = new VersionNode(
                                parent.getNodePath() + File.separator + version,
                                FileType.VARIAN, version);
                        parent.addChild(etp);
                        FidNode fid = new FidNode(
                                etp.getNodePath() + File.separator + "fid",
                                FileType.BRUKER, "fid");
                        ParamtersNode params = new ParamtersNode(
                                etp.getNodePath() + File.separator
                                        + "paramters",
                                FileType.BRUKER);
                        etp.addChild(fid);
                        etp.addChild(params);
                        this.parserFile(directoryPath, version, etp);
                        
                    }
                }
            }
        }
    }
    
    private void parserFile(String directoryPath, String version,
            VersionNode versionNode) {
            
        File fid = new File(directoryPath + File.separator + "fid");
        File procpar = new File(directoryPath + File.separator + "procpar");
        if (!fid.exists() || !procpar.exists()) {
            log.error(directoryPath + "'s fid or procpar is not exists.");
            return;
        }
        
        FidNode fn = null;
        ParamtersNode pn = null;
        for (AbstractDataNode ab : versionNode.getChildren()) {
            if (ab instanceof FidNode) {
                fn = (FidNode) ab;
            } else if (ab instanceof ParamtersNode) {
                pn = (ParamtersNode) ab;
            }
        }
        
        VarianFidReader fidReader = new VarianFidReader(fid);
        fidReader.parser();
        fn.setIsComplex(fidReader.isComplex());
        for (int i = 0; i < fidReader.getTraces().size(); i++) {
            fn.putData(String.valueOf(i), fidReader.getTraces().get(i));
        }
        
        VarianParamsReader pReader = new VarianParamsReader(procpar);
        pReader.parser();
        pn.setParams(pReader.getParams());
    }
    
    @Override protected boolean isCanAddExplorerNode(TreeObject object) {
        TreeParent parent = object.getParent();
        for (TreeObject obj : parent.getChildren()) {
            if (canConvertToDotNmr(obj)) {
                return true;
            }
        }
        return false;
    }
    
    @Override protected boolean canConvertToDotNmr(TreeObject object) {
        if (object.getName().endsWith(".fid")) {
            return true;
        }
        return false;
    }
    
    // @Override
    // protected boolean canAddExplorerNode(TreeObject object) {
    // if (object instanceof TreeParent) {
    // if (canConvertToDotNmr(object))
    // // {
    // return true;
    // // }
    // // }
    // }
    // return false;
    // }
    
    @Override protected boolean judgeFileIsNeed(String fileName) {
        if (fileName.equals("fid") || fileName.equals("procpar")) {
            return true;
        }
        return false;
    }
}