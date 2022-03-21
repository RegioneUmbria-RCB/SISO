package it.webred.utils;

import java.io.*;
import java.sql.*;

public class RsUtils {

	public static final String TXT_DEF_SEPARATOR = ";";
	public static final String XLS_DEF_SEPARATOR = "\t";
	public static final String TXT = "txt";
	public static final String XLS = "xls";
	
	public static byte[] getBytesFromRs(ResultSet rs, String separator) {
		byte[] retVal = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			if (rs == null) {
				return retVal;
			}
			int columnCount = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				String line = "";
				for (int i = 1; i <= columnCount; i++) {
					String str = rs.getObject(i) == null ? "" : rs.getObject(i).toString();
					line += str;
					if (i < columnCount) {
						line += separator;
					}
				}
				baos.write(line.getBytes());
				baos.write("\n".getBytes());
			}
			baos.close();
			/*byte[] bytes = baos.toByteArray();
			retVal = new Byte[bytes.length];
			for (int i = 0; i < bytes.length; i++) {
				retVal[i] = new Byte(bytes[i]);
			}*/
			return  baos.toByteArray();
		}catch (SQLException sqle) {
			sqle.printStackTrace();
		}catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return retVal;
	}
	
	public static void txtOrSimpleXlsExport(ResultSet rs, String path, String filename, String mode) {
		try {
			FileOutputStream fos = new FileOutputStream(path + filename + mode);
			PrintStream ps = new PrintStream(fos);
			if (rs == null) {
				ps.close();
				return;
			}
			int rowCount = 0;
			int columnCount = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				rowCount++;
				if (rowCount > 65536)
					break;
				String line = "";
				for (int i = 1; i <= columnCount; i++) {
					String str = rs.getObject(i) == null ? "" : rs.getObject(i).toString();
					line += str;
					if (i < columnCount) {
						if (mode.equals(TXT)) {
							line += TXT_DEF_SEPARATOR;
						}else if (mode.equals(XLS)) {
							line += XLS_DEF_SEPARATOR;
						}
					}
				}
				ps.println(line);
			}
			if (mode.equals(XLS) && columnCount > 256)
				System.out.println("Impossibile trascrivere con questo metodo resultset con più di 256 righe");
			if (mode.equals(XLS) && rowCount > 65536)
				System.out.println("Impossibile trascrivere con questo metodo resultset con più di 65536 righe");
			ps.close();
		}catch (SQLException sqle) {
			sqle.printStackTrace();
		}catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
	}


}

