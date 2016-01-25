package edu.xmu.nmr.dataAnalysis.menuHandlers;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

import edu.xmu.dataanalysis.importpage.wizard.DAImportWizard;
import edu.xmu.nmr.dataAnalysis.dataLogic.BrukerAcqusReader;
import edu.xmu.nmr.dataAnalysis.dataLogic.BrukerFidReader;
import edu.xmu.nmr.dataanalysis.logic.datacenter.FileType;
import edu.xmu.nmr.dataanalysis.logic.explorermodel.AbstractDataNode;
import edu.xmu.nmr.dataanalysis.logic.explorermodel.FidNode;
import edu.xmu.nmr.dataanalysis.logic.explorermodel.ParamtersNode;
import edu.xmu.nmr.dataanalysis.logic.explorermodel.VersionNode;

public class ImportBrukerWizard extends DAImportWizard {
    
    private Logger log = Logger.getLogger(this.getClass());
    private ImportBrukerPage importPage;
    
    @Override public void addPages() {
        addPage(importPage);
    }
    
    @Override public void init(IWorkbench workbench,
            IStructuredSelection selection) {
        setWindowTitle("Import Bruker");
        setNeedsProgressMonitor(true);
        importPage = new ImportBrukerPage(selection,
                "Select your bruker data's directory.");
    }
    
    @Override protected void initSomeParams() {
        selectedDirectory = importPage.getSelectedDirectory();
        leftToRight = importPage.getLeftToRight();
        leftRoot = importPage.getLeftRoot();
        fileType = FileType.BRUKER;
    }
    
    @Override protected void parserFile(String fidFilePath,
            String fileProcsPath, String version, VersionNode versionNode) {
            
        File acqus = new File(fidFilePath + File.separator + "acqus");
        File fid = new File(fidFilePath + File.separator + "fid");
        File procs = new File(fileProcsPath + File.separator + "procs");
        // 判断文件是否存在
        if (!acqus.exists() || !procs.exists()) {
            log.error(fidFilePath + "'s acqus or procs is not exists");
            return;
        }
        if (!fid.exists()) {
            fid = new File(fidFilePath + File.separator + "ser");
        } else if (!fid.exists()) {
            log.error(fidFilePath + "'s acqus is not exists");
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
        // 解析acqus参数文件
        BrukerAcqusReader acqusReader = new BrukerAcqusReader(acqus);
        acqusReader.parser();
        pn.setParams(acqusReader.getParams());
        int bytOrder = acqusReader.getInt("bytorda");
        
        // 解析fid参数文件
        try {
            BrukerFidReader bfd = new BrukerFidReader(fid, bytOrder);
            bfd.parser();
            fn.putData("0", bfd.getFidData());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        // 解析procs文件
        BrukerAcqusReader procsReader = new BrukerAcqusReader(procs);
        procsReader.parser();
        for (String key : procsReader.getParams().keySet()) {
            pn.putParam(false, key, procsReader.getParams().get(key));
        }
        
        // 读取title文件
        // File title = new File(fileProcsPath + File.separator + "title");
        // if (!title.exists()) {
        // log.error(fileProcsPath + "'s title is not exists");
        // } else {
        // try {
        // FileInputStream fis = new FileInputStream(title);
        // bdm.setTitle(fis);
        // } catch (FileNotFoundException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // }
    }
}
