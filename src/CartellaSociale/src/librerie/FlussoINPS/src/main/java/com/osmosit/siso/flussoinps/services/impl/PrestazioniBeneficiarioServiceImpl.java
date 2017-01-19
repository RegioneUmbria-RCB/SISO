package com.osmosit.siso.flussoinps.services.impl;

import javax.xml.datatype.XMLGregorianCalendar;

import com.osmosit.siso.flussoinps.dao.impl.PrestazioniBeneficiarioDAO;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.ObjectFactory;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.PrestazioniBeneficiario;
import com.osmosit.siso.flussoinps.services.IPrestazioniBeneficiarioService;

public class PrestazioniBeneficiarioServiceImpl implements IPrestazioniBeneficiarioService {
	private PrestazioniBeneficiarioDAO prestazioniBeneficiarioDao;
	
	public PrestazioniBeneficiarioServiceImpl(ObjectFactory objFactory){
		prestazioniBeneficiarioDao= new PrestazioniBeneficiarioDAO(objFactory);
	}

	@Override
	public PrestazioniBeneficiario createPrestazioneBeneficiario(
			XMLGregorianCalendar dataInizio, String dataFine,
			String periodErog, String importoMens, String quotaEnte,
			String quotaUtente, String quotaSSN, String quotaRichiesta,
			String sogliaISEE, String carattere, String numProtDSU,
			String annoProt, String dataDSU, String codice,
			String denominazione, String protEnte, String dataErog,
			String importo) {
		
		return prestazioniBeneficiarioDao.createPrestazioneBeneficiario(
				dataInizio, dataFine,
				periodErog, importoMens, quotaEnte,
				quotaUtente, quotaSSN, quotaRichiesta,
				sogliaISEE, carattere, numProtDSU,
				annoProt, dataDSU, codice,
				denominazione, protEnte, dataErog,
				importo);
	}
}
