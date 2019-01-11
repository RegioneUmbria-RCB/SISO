package com.osmosit.siso.flussoinps.dao;

import com.osmosit.siso.flussoinps.psa_invioprestazioni.ObjectFactory;

public class BaseDAO {
	
	protected ObjectFactory objFactory;
	
	public BaseDAO(ObjectFactory objFactory){
		this.objFactory=objFactory;
	}

}
