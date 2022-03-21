package com.osmosit.siso.flussoinps.services.impl;

import com.osmosit.siso.flussoinps.dao.impl.ObjectFactoryDAO;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.ObjectFactory;
import com.osmosit.siso.flussoinps.services.IObjectFactoryService;

public class ObjectFactoryServiceImpl implements IObjectFactoryService{

	private ObjectFactoryDAO objFactoryDAO;
	private ObjectFactory objFactory;
	
	public ObjectFactoryServiceImpl(){
		objFactoryDAO= new ObjectFactoryDAO();
	}
	
	@Override
	public ObjectFactory getObjectFactory() {
		if(objFactory==null)
			objFactory=objFactoryDAO.getObjectFactory();
		return objFactory;
	}		

}
