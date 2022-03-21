package it.webred.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;


public class JDomModifyXPath
{

	protected ByteArrayOutputStream Element(InputStream XmlInputStrem, String strXPathExpression, String strTagValue)
			throws Exception
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		// create a XML parser and read the XML file
		SAXBuilder oBuilder = new SAXBuilder(false);
		
		oBuilder.setEntityResolver(new NoOpEntityResolver());

		
		Document oDoc = oBuilder.build(XmlInputStrem);

		XPath oPath = XPath.newInstance(strXPathExpression);
		


		
		List oResult = oPath.selectNodes(oDoc);
		Iterator iter = oResult.iterator();

		if (!iter.hasNext())
			System.out.println("WARNING Xpath " + strXPathExpression + " not found");

		while (iter.hasNext())
		{
			Element oNode = (Element) iter.next();
			System.out.println("Replacing Tag Text: " + oNode.getText() + " with " + strTagValue);
			oNode.setText(strTagValue);
		}

		// write the document to output stream
		XMLOutputter outer = new XMLOutputter();
		outer.output(oDoc, out);

		return out;
	}

	protected ByteArrayOutputStream Attribute(InputStream XmlInputStrem, String strXPathAttrExpression, String strAttrValue)
			throws Exception
	{

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		int indexofatt = strXPathAttrExpression.indexOf("/@");
		if (indexofatt == -1)
		{
			System.out.println("ERROR: no attribute found");
			return out;
		}

		String strXPathExpression = strXPathAttrExpression.subSequence(0, indexofatt).toString();
		String strAttrExpression = strXPathAttrExpression.subSequence(indexofatt + 2, strXPathAttrExpression.length()).toString();

		// create a XML parser and read the XML file
		SAXBuilder oBuilder = new SAXBuilder(false);
		oBuilder.setEntityResolver(new NoOpEntityResolver());

		
		oBuilder.setEntityResolver(new NoOpEntityResolver());
		Document oDoc = oBuilder.build(XmlInputStrem);

		
		
		XPath oPath = XPath.newInstance(strXPathExpression);
		

      
        
		List oResult = oPath.selectNodes(oDoc);
		Iterator iter = oResult.iterator();

		if (!iter.hasNext())
			System.out.println("WARNING Xpath " + strXPathExpression + " not found");

		while (iter.hasNext())
		{
			Element oNode = (Element) iter.next();
			System.out.println("Replacing Attribute: " + oNode.getAttributeValue(strAttrExpression) + " with " + strAttrValue);
			oNode.setAttribute(strAttrExpression, strAttrValue);
		}

		// write the document to output stream
		XMLOutputter outer = new XMLOutputter();
		outer.output(oDoc, out);

		return out;
	}

	public static void main(String[] args)
			throws Exception
	{
		/*
		  String a =
		  "c:\\faces-config.xml";
		  args = new String[]{a,
		  a + "2", "tag",
		  "faces-config/managed-bean[managed-bean-name/text()='ricercaDettaglioBck']/managed-property[property-name/text()='pathPlanimetrie']/value", "ciao"};
		*/
		
		if (args.length < 5)
		{
			System.out.println("Usage: JDomModifyXPath <xmlInput> <xmlOutput> TAG|ATT <Xpath tag> <value> .... TAG|ATT  <XpathAtt> <value> ....");
			System.exit(1);
		}
		String strXmlInputFile = args[0];
		String strXmlOutputFile = args[1];
		String strXPathExpression;
		String strValue;
		String strType;
		String strTmp;
		int i;
		//FileInputStream XmlInputStram = new FileInputStream(strXmlInputFile);

		JDomModifyXPath XpathModify = new JDomModifyXPath();

		strType = args[2];

		if ((!strType.equalsIgnoreCase("tag")) && (!strType.equalsIgnoreCase("att")))
		{
			System.out.println("Invalid parametrers, usage: JDomReadXPath <xmlInput> <xmlOutput> TAG|ATT <Xpath tag> <value> .... TAG|ATT  <XpathAtt> <value> ....");
			System.exit(1);
		}

		ByteArrayOutputStream XmlOutput;

		for (i = 3; i < args.length - 1;)
		{

			FileInputStream XmlInputFile = new FileInputStream(strXmlInputFile);

			strTmp = args[i];

			if ((strTmp.equalsIgnoreCase("tag")) || (strTmp.equalsIgnoreCase("att")))
			{
				// get new type
				strType = strTmp;
				i++;
				continue;
			}

			// get XPATH and new value
			strXPathExpression = args[i];
			strValue = args[i + 1];

			if (strType.equalsIgnoreCase("tag"))
			{
				System.out.println("TAG modification: XPATH " + strXPathExpression + " VALUE " + strValue);
				XmlOutput = XpathModify.Element(XmlInputFile, strXPathExpression, strValue);
			}
			else
			{
				System.out.println("ATT modification: XPATH " + strXPathExpression + " VALUE " + strValue);
				XmlOutput = XpathModify.Attribute(XmlInputFile, strXPathExpression, strValue);
			}

			// write ByteArray to FileOutputStream
			FileOutputStream XmlOutputFile = new FileOutputStream(strXmlOutputFile);
			XmlOutputFile.write(XmlOutput.toByteArray(), 0, XmlOutput.size());
			XmlOutputFile.close();

			strXmlInputFile = strXmlOutputFile;
			i += 2;
		}

		if (i < args.length)
			System.out.println("Missing <value> for last XPATH " + args[i]);

	}

	private class NoOpEntityResolver implements EntityResolver
	{
		public InputSource resolveEntity(String publicId, String systemId)
		{
			return new InputSource(new StringReader(""));
		}
	}
}
