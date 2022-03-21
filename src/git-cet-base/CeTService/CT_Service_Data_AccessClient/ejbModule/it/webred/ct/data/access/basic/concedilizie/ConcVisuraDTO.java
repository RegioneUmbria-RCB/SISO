package it.webred.ct.data.access.basic.concedilizie;

import it.webred.ct.data.model.concedilizie.ConcEdilizieVisureDoc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

public class ConcVisuraDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String numProtGen;

	private String annoProtGen;

	private String numProtSett;
	
	private String tipoAtto;

	private String numeroAtto;
	
	private Date dataDoc;

	private String altravia;

	private String altriciv;

	private BigDecimal civico;

	private String civicoSub;

	private String destinazione;

	private String nomeIntestatario;

	private String prefisso;
	
	private String nomeVia;

	private boolean privata;

	private String riparto;

	private String tipologia;
	
	private String vincoloAmbientale;

	private String id;
	
	private BigDecimal idFile;
	
    private String pathFile;
	
	private static  Hashtable<String, String> htTipiAtti = new Hashtable<String, String>() ;
    private static  Hashtable<String, String> htDestinazioni = new Hashtable<String, String>();
    private static  Hashtable<String, String> htTipologie = new Hashtable<String, String>();
	
	public ConcVisuraDTO(){
		
		htTipiAtti.put("CE", "Concessioni Edilizie");
    	htTipiAtti.put("CEO", "Concessioni a scomputo Oneri");
    	htTipiAtti.put("CES", "Concessioni a Sanatoria");
    	htTipiAtti.put("CESP", "Concessioni a parziale Sanatoria");
    	htTipiAtti.put("CEV", "Concessione a Variante");
    	htTipiAtti.put("CEX", "Voltura");
    	htTipiAtti.put("AE", "Autorizzazioni Edilizie");
    	htTipiAtti.put("LA", "Licenze di Abitabilità");
    	htTipiAtti.put("DM", "Demolizioni");
    	htTipiAtti.put("DE", "Atti Densita Edilizia");
    	htTipiAtti.put("AP", "Autorizzazioni Paesistiche");
    	htTipiAtti.put("DA", "Assenza Danno Ambientale");
    	htTipiAtti.put("DIA", "DIA ex LR 22/99");
    	htTipiAtti.put("SDIA", "DIA ex L 662/99");
    	htTipiAtti.put("CO", "Condono Ordinario");
    	htTipiAtti.put("CS", "Condono Straordinario");
    	
    	htDestinazioni.put("RES", "Residenziale");
    	htDestinazioni.put("IND", "Industriale");
    	htDestinazioni.put("COM", "Commerciale-Industriale");
    	htDestinazioni.put("MIX", "Mista");
    	htDestinazioni.put("AGR", "Agricola");
    	htDestinazioni.put("REL", "Attrezzature Religiose");
    	htDestinazioni.put("HCC", "Ospedali-Case di Cura");
    	htDestinazioni.put("SCO", "Edilizia Scolastica");
    	htDestinazioni.put("ESA", "Edilizia Socio Assistenziale");
    	
    	htTipologie.put("BOX", "Box");
    	htTipologie.put("ANT", "Antenna");
    	htTipologie.put("STT", "Sottotetto");
    	htTipologie.put("RIS", "Ristrutturazione");
    	htTipologie.put("NEO", "Nuova Edificazione");
    	htTipologie.put("MST", "Manutenzione Straordinaria");
    	htTipologie.put("RCS", "Risanamento Conservativo");
    	htTipologie.put("RES", "Restauro");
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumProtGen() {
		return numProtGen;
	}

	public void setNumProtGen(String numProtGen) {
		this.numProtGen = numProtGen;
	}

	public String getAnnoProtGen() {
		return annoProtGen;
	}

	public void setAnnoProtGen(String annoProtGen) {
		this.annoProtGen = annoProtGen;
	}

	public String getNumProtSett() {
		return numProtSett;
	}

	public void setNumProtSett(String numProtSett) {
		this.numProtSett = numProtSett;
	}

	public String getTipoAtto() {
		return tipoAtto;
	}

	public void setTipoAtto(String tipoAtto) {
		this.tipoAtto = tipoAtto;
	}

	public String getNumeroAtto() {
		return numeroAtto;
	}

	public void setNumeroAtto(String numeroAtto) {
		this.numeroAtto = numeroAtto;
	}

	public Date getDataDoc() {
		return dataDoc;
	}

	public void setDataDoc(Date dataDoc) {
		this.dataDoc = dataDoc;
	}

	public String getAltravia() {
		return altravia;
	}

	public void setAltravia(String altravia) {
		this.altravia = altravia;
	}

	public String getAltriciv() {
		return altriciv;
	}

	public void setAltriciv(String altriciv) {
		this.altriciv = altriciv;
	}

	public BigDecimal getCivico() {
		return civico;
	}

	public void setCivico(BigDecimal civico) {
		this.civico = civico;
	}

	public String getCivicoSub() {
		return civicoSub;
	}

	public void setCivicoSub(String civicoSub) {
		this.civicoSub = civicoSub;
	}

	public String getDestinazione() {
		return destinazione;
	}

	public void setDestinazione(String destinazione) {
		this.destinazione = destinazione;
	}

	public String getNomeIntestatario() {
		return nomeIntestatario;
	}

	public void setNomeIntestatario(String nomeIntestatario) {
		this.nomeIntestatario = nomeIntestatario;
	}

	public String getPrefisso() {
		return prefisso;
	}

	public void setPrefisso(String prefisso) {
		this.prefisso = prefisso;
	}

	public String getNomeVia() {
		return nomeVia;
	}

	public void setNomeVia(String nomeVia) {
		this.nomeVia = nomeVia;
	}

	public boolean isPrivata() {
		return privata;
	}

	public void setPrivata(boolean privata) {
		this.privata = privata;
	}

	public String getRiparto() {
		return riparto;
	}

	public void setRiparto(String riparto) {
		this.riparto = riparto;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	
	
	public String getDescTipoAtto(){
		return (this.getTipoAtto()!=null ? htTipiAtti.get(this.getTipoAtto()):null);
	}
	
	public String getDescDestinazione(){
		return (this.getDestinazione()!=null ? htDestinazioni.get(this.getDestinazione()):null);
	}
	
	public String getDescTipologia(){
		return (this.getTipologia()!=null ? htTipologie.get(this.getTipologia()):null);
	}

	public String getPathFile() {
		return pathFile;
	}

	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}

	public BigDecimal getIdFile() {
		return idFile;
	}

	public void setIdFile(BigDecimal idFile) {
		this.idFile = idFile;
	}

	public String getVincoloAmbientale() {
		return vincoloAmbientale;
	}

	public void setVincoloAmbientale(String vincoloAmbientale) {
		this.vincoloAmbientale = vincoloAmbientale;
	}

}
