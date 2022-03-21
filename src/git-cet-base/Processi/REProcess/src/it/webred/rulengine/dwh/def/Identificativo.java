package it.webred.rulengine.dwh.def;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Identificativo implements Campo
{
	private BigDecimal valore;

	public BigDecimal getValore()
	{
		return valore;
	}

	public void setValore(BigDecimal o)
	{
		valore = o;
		
	}
	public void setValore(Object o)
	{
		if (o instanceof Double)
			valore = BigDecimal.valueOf(((Double)o).doubleValue());
		else if (o instanceof Long)
			valore = BigDecimal.valueOf(((Long)o).longValue());
		else if (o instanceof Integer) {
			valore = new BigDecimal(((Integer)o));
		} else if (o instanceof BigDecimal) 
			valore = (BigDecimal)o;
		else 
			valore = null;
		
	}
}
