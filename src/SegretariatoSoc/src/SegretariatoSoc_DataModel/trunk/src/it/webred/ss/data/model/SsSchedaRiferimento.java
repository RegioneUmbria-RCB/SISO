package it.webred.ss.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the SS_SCHEDA_rIFERIMENTO database table.
 * 
 */
@Entity
@Table(name="SS_SCHEDA_RIFERIMENTO")
@NamedQuery(name="SsSchedaRiferimento.findAll", query="SELECT c FROM SsSchedaRiferimento c")
public class SsSchedaRiferimento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SS_SCHEDA_RIFERIMENTO_ID_GENERATOR", sequenceName="SQ_SS_SCHEDA_RIFERIMENTO", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SS_SCHEDA_RIFERIMENTO_ID_GENERATOR")
	private Long id;
	
	private String nome;
	
	private String cognome;
	
	private Boolean problemi;
	
	private String problemi_desc;
	
    private String telefono;
	
	private String recapito;
	
	private String cel;
	
	private String email;
	
	@Column(name="DATA_NASCITA")
	private Date dataNascita;
	
	private String sesso;
	
	@Column(name="COD_STATO_CIVILE")
	private String codStatoCivile;
	
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
	
	@Column(name="RELAZIONE_ID")
	private Long relazioneId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Boolean getProblemi() {
		return problemi;
	}

	public void setProblemi(Boolean problemi) {
		this.problemi = problemi;
	}

	public String getProblemi_desc() {
		return problemi_desc;
	}

	public void setProblemi_desc(String problemi_desc) {
		this.problemi_desc = problemi_desc;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getRecapito() {
		return recapito;
	}

	public void setRecapito(String recapito) {
		this.recapito = recapito;
	}
	
	public String getCel() {
		return cel;
	}

	public void setCel(String cel) {
		this.cel = cel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getCodStatoCivile() {
		return codStatoCivile;
	}

	public void setCodStatoCivile(String codStatoCivile) {
		this.codStatoCivile = codStatoCivile;
	}

	public String getComuneNascitaCod() {
		return comuneNascitaCod;
	}

	public void setComuneNascitaCod(String comuneNascitaCod) {
		this.comuneNascitaCod = comuneNascitaCod;
	}

	public String getComuneNascitaDes() {
		return comuneNascitaDes;
	}

	public void setComuneNascitaDes(String comuneNascitaDes) {
		this.comuneNascitaDes = comuneNascitaDes;
	}

	public String getProvNascitaCod() {
		return provNascitaCod;
	}

	public void setProvNascitaCod(String provNascitaCod) {
		this.provNascitaCod = provNascitaCod;
	}

	public String getStatoNascitaCod() {
		return statoNascitaCod;
	}

	public void setStatoNascitaCod(String statoNascitaCod) {
		this.statoNascitaCod = statoNascitaCod;
	}

	public String getStatoNascitaDes() {
		return statoNascitaDes;
	}

	public void setStatoNascitaDes(String statoNascitaDes) {
		this.statoNascitaDes = statoNascitaDes;
	}
	
	public Long getRelazioneId() {
		return relazioneId;
	}

	public void setRelazioneId(Long relazioneId) {
		this.relazioneId = relazioneId;
	}

	public String getLuogoDiNascita(){
		String s="";
		if(this.comuneNascitaDes!=null)
			s = this.comuneNascitaDes+" ("+this.provNascitaCod+")";
		else if(this.statoNascitaDes!=null)
			s = statoNascitaDes;
		return s;
	}
	
}