package it.webred.rulengine.brick.loadDwh.utils;

import it.webred.rulengine.brick.loadDwh.load.tributi.utils.TributiFileConverter;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		//FileConverter conv1 = new XLSToCSVConverter();
		//FileConverter conv2 = new DBFToCSVConverter();
		FileConverter conv3 = new TributiFileConverter();
		try {
			//conv1.save("C:\\dati_monza\\CONCESSIONI\\TEST\\Concess.xls", "C:\\dati_monza\\CONCESSIONI\\TEST\\concessxls.csv");
			//conv2.save("C:\\dati_monza\\CONCESSIONI\\TEST\\Concess.dbf", "C:\\dati_monza\\CONCESSIONI\\TEST\\concessdbf.csv");
			//conv3.save("C:\\Dati_Diogene\\tributi\\", "C:\\Dati_Diogene\\tributi\\");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
