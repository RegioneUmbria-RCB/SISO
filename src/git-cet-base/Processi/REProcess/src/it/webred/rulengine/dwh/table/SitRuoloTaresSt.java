package it.webred.rulengine.dwh.table;

import java.math.BigDecimal;
import java.sql.Timestamp;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.RelazioneToMasterTable;

public class SitRuoloTaresSt extends TabellaDwh {
	
	private RelazioneToMasterTable idExtRuolo = new RelazioneToMasterTable(SitRuoloTarsu.class, new ChiaveEsterna());

	private String anno;
	private Integer prog;
	private String indirizzo;
	private String desTipo;
	private String numFattura;
	private DataDwh dataFattura;
	private BigDecimal riduzioneQf;
	private BigDecimal riduzioneQv;
	private BigDecimal riduzioneQs;
	private BigDecimal importo;
	private BigDecimal importoQf;
	private BigDecimal importoQv;
	private BigDecimal importoQs;
	private String codTributo;

	@Override
	public String getValueForCtrHash()
	{
		return this.getIdOrig()+this.getAnno()+
				(String)this.idExtRuolo.getRelazione().getValore() +
				this.getProg()+
				this.getIndirizzo() +
				this.getDesTipo()+
				this.getNumFattura() +
				this.getDataFattura().getValore()+
				this.getImporto()+this.getImportoQf()+this.getImportoQv()+this.getImportoQs()+
				this.getRiduzioneQf()+this.getRiduzioneQs()+this.getRiduzioneQv()+
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

	public String getDesTipo() {
		return desTipo;
	}

	public void setDesTipo(String desTipo) {
		this.desTipo = desTipo;
	}

	public BigDecimal getRiduzioneQf() {
		return riduzioneQf;
	}

	public void setRiduzioneQf(BigDecimal riduzioneQf) {
		this.riduzioneQf = riduzioneQf;
	}

	public BigDecimal getRiduzioneQv() {
		return riduzioneQv;
	}

	public void setRiduzioneQv(BigDecimal riduzioneQv) {
		this.riduzioneQv = riduzioneQv;
	}

	public BigDecimal getRiduzioneQs() {
		return riduzioneQs;
	}

	public void setRiduzioneQs(BigDecimal riduzioneQs) {
		this.riduzioneQs = riduzioneQs;
	}

	public BigDecimal getImportoQf() {
		return importoQf;
	}

	public void setImportoQf(BigDecimal importoQf) {
		this.importoQf = importoQf;
	}

	public BigDecimal getImportoQv() {
		return importoQv;
	}

	public void setImportoQv(BigDecimal importoQv) {
		this.importoQv = importoQv;
	}

	public BigDecimal getImportoQs() {
		return importoQs;
	}

	public void setImportoQs(BigDecimal importoQs) {
		this.importoQs = importoQs;
	}

	public void setIdExtRuolo(RelazioneToMasterTable idExtRuolo) {
		this.idExtRuolo = idExtRuolo;
	}

}
