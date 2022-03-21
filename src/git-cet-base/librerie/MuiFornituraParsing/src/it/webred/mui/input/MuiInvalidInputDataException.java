package it.webred.mui.input;

import it.webred.mui.MuiException;

public class MuiInvalidInputDataException extends MuiException {
	public MuiInvalidInputDataException() {
		super();
	}
	public MuiInvalidInputDataException(String code) {
		super(code);
	}
	public MuiInvalidInputDataException(Throwable t) {
		super(t);
	}
	public MuiInvalidInputDataException(String code,Throwable t) {
		super(code,t);
	}
	public MuiInvalidInputDataException(String code,Object obj,Throwable t) {
		super(code,obj,t);
	}
}
