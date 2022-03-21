package com.osmosit.siso.flussoinps.services;

import com.osmosit.siso.flussoinps.psa_invioprestazioni.AnagraficaBeneficiario;

public interface IAnagraficaBeneficiarioService {
	public AnagraficaBeneficiario createAnagrafica(String nome,String cognome,String annoNascita,String luogoNascita,String sesso,String cittadISO,String secCittadISO);
}
