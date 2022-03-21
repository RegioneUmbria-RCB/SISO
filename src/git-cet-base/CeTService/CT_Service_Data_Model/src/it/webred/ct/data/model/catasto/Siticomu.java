package it.webred.ct.data.model.catasto;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SITICOMU database table.
 * 
 */
@Entity
public class Siticomu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COD_NAZIONALE")
	private String codNazionale;

	@Column(name="CO_FOGLIO1")
	private BigDecimal coFoglio1;

	@Column(name="CO_FOGLIO2")
	private BigDecimal coFoglio2;

	@Column(name="COD_COMUNE")
	private BigDecimal codComune;

	@Column(name="COD_PROVINCIA")
	private BigDecimal codProvincia;

	@Column(name="CODI_FISC_LUNA")
	private String codiFiscLuna;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_AGGIORNAMENTO")
	private Date dataAggiornamento;

	@Column(name="DENO_PROV")
	private String denoProv;

	@Column(name="FLAG_CATA")
	private BigDecimal flagCata;

	@Column(name="ID_SEZC")
	private String idSezc;

	private BigDecimal isopr;

	private String istatc;

	private String istatp;

	private String istatr;

	private String nome;

	@Column(name="SEZIONE_CENSUARIA")
	private String sezioneCensuaria;

	@Column(name="SIGLA_PROV")
	private String siglaProv;

    public Siticomu() {
    }

	public String getCodNazionale() {
		return this.codNazionale;
	}

	public void setCodNazionale(String codNazionale) {
		this.codNazionale = codNazionale;
	}

	public BigDecimal getCoFoglio1() {
		return this.coFoglio1;
	}

	public void setCoFoglio1(BigDecimal coFoglio1) {
		this.coFoglio1 = coFoglio1;
	}

	public BigDecimal getCoFoglio2() {
		return this.coFoglio2;
	}

	public void setCoFoglio2(BigDecimal coFoglio2) {
		this.coFoglio2 = coFoglio2;
	}

	public BigDecimal getCodComune() {
		return this.codComune;
	}

	public void setCodComune(BigDecimal codComune) {
		this.codComune = codComune;
	}

	public BigDecimal getCodProvincia() {
		return this.codProvincia;
	}

	public void setCodProvincia(BigDecimal codProvincia) {
		this.codProvincia = codProvincia;
	}

	public String getCodiFiscLuna() {
		return this.codiFiscLuna;
	}

	public void setCodiFiscLuna(String codiFiscLuna) {
		this.codiFiscLuna = codiFiscLuna;
	}

	public Date getDataAggiornamento() {
		return this.dataAggiornamento;
	}

	public void setDataAggiornamento(Date dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}

	public String getDenoProv() {
		return this.denoProv;
	}

	public void setDenoProv(String denoProv) {
		this.denoProv = denoProv;
	}

	public BigDecimal getFlagCata() {
		return this.flagCata;
	}

	public void setFlagCata(BigDecimal flagCata) {
		this.flagCata = flagCata;
	}

	public String getIdSezc() {
		return this.idSezc;
	}

	public void setIdSezc(String idSezc) {
		this.idSezc = idSezc;
	}

	public BigDecimal getIsopr() {
		return this.isopr;
	}

	public void setIsopr(BigDecimal isopr) {
		this.isopr = isopr;
	}

	public String getIstatc() {
		return this.istatc;
	}

	public void setIstatc(String istatc) {
		this.istatc = istatc;
	}

	public String getIstatp() {
		return this.istatp;
	}

	public void setIstatp(String istatp) {
		this.istatp = istatp;
	}

	public String getIstatr() {
		return this.istatr;
	}

	public void setIstatr(String istatr) {
		this.istatr = istatr;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSezioneCensuaria() {
		return this.sezioneCensuaria;
	}

	public void setSezioneCensuaria(String sezioneCensuaria) {
		this.sezioneCensuaria = sezioneCensuaria;
	}

	public String getSiglaProv() {
		return this.siglaProv;
	}

	public void setSiglaProv(String siglaProv) {
		this.siglaProv = siglaProv;
	}

}