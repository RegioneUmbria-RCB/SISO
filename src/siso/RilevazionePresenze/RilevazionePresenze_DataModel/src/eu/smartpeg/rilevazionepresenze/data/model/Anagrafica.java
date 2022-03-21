package eu.smartpeg.rilevazionepresenze.data.model;

import java.io.Serializable;
import javax.persistence.*;

import it.webred.ct.config.model.AmTabComuni;

import java.util.Set;
import java.util.Date;
import java.util.HashSet;

/**
 * The persistent class for the AR_RP_ANAGRAFICA database table.
 * 
 */
@Entity
@Table(name="AR_RP_ANAGRAFICA")
@NamedQueries(value={
		@NamedQuery(name="Anagrafica.findAll", query="SELECT a FROM Anagrafica a ORDER BY a.cognome, a.nome"),
		 @NamedQuery(
			      name = "Anagrafica.deleteById",
			      query="DELETE FROM Anagrafica s where s.id= :id"),
	    @NamedQuery(
	      name = "Anagrafica.findReferenti",
	      query="SELECT a FROM Anagrafica a WHERE a.flgReferente = true ORDER BY a.cognome, a.nome"),
	    @NamedQuery(
	  	      name = "Anagrafica.findReferenteById",
	  	      query="SELECT a FROM Anagrafica a WHERE a.flgReferente = true and a.id = :idReferente"),
	    @NamedQuery(
			      name = "Anagrafica.findAppartenentiNucleo",
			      query="SELECT a FROM Anagrafica a WHERE a.idReferente = :idReferente"),
	    @NamedQuery(
			      name = "Anagrafica.findAnagraficaById",
			      query="SELECT a FROM Anagrafica a WHERE a.id = :id"),
	    @NamedQuery(
			      name = "Anagrafica.findAnagraficaByCf",
			      query="SELECT a FROM Anagrafica a WHERE a.cf = :cf")
		
	})

public class Anagrafica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="AR_RP_ANAGRAFICA_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AR_RP_ANAGRAFICA_ID_GENERATOR")
	private Long id;

	private String cf;

	private String cittadinanza;

	private String cognome;

	@Column(name="COMUNE_NASCITA_COD")
	private String comuneNascitaCod;

	@Column(name="COMUNE_NASCITA_DES")
	private String comuneNascitaDes;
	
	@Column(name="PROV_NASCITA_COD")
	private String provNascitaCod;
	
	@Column(name="INDIRIZZO_RESIDENZA")
	private String indirizzoResidenza;
	
	@Column(name="COMUNE_RESIDENZA_COD")
	private String comuneResidenzaCod;

	@Column(name="COMUNE_RESIDENZA_DES")
	private String comuneResidenzaDes;

	@Column(name="PROV_RESIDENZA_COD")
	private String provResidenzaCod;
	
	@Column(name="ID_MUNICIPIO_RES")
	private Long idMunicipioRes;

	@Column(name="INDIRIZZO_DOMICILIO")
	private String indirizzoDomicilio;
	
	@Column(name="COMUNE_DOMICILIO_COD")
	private String comuneDomicilioCod;

	@Column(name="COMUNE_DOMICILIO_DES")
	private String comuneDomicilioDes;

	@Column(name="PROV_DOMICILIO_COD")
	private String provDomicilioCod;
	
	@Column(name="ID_MUNICIPIO_DOM")
	private Long idMunicipioDom;
	
	@Column(name="DATA_NASCITA")
	private Date dataNascita;
	
	@Column(name="DATA_INSEDIAMENTO")
	private Date dataInsediamento;
	
	@Column(name="DATA_USCITA")
	private Date dataUscita;
	
	@Column(name="STATO_TIT_CONSEGUITO_DES")
	private String statoTitConseguitoDes;

	@Column(name="STATO_TIT_CONSEGUITO_COD")
	private Long statoTitConseguitoCod;
	
	@Column(name="DT_INS")
	private Date dtIns;

	@Column(name="DT_MOD")
	private Date dtMod;

	@Column(name="FLG_REFERENTE")
	private Boolean flgReferente;

	@Column(name="ID_PARENTELA")
	private Long idParentela;
	
	private String genere;

	@Column(name="ID_AREA_STRUTTURA")
	private Long idAreaStruttura;

	@Column(name="ID_CONDIZIONE_LAVORATIVA")
	private Long idCondizioneLavorativa;

	@Column(name="ID_ORDINE_SCUOLA")
	private Long idOrdineScuola;

	@Column(name="ID_REFERENTE")
	private Long idReferente;

