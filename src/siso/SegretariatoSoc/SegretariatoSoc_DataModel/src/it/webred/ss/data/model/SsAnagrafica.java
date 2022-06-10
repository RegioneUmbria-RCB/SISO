package it.webred.ss.data.model;

import it.webred.ss.data.model.tb.CsTbCittadinanzaAcq;
import it.webred.ss.data.model.tb.CsTbTipoRapportoCon;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

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
	
	@Column(name="DATA_DECESSO")
	private Date dataDecesso;
		
	private String sesso;
	
	private String cittadinanza;
	
	private String stato_civile;
	
	private String comune_nascita; //NON UTILIZZARE IN SCRITTURA, SOLO IN LETTURA PER GESTIONE PREGRESSA (nel caso di informazioni complete non recuperabili)!!
	
	private String alias;
	
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
	
	@Column(name="ID_ORIG_WS")
	private String idOrigWs;

	@OneToMany(mappedBy="ssAnagrafica", cascade=CascadeType.ALL )
	private List<SsAnagraficaLog> ssAnagraficaLog;
	
	@ManyToOne 
	@JoinColumn(name="CITTADINANZA_ACQ", insertable=false, updatable=false)
	private CsTbCittadinanzaAcq tbCittadinanzaAcq;
	
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

	public String getLuogoDiNascita(){
		String s = this.comune_nascita;
		if(this.comuneNascitaDes!=null)
			s = this.comuneNascitaDes+" ("+this.provNascitaCod+")";
		else if(this.statoNascitaDes!=null)
			s = statoNascitaDes;
		return s;
	}
	//SISO-948
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public List<SsAnagraficaLog> getSsAnagraficaLog() {
		return ssAnagraficaLog;
	}

	public void setSsAnagraficaLog(List<SsAnagraficaLog> ssAnagraficaLog) {
		this.ssAnagraficaLog = ssAnagraficaLog;
	}

	public boolean isAnonimo() {
		return  "ANONIMO".equalsIgnoreCase(this.cf) ||
				"ANONIMO".equalsIgnoreCase(this.cognome) ||
				"ANONIMO".equalsIgnoreCase(this.nome);
	}

	public CsTbCittadinanzaAcq getTbCittadinanzaAcq() {
		return tbCittadinanzaAcq;
	}

	public void setTbCittadinanzaAcq(CsTbCittadinanzaAcq tbCittadinanzaAcq) {
		this.tbCittadinanzaAcq = tbCittadinanzaAcq;
	}

	public Date getDataDecesso() {
		return dataDecesso;
	}

	public void setDataDecesso(Date dataDecesso) {
		this.dataDecesso = dataDecesso;
	}

}
