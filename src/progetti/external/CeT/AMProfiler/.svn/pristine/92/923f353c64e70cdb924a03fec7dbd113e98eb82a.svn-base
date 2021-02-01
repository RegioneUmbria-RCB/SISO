package it.webred.amprofiler.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the AM_ANAGRAFICA database table.
 * 
 */
@Entity
@Table(name="AM_ANAGRAFICA")
public class AmAnagrafica implements Serializable {
	private static final long serialVersionUID = 1L;

	
			
	@Id 
	@SequenceGenerator(name="GEN_ANAGRAFICA", sequenceName="SEQ_ANAGRAFICA",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="GEN_ANAGRAFICA")
	private Integer id;

	@Column(name = "CAP_DOMICILIO")
	private String capDomicilio;

	@Column(name = "CAP_RESIDENZA")
	private String capResidenza;

	@Column(name = "CELLULARE_DOMICILIO")
	private String cellulareDomicilio;

	@Column(name = "CELLULARE_RESIDENZA")
	private String cellulareResidenza;

	private String cittadinanza;

	@Column(name = "CIVICO_DOMICILIO")
	private String civicoDomicilio;

	@Column(name = "CIVICO_RESIDENZA")
	private String civicoResidenza;

	@Column(name = "CODICE_FISCALE")
	private String codiceFiscale;

	private String cognome;

	@Column(name = "COMUNE_DOMICILIO")
	private String comuneDomicilio;

	@Column(name = "COMUNE_NASCITA")
	private String comuneNascita;

	@Column(name="COMUNE_RESIDENZA")
	private String comuneResidenza;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_NASCITA")
	private Date dataNascita;

	@Column(name = "FAX_DOMICILIO")
	private String faxDomicilio;

	@Column(name = "FAX_RESIDENZA")
	private String faxResidenza;

	@Column(name = "FK_CET_ANAGRAFICA")
	private String fkCetAnagrafica;

	@Column(name="FK_CET_ENTE")
	private String fkCetEnte;

	private String nome;

	@Column(name = "PROVINCIA_DOMICILIO")
	private String provinciaDomicilio;

	@Column(name="PROVINCIA_NASCITA")
	private String provinciaNascita;

	@Column(name="PROVINCIA_RESIDENZA")
	private String provinciaResidenza;

	private String sesso;
	
	@Column(name = "STATO_ESTERO_NASCITA")
	private String statoEsteroNascita;

	@Column(name = "TELEFONO_DOMICILIO")
	private String telefonoDomicilio;

	@Column(name = "TELEFONO_RESIDENZA")
	private String telefonoResidenza;

	@Column(name = "VIA_DOMICILIO")
	private String viaDomicilio;

	@Column(name = "VIA_RESIDENZA")
	private String viaResidenza;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_INS")
	private Date dtIns;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_UPD")
	private Date dtUpd;
    
	@Column(name="USER_INS")
	private String userIns;
	
	@Column(name="USER_UPD")
	private String userUpd;
	
	//uni-directional one-to-one association to AmUser
	@OneToOne
	@JoinColumn(name="FK_AM_USER")
	private AmUser amUser;

