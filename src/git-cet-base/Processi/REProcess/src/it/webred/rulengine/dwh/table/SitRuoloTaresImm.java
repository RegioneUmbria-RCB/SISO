package it.webred.rulengine.dwh.table;

import java.math.BigDecimal;
import java.sql.Timestamp;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.RelazioneToMasterTable;

public class SitRuoloTaresImm extends TabellaDwh {
	
	private RelazioneToMasterTable idExtRuolo = new RelazioneToMasterTable(SitRuoloTarsu.class, new ChiaveEsterna());

	private String anno;
	private Integer prog;
	private String indirizzo ;
	private String codTipo;
	private String desTipo;
	private String cat;
	private String desCat ;
	private String sez;
	private String foglio;
	private String particella;
	private String sub;
	private BigDecimal mq;
	private BigDecimal tariffaQf;
	private BigDecimal tariffaQv;
	private BigDecimal tariffaQs;
	
	private BigDecimal riduzioneQf;
	private BigDecimal riduzioneQv;
	private BigDecimal riduzioneQs;
	
	private String causale;
	private BigDecimal importo;
	private BigDecimal importoQf;
	private BigDecimal importoQv;
	private BigDecimal importoQs;
	private String codTributo;
	private DataDwh periodoDa;
	private DataDwh periodoA;


	@Override
	public String getValueForCtrHash()
	{
		return this.getIdOrig()+this.getAnno()+
				(String)this.idExtRuolo.getRelazione().getValore() +
				this.getProg()+
				this.getIndirizzo() +
				this.getCodTipo()+
				this.getCat()+
				this.getSez()+this.getFoglio()+this.getParticella()+this.getSub()+
				this.getMq() +
				this.getTariffaQf() + this.getTariffaQv() + this.getTariffaQs() + 
				this.getRiduzioneQf() +this.getRiduzioneQv() +this.getRiduzioneQs() +
				this.getCausale() +
				this.getImporto()+ this.getImportoQf()+this.getImportoQv()+this.getImportoQs()+
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

	public String getCausale() {
		return causale;
	}

	public void setCausale(String causale) {
		this.causale = causale;
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

	public String getCodTipo() {
		return codTipo;
	}

	public void setCodTipo(String codTipo) {
		this.codTipo = codTipo;
	}

	public String getDesTipo() {
		return desTipo;
	}

	public void setDesTipo(String desTipo) {
		this.desTipo = desTipo;
	}

	public String getCat() {
		return cat;
	}

	public void setCat(String cat) {
		this.cat = cat;
	}

	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public String getSez() {
		return sez;
	}

	public void setSez(String sez) {
		this.sez = sez;
	}

	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getParticella() {
		return particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public BigDecimal getTariffaQf() {
		return tariffaQf;
	}

	public void setTariffaQf(BigDecimal tariffaQf) {
		this.tariffaQf = tariffaQf;
	}

	public BigDecimal getTariffaQv() {
		return tariffaQv;
	}

	public void setTariffaQv(BigDecimal tariffaQv) {
		this.tariffaQv = tariffaQv;
	}

	public BigDecimal getTariffaQs() {
		return tariffaQs;
	}

	public void setTariffaQs(BigDecimal tariffaQs) {
		this.tariffaQs = tariffaQs;
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

	public DataDwh getPeriodoDa() {
		return periodoDa;
	}

	public void setPeriodoDa(DataDwh periodoDa) {
		this.periodoDa = periodoDa;
	}

	public DataDwh getPeriodoA() {
		return periodoA;
	}

	public void setPeriodoA(DataDwh periodoA) {
		this.periodoA = periodoA;
	}

	public void setIdExtRuolo(RelazioneToMasterTable idExtRuolo) {
		this.idExtRuolo = idExtRuolo;
	}

}
