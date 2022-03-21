package com.osmosit.siso.flussoinps.dao.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.osmosit.siso.flussoinps.dao.BaseDAO;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.ObjectFactory;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.PrestazioniBeneficiario;

public class PrestazioniBeneficiarioDAO extends BaseDAO{

	public PrestazioniBeneficiarioDAO(ObjectFactory objFactory) {
		super(objFactory);
		// TODO Auto-generated constructor stub
	}

	public PrestazioniBeneficiario createPrestazioneBeneficiario(
			String dataInizio, String dataFine,
			String periodErog, String importoMens, String quotaEnte,
			String quotaUtente, String quotaSSN, String quotaRichiesta,
			String sogliaISEE, String carattere, String numProtDSU,
			String annoProt, String dataDSU, String codice,
			String denominazione, String protEnte, String dataErog,
			String importo,
			String dataEvento //INIZIO SISO-538
			) {
		
		PrestazioniBeneficiario prest= objFactory.createPrestazioniBeneficiario();
		if(dataInizio!=null && !"".equals(dataInizio)){			
			prest.setDataInizio(dataInizio);
		}		
		if(dataFine!=null && !"".equals(dataFine)){
			prest.setDataFine(dataFine);
		}		
		if(periodErog!=null && !"".equals(periodErog)){
			prest.setPeriodoErogazione(periodErog);
		}		
		if(importoMens!=null && !"".equals(importoMens)){
			prest.setImportoMensile(importoMens);
		}		
		if(quotaEnte!=null && !"".equals(quotaEnte)){
			prest.setQuotaEnte(quotaEnte);
		}		
		if(quotaUtente!=null && !"".equals(quotaUtente)){
			prest.setQuotaUtente(quotaUtente);
		}		
		if(quotaSSN!=null && !"".equals(quotaSSN)){
			prest.setQuotaSSN(quotaSSN);
		}		
		if(quotaRichiesta!=null && !"".equals(quotaRichiesta)){
			prest.setQuotaRichiesta(quotaRichiesta);
		}		
		if(sogliaISEE!=null && !"".equals(sogliaISEE)){
			prest.setSogliaISEE(sogliaISEE);
		}			
		if(carattere!=null && !"".equals(carattere)){
			prest.setCarattere(carattere);
		}		
		if(numProtDSU!=null && !"".equals(numProtDSU)){
			prest.setNumeroProtocolloDSU(numProtDSU);
		}		
		if(annoProt!=null && !"".equals(annoProt)){
			prest.setAnnoProtocollo(annoProt);
		}		
		if(dataDSU!=null && !"".equals(dataDSU)){
			prest.setDataDSU(dataDSU);
		}		
		if(codice!=null && !"".equals(codice)){
			prest.setCodice(codice);
		}		
		if(denominazione!=null && !"".equals(denominazione)){
			prest.setDenominazione(denominazione);
		}		
		if(protEnte!=null && !"".equals(protEnte)){
			prest.setProtocolloEnte(protEnte);
		}		
		if(dataErog!=null && !"".equals(dataErog)){
			prest.setDataErogazione(dataErog);	
		}		
		if(importo!=null && !"".equals(importo)){
			prest.setImporto(importo);
		}		
		//INIZIO SISO-538	
		if(dataEvento!=null && !"".equals(dataEvento)){
			prest.setDataEvento(dataEvento);
		}	
		//FINE SISO-538		
		
		return prest;
	}

}
