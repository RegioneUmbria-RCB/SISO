package com.osmosit.siso.flussoinps.dao.impl;

import com.osmosit.siso.flussoinps.dao.BaseDAO;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.AnagraficaBeneficiario;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.Beneficiario;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.ObjectFactory;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.ResidenzaBeneficiario;

public class BeneficiarioDAO extends BaseDAO{

	public BeneficiarioDAO(ObjectFactory objFactory) {
		super(objFactory);		
	}

	public Beneficiario createBeneficiario(
			String cfBeneficiario, ResidenzaBeneficiario residenzaBeneficiario,
			AnagraficaBeneficiario anagraficaBeneficiario) {
		Beneficiario beneficiario=objFactory.createBeneficiario();
		beneficiario.setAnagrafica(anagraficaBeneficiario);
		beneficiario.setCodiceFiscale(cfBeneficiario);
		beneficiario.setResidenza(residenzaBeneficiario);
		return beneficiario;
	}

}
