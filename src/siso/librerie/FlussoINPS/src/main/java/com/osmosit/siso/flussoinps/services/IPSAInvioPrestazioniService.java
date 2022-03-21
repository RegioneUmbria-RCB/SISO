package com.osmosit.siso.flussoinps.services;

import com.osmosit.siso.flussoinps.psa_invioprestazioni.Beneficiari;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.Ente;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.PSAInvioPrestazioniInput;

public interface IPSAInvioPrestazioniService {

	public PSAInvioPrestazioniInput cratePSAInvioPrestazioniInput(String idFlusso, Ente ente,
			Beneficiari beneficiari);

}
