package it.webred.AMProfiler.services;

import it.webred.AMProfiler.beans.AmApplication;
import it.webred.AMProfiler.servlet.BaseAction;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;

import javax.xml.namespace.QName;
import javax.xml.rpc.soap.SOAPFaultException;
import javax.xml.soap.Detail;
import javax.xml.soap.DetailEntry;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class Services {

	private static final Logger log = Logger.getLogger(Services.class.getName());

	/**
	 * Restituisce una lista degli url applicativi presenti in piattaforma per
	 * l'ente passato La HashMap ha come chiave il TIPO_APP di AmApplication
	 * 
	 * @param ente
	 * @return Il metodo ritorna un SOAPFaultException in caso di errore
	 */
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public HashMap<String, String> getListaUrl(String ente) throws SOAPFaultException {
		HashMap<String, String> lista = new HashMap<String, String>();

		ArrayList<AmApplication> apps = null;
		try {
			apps = BaseAction.listaApplication();
			lista = new HashMap<String, String>();

			ListIterator<AmApplication> li = apps.listIterator();
			while (li.hasNext()) {
				AmApplication ama = li.next();
				if (ama.getComune() != null) {
					if (ama.getComune().getBelfiore().equals(ente)) {
						lista.put(ama.getTipo_app(), ama.getUrl());
					}
				}
			}

		}
		catch (Exception e) {
			throw makeSOAPFaultException(e, "getListaUrl");
		}

		return lista;

	}

	/**
	 * This method will convert an exception to a SOAPFaultException<br>
	 * JAX-RPC RI will build a proper SOAP Fault from the SOAPFaultException
	 * 
	 * @param e
	 *            Exception
	 * @param log
	 *            String
	 * @return SOAPFaultException
	 */
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public static SOAPFaultException makeSOAPFaultException(Exception e, String webServiceName) {
		QName faultCode;
		String faultString;
		String faultActor;
		Detail faultDetail = null;

		faultCode = new QName("http://AMProfiler", e.getClass().getName());
		faultString = e.getMessage();
		faultActor = webServiceName;

		try {
			MessageFactory mf = MessageFactory.newInstance();
			SOAPMessage msg = mf.createMessage();
			SOAPEnvelope env = msg.getSOAPPart().getEnvelope();

			faultDetail = msg.getSOAPBody().addFault().addDetail();

			Name faultDetailName = env.createName("StackTrace");
			DetailEntry dEntry = faultDetail.addDetailEntry(faultDetailName);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			pw.flush();
			sw.flush();
			pw.close();
			String StackTrace = sw.toString();
			if (StringUtils.isNotEmpty(StackTrace) ) {
				dEntry.addTextNode(sw.toString());
			}
		}
		catch (SOAPException se) {
			// could not attach FaultDetail, can't fix that
			log.warn("Non sono riuscito ad agganciare FaultDetail a SOAPFaultException", se);
		}

		return new SOAPFaultException(faultCode, faultString, faultActor, faultDetail);
	}

}
