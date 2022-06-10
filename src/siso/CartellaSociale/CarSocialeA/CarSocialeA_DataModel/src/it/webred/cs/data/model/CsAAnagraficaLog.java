package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

@Entity
@Table(name="CS_A_ANAGRAFICA_LOG")
@NamedQuery(name="CsAAnagraficaLog.findAll", query="SELECT c FROM CsAAnagraficaLog c")
public class CsAAnagraficaLog implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="CS_A_ANAGRAFICA_LOG_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_A_ANAGRAFICA_LOG_ID_GENERATOR")
	private Long id;

//	//bi-directional one-to-one association to CsAComponente
	@ManyToOne
	@JoinColumn(name = "ID_ANAGRAFICA", referencedColumnName = "id", nullable=false)
	private CsAAnagrafica csAAnagrafica;
//	
//	//bi-directional many-to-one association to CsAAnagrafica
//		@ManyToOne
//		@JoinColumn(name="ID_ANAGRAFICA")
//		private CsAAnagrafica csAAnagrafica;
	
	private String cognome;
	
	private String nome;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_NASCITA")
	private Date dataNascita;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_DECESSO")
	private Date dataDecesso;
	
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
	
	@Column(name="CITTADINANZA")
	private String cittadinanza;

	@Column(name="OPERATORE")
	private String operatore;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_MODIFICA")
	private Date dataModifica;

	@Column(name="TIPO_AZIONE")
	private String tipoAzione;
	
	public CsAAnagraficaLog() {
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

	public String getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
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

	public CsAAnagrafica getCsAAnagrafica() {
		return csAAnagrafica;
	}

	public void setCsAAnagrafica(CsAAnagrafica csAAnagrafica) {
		this.csAAnagrafica = csAAnagrafica;
	}
	
	public Date getDataDecesso() {
		return dataDecesso;
	}

	public void setDataDecesso(Date dataDecesso) {
		this.dataDecesso = dataDecesso;
	}

	public String getLuogoNascita(){
		String luogo = !StringUtils.isBlank(this.statoNascitaDes) ? statoNascitaDes : "";
		luogo+= !StringUtils.isBlank(this.comuneNascitaDes) ? comuneNascitaDes : "";
		luogo+= !StringUtils.isBlank(this.provNascitaCod) ? " ("+provNascitaCod+")" : "";
		return luogo;
	}
}
