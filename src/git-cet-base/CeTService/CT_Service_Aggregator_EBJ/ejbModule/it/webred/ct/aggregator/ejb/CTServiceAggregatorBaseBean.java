package it.webred.ct.aggregator.ejb;

import it.webred.ct.data.access.basic.catasto.CatastoServiceException;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class CTServiceAggregatorBaseBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected static Logger logger = Logger.getLogger("ctservice.log");
	
	protected SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    protected DecimalFormat df2 = new DecimalFormat("##.00");
	
	protected Object xml2Jaxb(String xml,String jaxbObjectPkgReference) throws Exception {
		Object o = null;
		
		try {
			logger.info("Trasformazione xml in oggetto Jaxb ["+jaxbObjectPkgReference+"]");
			javax.xml.bind.JAXBContext jc = javax.xml.bind.JAXBContext.newInstance(jaxbObjectPkgReference);
			
			javax.xml.bind.Unmarshaller u = jc.createUnmarshaller();
			o = u.unmarshal(new ByteArrayInputStream(xml.getBytes()));
			logger.info("Trasformazione eseguita con successo ["+o.getClass().getName()+"]");
			
		}catch(Exception e) {
			logger.error("Eccezione parsing xml: "+e.getMessage());
			throw e;
		}
		
		return o;
	}
	
	
	protected String jaxb2Xml(Object o) throws Exception {
		String xml = null;
		
		try {
			logger.info("Trasformazione oggetto Jaxb in xml ["+o.getClass().getPackage().getName()+"]");
			javax.xml.bind.JAXBContext jc = javax.xml.bind.JAXBContext.newInstance(o.getClass().getPackage().getName());
			
			javax.xml.bind.Marshaller m = jc.createMarshaller();
			m.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
			m.marshal(o, baos);
			xml = new String(baos.toByteArray());
			logger.info("Trasformazione eseguita con successo");
			logger.debug(xml);
			
		}catch(Exception e) {
			logger.error("Eccezione parsing jaxb object: "+e.getMessage());
			throw e;
		}
		
		return xml;
	}
	
	protected String getNullString(Object o){
		String s = "";
		
		try{
			
			s= o!=null ? o.toString() : "";
		
		}catch(Exception e){
			logger.error("Eccezione in getNullString:", e);
			throw new CatastoServiceException(e);	
		}
	
		return s;
	}
	
	protected Date getDateFromString(String d){
		Date date = null;	
		try{

			if(d!=null && !"".equalsIgnoreCase(d))
				date = sdf.parse(d);
		}catch(Exception e){
			throw new AggregatorException("Data ["+d+"] non valida.");
		}
		return date;
			
	}
	
}
