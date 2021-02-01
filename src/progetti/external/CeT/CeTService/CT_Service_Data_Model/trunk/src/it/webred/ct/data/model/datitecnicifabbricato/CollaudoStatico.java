package it.webred.ct.data.model.datitecnicifabbricato;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The persistent class for the DATI_TEC_FABBR_COLL_STA database table.
 * 
 */
@Entity
@Table(name="DATI_TEC_FABBR_COLL_STA")
public class CollaudoStatico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Column(name="CF_PIVA_COLLAUDATORE")
	private String cfPivaCollaudatore;

	@Column(name="DENOM_COLLAUDATORE")
	private String denomCollaudatore;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_PROT_DOC_COLL")
	private Date dtProtDocColl;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_PROT_PRA_COLL")
	private Date dtProtPraColl;
    
    @Transient
    private String dtProtPraCollStr;

	@Column(name="NUM_PROT_DOC_COLL")
	private BigDecimal numProtDocColl;

	@Column(name="NUM_PROT_PRA_COLL")
	private BigDecimal numProtPraColl;

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

	private String foglio;

	@Column(name="NUM_RIF_PRA_EDI")
	private String numRifPraEdi;

	@Column(name="NUM_PROT_PRA_EDI")
	private BigDecimal numProtPraEdi;

	@Temporal( TemporalType.DATE)
	@Column(name="DT_PROT_PRA_EDI")
	private Date dtProtPraEdi;
	
	@Column(name="CF_PIVA_PROPRIETARIO")
	private String cfPivaProprietario;

	@Column(name="DENOM_PROPRIETARIO")
	private String denomProprietario;


    public CollaudoStatico() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCfPivaCollaudatore() {
		return this.cfPivaCollaudatore;
	}

	public void setCfPivaCollaudatore(String cfPivaCollaudatore) {
		this.cfPivaCollaudatore = cfPivaCollaudatore;
	}

	public String getDenomCollaudatore() {
		return this.denomCollaudatore;
	}

	public void setDenomCollaudatore(String denomCollaudatore) {
		this.denomCollaudatore = denomCollaudatore;
	}

	public Date getDtProtDocColl() {
		return this.dtProtDocColl;
	}

	public void setDtProtDocColl(Date dtProtDocColl) {
		this.dtProtDocColl = dtProtDocColl;
	}

	public Date getDtProtPraColl() {
		return this.dtProtPraColl;
	}

	public void setDtProtPraColl(Date dtProtPraColl) {
		this.dtProtPraColl = dtProtPraColl;
	}

	public String getDtProtPraCollStr() {
		SimpleDateFormat sdf = new SimpleDateFormat ("dd/MM/yyyy");
		return sdf.format(dtProtPraColl);
	}

	public BigDecimal getNumProtDocColl() {
		return this.numProtDocColl;
	}

	public void setNumProtDocColl(BigDecimal numProtDocColl) {
		this.numProtDocColl = numProtDocColl;
	}

	public BigDecimal getNumProtPraColl() {
		return this.numProtPraColl;
	}

	public void setNumProtPraColl(BigDecimal numProtPraColl) {
		this.numProtPraColl = numProtPraColl;
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

	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getNumRifPraEdi() {
		return numRifPraEdi;
	}

	public void setNumRifPraEdi(String numRifPraEdi) {
		this.numRifPraEdi = numRifPraEdi;
	}

	public BigDecimal getNumProtPraEdi() {
		return numProtPraEdi;
	}

	public void setNumProtPraEdi(BigDecimal numProtPraEdi) {
		this.numProtPraEdi = numProtPraEdi;
	}

	public Date getDtProtPraEdi() {
		return dtProtPraEdi;
	}

	public void setDtProtPraEdi(Date dtProtPraEdi) {
		this.dtProtPraEdi = dtProtPraEdi;
	}

	public String getCfPivaProprietario() {
		return cfPivaProprietario;
	}

	public void setCfPivaProprietario(String cfPivaProprietario) {
		this.cfPivaProprietario = cfPivaProprietario;
	}

	public String getDenomProprietario() {
		return denomProprietario;
	}

	public void setDenomProprietario(String denomProprietario) {
		this.denomProprietario = denomProprietario;
	}

}