//	@Column(name="ID_STRUTTURA")
//	private Long idStruttura;

	@Column(name="ID_TITOLO_STUDIO")
	private Long idTitoloStudio;

	@Column(name="ID_VULNERABILITA")
	private Long idVulnerabilita;

	private String nome;

	private String note;

	@Column(name="STATO_NASCITA_COD")
	private String statoNascitaCod;

	@Column(name="STATO_NASCITA_DES")
	private String statoNascitaDes;

	@Column(name="UNITA_ABITATIVA")
	private String unitaAbitativa;

	@Column(name="USR_INS")
	private String usrIns;

	@Column(name="USR_MOD")
	private String usrMod;
	
	@Column(name="ID_MOTIVAZIONE")
	private Long idMotivazione;

	//bi-directional many-to-one association to DocumentiAnag
	@OneToMany(fetch = FetchType.EAGER, mappedBy="arRpAnagrafica", cascade = CascadeType.ALL)	
	private Set<DocumentiAnag> arRpDocumentiAnags;
		
	public Anagrafica() {
		this.Struttura = new Struttura();
        this.arRpDocumentiAnags = new HashSet<>();
        this.flgReferente= false;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	
	public String getComuneResidenzaDes() {
		return this.comuneResidenzaDes;
	}

	public void setComuneResidenzaDes(String comuneResidenzaDes) {
		this.comuneResidenzaDes = comuneResidenzaDes;
	}

	public Date getDataNascita() {
		return this.dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Object getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public Object getDtMod() {
		return this.dtMod;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	public Boolean getFlgReferente() {
		return this.flgReferente;
	}

	public void setFlgReferente(Boolean flgReferente) {
		this.flgReferente = flgReferente;
	}

	public String getGenere() {
		return this.genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	//bi-directional many-to-one association to ArRpStruttura
		@ManyToOne
		@JoinColumn(name="ID_STRUTTURA")
		private Struttura Struttura;


	public Struttura getStruttura() {
			return Struttura;
		}

		public void setStruttura(Struttura struttura) {
			Struttura = struttura;
		}

	public Long getIdAreaStruttura() {
		return this.idAreaStruttura;
	}

	public void setIdAreaStruttura(Long idAreaStruttura) {
		this.idAreaStruttura = idAreaStruttura;
	}

	public Long getIdCondizioneLavorativa() {
		return this.idCondizioneLavorativa;
	}

	public void setIdCondizioneLavorativa(Long idCondizioneLavorativa) {
		this.idCondizioneLavorativa = idCondizioneLavorativa;
	}

	public Long getIdOrdineScuola() {
		return this.idOrdineScuola;
	}

	public void setIdOrdineScuola(Long idOrdineScuola) {
		this.idOrdineScuola = idOrdineScuola;
	}

	public Long getIdReferente() {
		return this.idReferente;
	}

	public void setIdReferente(Long idReferente) {
		this.idReferente = idReferente;
	}

	public Long getIdTitoloStudio() {
		return this.idTitoloStudio;
	}

	public void setIdTitoloStudio(Long idTitoloStudio) {
		this.idTitoloStudio = idTitoloStudio;
	}

	public Long getIdVulnerabilita() {
		return this.idVulnerabilita;
	}

	public void setIdVulnerabilita(Long idVulnerabilita) {
		this.idVulnerabilita = idVulnerabilita;
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

	public String getProvResidenzaCod() {
		return this.provResidenzaCod;
	}

	public void setProvResidenzaCod(String provResidenzaCod) {
		this.provResidenzaCod = provResidenzaCod;
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

	public String getUnitaAbitativa() {
		return this.unitaAbitativa;
	}

	public void setUnitaAbitativa(String unitaAbitativa) {
		this.unitaAbitativa = unitaAbitativa;
	}

	public String getUsrIns() {
		return this.usrIns;
	}

	public void setUsrIns(String usrIns) {
		this.usrIns = usrIns;
	}

	public String getUsrMod() {
		return this.usrMod;
	}

	public void setUsrMod(String usrMod) {
		this.usrMod = usrMod;
	}
	

	public Long getIdParentela() {
		return idParentela;
	}

	public void setIdParentela(Long idParentela) {
		this.idParentela = idParentela;
	}

	
	public String getComuneResidenzaCod() {
		return comuneResidenzaCod;
	}

	public void setComuneResidenzaCod(String comuneResidenzaCod) {
		this.comuneResidenzaCod = comuneResidenzaCod;
	}

	public String getComuneDomicilioDes() {
		return comuneDomicilioDes;
	}

	public void setComuneDomicilioDes(String comuneDomicilioDes) {
		this.comuneDomicilioDes = comuneDomicilioDes;
	}

	public String getProvDomicilioCod() {
		return provDomicilioCod;
	}

	public void setProvDomicilioCod(String provDomicilioCod) {
		this.provDomicilioCod = provDomicilioCod;
	}

	public Long getIdMunicipioRes() {
		return idMunicipioRes;
	}

	public void setIdMunicipioRes(Long idMunicipioRes) {
		this.idMunicipioRes = idMunicipioRes;
	}

	public String getComuneDomicilioCod() {
		return comuneDomicilioCod;
	}

	public void setComuneDomicilioCod(String comuneDomicilioCod) {
		this.comuneDomicilioCod = comuneDomicilioCod;
	}

	public Long getIdMunicipioDom() {
		return idMunicipioDom;
	}

	public void setIdMunicipioDom(Long idMunicipioDom) {
		this.idMunicipioDom = idMunicipioDom;
	}

	public Date getDataInsediamento() {
		return dataInsediamento;
	}

	public void setDataInsediamento(Date dataInsediamento) {
		this.dataInsediamento = dataInsediamento;
	}

	public Date getDataUscita() {
		return dataUscita;
	}

	public void setDataUscita(Date dataUscita) {
		this.dataUscita = dataUscita;
	}

	public String getStatoTitConseguitoDes() {
		return statoTitConseguitoDes;
	}

	public void setStatoTitConseguitoDes(String statoTitConseguitoDes) {
		this.statoTitConseguitoDes = statoTitConseguitoDes;
	}

	public Long getStatoTitConseguitoCod() {
		return statoTitConseguitoCod;
	}

	public void setStatoTitConseguitoCod(Long statoTitConseguitoCod) {
		this.statoTitConseguitoCod = statoTitConseguitoCod;
	}
	
	public String getIndirizzoResidenza() {
		return indirizzoResidenza;
	}

	public void setIndirizzoResidenza(String indirizzoResidenza) {
		this.indirizzoResidenza = indirizzoResidenza;
	}

	public String getIndirizzoDomicilio() {
		return indirizzoDomicilio;
	}

	public void setIndirizzoDomicilio(String indirizzoDomicilio) {
		this.indirizzoDomicilio = indirizzoDomicilio;
	}
	
	public Long getIdMotivazione() {
		return idMotivazione;
	}

	public void setIdMotivazione(Long idMotivazione) {
		if (idMotivazione == 0) {
			this.idMotivazione = null;
		} else {
			this.idMotivazione = idMotivazione;
		}
	}
	
	public Set<DocumentiAnag> getArRpDocumentiAnags() {
		return this.arRpDocumentiAnags;
	}

	public DocumentiAnag addArRpDocumentiAnag(DocumentiAnag arRpDocumentiAnag) {
		getArRpDocumentiAnags().add(arRpDocumentiAnag);
		arRpDocumentiAnag.setArRpAnagrafica(this);

		return arRpDocumentiAnag;
	}

	public DocumentiAnag removeArRpDocumentiAnag(DocumentiAnag arRpDocumentiAnag) {
		getArRpDocumentiAnags().remove(arRpDocumentiAnag);
		arRpDocumentiAnag.setArRpAnagrafica(null);

		return arRpDocumentiAnag;
	}
	
	public String getCognomeNome() {
		return this.getCognome() +" "+ this.getNome();
	}

	public void setComuneDomicilio(AmTabComuni comune) {
		if(comune==null) {
			setComuneDomicilioCod(null);
			setComuneDomicilioDes(null);
			setProvDomicilioCod(null);
		}else {
			setComuneDomicilioCod(comune.getCodIstatComune());
			setComuneDomicilioDes(comune.getDenominazione());
			setProvDomicilioCod(comune.getSiglaProv());
		}		
	}
	
	public void setComuneResidenza(AmTabComuni comune) {
		if(comune==null) {
			setComuneResidenzaCod(null);
			setComuneResidenzaDes(null);
			setProvResidenzaCod(null);
		}else {
			setComuneResidenzaCod(comune.getCodIstatComune());
			setComuneResidenzaDes(comune.getDenominazione());
			setProvResidenzaCod(comune.getSiglaProv());
		}		
	}
	
	public void setComuneNascita(AmTabComuni comune) {
		if(comune==null) {
			setComuneNascitaCod(null);
			setComuneNascitaDes(null);
			setProvNascitaCod(null);
		}else {
			setComuneNascitaCod(comune.getCodIstatComune());
			setComuneNascitaDes(comune.getDenominazione());
			setProvNascitaCod(comune.getSiglaProv());
		}		
	}

	@Override
	public int hashCode() {
		//TODO: riscrivere se esiste un @NaturalID
		return 31;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Anagrafica other = (Anagrafica) obj;
		return id == other.id;
	}

	public String getNomeVillaggio() {
		String nomeVillaggio= getStruttura()!=null?getStruttura().getNomeStruttura():"";
		return nomeVillaggio;
	}	
	
}