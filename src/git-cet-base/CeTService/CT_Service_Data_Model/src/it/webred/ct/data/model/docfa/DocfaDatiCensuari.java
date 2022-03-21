package it.webred.ct.data.model.docfa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the DOCFA_DATI_CENSUARI database table.
 * 
 */

@Entity
@Table(name="DOCFA_DATI_CENSUARI")
public class DocfaDatiCensuari implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DocfaDatiCensuariPK id;
	
	private String categoria;

	@Column(name="CIVICO_1")
	private String civico1;

	@Column(name="CIVICO_2")
	private String civico2;

	@Column(name="CIVICO_3")
	private String civico3;

	private String classe;

	@Column(name="CODICE_AMMINSTRATIVO")
	private String codiceAmminstrativo;

	private String consistenza;

	private String denominatore;

	private String edificabilita;

	private String edificio;

	private String foglio;

	private String indirizzo;

	@Column(name="INTERNO_1")
	private String interno1;

	@Column(name="INTERNO_2")
	private String interno2;

	private String lotto;

	private String numero;

	@Column(name="PARTITA_SPECIALE")
	private String partitaSpeciale;

	@Column(name="PIANO_1")
	private String piano1;

	@Column(name="PIANO_2")
	private String piano2;

	@Column(name="PIANO_3")
	private String piano3;

	@Column(name="PIANO_4")
	private String piano4;

	@Column(name="PRESENZA_GRAFFATI")
	private String presenzaGraffati;

	@Column(name="RENDITA_EURO")
	private String renditaEuro;

	private String scala;

	private String sezione;

	@Column(name="SEZIONE_URBANA")
	private String sezioneUrbana;

	private String subalterno;

	private String superficie;

	private String toponimo;

	@Column(name="ULTERIORI_INDIRIZZI")
	private String ulterioriIndirizzi;

	private String zona;

    public DocfaDatiCensuari() {
    }

	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getCivico1() {
		String s = this.civico1;
		if (s != null) {
			//s = s.trim();
			while (s.length() > 1 && s.charAt(0) == '0')
				s = s.substring(1);
				
		}
		return s;
	}

	public void setCivico1(String civico1) {
		this.civico1 = civico1;
	}

	public String getCivico2() {
		String s = this.civico2;
		if (s != null) {
			//s = s.trim();
			while (s.length() > 1 && s.charAt(0) == '0')
				s = s.substring(1);
				
		}
		return s;
	}

	public void setCivico2(String civico2) {
		this.civico2 = civico2;
	}

	public String getCivico3() {
		String s = this.civico3;
		if (s != null) {
			//s = s.trim();
			while (s.length() > 1 && s.charAt(0) == '0')
				s = s.substring(1);
				
		}
		return s;
	
	}

	public void setCivico3(String civico3) {
		this.civico3 = civico3;
	}

	public String getClasse() {
		return this.classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getCodiceAmminstrativo() {
		return this.codiceAmminstrativo;
	}

	public void setCodiceAmminstrativo(String codiceAmminstrativo) {
		this.codiceAmminstrativo = codiceAmminstrativo;
	}

	public String getConsistenza() {
		return this.consistenza;
	}

	public void setConsistenza(String consistenza) {
		this.consistenza = consistenza;
	}

	public String getDataRegistrazione() {
		return this.id.getDataRegistrazione();
	}

	public void setDataRegistrazione(String dataRegistrazione) {
		this.id.setDataRegistrazione(dataRegistrazione);
	}

	public String getDenominatore() {
		return this.denominatore;
	}

	public void setDenominatore(String denominatore) {
		this.denominatore = denominatore;
	}

	public String getEdificabilita() {
		return this.edificabilita;
	}

	public void setEdificabilita(String edificabilita) {
		this.edificabilita = edificabilita;
	}

	public String getEdificio() {
		return this.edificio;
	}

	public void setEdificio(String edificio) {
		this.edificio = edificio;
	}

	public String getFoglio() {
		return this.foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public Date getFornitura() {
		return this.id.getFornitura();
	}

	public void setFornitura(Date fornitura) {
		this.id.setFornitura(fornitura);
	}

	public String getIdentificativoImmobile() {
		return this.getIdentificativoImmobile();
	}

	public void setIdentificativoImmobile(String identificativoImmobile) {
		this.id.setIdentificativoImmobile(identificativoImmobile);
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getInterno1() {
		return this.interno1;
	}

	public void setInterno1(String interno1) {
		this.interno1 = interno1;
	}

	public String getInterno2() {
		return this.interno2;
	}

	public void setInterno2(String interno2) {
		this.interno2 = interno2;
	}

	public String getLotto() {
		return this.lotto;
	}

	public void setLotto(String lotto) {
		this.lotto = lotto;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPartitaSpeciale() {
		return this.partitaSpeciale;
	}

	public void setPartitaSpeciale(String partitaSpeciale) {
		this.partitaSpeciale = partitaSpeciale;
	}

	public String getPiano1() {
		return this.piano1;
	}

	public void setPiano1(String piano1) {
		this.piano1 = piano1;
	}

	public String getPiano2() {
		return this.piano2;
	}

	public void setPiano2(String piano2) {
		this.piano2 = piano2;
	}

	public String getPiano3() {
		return this.piano3;
	}

	public void setPiano3(String piano3) {
		this.piano3 = piano3;
	}

	public String getPiano4() {
		return this.piano4;
	}

	public void setPiano4(String piano4) {
		this.piano4 = piano4;
	}

	public String getPresenzaGraffati() {
		return this.presenzaGraffati;
	}

	public void setPresenzaGraffati(String presenzaGraffati) {
		this.presenzaGraffati = presenzaGraffati;
	}

	public String getProtocolloRegistrazione() {
		return this.id.getProtocolloRegistrazione();
	}

	public void setProtocolloRegistrazione(String protocolloRegistrazione) {
		this.id.setProtocolloRegistrazione(protocolloRegistrazione);
	}

	public String getRenditaEuro() {
		return this.renditaEuro;
	}

	public void setRenditaEuro(String renditaEuro) {
		this.renditaEuro = renditaEuro;
	}

	public String getScala() {
		return this.scala;
	}

	public void setScala(String scala) {
		this.scala = scala;
	}

	public String getSezione() {
		return this.sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public String getSezioneUrbana() {
		return this.sezioneUrbana;
	}

	public void setSezioneUrbana(String sezioneUrbana) {
		this.sezioneUrbana = sezioneUrbana;
	}

	public String getSubalterno() {
		return this.subalterno;
	}

	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}

	public String getSuperficie() {
		return this.superficie;
	}

	public void setSuperficie(String superficie) {
		this.superficie = superficie;
	}

	public String getToponimo() {
		return this.toponimo;
	}

	public void setToponimo(String toponimo) {
		this.toponimo = toponimo;
	}

	public String getUlterioriIndirizzi() {
		return this.ulterioriIndirizzi;
	}

	public void setUlterioriIndirizzi(String ulterioriIndirizzi) {
		this.ulterioriIndirizzi = ulterioriIndirizzi;
	}

	public String getZona() {
		return this.zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public void setId(DocfaDatiCensuariPK id) {
		this.id = id;
	}

	public DocfaDatiCensuariPK getId() {
		return id;
	}

}