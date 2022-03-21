package com.osmosit.siso.flussoinps.services.impl;

import com.osmosit.siso.flussoinps.dao.impl.BeneficiarioDAO;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.AnagraficaBeneficiario;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.Beneficiario;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.ObjectFactory;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.ResidenzaBeneficiario;
import com.osmosit.siso.flussoinps.services.IBeneficiarioService;

public class BeneficiarioServiceImpl implements IBeneficiarioService{
	private BeneficiarioDAO beneficiarioDao;
	
	public BeneficiarioServiceImpl(ObjectFactory objFactory){
		beneficiarioDao= new BeneficiarioDAO(objFactory);
	}

	@Override
	public Beneficiario createBeneficiario( String cfBeneficiario,
			ResidenzaBeneficiario residenzaBeneficiario,
			AnagraficaBeneficiario anagraficaBeneficiario) {		
		return beneficiarioDao.createBeneficiario(cfBeneficiario,residenzaBeneficiario, anagraficaBeneficiario);
	}
}
