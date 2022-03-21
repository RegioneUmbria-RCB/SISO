package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.DataDwh;

public class SitRuoloTarsuStSg extends SitRuoloTarsuSt {
	
	private String numNota;
	private DataDwh dataNota;

	@Override
	public String getValueForCtrHash()
	{
		return super.getValueForCtrHash()+
				this.getNumNota()+
				this.getDataNota().getValore();
	}

	public String getNumNota() {
		return numNota;
	}

	public void setNumNota(String numNota) {
		this.numNota = numNota;
	}

	public DataDwh getDataNota() {
		return dataNota;
	}

	public void setDataNota(DataDwh dataNota) {
		this.dataNota = dataNota;
	}
	
	
}
