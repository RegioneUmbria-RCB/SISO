package it.webred.rulengine.dwh.table;

import java.math.BigDecimal;
import java.sql.Connection;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.RelazioneToMasterTable;

public class SitTIciRiduzioni extends TabellaDwhMultiProv implements IdExtFromSequence {
	
	
	private RelazioneToMasterTable idExtOggIci = new RelazioneToMasterTable(SitTIciOggetto.class, new ChiaveEsterna());
	private BigDecimal valRid;
	private String tipoRiduzione;
	private String descrRiduzione;
	
	public Relazione getIdExtOggIci() {
		return idExtOggIci;
	}

	public void setIdExtOggIci(ChiaveEsterna idExtOggIci)
	{
		RelazioneToMasterTable r = new RelazioneToMasterTable(SitTIciOggetto.class, idExtOggIci);
		this.idExtOggIci = r;
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
		return (String)this.idExtOggIci.getRelazione().getValore() +
		this.valRid +
		this.tipoRiduzione +
		this.descrRiduzione;
	}
	
	public String getSequenceName() {
		return "SEQ_SIT_T_ICI_SOGGETTI";
	}

	
}
