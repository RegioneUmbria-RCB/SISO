package it.webred.rulengine.dwh.table;

import java.math.BigDecimal;
import java.sql.Timestamp;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.RelazioneToMasterTable;

public class SitRuoloTaresRata extends TabellaDwh {
	
	private RelazioneToMasterTable idExtRuolo = new RelazioneToMasterTable(SitRuoloTarsu.class, new ChiaveEsterna());

	private Integer prog;
	private String VCampo ;
	private BigDecimal impBollettino;
	private DataDwh dataScadenza;
	private String totLettere;
	private String codRendAuto;
	private String impCodline;

	@Override
	public String getValueForCtrHash()
	{
		return (String)this.idExtRuolo.getRelazione().getValore() +
				this.getProg()+
				this.getVCampo()+
				this.getImpBollettino()+
				this.getDataScadenza().getValore()+
				this.getTotLettere() +
				this.getCodRendAuto()+
				this.getImpCodline();
			
	}
	

	

	public String getVCampo() {
		return VCampo;
	}




	public void setVCampo(String vCampo) {
		VCampo = vCampo;
	}


	public BigDecimal getImpBollettino() {
		return impBollettino;
	}

	public void setImpBollettino(BigDecimal impBollettino) {
		this.impBollettino = impBollettino;
	}

	public String getTotLettere() {
		return totLettere;
	}

	public void setTotLettere(String totLettere) {
		this.totLettere = totLettere;
	}

	public String getCodRendAuto() {
		return codRendAuto;
	}

	public void setCodRendAuto(String codRendAuto) {
		this.codRendAuto = codRendAuto;
	}

	public String getImpCodline() {
		return impCodline;
	}


	public void setImpCodline(String impCodline) {
		this.impCodline = impCodline;
	}


	public RelazioneToMasterTable getIdExtRuolo() {
		return idExtRuolo;
	}
	
	public void setIdExtRuolo(ChiaveEsterna idExtRuolo) {
		RelazioneToMasterTable r = new RelazioneToMasterTable(SitRuoloTarsu.class, idExtRuolo);
		this.idExtRuolo = r;
	}

	public Integer getProg() {
		return prog;
	}

	public void setProg(Integer prog) {
		this.prog = prog;
	}

	public void setDataScadenza(DataDwh dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public DataDwh getDataScadenza() {
		return dataScadenza;
	}

}
