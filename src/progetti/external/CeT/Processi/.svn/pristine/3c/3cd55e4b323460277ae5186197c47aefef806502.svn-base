package it.webred.rulengine.dwh.table;

import java.math.BigDecimal;
import java.sql.Timestamp;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.RelazioneToMasterTable;

public class SitRuoloTarsuSt extends TabellaDwh {
	
	private RelazioneToMasterTable idExtRuolo = new RelazioneToMasterTable(SitRuoloTarsu.class, new ChiaveEsterna());

	private String anno;
	private Integer prog;
	private String indirizzo;
	private String numFattura;
	private DataDwh dataFattura;
	private BigDecimal importo;
	private String codTributo;

	@Override
	public String getValueForCtrHash()
	{
		return this.getIdOrig()+this.getAnno()+
				(String)this.idExtRuolo.getRelazione().getValore() +
				this.getProg()+
				this.getIndirizzo() +
				this.getNumFattura() +
				this.getDataFattura().getValore()+
				this.getImporto()+
				this.getCodTributo();		
	}
	
	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}


	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public RelazioneToMasterTable getIdExtRuolo() {
		return idExtRuolo;
	}
	
	public void setIdExtRuolo(ChiaveEsterna idExtRuolo) {
		RelazioneToMasterTable r = new RelazioneToMasterTable(SitRuoloTarsu.class, idExtRuolo);
		this.idExtRuolo = r;
	}

	public String getNumFattura() {
		return numFattura;
	}

	public void setNumFattura(String numFattura) {
		this.numFattura = numFattura;
	}

	public DataDwh getDataFattura() {
		return dataFattura;
	}

	public void setDataFattura(DataDwh dataFattura) {
		this.dataFattura = dataFattura;
	}

	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public String getCodTributo() {
		return codTributo;
	}

	public void setCodTributo(String codTributo) {
		this.codTributo = codTributo;
	}

	public Integer getProg() {
		return prog;
	}

	public void setProg(Integer prog) {
		this.prog = prog;
	}

}
