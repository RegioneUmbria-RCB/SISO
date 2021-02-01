package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.Relazione;

import java.math.BigDecimal;

public class RDemanioTerreni extends TabellaDwhMultiProv {
	
	private Relazione idExtBene = new Relazione(RDemanioBene.class, new ChiaveEsterna());
	private String annotazione = "";
	private String chiaveBene = "";
	private String chiave1 = "";
	private String chiave2;
	private String chiave3;
	private String chiave4;
	private String chiave5;
	private String classe = "";
	private String codComune = "";
	private String codUtilizzo = "";
	private String contratti = "";
	private DataDwh dataVendita = null;
	private String dismesso = "";
	private DataDwh dtElab = null;
	private String finalita = "";
	private String foglio = "";
	private String fonte = "";
	private String mappale = "";
	private String note = "";
	private String partita = "";
	private String qualita = "";
	private BigDecimal quotaPoss = null;
	private BigDecimal reddAgr = null;
	private BigDecimal reddDomin = null;
	private String renditaPresunta = "";
	private String sezione = "";
	private String sub = "";
	private BigDecimal superficie = null;
	private String tipo = "";
	private String tipologia = "";
	private String unita = "";
	private String usoAttuale = "";
	private String valAcquisto = "";
	private String valCatastale = "";
	private String valInventario = "";
	
	public RDemanioTerreni() {
	
	}//-------------------------------------------------------------------------
	
	@Override
	public String getValueForCtrHash(){
		
		String hash = this.annotazione + this.getIdOrig().getValore() +  chiaveBene + classe + codComune + codUtilizzo +   
				chiave1 +  chiave2 +  chiave3 +  chiave4 +  chiave5 +  finalita +  foglio +  fonte + 
				mappale + note + partita + qualita + sezione + sub + this.tipo + tipologia + unita + usoAttuale + 
				valAcquisto + valCatastale + valInventario;
		
		return hash;
	}//-------------------------------------------------------------------------

	public String getAnnotazione() {
		return annotazione;
	}

	public void setAnnotazione(String annotazione) {
		this.annotazione = annotazione;
	}

	public String getChiave1() {
		return chiave1;
	}

	public void setChiave1(String chiave1) {
		this.chiave1 = chiave1;
	}

	public String getChiave2() {
		return chiave2;
	}

	public void setChiave2(String chiave2) {
		this.chiave2 = chiave2;
	}

	public String getChiave3() {
		return chiave3;
	}

	public void setChiave3(String chiave3) {
		this.chiave3 = chiave3;
	}

	public String getChiave4() {
		return chiave4;
	}

	public void setChiave4(String chiave4) {
		this.chiave4 = chiave4;
	}

	public String getChiave5() {
		return chiave5;
	}

	public void setChiave5(String chiave5) {
		this.chiave5 = chiave5;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getCodComune() {
		return codComune;
	}

	public void setCodComune(String codComune) {
		this.codComune = codComune;
	}

	public String getCodUtilizzo() {
		return codUtilizzo;
	}

	public void setCodUtilizzo(String codUtilizzo) {
		this.codUtilizzo = codUtilizzo;
	}

	public String getContratti() {
		return contratti;
	}

	public void setContratti(String contratti) {
		this.contratti = contratti;
	}

	public DataDwh getDataVendita() {
		return dataVendita;
	}

	public void setDataVendita(DataDwh dataVendita) {
		this.dataVendita = dataVendita;
	}

	public String getDismesso() {
		return dismesso;
	}

	public void setDismesso(String dismesso) {
		this.dismesso = dismesso;
	}

	public DataDwh getDtElab() {
		return dtElab;
	}

	public void setDtElab(DataDwh dtElab) {
		this.dtElab = dtElab;
	}

	public String getFinalita() {
		return finalita;
	}

	public void setFinalita(String finalita) {
		this.finalita = finalita;
	}

	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getFonte() {
		return fonte;
	}

	public void setFonte(String fonte) {
		this.fonte = fonte;
	}

	public String getMappale() {
		return mappale;
	}

	public void setMappale(String mappale) {
		this.mappale = mappale;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPartita() {
		return partita;
	}

	public void setPartita(String partita) {
		this.partita = partita;
	}

	public String getQualita() {
		return qualita;
	}

	public void setQualita(String qualita) {
		this.qualita = qualita;
	}

	public BigDecimal getQuotaPoss() {
		return quotaPoss;
	}

	public void setQuotaPoss(BigDecimal quotaPoss) {
		this.quotaPoss = quotaPoss;
	}

	public BigDecimal getReddAgr() {
		return reddAgr;
	}

	public void setReddAgr(BigDecimal reddAgr) {
		this.reddAgr = reddAgr;
	}

	public BigDecimal getReddDomin() {
		return reddDomin;
	}

	public void setReddDomin(BigDecimal reddDomin) {
		this.reddDomin = reddDomin;
	}

	public String getRenditaPresunta() {
		return renditaPresunta;
	}

	public void setRenditaPresunta(String renditaPresunta) {
		this.renditaPresunta = renditaPresunta;
	}

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public BigDecimal getSuperficie() {
		return superficie;
	}

	public void setSuperficie(BigDecimal superficie) {
		this.superficie = superficie;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public String getUnita() {
		return unita;
	}

	public void setUnita(String unita) {
		this.unita = unita;
	}

	public String getUsoAttuale() {
		return usoAttuale;
	}

	public void setUsoAttuale(String usoAttuale) {
		this.usoAttuale = usoAttuale;
	}

	public String getValAcquisto() {
		return valAcquisto;
	}

	public void setValAcquisto(String valAcquisto) {
		this.valAcquisto = valAcquisto;
	}

	public String getValCatastale() {
		return valCatastale;
	}

	public void setValCatastale(String valCatastale) {
		this.valCatastale = valCatastale;
	}

	public String getValInventario() {
		return valInventario;
	}

	public void setValInventario(String valInventario) {
		this.valInventario = valInventario;
	}

	public String getChiaveBene() {
		return chiaveBene;
	}

	public void setChiaveBene(String chiaveBene) {
		this.chiaveBene = chiaveBene;
	}

	public Relazione getIdExtBene() {
		return idExtBene;
	}

	public void setIdExtBene(Relazione idExtBene) {
		this.idExtBene = idExtBene;
	}
	
	public void setIdExtBene(ChiaveEsterna idExt) {
		Relazione r = new Relazione(RDemanioBene.class, idExt);
		this.idExtBene = r;
	}


}
