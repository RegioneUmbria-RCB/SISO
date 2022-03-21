package it.webred.rulengine.diagnostics.bean;

import it.webred.rulengine.db.model.RRuleParamIn;

public class ParamInValue extends RRuleParamIn implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private Object paramValue = null;
	
	public ParamInValue(RRuleParamIn parIn){
		super.setDescr(parIn.getDescr());
		super.setType(parIn.getType());
	}
	
	public Object getParamValue() {
		return paramValue;
	}
	
	public void setParamValue(Object paramValue) {
		this.paramValue = paramValue;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(" (descParamIn>"+getDescr()+")");
		sb.append(" (typeParamIn>"+getType()+")");
		sb.append(" (valueParamIn>"+getParamValue()+")");
		
		return sb.toString();
	}
	
}
