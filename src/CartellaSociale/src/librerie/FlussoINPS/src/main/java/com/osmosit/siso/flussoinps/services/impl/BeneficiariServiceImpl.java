package com.osmosit.siso.flussoinps.services.impl;

import com.osmosit.siso.flussoinps.dao.impl.BeneficiariDAO;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.Beneficiari;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.ObjectFactory;
import com.osmosit.siso.flussoinps.services.IBeneficiariService;

public class BeneficiariServiceImpl implements IBeneficiariService{
	private BeneficiariDAO beneficiariDao;
	
	public BeneficiariServiceImpl(ObjectFactory objFactory){
		beneficiariDao= new BeneficiariDAO(objFactory);
	}

	@Override
	public Beneficiari createBeneficiari() {
		return beneficiariDao.createBeneficiari();
	}
}
