package com.osmosit.siso.flussoinps.dao.impl;

import com.osmosit.siso.flussoinps.dao.BaseDAO;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.ObjectFactory;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.ResidenzaBeneficiario;

public class ResidenzaBeneficiarioDAO extends BaseDAO{

	public ResidenzaBeneficiarioDAO(ObjectFactory objFactory) {
		super(objFactory);	
	}

	public ResidenzaBeneficiario createResidenza(String regione, String comune, String nazione){
		ResidenzaBeneficiario resid= objFactory.createResidenzaBeneficiario();
		resid.setRegione(regione);
		resid.setComune(comune);
		resid.setNazione(nazione);
		return resid;
	}
}
