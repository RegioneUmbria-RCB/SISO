package com.osmosit.siso.flussoinps.services;

import com.osmosit.siso.flussoinps.psa_invioprestazioni.ResidenzaBeneficiario;

public interface IResidenzaBeneficiarioService {

	public ResidenzaBeneficiario createResidenza(String regione, String comune, String nazione);
}
