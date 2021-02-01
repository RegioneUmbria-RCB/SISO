package it.webred.cs.sample.ejb;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AccessTableDataComuniSessionBeanRemote {


	public List<String> getComuni(CeTBaseObject dataIn);
}

