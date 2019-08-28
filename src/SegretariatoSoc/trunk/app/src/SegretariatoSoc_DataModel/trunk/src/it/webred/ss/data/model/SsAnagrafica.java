package it.webred.ss.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the SS_ANAGRAFICA database table.
 * 
 */
@Entity
@Table(name="SS_ANAGRAFICA")
@NamedQuery(name="SsAnagrafica.findAll", query="SELECT c FROM SsAnagrafica c")
public class SsAnagrafica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SS_ANAGRAFICA_ID_GENERATOR", sequenceName="SQ_SS_ANAGRAFICA", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SS_ANAGRAFICA_ID_GENERATOR")
	private Long id;
	
	private String nome;
	
	private String cognome;
	
	private String cf;
	
	private Date data_nascita;
		
	private String sesso;
	
	private String cittadinanza;
	
	private String stato_civile;
	
	private String comune_nascita; //NON UTILIZZARE IN SCRITTURA, SOLO IN LETTURA PER GESTIONE PREGRESSA (nel caso di informazioni complete non recuperabili)!!
	
	@Column(name="COMUNE_NASCITA_COD")
	private String comuneNascitaCod;
	
	@Column(name="COMUNE_NASCITA_DES")
	private String comuneNascitaDes;
	
	@Column(name="PROV_NASCITA_COD")
	private String provNascitaCod;
	
	@Column(name="STATO_NASCITA_COD")
	private String statoNascitaCod;
	
	@Column(name="STATO_NASCITA_DES")
	private String statoNascitaDes;
	
	@Column(name="CITTADINANZA_ACQ")
	private Long cittadinanzaAcq;
	
	@Column(name="CITTADINANZA_2")
	private String cittadinanza2;	

	public String getComuneNascitaCod() {
		return comuneNascitaCod;
	}

	public String getComuneNascitaDes() {
		return comuneNascitaDes;
	}

	public String getStatoNascitaCod() {
		return statoNascitaCod;
	}

	public String getStatoNascitaDes() {
		return statoNascitaDes;
	}

	public void setComuneNascitaCod(String comuneNascitaCod) {
		this.comuneNascitaCod = comuneNascitaCod;
	}

	public void setComuneNascitaDes(String comuneNascitaDes) {
		this.comuneNascitaDes = comuneNascitaDes;
	}

	public void setStatoNascitaCod(String statoNascitaCod) {
		this.statoNascitaCod = statoNascitaCod;
	}

	public void setStatoNascitaDes(String statoNascitaDes) {
		this.statoNascitaDes = statoNascitaDes;
	}

	public Long getCittadinanzaAcq() {
		return cittadinanzaAcq;
	}

	public void setCittadinanzaAcq(Long cittadinanzaAcq) {
		this.cittadinanzaAcq = cittadinanzaAcq;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStato_civile() {
		return stato_civile;
	}

	public void setStato_civile(String stato_civile) {
		this.stato_civile = stato_civile;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCf() {
		return cf;
	}

	public String getComune_nascita() {
		return comune_nascita;
	}

	public void setComune_nascita(String comune_nascita) {
		this.comune_nascita = comune_nascita;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public Date getData_nascita() {
		return data_nascita;
	}

	public void setData_nascita(Date data_nascita) {
		this.data_nascita = data_nascita;
	}

	public String getSesso() {
		return sesso;
	}

	public String getProvNascitaCod() {
		return provNascitaCod;
	}

	public void setProvNascitaCod(String provNascitaCod) {
		this.provNascitaCod = provNascitaCod;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public String getCittadinanza2() {
		return cittadinanza2;
	}

	public void setCittadinanza2(String cittadinanza2) {
		this.cittadinanza2 = cittadinanza2;
	}

	public String getLuogoDiNascita(){
		String s = this.comune_nascita;
		if(this.comuneNascitaDes!=null)
			s = this.comuneNascitaDes+" ("+this.provNascitaCod+")";
		else if(this.statoNascitaDes!=null)
			s = statoNascitaDes;
		return s;
	}

}
