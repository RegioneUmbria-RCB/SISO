package com.osmosit.siso.flussoinps.services.impl;

import com.osmosit.siso.flussoinps.dao.impl.EnteDAO;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.Ente;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.ObjectFactory;
import com.osmosit.siso.flussoinps.services.IEnteService;

public class EnteServiceImpl implements IEnteService{
	private EnteDAO enteDao;
	
	public EnteServiceImpl(ObjectFactory objFactory){
		enteDao=new EnteDAO(objFactory);
	}

	@Override
	public Ente createEnte(String denomEnte, String codEnte,
			String cfOperatore, String indirEnte) {
		return enteDao.createEnte(cfOperatore, indirEnte, cfOperatore, denomEnte);
	}
	

}
