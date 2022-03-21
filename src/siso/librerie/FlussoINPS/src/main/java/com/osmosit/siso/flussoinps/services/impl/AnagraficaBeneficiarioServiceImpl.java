package com.osmosit.siso.flussoinps.services.impl;

import com.osmosit.siso.flussoinps.dao.impl.AnagraficaBeneficiarioDAO;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.AnagraficaBeneficiario;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.ObjectFactory;
import com.osmosit.siso.flussoinps.services.IAnagraficaBeneficiarioService;

public class AnagraficaBeneficiarioServiceImpl implements IAnagraficaBeneficiarioService{

	private AnagraficaBeneficiarioDAO anagraficaBeneficiarioDao;
	
	public AnagraficaBeneficiarioServiceImpl(ObjectFactory objFactory){
		anagraficaBeneficiarioDao= new AnagraficaBeneficiarioDAO(objFactory);
	}

	@Override
	public AnagraficaBeneficiario createAnagrafica(String nome,
			String cognome, String annoNascita, String luogoNascita,
			String sesso, String cittadISO, String secCittadISO) {
		return anagraficaBeneficiarioDao.createAnagrafica(nome, cognome, annoNascita, luogoNascita, sesso, cittadISO, secCittadISO);
	}
}
