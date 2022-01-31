package it.webred.mui;

public class MuiException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1340843488467985351L;
	public MuiException() {
		super();
		setCode("UNKNOWN");
	}
	public MuiException(String code) {
		super();
		setCode(code);
	}
	public MuiException(Throwable t) {
		super(t);
		setNestedThrowable(t);
		setCode("UNKNOWN");
	}
	public MuiException(String code,Throwable t) {
		super(t);
		setNestedThrowable(t);
		setCode(code);
	}
	public MuiException(String code,Object obj,Throwable t) {
		super(t);
		setNestedThrowable(t);
		setCode(code);
		setObj(obj);
	}
	private Throwable _nestedThrowable;
	private String code;
	private Object obj;
	public String getCode() {
		return code;
	}
	protected void setCode(String code) {
		this.code = code;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public Throwable getNestedThrowable() {
		return _nestedThrowable;
	}
	public void setNestedThrowable(Throwable nestedThrowable) {
		this._nestedThrowable = nestedThrowable;
	}

}
