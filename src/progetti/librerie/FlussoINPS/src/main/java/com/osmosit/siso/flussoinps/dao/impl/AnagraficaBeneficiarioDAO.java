package com.osmosit.siso.flussoinps.dao.impl;

import com.osmosit.siso.flussoinps.dao.BaseDAO;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.AnagraficaBeneficiario;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.ObjectFactory;

public class AnagraficaBeneficiarioDAO extends BaseDAO{

	public AnagraficaBeneficiarioDAO(ObjectFactory objFactory) {
		super(objFactory);		
	}
	public AnagraficaBeneficiario createAnagrafica(String nome,
			String cognome, String annoNascita, String luogoNascita,
			String sesso, String cittadISO, String secCittadISO){
		AnagraficaBeneficiario ana=objFactory.createAnagraficaBeneficiario();
		ana.setNome(nome);
		ana.setCognome(cognome);
		ana.setAnnoNascita(annoNascita);
		ana.setLuogoNascita(luogoNascita);
		ana.setSesso(sesso);
		ana.setCittadinanzaISO(cittadISO);
		ana.setSecondaCittadinanzaISO(secCittadISO);
		return ana;
	}
}
