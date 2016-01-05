package edu.xmu.nmr.dataanalysis.importvarian.logic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Utiles {

	public Utiles() {
		// TODO Auto-generated constructor stub
	}

	public static void closeStream(Object... iss) {
		for (Object is : iss) {
			try {
				if (is instanceof InputStream && is != null) {
					((InputStream) is).close();
				}
				if (is instanceof OutputStream && is != null) {
					((OutputStream) is).close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
