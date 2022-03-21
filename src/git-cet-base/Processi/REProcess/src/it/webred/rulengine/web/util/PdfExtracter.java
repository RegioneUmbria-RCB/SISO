package it.webred.rulengine.web.util;

import org.apache.pdfbox.pdmodel.PDDocument;

import org.apache.pdfbox.pdfparser.PDFParser;
import java.io.*;
import org.apache.pdfbox.util.PDFTextStripper;

public class PdfExtracter {

	public PdfExtracter() {
		// TODO Auto-generated constructor stub
	}

	public static String getTextFromPdf(String filename) throws Exception
	{

         PDDocument pdfdocument = null;
         FileInputStream is = new FileInputStream(filename);
         PDFParser parser = new PDFParser( is );
         parser.parse();
         pdfdocument = parser.getPDDocument();
         ByteArrayOutputStream out = new ByteArrayOutputStream();
         OutputStreamWriter writer = new OutputStreamWriter( out );
         PDFTextStripper stripper = new PDFTextStripper();
         //stripper.writeText(pdfdocument.getDocument(), writer);
         String ts = stripper.getText(pdfdocument);
         writer.close();
         //byte[] contents = out.toByteArray();

         //String ts = new String(contents);
         //System.out.println("the string length is"+contents.length+"\n");
         //System.out.println(ts);
         pdfdocument.close();
         return ts;
	}
	
}
