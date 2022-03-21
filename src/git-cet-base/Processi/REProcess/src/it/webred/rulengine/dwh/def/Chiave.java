package it.webred.rulengine.dwh.def;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import it.webred.utils.DateFormat;
import it.webred.utils.StringUtils;

public class Chiave implements Campo
{

	
	private String valore;
	

	public void setValore(Object o)
	{
		valore = (String)o;
		
	}
	
	/**
	 * metodo utilizzato nel caso particolare di presenza di un record da inserire con stesso Id di uno già presente in archivio.
	 * Allora l'id viene calcolato in modo diverso come risultato dei una concatenazione con un hash
	 * @param o
	 * @param hash
	 */
	public void setValore(DtIniDato dt, CtrHash hash, ChiaveEsterna idExt)
	{
		MessageDigest md = null;
		try
		{
			md = MessageDigest.getInstance("SHA");
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		String stringa = hash.getValore() + idExt.getValore();
		md.update(stringa.getBytes());


		byte[] b = md.digest();
		
		valore = this.getDataFormattataPerId(dt)+ new String(StringUtils.toHexString(b));
		
		
		
	}
	

	public void setValore(ChiaveEsterna chiaveEsterna, DtIniVal dtIniVal, CtrHash ctrHash) 
	{
		try {
			if (chiaveEsterna==null || dtIniVal==null 
				||chiaveEsterna.getValore()==null || dtIniVal.getValore() ==null)
				this.valore = ctrHash.getValore();
			else
			{
				this.valore = this.getDataFormattataPerId(dtIniVal) +   StringUtils.padding(chiaveEsterna.getValore().toString(), 55, ' ', true) ;
			}
			
		} catch (Exception e) {
			valore = null;
		}
			
	}

	private String getDataFormattataPerId(DataDwh dt) {
		return DateFormat.dateToString(dt.getValore(),"yyyyMMdd HHmmss");
	}


	public String getValore()
	{
		return valore;
	}
	

	
}
