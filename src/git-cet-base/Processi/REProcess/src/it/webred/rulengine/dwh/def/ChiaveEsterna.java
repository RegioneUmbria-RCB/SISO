package it.webred.rulengine.dwh.def;

import it.webred.utils.StringUtils;


public class ChiaveEsterna implements Campo
{


	
	private String valore;
	
	public ChiaveEsterna(){
		
	}
	
	public ChiaveEsterna(String valore){
		this.valore = valore;
	}

	public void setValore(ChiaveOriginale chiaveOriginale, Identificativo idEenteSorgente)
	{
		try {
			if (chiaveOriginale==null  
				||chiaveOriginale.getValore()==null || idEenteSorgente.getValore()==null)
				
				valore = null;
			else
				valore = chiaveOriginale.getValore() +   StringUtils.padding(idEenteSorgente.getValore().toString(), 5, ' ', true);
		} catch (Exception e) {
			valore = null;
		}
	}

	public void setValore(String provenienza, ChiaveOriginale chiaveOriginale, Identificativo idEenteSorgente)
	{
		this.setValore(chiaveOriginale, idEenteSorgente);
		valore = provenienza + "@" + valore; 
	}
	
	public String getValore()
	{
		return valore;
	}

	public void setValore(Object o)
	{
		valore = (String)o;
		
	}


		
	
	
	


}