    public AmAnagrafica() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCittadinanza() {
		return this.cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getComuneNascita() {
		return this.comuneNascita;
	}

	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public String getComuneResidenza() {
		return this.comuneResidenza;
	}

	public void setComuneResidenza(String comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}

	public Date getDataNascita() {
		return this.dataNascita;
	}
	
	public String getDataNascitaToString() {
		if(this.dataNascita != null){
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf.format(this.dataNascita);
		}
		return null;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getProvinciaNascita() {
		return this.provinciaNascita;
	}

	public void setProvinciaNascita(String provinciaNascita) {
		this.provinciaNascita = provinciaNascita;
	}

	public String getProvinciaResidenza() {
		return this.provinciaResidenza;
	}

	public void setProvinciaResidenza(String provinciaResidenza) {
		this.provinciaResidenza = provinciaResidenza;
	}

	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public Date getDtIns() {
		return dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public Date getDtUpd() {
		return dtUpd;
	}

	public void setDtUpd(Date dtUpd) {
		this.dtUpd = dtUpd;
	}

	public String getUserIns() {
		return userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public String getUserUpd() {
		return userUpd;
	}

	public void setUserUpd(String userUpd) {
		this.userUpd = userUpd;
	}

	public AmUser getAmUser() {
		return amUser;
	}

	public void setAmUser(AmUser amUser) {
		this.amUser = amUser;
	}

	public String getFkCetAnagrafica() {
		return fkCetAnagrafica;
	}

	public void setFkCetAnagrafica(String fkCetAnagrafica) {
		this.fkCetAnagrafica = fkCetAnagrafica;
	}

	public String getFkCetEnte() {
		return fkCetEnte;
	}

	public void setFkCetEnte(String fkCetEnte) {
		this.fkCetEnte = fkCetEnte;
	}

	public String getCapDomicilio() {
		return capDomicilio;
	}

	public void setCapDomicilio(String capDomicilio) {
		this.capDomicilio = capDomicilio;
	}

	public String getCapResidenza() {
		return capResidenza;
	}

	public void setCapResidenza(String capResidenza) {
		this.capResidenza = capResidenza;
	}

	public String getCellulareDomicilio() {
		return cellulareDomicilio;
	}

	public void setCellulareDomicilio(String cellulareDomicilio) {
		this.cellulareDomicilio = cellulareDomicilio;
	}

	public String getCellulareResidenza() {
		return cellulareResidenza;
	}

	public void setCellulareResidenza(String cellulareResidenza) {
		this.cellulareResidenza = cellulareResidenza;
	}

	public String getCivicoDomicilio() {
		return civicoDomicilio;
	}

	public void setCivicoDomicilio(String civicoDomicilio) {
		this.civicoDomicilio = civicoDomicilio;
	}

	public String getCivicoResidenza() {
		return civicoResidenza;
	}

	public void setCivicoResidenza(String civicoResidenza) {
		this.civicoResidenza = civicoResidenza;
	}

	public String getComuneDomicilio() {
		return comuneDomicilio;
	}

	public void setComuneDomicilio(String comuneDomicilio) {
		this.comuneDomicilio = comuneDomicilio;
	}

	public String getFaxDomicilio() {
		return faxDomicilio;
	}

	public void setFaxDomicilio(String faxDomicilio) {
		this.faxDomicilio = faxDomicilio;
	}

	public String getFaxResidenza() {
		return faxResidenza;
	}

	public void setFaxResidenza(String faxResidenza) {
		this.faxResidenza = faxResidenza;
	}

	public String getProvinciaDomicilio() {
		return provinciaDomicilio;
	}

	public void setProvinciaDomicilio(String provinciaDomicilio) {
		this.provinciaDomicilio = provinciaDomicilio;
	}

	public String getStatoEsteroNascita() {
		return statoEsteroNascita;
	}

	public void setStatoEsteroNascita(String statoEsteroNascita) {
		this.statoEsteroNascita = statoEsteroNascita;
	}

	public String getTelefonoDomicilio() {
		return telefonoDomicilio;
	}

	public void setTelefonoDomicilio(String telefonoDomicilio) {
		this.telefonoDomicilio = telefonoDomicilio;
	}

	public String getTelefonoResidenza() {
		return telefonoResidenza;
	}

	public void setTelefonoResidenza(String telefonoResidenza) {
		this.telefonoResidenza = telefonoResidenza;
	}

	public String getViaDomicilio() {
		return viaDomicilio;
	}

	public void setViaDomicilio(String viaDomicilio) {
		this.viaDomicilio = viaDomicilio;
	}

	public String getViaResidenza() {
		return viaResidenza;
	}

	public void setViaResidenza(String viaResidenza) {
		this.viaResidenza = viaResidenza;
	}
}
