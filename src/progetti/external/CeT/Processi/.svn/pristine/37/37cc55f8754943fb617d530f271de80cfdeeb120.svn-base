package it.webred.rulengine.brick.loadDwh.utils.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import it.webred.rulengine.brick.loadDwh.utils.XLSToTXTConverter;
import it.webred.rulengine.brick.loadDwh.utils.bean.TxtFileField;

public class XLSToTXTAnaVieConverter extends XLSToTXTConverter {
	 
	{
		tracciato = new ArrayList<TxtFileField>();
		tracciato.add(new TxtFileField(1, "PKID", 8, '0', true));
		tracciato.add(new TxtFileField(3, "Specie", 15, ' ', false));
		tracciato.add(new TxtFileField(4, "Descrizione", 50, ' ', false));
	}
	
	public static void main(String[] args) {
		try {
			new XLSToTXTAnaVieConverter().save("C:\\Users\\Stage_02\\Desktop\\anagrafe_nuova\\estrattore_xls_txt\\STRADARIO_COMUNALE_E256.xls",
					null,
					"E256");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected String getSpecFilePathTo(String filePathFrom, String filePathTo, String belfiore) {
		if (filePathTo == null || filePathTo.length() == 0) {
			filePathTo = filePathFrom.toLowerCase().replace(".xls", ".txt");
		}
		String nomeFile = "ANA_VIE_" + belfiore + "_" + new SimpleDateFormat("yyyyMMddHHmm").format(new Date()) + ".txt";
		filePathTo = filePathTo.
					replace("/", File.separator).
					replace("\\", File.separator).
					substring(0, filePathTo.lastIndexOf(File.separator) + 1) +
					nomeFile;
		return filePathTo;
	}
	
}
