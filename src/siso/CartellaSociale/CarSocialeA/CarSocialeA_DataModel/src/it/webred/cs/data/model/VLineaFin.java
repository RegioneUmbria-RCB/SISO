package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="V_LINEAFIN")
@NamedQuery(name="VLineaFin.findAll", query="SELECT a FROM VLineaFin a")
public class VLineaFin implements Serializable{	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="V_LINEAFIN_ID_GENERATOR", sequenceName="SQ_ID_ARGO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="V_LINEAFIN_ID_GENERATOR")
	private Long id;
	
	@Column(name="CODICE_MEMO")
	private String codiceMemo;
	
	private String descrizione;
	
	@Column(name="USER_INS")
	private String userIns;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_INS")
	private Date dtIns;
	
	@Column(name="USR_MOD")
	private String usrMod;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_MOD") 
	private Date dtMod;
	
	private Boolean abilitato;		
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INIZIO_VAL")
	private Date dtInizioVal;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE_VAL")
	private Date dtFineVal;
	
	@Column(name="FONDO_ID") 
	private int fondoId;
	
	@Column(name="PROGETTO_ID") 
	private Integer progettoId;
		
	private Long importo;
	
	private String fondo;
	
	private String progetto;
	
	@Column(name="BELFIORE")
	private String belfiore;

	
	public String getBelfiore() {
		return belfiore;
	}

	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}

	public VLineaFin(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodiceMemo() {
		return codiceMemo;
	}

	public void setCodiceMemo(String codiceMemo) {
		this.codiceMemo = codiceMemo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getUserIns() {
		return userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public Date getDtIns() {
		return dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	
	public String getUsrMod() {
		return usrMod;
	}

	public void setUsrMod(String usrMod) {
		this.usrMod = usrMod;
	}

	public Date getDtMod() {
		return dtMod;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	public Boolean getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}

	public Date getDtInizioVal() {
		return dtInizioVal;
	}

	public void setDtInizioVal(Date dtInizioVal) {
		this.dtInizioVal = dtInizioVal;
	}

	public Date getDtFineVal() {
		return dtFineVal;
	}

	public void setDtFineVal(Date dtFineVal) {
		this.dtFineVal = dtFineVal;
	}

	public int getFondoId() {
		return fondoId;
	}

	public void setFondoId(int fondoId) {
		this.fondoId = fondoId;
	}

	public Integer getProgettoId() {
		return progettoId;
	}

	public void setProgettoId(Integer progettoId) {
		this.progettoId = progettoId;
	}

	public Long getImporto() {
		return importo;
	}

	public void setImporto(Long importo) {
		this.importo = importo;
	}

	public String getFondo() {
		return fondo;
	}

	public void setFondo(String fondo) {
		this.fondo = fondo;
	}

	public String getProgetto() {
		return progetto;
	}

	public void setProgetto(String progetto) {
		this.progetto = progetto;
	}		
}
