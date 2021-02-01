package it.webred.cs.data.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CS_A_ANAGRAFICA database table.
 * 
 */
@Entity
@Table(name="CS_A_ANAGRAFICA")
@NamedQuery(name="CsAAnagrafica.findAll", query="SELECT c FROM CsAAnagrafica c")
public class CsAAnagrafica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_A_ANAGRAFICA_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_A_ANAGRAFICA_ID_GENERATOR")
	private Long id;

	private String cf;

	private String cittadinanza;

	private String cognome;
	
	@Column(name="COMUNE_NASCITA_COD")
	private String comuneNascitaCod;          //COD.ISTAT

	@Column(name="COMUNE_NASCITA_DES")
	private String comuneNascitaDes;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_DECESSO")
	private Date dataDecesso;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_NASCITA")
	private Date dataNascita;

	private String tel;
	
	private String cell;

	private String email;

	private String nome;

	private String note;

	@Column(name="PROV_NASCITA_COD")
	private String provNascitaCod;

	private String sesso;

	@Column(name="STATO_NASCITA_COD")
	private String statoNascitaCod;

	@Column(name="STATO_NASCITA_DES")
	private String statoNascitaDes;

	@Column(name="CITTADINANZA_ACQ")
	private Long cittadinanzaAcq;
	
	@Column(name="CITTADINANZA_2")
	private String cittadinanza2;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_APERTURA_FASC_FAM")
	private Date dataAperturaFascFam;
	
	//bi-directional one-to-one association to CsAComponente
	@OneToOne(mappedBy="csAAnagrafica", fetch = FetchType.LAZY)
	private CsAComponente csAComponente;
	
	//bi-directional one-to-one association to CsASoggetto
	@OneToOne(mappedBy="csAAnagrafica", fetch = FetchType.LAZY )
	@PrimaryKeyJoinColumn
	private CsASoggettoLAZY csASoggetto;
	
//	//bi-directional one-to-one association to CsAAnagraficaLog
	@OneToMany(mappedBy="csAAnagrafica", cascade=CascadeType.ALL )
	private List<CsAAnagraficaLog> csAAnagraficaLog;
	
	@Column(name="TIT_TELEFONO")
	private String titolareTelefono;
	
	@Column(name="TIT_CELLULARE")
	private String titolareCellulare;
	
	@Column(name="TIT_EMAIL")
	private String titolareEmail;
	
	@Column(name="ID_ORIG_WS")
	private String idOrigWs;
	
		
	//bi-directional many-to-one association to CsCComunita
	/*@OneToMany(mappedBy="csAAnagrafica")
	private List<CsCComunita> csCComunitas;*/

	public CsAAnagrafica() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCell() {
		return this.cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public String getCf() {
		return this.cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getCittadinanza() {
		return this.cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
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

	public Date getDataDecesso() {
		return this.dataDecesso;
	}

	public void setDataDecesso(Date dataDecesso) {
		this.dataDecesso = dataDecesso;
	}

	public Date getDataNascita() {
		return this.dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getProvNascitaCod() {
		return this.provNascitaCod;
	}

	public void setProvNascitaCod(String provNascitaCod) {
		this.provNascitaCod = provNascitaCod;
	}

	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
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

	public void setStatoNascitaDes(String statoNascitaDes) {
		this.statoNascitaDes = statoNascitaDes;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCittadinanza2() {
		return cittadinanza2;
	}

	public void setCittadinanza2(String cittadinanza2) {
		this.cittadinanza2 = cittadinanza2;
	}

	public Long getCittadinanzaAcq() {
		return cittadinanzaAcq;
	}

	public void setCittadinanzaAcq(Long cittadinanzaAcq) {
		this.cittadinanzaAcq = cittadinanzaAcq;
	}

	public CsASoggettoLAZY getCsASoggetto() {
		return csASoggetto;
	}

	public void setCsASoggetto(CsASoggettoLAZY csASoggetto) {
		this.csASoggetto = csASoggetto;
	}

	public CsAComponente getCsAComponente() {
		return csAComponente;
	}

	public void setCsAComponente(CsAComponente csAComponente) {
		this.csAComponente = csAComponente;
	}

	public Date getDataAperturaFascFam() {
		return dataAperturaFascFam;
	}

	public List<CsAAnagraficaLog> getCsAAnagraficaLog() {
		return csAAnagraficaLog;
	}

	public void setCsAAnagraficaLog(List<CsAAnagraficaLog> csAAnagraficaLog) {
		this.csAAnagraficaLog = csAAnagraficaLog;
	}

	public void setDataAperturaFascFam(Date dataAperturaFascFam) {
		this.dataAperturaFascFam = dataAperturaFascFam;
	}

	public String getTitolareTelefono() {
		return titolareTelefono;
	}

	public String getTitolareCellulare() {
		return titolareCellulare;
	}

	public String getTitolareEmail() {
		return titolareEmail;
	}

	public void setTitolareTelefono(String titolareTelefono) {
		this.titolareTelefono = titolareTelefono;
	}

	public void setTitolareCellulare(String titolareCellulare) {
		this.titolareCellulare = titolareCellulare;
	}

	public void setTitolareEmail(String titolareEmail) {
		this.titolareEmail = titolareEmail;
	}

	public String getIdOrigWs() {
		return idOrigWs;
	}

	public void setIdOrigWs(String idOrigWs) {
		this.idOrigWs = idOrigWs;
	}	
	
	public String getIdOrigWsTipo(){
		String tipo = null;
		if(!StringUtils.isBlank(idOrigWs)){
			String[] xx = idOrigWs.split("@");
			tipo = xx.length>0 ? xx[0] : null;
		}
		return tipo;
	}
	public String getIdOrigWsId(){
		String ide = null;
		if(!StringUtils.isBlank(idOrigWs)){
			String[] xx = idOrigWs.split("@");
			ide = xx.length>1 ? xx[1] : null;
		}
		return ide;
	}

	public boolean isAnonimo() {
		return  "ANONIMO".equalsIgnoreCase(this.cf) ||
				"ANONIMO".equalsIgnoreCase(this.cognome) ||
				"ANONIMO".equalsIgnoreCase(this.nome);
	}

	
}