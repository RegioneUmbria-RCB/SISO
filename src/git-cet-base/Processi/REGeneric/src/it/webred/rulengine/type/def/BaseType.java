package it.webred.rulengine.type.def;

public abstract class BaseType implements ReType
{
	protected String stringConfig;
	
	
	protected BaseType() 
	{
	}
	
	protected BaseType(String config) 
	{
		this.stringConfig = config;
		
	}
	


	public String getStringConfig() {
		return stringConfig;
	}
}
