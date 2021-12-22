package it.webred.ss.data.model;


import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * The persistent class for the SS_ANAGRAFICA_LOG database table.
 * 
 */


@Entity
@Table(name="SS_ANAGRAFICA_LOG")
@NamedQuery(name="SsAnagraficaLog.findAll", query="SELECT c FROM SsAnagraficaLog c")
public class SsAnagraficaLog implements Serializable{

     private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="SS_ANAGRAFICA_LOG_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SS_ANAGRAFICA_LOG_ID_GENERATOR")
	private Long id;


	@ManyToOne
	@JoinColumn(name="ID_ANAGRAFICA", referencedColumnName = "id", nullable=false)
	private SsAnagrafica ssAnagrafica;
	
    private String cognome;
	
	private String nome;
	
	private String alias;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_NASCITA")
	private Date dataNascita;
	
	@Column(name="PROV_NASCITA_COD")
	private String provNascitaCod; 
	
	@Column(name="COMUNE_NASCITA_COD")
	private String comuneNascitaCod;          //COD.ISTAT

	@Column(name="COMUNE_NASCITA_DES")
	private String comuneNascitaDes;

	@Column(name="STATO_NASCITA_COD")
	private String statoNascitaCod;

	@Column(name="STATO_NASCITA_DES")
	private String statoNascitaDes;
	
	private String sesso;
	
	@Column(name="OPERATORE")
	private String operatore;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_MODIFICA")
	private Date dataModifica;

	@Column(name="TIPO_AZIONE")
	private String tipoAzione;
	
	public SsAnagraficaLog() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return this.nome;
	}

	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getProvNascitaCod() {
		return this.provNascitaCod;
	}

	public void setProvNascitaCod(String provNascitaCod) {
		this.provNascitaCod = provNascitaCod;
	}
	
	public String getComuneNascitaCod() {
		return this.comuneNascitaCod;
	}

	public void setComuneNascitaCod(String comuneNascitaCod) {
		this.comuneNascitaCod = comuneNascitaCod;
	}

	public String getComuneNascitaDes() {
		return this.comuneNascitaDes;
	}

	public void setComuneNascitaDes(String comuneNascitaDes) {
		this.comuneNascitaDes = comuneNascitaDes;
	}
	public void setStatoNascitaDes(String statoNascitaDes) {
		this.statoNascitaDes = statoNascitaDes;
	}
	public String getStatoNascitaCod() {
		return this.statoNascitaCod;
	}

	public void setStatoNascitaCod(String statoNascitaCod) {
		this.statoNascitaCod = statoNascitaCod;
	}

	public String getStatoNascitaDes() {
		return this.statoNascitaDes;
	}

	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public String getOperatore() {
		return operatore;
	}

	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}

	public Date getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}

	public String getTipoAzione() {
		return tipoAzione;
	}

	public void setTipoAzione(String tipoAzione) {
		this.tipoAzione = tipoAzione;
	}
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	public SsAnagrafica getSsAnagrafica() {
		return ssAnagrafica;
	}

	public void setSsAnagrafica(SsAnagrafica ssAnagrafica) {
		this.ssAnagrafica = ssAnagrafica;
	}
	
	
}
