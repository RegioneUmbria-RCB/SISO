package com.osmosit.siso.flussoinps.services.impl;

import com.osmosit.siso.flussoinps.dao.impl.PSAInvioPrestazioniInputDAO;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.Beneficiari;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.Ente;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.ObjectFactory;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.PSAInvioPrestazioniInput;
import com.osmosit.siso.flussoinps.services.IPSAInvioPrestazioniService;

public class PSAInvioPrestazioniServiceImpl implements IPSAInvioPrestazioniService{
	private PSAInvioPrestazioniInputDAO psaInvioPrestazioniDao;
	
	public PSAInvioPrestazioniServiceImpl(ObjectFactory objFactory){
		psaInvioPrestazioniDao= new PSAInvioPrestazioniInputDAO(objFactory);
	}

	@Override
	public PSAInvioPrestazioniInput cratePSAInvioPrestazioniInput(
			String idFlusso, Ente ente, Beneficiari beneficiari) {
		return psaInvioPrestazioniDao.cratePSAInvioPrestazioniInput(idFlusso, ente, beneficiari);
	}
}
