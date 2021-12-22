package com.osmosit.siso.flussoinps.dao.impl;

import com.osmosit.siso.flussoinps.dao.BaseDAO;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.Beneficiari;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.Ente;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.ObjectFactory;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.PSAInvioPrestazioniInput;

public class PSAInvioPrestazioniInputDAO extends BaseDAO{

	public PSAInvioPrestazioniInputDAO(ObjectFactory objFactory) {
		super(objFactory);
	}

	public PSAInvioPrestazioniInput cratePSAInvioPrestazioniInput(
			String idFlusso, Ente ente, Beneficiari beneficiari) {
		PSAInvioPrestazioniInput psaInvioPrestazioniInput = objFactory.createPSAInvioPrestazioniInput();
		psaInvioPrestazioniInput.setIdentificazioneFlusso(idFlusso);
		psaInvioPrestazioniInput.setIdentificazioneEnte(ente);
		psaInvioPrestazioniInput.setBeneficiari(beneficiari);
		return psaInvioPrestazioniInput;
	}

}
