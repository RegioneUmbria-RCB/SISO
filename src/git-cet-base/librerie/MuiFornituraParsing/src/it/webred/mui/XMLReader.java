package it.webred.mui;

import it.webred.mui.input.MuiFornituraParser;
import it.webred.mui.inputxml.MuiFornituraXML2TXTTransformer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;


public class XMLReader {

	public static void read(File fileXML, HashMap<String, String> appProperties) throws Exception {
		
		AppProperties.setProperties(appProperties);
		
		final File fileTXT = new File(fileXML.getAbsolutePath() + ".txt");
		if (!fileTXT.exists()) {
			fileTXT.createNewFile();
		}
		String xslPath = appProperties.get(AppProperties.XSL_PATH);
		MuiFornituraXML2TXTTransformer test = new MuiFornituraXML2TXTTransformer(xslPath);
		int counters[] = test.transform(fileXML.getAbsolutePath(), new FileOutputStream(fileTXT));
		System.out.println("file xml parziale "+counters[0]+"/"+counters[1]);
		
		MuiFornituraParser parser = new MuiFornituraParser();
		
		parser.setInput(new FileInputStream(fileTXT));
		parser.setFileAbsolutePath(fileXML.getAbsolutePath());
		
		parser.setNFileCount(counters[0]);
		parser.setNFileTot(counters[1]);
		parser.parse(true);
		parser.parseLastLine(parser.getLastLine(fileTXT));
		parser.parseNotes();
		
		parser.getInput().close();
		System.gc();
		System.out.print("file txt eliminato: " + fileTXT.delete());

	}
}
