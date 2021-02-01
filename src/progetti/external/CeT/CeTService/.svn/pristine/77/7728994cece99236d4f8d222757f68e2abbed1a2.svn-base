package it.webred.ct.data.model.successioni;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="SUCCESSIONI_A")
public class SuccessioniA implements Serializable{

	private static final long serialVersionUID = 575081418675088263L;

//	@Id
//	@IndiceKey(pos="1")
//	private String ufficio = "";
//
//	@Id
//	@IndiceKey(pos="2")
//	private String anno = "";
//	
//	@Id
//	@IndiceKey(pos="3")
//	private BigDecimal volume = null;
//
//	@Id
//	@IndiceKey(pos="4")
//	private BigDecimal numero = null;
//	
//	@Id
//	@IndiceKey(pos="5")
//	private BigDecimal sottonumero = null;
//	
//	@Id
//	@IndiceKey(pos="6")
//	private String comune = "";
//	
//	@Id
//	@IndiceKey(pos="7")
//	private BigDecimal progressivo = null;

	@EmbeddedId
	private SuccessioniPK id = null;
	
	@Column(name="TIPO_DICHIARAZIONE")
	private String tipoDichiarazione = "";
	
	@Column(name="DATA_APERTURA")
	private String dataApertura = "";
	
	@Column(name="CF_DEFUNTO")
	private String cfDefunto = "";
	
	@Column(name="COGNOME_DEFUNTO")
	private String cognomeDefunto = "";
	
	@Column(name="NOME_DEFUNTO")
	private String nomeDefunto = "";
	
	@Column(name="SESSO")
	private String sesso = "";
	
	@Column(name="CITTA_NASCITA")
	private String cittaNascita = "";
	
	@Column(name="PROV_NASCITA")
	private String provNascita = "";
	
	@Column(name="DATA_NASCITA")
	private String dataNascita = "";
	
	@Column(name="CITTA_RESIDENZA")
	private String cittaResidenza = "";
	
	@Column(name="PROV_RESIDENZA")
	private String provResidenza = "";
	
	@Column(name="INDIRIZZO_RESIDENZA")
	private String indirizzoResidenza = "";
	
	@Column(name="TIPO_RECORD")
	private String tipoRecord = "";
	

	     
	public SuccessioniA() {
	}//-------------------------------------------------------------------------

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public SuccessioniPK getId() {
		return id;
	}

	public void setId(SuccessioniPK id) {
		this.id = id;
	}

	public String getTipoDichiarazione() {
		return tipoDichiarazione;
	}

	public void setTipoDichiarazione(String tipoDichiarazione) {
		this.tipoDichiarazione = tipoDichiarazione;
	}

	public String getDataApertura() {
		return dataApertura;
	}

	public void setDataApertura(String dataApertura) {
		this.dataApertura = dataApertura;
	}

	public String getCfDefunto() {
		return cfDefunto;
	}

	public void setCfDefunto(String cfDefunto) {
		this.cfDefunto = cfDefunto;
	}

	public String getCognomeDefutno() {
		return cognomeDefunto;
	}

	public void setCognomeDefutno(String cognomeDefutno) {
		this.cognomeDefunto = cognomeDefutno;
	}

	public String getNomeDefunto() {
		return nomeDefunto;
	}

	public void setNomeDefunto(String nomeDefunto) {
		this.nomeDefunto = nomeDefunto;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getCittaNascita() {
		return cittaNascita;
	}

	public void setCittaNascita(String cittaNascita) {
		this.cittaNascita = cittaNascita;
	}

	public String getProvNascita() {
		return provNascita;
	}

	public void setProvNascita(String provNascita) {
		this.provNascita = provNascita;
	}

	public String getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getCittaResidenza() {
		return cittaResidenza;
	}

	public void setCittaResidenza(String cittaResidenza) {
		this.cittaResidenza = cittaResidenza;
	}

	public String getProvResidenza() {
		return provResidenza;
	}

	public void setProvResidenza(String provResidenza) {
		this.provResidenza = provResidenza;
	}

	public String getIndirizzoResidenza() {
		return indirizzoResidenza;
	}

	public void setIndirizzoResidenza(String indirizzoResidenza) {
		this.indirizzoResidenza = indirizzoResidenza;
	}

	public String getTipoRecord() {
		return tipoRecord;
	}

	public void setTipoRecord(String tipoRecord) {
		this.tipoRecord = tipoRecord;
	}

	

}
