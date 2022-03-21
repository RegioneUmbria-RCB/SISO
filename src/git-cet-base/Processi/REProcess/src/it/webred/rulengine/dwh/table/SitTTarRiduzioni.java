package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.RelazioneToMasterTable;

import java.math.BigDecimal;
import java.sql.Connection;

public class SitTTarRiduzioni extends TabellaDwhMultiProv implements IdExtFromSequence {
		
	
	private RelazioneToMasterTable idExtOggRsu = new RelazioneToMasterTable(SitTTarOggetto.class, new ChiaveEsterna());
	private BigDecimal valRid;
	private String tipoRiduzione;
	private String descrRiduzione;

	public Relazione getIdExtOggRsu() {
		return idExtOggRsu;
	}

	public void setIdExtOggRsu(ChiaveEsterna idExtOggRsu)
	{
		RelazioneToMasterTable r = new RelazioneToMasterTable(SitTTarOggetto.class, idExtOggRsu);
		this.idExtOggRsu = r;
	}
	
	public BigDecimal getValRid() {
		return valRid;
	}

	public void setValRid(BigDecimal valRid) {
		this.valRid = valRid;
	}

	public String getTipoRiduzione() {
		return tipoRiduzione;
	}

	public void setTipoRiduzione(String tipoRiduzione) {
		this.tipoRiduzione = tipoRiduzione;
	}

	public String getDescrRiduzione() {
		return descrRiduzione;
	}

	public void setDescrRiduzione(String descrRiduzione) {
		this.descrRiduzione = descrRiduzione;
	}

	public String getValueForCtrHash() {		
		return (String)this.idExtOggRsu.getRelazione().getValore() +
		this.valRid +
		this.tipoRiduzione +
		this.descrRiduzione;
	}

	public String getSequenceName() {
		return "SEQ_SIT_T_TAR_SOGGETTI";
	}
	


	
}
