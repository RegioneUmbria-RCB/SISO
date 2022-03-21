package com.osmosit.siso.flussoinps.dao.impl;

import com.osmosit.siso.flussoinps.dao.BaseDAO;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.Beneficiari;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.ObjectFactory;

public class BeneficiariDAO extends BaseDAO{

	public BeneficiariDAO(ObjectFactory objFactory) {
		super(objFactory);
	}

	public Beneficiari createBeneficiari() {
		return objFactory.createBeneficiari();
	}

}
