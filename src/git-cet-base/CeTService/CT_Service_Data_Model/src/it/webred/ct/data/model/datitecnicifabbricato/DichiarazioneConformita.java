package it.webred.ct.data.model.datitecnicifabbricato;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The persistent class for the DATI_TEC_FABBR_DIC_CONFOR database table.
 * 
 */
@Entity
@Table(name="DATI_TEC_FABBR_DIC_CONFOR")
public class DichiarazioneConformita implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Column(name="CF_PIVA_COMMITTENTE")
	private String cfPivaCommittente;

	@Column(name="CF_PIVA_ESECUTORE")
	private String cfPivaEsecutore;

	@Column(name="CF_PIVA_PROPRIETARIO")
	private String cfPivaProprietario;

	@Column(name="DENOM_COMMITTENTE")
	private String denomCommittente;

	@Column(name="DENOM_ESECUTORE")
	private String denomEsecutore;

	@Column(name="DENOM_PROPRIETARIO")
	private String denomProprietario;

	@Column(name="DES_IMPIANTO")
	private String desImpianto;

	@Column(name="DESTINAZIONE_USO")
	private String destinazioneUso;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_PROT")
	private Date dtProt;
    
    @Transient
    private String dtProtStr;

	@Column(name="NUM_PROT")
	private BigDecimal numProt;

	@Column(name="TIPO_IMPIANTO")
	private String tipoImpianto;

	@Column(name="UBIC_INTERNO")
	private String ubicInterno;

	@Column(name="UBIC_PIANO")
	private String ubicPiano;

	@Column(name="UBIC_SCALA")
	private String ubicScala;

	@Column(name="UBIC_SITICIVI_PKID_CIVI")
	private BigDecimal ubicSiticiviPkidCivi;
	
	private String particella;

	private String sezione;

	@Column(name="SUB_TUTTI")
	private String subTutti;

	private String foglio;


    public DichiarazioneConformita() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCfPivaCommittente() {
		return this.cfPivaCommittente;
	}

	public void setCfPivaCommittente(String cfPivaCommittente) {
		this.cfPivaCommittente = cfPivaCommittente;
	}

	public String getCfPivaEsecutore() {
		return this.cfPivaEsecutore;
	}

	public void setCfPivaEsecutore(String cfPivaEsecutore) {
		this.cfPivaEsecutore = cfPivaEsecutore;
	}

	public String getCfPivaProprietario() {
		return this.cfPivaProprietario;
	}

	public void setCfPivaProprietario(String cfPivaProprietario) {
		this.cfPivaProprietario = cfPivaProprietario;
	}

	public String getDenomCommittente() {
		return this.denomCommittente;
	}

	public void setDenomCommittente(String denomCommittente) {
		this.denomCommittente = denomCommittente;
	}

	public String getDenomEsecutore() {
		return this.denomEsecutore;
	}

	public void setDenomEsecutore(String denomEsecutore) {
		this.denomEsecutore = denomEsecutore;
	}

	public String getDenomProprietario() {
		return this.denomProprietario;
	}

	public void setDenomProprietario(String denomProprietario) {
		this.denomProprietario = denomProprietario;
	}

	public String getDesImpianto() {
		return this.desImpianto;
	}

	public void setDesImpianto(String desImpianto) {
		this.desImpianto = desImpianto;
	}

	public String getDestinazioneUso() {
		return this.destinazioneUso;
	}

	public void setDestinazioneUso(String destinazioneUso) {
		this.destinazioneUso = destinazioneUso;
	}

	public Date getDtProt() {
		return this.dtProt;
	}

	public void setDtProt(Date dtProt) {
		this.dtProt = dtProt;
	}

	public String getDtProtStr() {
		SimpleDateFormat sdf = new SimpleDateFormat ("dd/MM/yyyy");
		return sdf.format(dtProt);
	}

	public BigDecimal getNumProt() {
		return this.numProt;
	}

	public void setNumProt(BigDecimal numProt) {
		this.numProt = numProt;
	}

	public String getTipoImpianto() {
		return this.tipoImpianto;
	}

	public void setTipoImpianto(String tipoImpianto) {
		this.tipoImpianto = tipoImpianto;
	}

	public String getUbicInterno() {
		return this.ubicInterno;
	}

	public void setUbicInterno(String ubicInterno) {
		this.ubicInterno = ubicInterno;
	}

	public String getUbicPiano() {
		return this.ubicPiano;
	}

	public void setUbicPiano(String ubicPiano) {
		this.ubicPiano = ubicPiano;
	}

	public String getUbicScala() {
		return this.ubicScala;
	}

	public void setUbicScala(String ubicScala) {
		this.ubicScala = ubicScala;
	}

	public BigDecimal getUbicSiticiviPkidCivi() {
		return this.ubicSiticiviPkidCivi;
	}

	public void setUbicSiticiviPkidCivi(BigDecimal ubicSiticiviPkidCivi) {
		this.ubicSiticiviPkidCivi = ubicSiticiviPkidCivi;
	}

	public String getParticella() {
		return particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
	}

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public String getSubTutti() {
		return subTutti;
	}

	public void setSubTutti(String subTutti) {
		this.subTutti = subTutti;
	}

	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

}