package com.osmosit.siso.flussoinps.services.impl;

import com.osmosit.siso.flussoinps.dao.impl.ResidenzaBeneficiarioDAO;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.ObjectFactory;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.ResidenzaBeneficiario;
import com.osmosit.siso.flussoinps.services.IResidenzaBeneficiarioService;

public class ResidenzaBeneficiarioServiceImpl implements IResidenzaBeneficiarioService {
	private ResidenzaBeneficiarioDAO residenzaBeneficiarioDao;
	
	public ResidenzaBeneficiarioServiceImpl(ObjectFactory objFactory){
		residenzaBeneficiarioDao= new ResidenzaBeneficiarioDAO(objFactory);
	}

	@Override
	public ResidenzaBeneficiario createResidenza(String regione,
			String comune, String nazione) {		
		return residenzaBeneficiarioDao.createResidenza(regione, comune, nazione);
	}
}
