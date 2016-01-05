package edu.xmu.nmr.dataAnalysis.dataLogic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Utils {

	public Utils() {
		// TODO Auto-generated constructor stub
	}

	public static void closeStream(Object... iss) {
		for (Object is : iss) {
			try {
				if (is instanceof InputStream) {
					((InputStream) is).close();
				}
				if (is instanceof OutputStream) {
					((OutputStream) is).close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
