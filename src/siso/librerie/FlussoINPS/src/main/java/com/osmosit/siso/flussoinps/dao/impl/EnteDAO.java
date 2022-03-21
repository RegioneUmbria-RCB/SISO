package com.osmosit.siso.flussoinps.dao.impl;

import com.osmosit.siso.flussoinps.dao.BaseDAO;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.Ente;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.ObjectFactory;

public class EnteDAO extends BaseDAO{
	
	public EnteDAO(ObjectFactory objFactory) {
		super(objFactory);		
	}

	public Ente createEnte(String denominazioneEnte, String codiceEnte, String cfOperatore, String indirizzoEnte){
		Ente ente= objFactory.createEnte();
		ente.setDenominazioneEnte(denominazioneEnte);
		ente.setCodiceEnte(codiceEnte);
		ente.setCodiceFiscaleOperatore(cfOperatore);
		if (indirizzoEnte!=null && !"".equals(indirizzoEnte)) {
			ente.setIndirizzoEnte(indirizzoEnte);			
		}
		return ente;
	}

}
