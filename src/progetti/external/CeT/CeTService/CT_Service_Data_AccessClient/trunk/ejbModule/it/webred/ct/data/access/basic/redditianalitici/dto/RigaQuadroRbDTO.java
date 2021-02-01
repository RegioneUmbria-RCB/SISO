package it.webred.ct.data.access.basic.redditianalitici.dto;

import java.io.Serializable;
import java.util.HashMap;

public class RigaQuadroRbDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private HashMap<String,String> codiciUtilizzo;
	
	private String annoImposta;
	private String canoneLoc;
	private String casiPart;
	private String codiceFiscale;
	private String comune;
	private String descComune;
	private boolean continuazione;
	private String giorni;
	private String ici;
	private String ideTelematico;
	private String imponibile;
	private String numFabb;
	private String numModulo;
	private String possesso;
	private String rendita;
	private String tipoModello;
	private String utilizzo;
	private String cedolareAq;
	
	public RigaQuadroRbDTO(){
		
		/*		
		codiciUtilizzo=new HashMap<String,String>();
		codiciUtilizzo.put("1", "Immobile utilizzato come abitazione principale");
		codiciUtilizzo.put("2", "immobile, ad uso abitativo, tenuto a disposizione, dunque posseduto in aggiunta a quello adibito ad abitazione principale del possessore o dei suoi familiari (coniuge,parenti entro il terzo grado ed affini entro il secondo grado)");
		codiciUtilizzo.put("3", "Immobile  locato in regime di libero mercato o \'patti in deroga\'");
		codiciUtilizzo.put("4", "Immobile locato in regime legale di determinazione del canone o \'equo canone\'");
		codiciUtilizzo.put("5", "Pertinenza abitazione principale (box, cantina, ecc.) dotata di rendita catastale autonoma.");
		codiciUtilizzo.put("8", "Unit immobiliare situata in un comune ad alta densità abitativa, concessa in locazione");
		codiciUtilizzo.put("9", "Altro");	
		codiciUtilizzo.put("10", "Abitazione o pertinenza data in uso gratuito a un proprio familiare a condizione che lo stesso vi dimori abitualmente e ciò risulti dall\'iscrizione anagrafica," +
								 "ovvero unit in compropriet utilizzata integralmente come abitazione principale di uno o più comproprietari diversi dal dichiarante");	
		codiciUtilizzo.put("11", "Pertinenza di immobile tenuto a disposizione");
		codiciUtilizzo.put("12", "Unit  immobiliare tenuta a disposizione in Italia da contribuenti residenti all\'estero o già utilizzata come abitazione principale " +
								 "(o pertinenza di abitazione principale) da contribuenti trasferiti temporaneamente in altro comune");
		codiciUtilizzo.put("13", "Bene di propriet condominiale (locali per la portineria, l\'alloggio del portiere, autorimesse collettive, ecc) " +
								 "dichiarato dal singolo condomino se la quota di reddito spettante superiore alla soglia prevista dalla normativa vigente");
 		*/
		
	}
	
	public String getAnnoImposta() {
		return annoImposta;
	}

	public void setAnnoImposta(String annoImposta) {
		this.annoImposta = annoImposta;
	}

	public String getCanoneLoc() {
		return canoneLoc;
	}

	public void setCanoneLoc(String canoneLoc) {
		this.canoneLoc = canoneLoc;
	}

	public String getCasiPart() {
		return casiPart;
	}

	public void setCasiPart(String casiPart) {
		this.casiPart = casiPart;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public boolean isContinuazione() {
		return continuazione;
	}

	public void setContinuazione(boolean continuazione) {
		this.continuazione = continuazione;
	}

	public String getGiorni() {
		return giorni;
	}

	public void setGiorni(String giorni) {
		this.giorni = giorni;
	}

	public String getIci() {
		return ici;
	}

	public void setIci(String ici) {
		this.ici = ici;
	}

	public String getIdeTelematico() {
		return ideTelematico;
	}

	public void setIdeTelematico(String ideTelematico) {
		this.ideTelematico = ideTelematico;
	}

	public String getImponibile() {
		return imponibile;
	}

	public void setImponibile(String imponibile) {
		this.imponibile = imponibile;
	}

	public String getNumFabb() {
		return numFabb;
	}

	public void setNumFabb(String numFabb) {
		this.numFabb = numFabb;
	}

	public String getPossesso() {
		return possesso;
	}

	public void setPossesso(String possesso) {
		this.possesso = possesso;
	}

	public String getRendita() {
		return rendita;
	}

	public void setRendita(String rendita) {
		this.rendita = rendita;
	}

	public String getTipoModello() {
		return tipoModello;
	}

	public void setTipoModello(String tipoModello) {
		this.tipoModello = tipoModello;
	}

	public String getUtilizzo() {
		return utilizzo;
	}

	public void setUtilizzo(String utilizzo) {
		this.utilizzo = utilizzo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getDescUtilizzo(){
		return codiciUtilizzo.get(utilizzo);
	}

	public String getDescComune() {
		return descComune;
	}

	public void setDescComune(String descComune) {
		this.descComune = descComune;
	}

	public String getNumModulo() {
		return numModulo;
	}

	public void setNumModulo(String numModulo) {
		this.numModulo = numModulo;
	}

	public String getCedolareAq() {
		return cedolareAq;
	}

	public void setCedolareAq(String cedolareAq) {
		this.cedolareAq = cedolareAq;
	}
	
}
