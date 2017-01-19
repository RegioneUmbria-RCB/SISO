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
			XMLGregorianCalendar dataInizio, String dataFine,
			String periodErog, String importoMens, String quotaEnte,
			String quotaUtente, String quotaSSN, String quotaRichiesta,
			String sogliaISEE, String carattere, String numProtDSU,
			String annoProt, String dataDSU, String codice,
			String denominazione, String protEnte, String dataErog,
			String importo) {
		
		PrestazioniBeneficiario prest= objFactory.createPrestazioniBeneficiario();
		if(dataInizio!=null){			
			prest.setDataInizio(dataInizio);
		}		
		if(dataFine!=null){
			prest.setDataFine(dataFine);
		}		
		if(periodErog!=null){
			prest.setPeriodoErogazione(periodErog);
		}		
		if(importoMens!=null){
			prest.setImportoMensile(importoMens);
		}		
		if(quotaEnte!=null){
			prest.setQuotaEnte(quotaEnte);
		}		
		if(quotaUtente!=null){
			prest.setQuotaUtente(quotaUtente);
		}		
		if(quotaSSN!=null){
			prest.setQuotaSSN(quotaSSN);
		}		
		if(quotaRichiesta!=null){
			prest.setQuotaRichiesta(quotaRichiesta);
		}		
		if(sogliaISEE!=null){
			prest.setSogliaISEE(sogliaISEE);
		}			
		if(carattere!=null){
			prest.setCarattere(carattere);
		}		
		if(numProtDSU!=null){
			prest.setNumeroProtocolloDSU(numProtDSU);
		}		
		if(annoProt!=null){
			prest.setAnnoProtocollo(annoProt);
		}		
		if(dataDSU!=null){
			prest.setDataDSU(dataDSU);
		}		
		if(codice!=null){
			prest.setCodice(codice);
		}		
		if(denominazione!=null){
			prest.setDenominazione(denominazione);
		}		
		if(protEnte!=null){
			prest.setProtocolloEnte(protEnte);
		}		
		if(dataErog!=null){
			prest.setDataErogazione(dataErog);	
		}		
		if(importo!=null){
			prest.setImporto(importo);
		}		
		
		return prest;
	}

}
