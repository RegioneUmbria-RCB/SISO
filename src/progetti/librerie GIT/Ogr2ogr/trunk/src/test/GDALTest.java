package test;

import it.webred.gdal.main.ogr2ogr;

public class GDALTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		(new GDALTest()).execute();
	}
	
	private void execute() {
		
		String[] args = {"/TEMP/DXF/servizio_sit/out_java/edifici2POLIGONI.shp",
						 "/TEMP/DXF/servizio_sit/edifici2POLIGONI.dxf"};
		
		ogr2ogr.main(args);
	}

}
