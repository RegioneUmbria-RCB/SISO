package it.umbriadigitale.argo.ejb.client.base.ejbclient;

import javax.ejb.ApplicationException;

@ApplicationException
public class ArgoServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ArgoServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public ArgoServiceException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ArgoServiceException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ArgoServiceException() {
		super();
		// TODO Auto-generated constructor stub
	}

}
