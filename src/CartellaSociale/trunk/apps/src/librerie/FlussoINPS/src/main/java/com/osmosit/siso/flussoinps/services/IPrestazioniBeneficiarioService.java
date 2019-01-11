package com.osmosit.siso.flussoinps.services;

import javax.xml.datatype.XMLGregorianCalendar;

import com.osmosit.siso.flussoinps.psa_invioprestazioni.PrestazioniBeneficiario;

public interface IPrestazioniBeneficiarioService {
	public PrestazioniBeneficiario createPrestazioneBeneficiario(String dataInizio,String dataFine,String periodErog,String importoMens,String quotaEnte,String quotaUtente,String quotaSSN,
			String quotaRichiesta,String sogliaISEE,String carattere,String numProtDSU,String annoProt,String dataDSU,String codice,String denominazione,String protEnte,String dataErog,String importo
			,String dataEvento  //INIZIO SISO-538
			);
}
