package it.webred.rulengine.dwh.table;

import java.math.BigDecimal;
import java.sql.Timestamp;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.RelazioneToMasterTable;

public class SitRuoloTarsuImm extends TabellaDwh {
	
	private RelazioneToMasterTable idExtRuolo = new RelazioneToMasterTable(SitRuoloTarsu.class, new ChiaveEsterna());

	private String anno;
	private Integer prog;
	private String indirizzo ;
	private String desCat ;
	private BigDecimal mq;
	private BigDecimal tariffa;
	private BigDecimal riduzione;
	private String causale;
	private BigDecimal importo;
	private String codTributo;

	@Override
	public String getValueForCtrHash()
	{
		return this.getIdOrig()+this.getAnno()+
				(String)this.idExtRuolo.getRelazione().getValore() +
				this.getProg()+
				this.getIndirizzo() +
				this.getDesCat() + 
				this.getMq() +
				this.getTariffa() + 
				this.getRiduzione() +
				this.getCausale() +
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

	public String getDesCat() {
		return desCat;
	}

	public void setDesCat(String desCat) {
		this.desCat = desCat;
	}

	public BigDecimal getMq() {
		return mq;
	}

	public void setMq(BigDecimal mq) {
		this.mq = mq;
	}

	public BigDecimal getTariffa() {
		return tariffa;
	}

	public void setTariffa(BigDecimal tariffa) {
		this.tariffa = tariffa;
	}

	public BigDecimal getRiduzione() {
		return riduzione;
	}

	public void setRiduzione(BigDecimal riduzione) {
		this.riduzione = riduzione;
	}

	public String getCausale() {
		return causale;
	}

	public void setCausale(String causale) {
		this.causale = causale;
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
