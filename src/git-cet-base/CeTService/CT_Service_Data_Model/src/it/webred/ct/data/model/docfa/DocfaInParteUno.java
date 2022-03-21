package it.webred.ct.data.model.docfa;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DOCFA_IN_PARTE_UNO database table.
 * 
 */
@Entity
@Table(name="DOCFA_IN_PARTE_UNO")
public class DocfaInParteUno implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DocfaInPartePK id;
	
	@Column(name="AF_ALTRO_01")
	private BigDecimal afAltro01;

	@Column(name="AF_ALTRO_02")
	private BigDecimal afAltro02;

	@Column(name="AF_ALTRO_03")
	private BigDecimal afAltro03;

	@Column(name="AF_ALTRO_04")
	private BigDecimal afAltro04;

	@Column(name="AF_DESC_ALTRO_TIPO")
	private String afDescAltroTipo;

	@Column(name="AF_FABB_ABIT_01")
	private BigDecimal afFabbAbit01;

	@Column(name="AF_FABB_ABIT_02")
	private BigDecimal afFabbAbit02;

	@Column(name="AF_FABB_ABIT_03")
	private BigDecimal afFabbAbit03;

	@Column(name="AF_FABB_ABIT_04")
	private BigDecimal afFabbAbit04;

	@Column(name="AF_FABB_USO_DIV_01")
	private BigDecimal afFabbUsoDiv01;

	@Column(name="AF_FABB_USO_DIV_02")
	private BigDecimal afFabbUsoDiv02;

	@Column(name="AF_FABB_USO_DIV_03")
	private BigDecimal afFabbUsoDiv03;

	@Column(name="AF_FABB_USO_DIV_04")
	private BigDecimal afFabbUsoDiv04;

	@Column(name="AF_FACCIATA_ADER_01")
	private BigDecimal afFacciataAder01;

	@Column(name="AF_FACCIATA_ADER_02")
	private BigDecimal afFacciataAder02;

	@Column(name="AF_FACCIATA_ADER_03")
	private BigDecimal afFacciataAder03;

	@Column(name="AF_FACCIATA_ADER_04")
	private BigDecimal afFacciataAder04;

	@Column(name="AF_PIAZZA_01")
	private BigDecimal afPiazza01;

	@Column(name="AF_PIAZZA_02")
	private BigDecimal afPiazza02;

	@Column(name="AF_PIAZZA_03")
	private BigDecimal afPiazza03;

	@Column(name="AF_PIAZZA_04")
	private BigDecimal afPiazza04;

	@Column(name="AF_STR_FINO_10M_01")
	private BigDecimal afStrFino10m01;

	@Column(name="AF_STR_FINO_10M_02")
	private BigDecimal afStrFino10m02;

	@Column(name="AF_STR_FINO_10M_03")
	private BigDecimal afStrFino10m03;

	@Column(name="AF_STR_FINO_10M_04")
	private BigDecimal afStrFino10m04;

	@Column(name="AF_STR_OLTRE_10M_01")
	private BigDecimal afStrOltre10m01;

	@Column(name="AF_STR_OLTRE_10M_02")
	private BigDecimal afStrOltre10m02;

	@Column(name="AF_STR_OLTRE_10M_03")
	private BigDecimal afStrOltre10m03;

	@Column(name="AF_STR_OLTRE_10M_04")
	private BigDecimal afStrOltre10m04;

	@Column(name="AF_VERDE_PRIV_01")
	private BigDecimal afVerdePriv01;

	@Column(name="AF_VERDE_PRIV_02")
	private BigDecimal afVerdePriv02;

	@Column(name="AF_VERDE_PRIV_03")
	private BigDecimal afVerdePriv03;

	@Column(name="AF_VERDE_PRIV_04")
	private BigDecimal afVerdePriv04;

	@Column(name="AF_VERDE_PUBB_01")
	private BigDecimal afVerdePubb01;

	@Column(name="AF_VERDE_PUBB_02")
	private BigDecimal afVerdePubb02;

	@Column(name="AF_VERDE_PUBB_03")
	private BigDecimal afVerdePubb03;

	@Column(name="AF_VERDE_PUBB_04")
	private BigDecimal afVerdePubb04;

	@Column(name="ANNO_COSTRU")
	private BigDecimal annoCostru;

	@Column(name="ANNO_RISTRU")
	private BigDecimal annoRistru;

	@Column(name="COMPLESSO_IMM")
	private BigDecimal complessoImm;

	@Column(name="CORPI_ACC_ENT_TE_01")
	private BigDecimal corpiAccEntTe01;

	@Column(name="CORPI_ACC_ENT_TE_02")
	private BigDecimal corpiAccEntTe02;

	@Column(name="CORPI_ACC_ENT_TE_03")
	private BigDecimal corpiAccEntTe03;

	@Column(name="CORPI_ACC_ENT_TE_04")
	private BigDecimal corpiAccEntTe04;

	@Column(name="CORPI_ACC_FUO_TE_01")
	private BigDecimal corpiAccFuoTe01;

	@Column(name="CORPI_ACC_FUO_TE_02")
	private BigDecimal corpiAccFuoTe02;

	@Column(name="CORPI_ACC_FUO_TE_03")
	private BigDecimal corpiAccFuoTe03;

	@Column(name="CORPI_ACC_FUO_TE_04")
	private BigDecimal corpiAccFuoTe04;

	@Column(name="CORTILE_INTERNO")
	private BigDecimal cortileInterno;

	@Column(name="CT_DENOMINATORE_01")
	private BigDecimal ctDenominatore01;

	@Column(name="CT_DENOMINATORE_02")
	private BigDecimal ctDenominatore02;

	@Column(name="CT_FOGLIO")
	private String ctFoglio;

	@Column(name="CT_NUMERO")
	private String ctNumero;

	@Column(name="CT_NUMERO_01")
	private String ctNumero01;

	@Column(name="CT_SEZIONE")
	private String ctSezione;

	@Column(name="CU_DENOMINATORE_01")
	private BigDecimal cuDenominatore01;

	@Column(name="CU_DENOMINATORE_02")
	private BigDecimal cuDenominatore02;

	@Column(name="CU_FOGLIO")
	private String cuFoglio;

	@Column(name="CU_NUMERO")
	private String cuNumero;

	@Column(name="CU_NUMERO_01")
	private String cuNumero01;

	@Column(name="CU_SEZIONE")
	private String cuSezione;

	@Column(name="DIM_P_ENTRO_TER")
	private BigDecimal dimPEntroTer;

	@Column(name="DIM_P_ENTRO_TER_MAX")
	private BigDecimal dimPEntroTerMax;

	@Column(name="DIM_P_ENTRO_TER_MIN")
	private BigDecimal dimPEntroTerMin;

	@Column(name="DIM_P_FUORI_TER")
	private BigDecimal dimPFuoriTer;

	@Column(name="DIM_P_FUORI_TER_MAX")
	private BigDecimal dimPFuoriTerMax;

	@Column(name="DIM_P_FUORI_TER_MIN")
	private BigDecimal dimPFuoriTerMin;

	@Column(name="DNS_F_ENTRO_TER_DEC")
	private BigDecimal dnsFEntroTerDec;

	@Column(name="DNS_F_ENTRO_TER_INT")
	private BigDecimal dnsFEntroTerInt;

	@Column(name="DNS_F_FUORI_TER_DEC")
	private BigDecimal dnsFFuoriTerDec;

	@Column(name="DNS_F_FUORI_TER_INT")
	private BigDecimal dnsFFuoriTerInt;

	@Column(name="NR_ABITAZ")
	private BigDecimal nrAbitaz;

	@Column(name="NR_AUTORIM")
	private BigDecimal nrAutorim;

	@Column(name="NR_BOX")
	private BigDecimal nrBox;

	@Column(name="NR_CAMPI_TENNIS")
	private BigDecimal nrCampiTennis;

	@Column(name="NR_EDIFICI")
	private BigDecimal nrEdifici;

	@Column(name="NR_IMP_SPO")
	private BigDecimal nrImpSpo;

	@Column(name="NR_LABORA")
	private BigDecimal nrLabora;

	@Column(name="NR_MAGAZZ")
	private BigDecimal nrMagazz;

	@Column(name="NR_NEGOZI")
	private BigDecimal nrNegozi;

	@Column(name="NR_POSTI_AUTO")
	private BigDecimal nrPostiAuto;

	@Column(name="NR_SCALE")
	private BigDecimal nrScale;

	@Column(name="NR_UFFICI")
	private BigDecimal nrUffici;

	@Column(name="NR_UIU_CTB")
	private BigDecimal nrUiuCtb;

	@Column(name="POSIZIONE_FABB")
	private BigDecimal posizioneFabb;

	@Column(name="SPZ_COP_ALLOG_CUS")
	private BigDecimal spzCopAllogCus;

	@Column(name="SPZ_COP_ALTRA_DES")
	private BigDecimal spzCopAltraDes;

	@Column(name="SPZ_COP_ATRIO")
	private BigDecimal spzCopAtrio;

	@Column(name="SPZ_COP_GUARDIOLA")
	private BigDecimal spzCopGuardiola;

	@Column(name="SPZ_COP_LAVATOIO")
	private BigDecimal spzCopLavatoio;

	@Column(name="SPZ_COP_PISCINA")
	private BigDecimal spzCopPiscina;

	@Column(name="SPZ_COP_PORTICATO")
	private BigDecimal spzCopPorticato;

	@Column(name="SPZ_COP_SALA_RIUN")
	private BigDecimal spzCopSalaRiun;

	@Column(name="SPZ_SCO_ALTRE_DES")
	private BigDecimal spzScoAltreDes;

	@Column(name="SPZ_SCO_CORTILE")
	private BigDecimal spzScoCortile;

	@Column(name="SPZ_SCO_PARCHEGGIO")
	private BigDecimal spzScoParcheggio;

	@Column(name="SPZ_SCO_PISCINE")
	private BigDecimal spzScoPiscine;

	@Column(name="SPZ_SCO_TENNIS")
	private BigDecimal spzScoTennis;

	@Column(name="SPZ_SCO_TERRAZZA")
	private BigDecimal spzScoTerrazza;

	@Column(name="SPZ_SCO_VERDE")
	private BigDecimal spzScoVerde;

	@Column(name="SUPMQ_ATRIO")
	private BigDecimal supmqAtrio;

	@Column(name="SUPMQ_COP_ALTRA_DES")
	private String supmqCopAltraDes;

	@Column(name="SUPMQ_CORTILE")
	private BigDecimal supmqCortile;

	@Column(name="SUPMQ_LAVATOIO")
	private BigDecimal supmqLavatoio;

	@Column(name="SUPMQ_PISCINA_COPE")
	private BigDecimal supmqPiscinaCope;

	@Column(name="SUPMQ_PISCINA_SCO")
	private BigDecimal supmqPiscinaSco;

	@Column(name="SUPMQ_PORTICATO")
	private BigDecimal supmqPorticato;

	@Column(name="SUPMQ_SALA_RIUNIONI")
	private BigDecimal supmqSalaRiunioni;

	@Column(name="SUPMQ_SCO_ALTRA_DES")
	private String supmqScoAltraDes;

	@Column(name="SUPMQ_TERRAZZA")
	private BigDecimal supmqTerrazza;

	@Column(name="SUPMQ_VERDE")
	private BigDecimal supmqVerde;

	@Column(name="TIPO_ACCES")
	private BigDecimal tipoAcces;

    public DocfaInParteUno() {
    }

	public BigDecimal getAfAltro01() {
		return this.afAltro01;
	}

	public void setAfAltro01(BigDecimal afAltro01) {
		this.afAltro01 = afAltro01;
	}

	public BigDecimal getAfAltro02() {
		return this.afAltro02;
	}

	public void setAfAltro02(BigDecimal afAltro02) {
		this.afAltro02 = afAltro02;
	}

	public BigDecimal getAfAltro03() {
		return this.afAltro03;
	}

	public void setAfAltro03(BigDecimal afAltro03) {
		this.afAltro03 = afAltro03;
	}

	public BigDecimal getAfAltro04() {
		return this.afAltro04;
	}

	public void setAfAltro04(BigDecimal afAltro04) {
		this.afAltro04 = afAltro04;
	}

	public String getAfDescAltroTipo() {
		return this.afDescAltroTipo;
	}

	public void setAfDescAltroTipo(String afDescAltroTipo) {
		this.afDescAltroTipo = afDescAltroTipo;
	}

	public BigDecimal getAfFabbAbit01() {
		return this.afFabbAbit01;
	}

	public void setAfFabbAbit01(BigDecimal afFabbAbit01) {
		this.afFabbAbit01 = afFabbAbit01;
	}

	public BigDecimal getAfFabbAbit02() {
		return this.afFabbAbit02;
	}

	public void setAfFabbAbit02(BigDecimal afFabbAbit02) {
		this.afFabbAbit02 = afFabbAbit02;
	}

	public BigDecimal getAfFabbAbit03() {
		return this.afFabbAbit03;
	}

	public void setAfFabbAbit03(BigDecimal afFabbAbit03) {
		this.afFabbAbit03 = afFabbAbit03;
	}

	public BigDecimal getAfFabbAbit04() {
		return this.afFabbAbit04;
	}

	public void setAfFabbAbit04(BigDecimal afFabbAbit04) {
		this.afFabbAbit04 = afFabbAbit04;
	}

	public BigDecimal getAfFabbUsoDiv01() {
		return this.afFabbUsoDiv01;
	}

	public void setAfFabbUsoDiv01(BigDecimal afFabbUsoDiv01) {
		this.afFabbUsoDiv01 = afFabbUsoDiv01;
	}

	public BigDecimal getAfFabbUsoDiv02() {
		return this.afFabbUsoDiv02;
	}

	public void setAfFabbUsoDiv02(BigDecimal afFabbUsoDiv02) {
		this.afFabbUsoDiv02 = afFabbUsoDiv02;
	}

	public BigDecimal getAfFabbUsoDiv03() {
		return this.afFabbUsoDiv03;
	}

	public void setAfFabbUsoDiv03(BigDecimal afFabbUsoDiv03) {
		this.afFabbUsoDiv03 = afFabbUsoDiv03;
	}

	public BigDecimal getAfFabbUsoDiv04() {
		return this.afFabbUsoDiv04;
	}

	public void setAfFabbUsoDiv04(BigDecimal afFabbUsoDiv04) {
		this.afFabbUsoDiv04 = afFabbUsoDiv04;
	}

	public BigDecimal getAfFacciataAder01() {
		return this.afFacciataAder01;
	}

	public void setAfFacciataAder01(BigDecimal afFacciataAder01) {
		this.afFacciataAder01 = afFacciataAder01;
	}

	public BigDecimal getAfFacciataAder02() {
		return this.afFacciataAder02;
	}

	public void setAfFacciataAder02(BigDecimal afFacciataAder02) {
		this.afFacciataAder02 = afFacciataAder02;
	}

	public BigDecimal getAfFacciataAder03() {
		return this.afFacciataAder03;
	}

	public void setAfFacciataAder03(BigDecimal afFacciataAder03) {
		this.afFacciataAder03 = afFacciataAder03;
	}

	public BigDecimal getAfFacciataAder04() {
		return this.afFacciataAder04;
	}

	public void setAfFacciataAder04(BigDecimal afFacciataAder04) {
		this.afFacciataAder04 = afFacciataAder04;
	}

	public BigDecimal getAfPiazza01() {
		return this.afPiazza01;
	}

	public void setAfPiazza01(BigDecimal afPiazza01) {
		this.afPiazza01 = afPiazza01;
	}

	public BigDecimal getAfPiazza02() {
		return this.afPiazza02;
	}

	public void setAfPiazza02(BigDecimal afPiazza02) {
		this.afPiazza02 = afPiazza02;
	}

	public BigDecimal getAfPiazza03() {
		return this.afPiazza03;
	}

	public void setAfPiazza03(BigDecimal afPiazza03) {
		this.afPiazza03 = afPiazza03;
	}

	public BigDecimal getAfPiazza04() {
		return this.afPiazza04;
	}

	public void setAfPiazza04(BigDecimal afPiazza04) {
		this.afPiazza04 = afPiazza04;
	}

	public BigDecimal getAfStrFino10m01() {
		return this.afStrFino10m01;
	}

	public void setAfStrFino10m01(BigDecimal afStrFino10m01) {
		this.afStrFino10m01 = afStrFino10m01;
	}

	public BigDecimal getAfStrFino10m02() {
		return this.afStrFino10m02;
	}

	public void setAfStrFino10m02(BigDecimal afStrFino10m02) {
		this.afStrFino10m02 = afStrFino10m02;
	}

	public BigDecimal getAfStrFino10m03() {
		return this.afStrFino10m03;
	}

	public void setAfStrFino10m03(BigDecimal afStrFino10m03) {
		this.afStrFino10m03 = afStrFino10m03;
	}

	public BigDecimal getAfStrFino10m04() {
		return this.afStrFino10m04;
	}

	public void setAfStrFino10m04(BigDecimal afStrFino10m04) {
		this.afStrFino10m04 = afStrFino10m04;
	}

	public BigDecimal getAfStrOltre10m01() {
		return this.afStrOltre10m01;
	}

	public void setAfStrOltre10m01(BigDecimal afStrOltre10m01) {
		this.afStrOltre10m01 = afStrOltre10m01;
	}

	public BigDecimal getAfStrOltre10m02() {
		return this.afStrOltre10m02;
	}

	public void setAfStrOltre10m02(BigDecimal afStrOltre10m02) {
		this.afStrOltre10m02 = afStrOltre10m02;
	}

	public BigDecimal getAfStrOltre10m03() {
		return this.afStrOltre10m03;
	}

	public void setAfStrOltre10m03(BigDecimal afStrOltre10m03) {
		this.afStrOltre10m03 = afStrOltre10m03;
	}

	public BigDecimal getAfStrOltre10m04() {
		return this.afStrOltre10m04;
	}

	public void setAfStrOltre10m04(BigDecimal afStrOltre10m04) {
		this.afStrOltre10m04 = afStrOltre10m04;
	}

	public BigDecimal getAfVerdePriv01() {
		return this.afVerdePriv01;
	}

	public void setAfVerdePriv01(BigDecimal afVerdePriv01) {
		this.afVerdePriv01 = afVerdePriv01;
	}

	public BigDecimal getAfVerdePriv02() {
		return this.afVerdePriv02;
	}

	public void setAfVerdePriv02(BigDecimal afVerdePriv02) {
		this.afVerdePriv02 = afVerdePriv02;
	}

	public BigDecimal getAfVerdePriv03() {
		return this.afVerdePriv03;
	}

	public void setAfVerdePriv03(BigDecimal afVerdePriv03) {
		this.afVerdePriv03 = afVerdePriv03;
	}

	public BigDecimal getAfVerdePriv04() {
		return this.afVerdePriv04;
	}

	public void setAfVerdePriv04(BigDecimal afVerdePriv04) {
		this.afVerdePriv04 = afVerdePriv04;
	}

	public BigDecimal getAfVerdePubb01() {
		return this.afVerdePubb01;
	}

	public void setAfVerdePubb01(BigDecimal afVerdePubb01) {
		this.afVerdePubb01 = afVerdePubb01;
	}

	public BigDecimal getAfVerdePubb02() {
		return this.afVerdePubb02;
	}

	public void setAfVerdePubb02(BigDecimal afVerdePubb02) {
		this.afVerdePubb02 = afVerdePubb02;
	}

	public BigDecimal getAfVerdePubb03() {
		return this.afVerdePubb03;
	}

	public void setAfVerdePubb03(BigDecimal afVerdePubb03) {
		this.afVerdePubb03 = afVerdePubb03;
	}

	public BigDecimal getAfVerdePubb04() {
		return this.afVerdePubb04;
	}

	public void setAfVerdePubb04(BigDecimal afVerdePubb04) {
		this.afVerdePubb04 = afVerdePubb04;
	}

	public BigDecimal getAnnoCostru() {
		return this.annoCostru;
	}

	public void setAnnoCostru(BigDecimal annoCostru) {
		this.annoCostru = annoCostru;
	}

	public BigDecimal getAnnoRistru() {
		return this.annoRistru;
	}

	public void setAnnoRistru(BigDecimal annoRistru) {
		this.annoRistru = annoRistru;
	}

	public BigDecimal getComplessoImm() {
		return this.complessoImm;
	}

	public void setComplessoImm(BigDecimal complessoImm) {
		this.complessoImm = complessoImm;
	}

	public BigDecimal getCorpiAccEntTe01() {
		return this.corpiAccEntTe01;
	}

	public void setCorpiAccEntTe01(BigDecimal corpiAccEntTe01) {
		this.corpiAccEntTe01 = corpiAccEntTe01;
	}

	public BigDecimal getCorpiAccEntTe02() {
		return this.corpiAccEntTe02;
	}

	public void setCorpiAccEntTe02(BigDecimal corpiAccEntTe02) {
		this.corpiAccEntTe02 = corpiAccEntTe02;
	}

	public BigDecimal getCorpiAccEntTe03() {
		return this.corpiAccEntTe03;
	}

	public void setCorpiAccEntTe03(BigDecimal corpiAccEntTe03) {
		this.corpiAccEntTe03 = corpiAccEntTe03;
	}

	public BigDecimal getCorpiAccEntTe04() {
		return this.corpiAccEntTe04;
	}

	public void setCorpiAccEntTe04(BigDecimal corpiAccEntTe04) {
		this.corpiAccEntTe04 = corpiAccEntTe04;
	}

	public BigDecimal getCorpiAccFuoTe01() {
		return this.corpiAccFuoTe01;
	}

	public void setCorpiAccFuoTe01(BigDecimal corpiAccFuoTe01) {
		this.corpiAccFuoTe01 = corpiAccFuoTe01;
	}

	public BigDecimal getCorpiAccFuoTe02() {
		return this.corpiAccFuoTe02;
	}

	public void setCorpiAccFuoTe02(BigDecimal corpiAccFuoTe02) {
		this.corpiAccFuoTe02 = corpiAccFuoTe02;
	}

	public BigDecimal getCorpiAccFuoTe03() {
		return this.corpiAccFuoTe03;
	}

	public void setCorpiAccFuoTe03(BigDecimal corpiAccFuoTe03) {
		this.corpiAccFuoTe03 = corpiAccFuoTe03;
	}

	public BigDecimal getCorpiAccFuoTe04() {
		return this.corpiAccFuoTe04;
	}

	public void setCorpiAccFuoTe04(BigDecimal corpiAccFuoTe04) {
		this.corpiAccFuoTe04 = corpiAccFuoTe04;
	}

	public BigDecimal getCortileInterno() {
		return this.cortileInterno;
	}

	public void setCortileInterno(BigDecimal cortileInterno) {
		this.cortileInterno = cortileInterno;
	}

	public BigDecimal getCtDenominatore01() {
		return this.ctDenominatore01;
	}

	public void setCtDenominatore01(BigDecimal ctDenominatore01) {
		this.ctDenominatore01 = ctDenominatore01;
	}

	public BigDecimal getCtDenominatore02() {
		return this.ctDenominatore02;
	}

	public void setCtDenominatore02(BigDecimal ctDenominatore02) {
		this.ctDenominatore02 = ctDenominatore02;
	}

	public String getCtFoglio() {
		return this.ctFoglio;
	}

	public void setCtFoglio(String ctFoglio) {
		this.ctFoglio = ctFoglio;
	}

	public String getCtNumero() {
		return this.ctNumero;
	}

	public void setCtNumero(String ctNumero) {
		this.ctNumero = ctNumero;
	}

	public String getCtNumero01() {
		return this.ctNumero01;
	}

	public void setCtNumero01(String ctNumero01) {
		this.ctNumero01 = ctNumero01;
	}

	public String getCtSezione() {
		return this.ctSezione;
	}

	public void setCtSezione(String ctSezione) {
		this.ctSezione = ctSezione;
	}

	public BigDecimal getCuDenominatore01() {
		return this.cuDenominatore01;
	}

	public void setCuDenominatore01(BigDecimal cuDenominatore01) {
		this.cuDenominatore01 = cuDenominatore01;
	}

	public BigDecimal getCuDenominatore02() {
		return this.cuDenominatore02;
	}

	public void setCuDenominatore02(BigDecimal cuDenominatore02) {
		this.cuDenominatore02 = cuDenominatore02;
	}

	public String getCuFoglio() {
		return this.cuFoglio;
	}

	public void setCuFoglio(String cuFoglio) {
		this.cuFoglio = cuFoglio;
	}

	public String getCuNumero() {
		return this.cuNumero;
	}

	public void setCuNumero(String cuNumero) {
		this.cuNumero = cuNumero;
	}

	public String getCuNumero01() {
		return this.cuNumero01;
	}

	public void setCuNumero01(String cuNumero01) {
		this.cuNumero01 = cuNumero01;
	}

	public String getCuSezione() {
		return this.cuSezione;
	}

	public void setCuSezione(String cuSezione) {
		this.cuSezione = cuSezione;
	}

	public BigDecimal getDimPEntroTer() {
		return this.dimPEntroTer;
	}

	public void setDimPEntroTer(BigDecimal dimPEntroTer) {
		this.dimPEntroTer = dimPEntroTer;
	}

	public BigDecimal getDimPEntroTerMax() {
		return this.dimPEntroTerMax;
	}

	public void setDimPEntroTerMax(BigDecimal dimPEntroTerMax) {
		this.dimPEntroTerMax = dimPEntroTerMax;
	}

	public BigDecimal getDimPEntroTerMin() {
		return this.dimPEntroTerMin;
	}

	public void setDimPEntroTerMin(BigDecimal dimPEntroTerMin) {
		this.dimPEntroTerMin = dimPEntroTerMin;
	}

	public BigDecimal getDimPFuoriTer() {
		return this.dimPFuoriTer;
	}

	public void setDimPFuoriTer(BigDecimal dimPFuoriTer) {
		this.dimPFuoriTer = dimPFuoriTer;
	}

	public BigDecimal getDimPFuoriTerMax() {
		return this.dimPFuoriTerMax;
	}

	public void setDimPFuoriTerMax(BigDecimal dimPFuoriTerMax) {
		this.dimPFuoriTerMax = dimPFuoriTerMax;
	}

	public BigDecimal getDimPFuoriTerMin() {
		return this.dimPFuoriTerMin;
	}

	public void setDimPFuoriTerMin(BigDecimal dimPFuoriTerMin) {
		this.dimPFuoriTerMin = dimPFuoriTerMin;
	}

	public BigDecimal getDnsFEntroTerDec() {
		return this.dnsFEntroTerDec;
	}

	public void setDnsFEntroTerDec(BigDecimal dnsFEntroTerDec) {
		this.dnsFEntroTerDec = dnsFEntroTerDec;
	}

	public BigDecimal getDnsFEntroTerInt() {
		return this.dnsFEntroTerInt;
	}

	public void setDnsFEntroTerInt(BigDecimal dnsFEntroTerInt) {
		this.dnsFEntroTerInt = dnsFEntroTerInt;
	}

	public BigDecimal getDnsFFuoriTerDec() {
		return this.dnsFFuoriTerDec;
	}

	public void setDnsFFuoriTerDec(BigDecimal dnsFFuoriTerDec) {
		this.dnsFFuoriTerDec = dnsFFuoriTerDec;
	}

	public BigDecimal getDnsFFuoriTerInt() {
		return this.dnsFFuoriTerInt;
	}

	public void setDnsFFuoriTerInt(BigDecimal dnsFFuoriTerInt) {
		this.dnsFFuoriTerInt = dnsFFuoriTerInt;
	}

	public BigDecimal getNrAbitaz() {
		return this.nrAbitaz;
	}

	public void setNrAbitaz(BigDecimal nrAbitaz) {
		this.nrAbitaz = nrAbitaz;
	}

	public BigDecimal getNrAutorim() {
		return this.nrAutorim;
	}

	public void setNrAutorim(BigDecimal nrAutorim) {
		this.nrAutorim = nrAutorim;
	}

	public BigDecimal getNrBox() {
		return this.nrBox;
	}

	public void setNrBox(BigDecimal nrBox) {
		this.nrBox = nrBox;
	}

	public BigDecimal getNrCampiTennis() {
		return this.nrCampiTennis;
	}

	public void setNrCampiTennis(BigDecimal nrCampiTennis) {
		this.nrCampiTennis = nrCampiTennis;
	}

	public BigDecimal getNrEdifici() {
		return this.nrEdifici;
	}

	public void setNrEdifici(BigDecimal nrEdifici) {
		this.nrEdifici = nrEdifici;
	}

	public BigDecimal getNrImpSpo() {
		return this.nrImpSpo;
	}

	public void setNrImpSpo(BigDecimal nrImpSpo) {
		this.nrImpSpo = nrImpSpo;
	}

	public BigDecimal getNrLabora() {
		return this.nrLabora;
	}

	public void setNrLabora(BigDecimal nrLabora) {
		this.nrLabora = nrLabora;
	}

	public BigDecimal getNrMagazz() {
		return this.nrMagazz;
	}

	public void setNrMagazz(BigDecimal nrMagazz) {
		this.nrMagazz = nrMagazz;
	}

	public BigDecimal getNrNegozi() {
		return this.nrNegozi;
	}

	public void setNrNegozi(BigDecimal nrNegozi) {
		this.nrNegozi = nrNegozi;
	}

	public BigDecimal getNrPostiAuto() {
		return this.nrPostiAuto;
	}

	public void setNrPostiAuto(BigDecimal nrPostiAuto) {
		this.nrPostiAuto = nrPostiAuto;
	}

	public BigDecimal getNrScale() {
		return this.nrScale;
	}

	public void setNrScale(BigDecimal nrScale) {
		this.nrScale = nrScale;
	}

	public BigDecimal getNrUffici() {
		return this.nrUffici;
	}

	public void setNrUffici(BigDecimal nrUffici) {
		this.nrUffici = nrUffici;
	}

	public BigDecimal getNrUiuCtb() {
		return this.nrUiuCtb;
	}

	public void setNrUiuCtb(BigDecimal nrUiuCtb) {
		this.nrUiuCtb = nrUiuCtb;
	}

	public BigDecimal getPosizioneFabb() {
		return this.posizioneFabb;
	}

	public void setPosizioneFabb(BigDecimal posizioneFabb) {
		this.posizioneFabb = posizioneFabb;
	}

	public BigDecimal getSpzCopAllogCus() {
		return this.spzCopAllogCus;
	}

	public void setSpzCopAllogCus(BigDecimal spzCopAllogCus) {
		this.spzCopAllogCus = spzCopAllogCus;
	}

	public BigDecimal getSpzCopAltraDes() {
		return this.spzCopAltraDes;
	}

	public void setSpzCopAltraDes(BigDecimal spzCopAltraDes) {
		this.spzCopAltraDes = spzCopAltraDes;
	}

	public BigDecimal getSpzCopAtrio() {
		return this.spzCopAtrio;
	}

	public void setSpzCopAtrio(BigDecimal spzCopAtrio) {
		this.spzCopAtrio = spzCopAtrio;
	}

	public BigDecimal getSpzCopGuardiola() {
		return this.spzCopGuardiola;
	}

	public void setSpzCopGuardiola(BigDecimal spzCopGuardiola) {
		this.spzCopGuardiola = spzCopGuardiola;
	}

	public BigDecimal getSpzCopLavatoio() {
		return this.spzCopLavatoio;
	}

	public void setSpzCopLavatoio(BigDecimal spzCopLavatoio) {
		this.spzCopLavatoio = spzCopLavatoio;
	}

	public BigDecimal getSpzCopPiscina() {
		return this.spzCopPiscina;
	}

	public void setSpzCopPiscina(BigDecimal spzCopPiscina) {
		this.spzCopPiscina = spzCopPiscina;
	}

	public BigDecimal getSpzCopPorticato() {
		return this.spzCopPorticato;
	}

	public void setSpzCopPorticato(BigDecimal spzCopPorticato) {
		this.spzCopPorticato = spzCopPorticato;
	}

	public BigDecimal getSpzCopSalaRiun() {
		return this.spzCopSalaRiun;
	}

	public void setSpzCopSalaRiun(BigDecimal spzCopSalaRiun) {
		this.spzCopSalaRiun = spzCopSalaRiun;
	}

	public BigDecimal getSpzScoAltreDes() {
		return this.spzScoAltreDes;
	}

	public void setSpzScoAltreDes(BigDecimal spzScoAltreDes) {
		this.spzScoAltreDes = spzScoAltreDes;
	}

	public BigDecimal getSpzScoCortile() {
		return this.spzScoCortile;
	}

	public void setSpzScoCortile(BigDecimal spzScoCortile) {
		this.spzScoCortile = spzScoCortile;
	}

	public BigDecimal getSpzScoParcheggio() {
		return this.spzScoParcheggio;
	}

	public void setSpzScoParcheggio(BigDecimal spzScoParcheggio) {
		this.spzScoParcheggio = spzScoParcheggio;
	}

	public BigDecimal getSpzScoPiscine() {
		return this.spzScoPiscine;
	}

	public void setSpzScoPiscine(BigDecimal spzScoPiscine) {
		this.spzScoPiscine = spzScoPiscine;
	}

	public BigDecimal getSpzScoTennis() {
		return this.spzScoTennis;
	}

	public void setSpzScoTennis(BigDecimal spzScoTennis) {
		this.spzScoTennis = spzScoTennis;
	}

	public BigDecimal getSpzScoTerrazza() {
		return this.spzScoTerrazza;
	}

	public void setSpzScoTerrazza(BigDecimal spzScoTerrazza) {
		this.spzScoTerrazza = spzScoTerrazza;
	}

	public BigDecimal getSpzScoVerde() {
		return this.spzScoVerde;
	}

	public void setSpzScoVerde(BigDecimal spzScoVerde) {
		this.spzScoVerde = spzScoVerde;
	}

	public BigDecimal getSupmqAtrio() {
		return this.supmqAtrio;
	}

	public void setSupmqAtrio(BigDecimal supmqAtrio) {
		this.supmqAtrio = supmqAtrio;
	}

	public String getSupmqCopAltraDes() {
		return this.supmqCopAltraDes;
	}

	public void setSupmqCopAltraDes(String supmqCopAltraDes) {
		this.supmqCopAltraDes = supmqCopAltraDes;
	}

	public BigDecimal getSupmqCortile() {
		return this.supmqCortile;
	}

	public void setSupmqCortile(BigDecimal supmqCortile) {
		this.supmqCortile = supmqCortile;
	}

	public BigDecimal getSupmqLavatoio() {
		return this.supmqLavatoio;
	}

	public void setSupmqLavatoio(BigDecimal supmqLavatoio) {
		this.supmqLavatoio = supmqLavatoio;
	}

	public BigDecimal getSupmqPiscinaCope() {
		return this.supmqPiscinaCope;
	}

	public void setSupmqPiscinaCope(BigDecimal supmqPiscinaCope) {
		this.supmqPiscinaCope = supmqPiscinaCope;
	}

	public BigDecimal getSupmqPiscinaSco() {
		return this.supmqPiscinaSco;
	}

	public void setSupmqPiscinaSco(BigDecimal supmqPiscinaSco) {
		this.supmqPiscinaSco = supmqPiscinaSco;
	}

	public BigDecimal getSupmqPorticato() {
		return this.supmqPorticato;
	}

	public void setSupmqPorticato(BigDecimal supmqPorticato) {
		this.supmqPorticato = supmqPorticato;
	}

	public BigDecimal getSupmqSalaRiunioni() {
		return this.supmqSalaRiunioni;
	}

	public void setSupmqSalaRiunioni(BigDecimal supmqSalaRiunioni) {
		this.supmqSalaRiunioni = supmqSalaRiunioni;
	}

	public String getSupmqScoAltraDes() {
		return this.supmqScoAltraDes;
	}

	public void setSupmqScoAltraDes(String supmqScoAltraDes) {
		this.supmqScoAltraDes = supmqScoAltraDes;
	}

	public BigDecimal getSupmqTerrazza() {
		return this.supmqTerrazza;
	}

	public void setSupmqTerrazza(BigDecimal supmqTerrazza) {
		this.supmqTerrazza = supmqTerrazza;
	}

	public BigDecimal getSupmqVerde() {
		return this.supmqVerde;
	}

	public void setSupmqVerde(BigDecimal supmqVerde) {
		this.supmqVerde = supmqVerde;
	}

	public BigDecimal getTipoAcces() {
		return this.tipoAcces;
	}

	public void setTipoAcces(BigDecimal tipoAcces) {
		this.tipoAcces = tipoAcces;
	}

	public DocfaInPartePK getId() {
		return id;
	}

	public void setId(DocfaInPartePK id) {
		this.id = id;
	}

}