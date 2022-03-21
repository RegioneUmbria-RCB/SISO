package it.webred.mui;

import it.webred.utils.FileUtils;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;

import org.hibernate.cfg.Configuration;


public class Entrypoint {

	public static void main(String[] args) {
		
		URL url = Thread.currentThread().getContextClassLoader().getResource("hibernate.cfg.xml"); 
		Configuration conf = new Configuration().configure(url);
		System.out.println(conf);
		if (true) return;
		
		DecimalFormat DF = new DecimalFormat();
		DF.setGroupingUsed(false);
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator(',');		
		DF.setDecimalFormatSymbols(dfs);
		try {
			System.out.println(new BigDecimal(DF.parse("12,123456").doubleValue()).scale());
			//System.out.println(DF.parse("12,123456").doubleValue());
			System.out.println(new BigDecimal("" + DF.parse("12,123456").doubleValue()).scale());
			System.out.println(new BigDecimal(DF.parse("12,123456").doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP).scale());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (true) return;
		
		try {
			String percorsoFiles = "C:\\Dati_Diogene\\MUI_TEST\\maggio_08\\";
			HashMap<String, String> appProperties = new HashMap<String, String>();
			appProperties.put("belfiore", "F205");
			appProperties.put("driverClass", "oracle.jdbc.driver.OracleDriver");
			appProperties.put("connString", "jdbc:oracle:thin:@praga:1521:praga");
			appProperties.put("userName", "VIRGILIO");
			appProperties.put("password", "VIRGILIO");
			appProperties.put("xslPath", Entrypoint.class.getResource("xsl/MUI-TEST.xsl").getPath());

			// unzip dei file
			String[] fs = it.webred.utils.FileUtils.cercaFileDaElaborare(percorsoFiles);
			for (int i = 0; i < fs.length; i++) {
				File f = new File(percorsoFiles + fs[i]);
				if (FileUtils.isZip(f))  {
					FileUtils.unzip(f);
					boolean del = false;
					while (!del) {
						del = f.delete();
					}
				}
			}
			//lettura dei file
			fs = it.webred.utils.FileUtils.cercaFileDaElaborare(percorsoFiles);
			for (String f : fs) {
				XMLReader.read(new File(percorsoFiles + f), appProperties);
			}
		} catch(Exception e) {
        	e.printStackTrace();
        }
		
	}

}
