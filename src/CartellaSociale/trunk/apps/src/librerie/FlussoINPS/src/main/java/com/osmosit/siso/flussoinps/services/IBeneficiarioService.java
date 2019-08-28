package com.osmosit.siso.flussoinps.services;

import com.osmosit.siso.flussoinps.psa_invioprestazioni.AnagraficaBeneficiario;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.Beneficiario;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.ResidenzaBeneficiario;

public interface IBeneficiarioService {

	public Beneficiario createBeneficiario(String cfBeneficiario, ResidenzaBeneficiario residenzaBeneficiario,
			AnagraficaBeneficiario anagraficaBeneficiario);


}
