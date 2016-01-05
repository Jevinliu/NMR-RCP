package edu.xmu.dataanalysis.importpage.tree;

import java.io.File;
import java.util.ArrayList;

public class TreeOperator {

	/**
	 * 找到当前节点的父路径
	 * 
	 * @param obj
	 *            初始节点
	 * @param path
	 *            当前父路径
	 */
	public static void getParentPath(Object obj, StringBuffer path) {
		if (obj instanceof TreeObject) {
			String name = ((TreeObject) obj).getName();
			if (path.length() == 0) {
				path.insert(0, name);
			} else {
				path.insert(0, File.separator).insert(0, name);
			}
			String parentName = ((TreeObject) obj).getParent().getName();
			if (parentName != "") {
				getParentPath(((TreeObject) obj).getParent(), path);
			}
		}
	}

	/**
	 * 根据选中的树节点，与getParentPath方法结合找到要操作的文件，并将文件压入栈中
	 * 
	 * @param obj
	 *            起始树节点
	 * @param filePath
	 *            起始文件路径
	 * @param fileStack
	 *            文件栈
	 */
	private static void pushFilesToStack(Object obj, String filePath,
			ArrayList<File> fileStack) {
		if (obj instanceof TreeObject) {
			String name = ((TreeObject) obj).getName();
			if (obj instanceof TreeParent) {
				if (name != "") {
					String path;
					if (filePath.length() == 0) {
						path = filePath + name;
					} else {
						path = filePath + File.separator + name;
					}
					File file = new File(path);
					fileStack.add(file);
					if (((TreeParent) obj).hasChildren()) {
						for (TreeObject treeObject : ((TreeParent) obj)
								.getChildren()) {
							pushFilesToStack(treeObject, path, fileStack);
						}
					}
				}
			} else {
				String path = filePath + File.separator + name;
				fileStack.add(new File(path));
			}
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param object
	 *            当前选中树节点
	 * @param path
	 *            当前节点的文件夹或文件路径
	 */
	public static void deleteNotNeedFiles(TreeObject object, String path) {
		ArrayList<File> fileStack = new ArrayList<File>();
		pushFilesToStack(object, path, fileStack);
		for (int i = fileStack.size() - 1; i >= 0; i--) {
			File file = fileStack.get(i);
			if (file.exists()) {
				file.delete();
			}
		}
	}
}
