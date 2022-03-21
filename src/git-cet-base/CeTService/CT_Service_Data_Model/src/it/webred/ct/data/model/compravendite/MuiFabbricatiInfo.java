package it.webred.ct.data.model.compravendite;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the MI_DUP_FABBRICATI_INFO database table.
 * 
 */
@Entity
@Table(name="MUI_FABBRICATI_INFO")
public class MuiFabbricatiInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long iid;

	private String annotazioni;

	@Column(name="C_CIVICO1")
	private String cCivico1;

	@Column(name="C_CIVICO2")
	private String cCivico2;

	@Column(name="C_CIVICO3")
	private String cCivico3;

	@Column(name="C_EDIFICIO")
	private String cEdificio;

	@Column(name="C_INDIRIZZO")
	private String cIndirizzo;

	@Column(name="C_INTERNO1")
	private String cInterno1;

	@Column(name="C_INTERNO2")
	private String cInterno2;

	@Column(name="C_LOTTO")
	private String cLotto;

	@Column(name="C_PIANO1")
	private String cPiano1;

	@Column(name="C_PIANO2")
	private String cPiano2;

	@Column(name="C_PIANO3")
	private String cPiano3;

	@Column(name="C_PIANO4")
	private String cPiano4;

	@Column(name="C_SCALA")
	private String cScala;

	@Column(name="C_TOPONIMO")
	private String cToponimo;

	private String categoria;

	private String classe;

	@Column(name="CODICE_ESITO")
	private String codiceEsito;

	@Column(name="FLAG_GRAFFATO")
	private String flagGraffato;

	@Column(name="ID_CATASTALE_IMMOBILE")
	private String idCatastaleImmobile;

	@Column(name="ID_IMMOBILE")
	private String idImmobile;

	@Column(name="ID_NOTA")
	private String idNota;

	@Column(name="IID_FORNITURA")
	private BigDecimal iidFornitura;

	@Column(name="IID_NOTA")
	private BigDecimal iidNota;

	private String mc;

	private String mq;

	@Column(name="NATURA_IMMOBILE")
	private String naturaImmobile;

	@Column(name="RENDITA_EURO")
	private String renditaEuro;

	private String superficie;

	@Column(name="T_CIVICO1")
	private String tCivico1;

	@Column(name="T_CIVICO2")
	private String tCivico2;

	@Column(name="T_CIVICO3")
	private String tCivico3;

	@Column(name="T_EDIFICIO")
	private String tEdificio;

	@Column(name="T_INDIRIZZO")
	private String tIndirizzo;

	@Column(name="T_INTERNO1")
	private String tInterno1;

	@Column(name="T_INTERNO2")
	private String tInterno2;

	@Column(name="T_LOTTO")
	private String tLotto;

	@Column(name="T_PIANO1")
	private String tPiano1;

	@Column(name="T_PIANO2")
	private String tPiano2;

	@Column(name="T_PIANO3")
	private String tPiano3;

	@Column(name="T_PIANO4")
	private String tPiano4;

	@Column(name="T_SCALA")
	private String tScala;

	@Column(name="T_TOPONIMO")
	private String tToponimo;

	@Column(name="TIPOLOGIA_IMMOBILE")
	private String tipologiaImmobile;

	private String vani;

	private String zona;
	
    public MuiFabbricatiInfo() {
    }//-------------------------------------------------------------------------

	public long getIid() {
		return this.iid;
	}

	public void setIid(long iid) {
		this.iid = iid;
	}

	public String getAnnotazioni() {
		return this.annotazioni;
	}

	public void setAnnotazioni(String annotazioni) {
		this.annotazioni = annotazioni;
	}

	public String getCCivico1() {
		return this.cCivico1;
	}

	public void setCCivico1(String cCivico1) {
		this.cCivico1 = cCivico1;
	}

	public String getCCivico2() {
		return this.cCivico2;
	}

	public void setCCivico2(String cCivico2) {
		this.cCivico2 = cCivico2;
	}

	public String getCCivico3() {
		return this.cCivico3;
	}

	public void setCCivico3(String cCivico3) {
		this.cCivico3 = cCivico3;
	}

	public String getCEdificio() {
		return this.cEdificio;
	}

	public void setCEdificio(String cEdificio) {
		this.cEdificio = cEdificio;
	}

	public String getCIndirizzo() {
		return this.cIndirizzo;
	}

	public void setCIndirizzo(String cIndirizzo) {
		this.cIndirizzo = cIndirizzo;
	}

	public String getCInterno1() {
		return this.cInterno1;
	}

	public void setCInterno1(String cInterno1) {
		this.cInterno1 = cInterno1;
	}

	public String getCInterno2() {
		return this.cInterno2;
	}

	public void setCInterno2(String cInterno2) {
		this.cInterno2 = cInterno2;
	}

	public String getCLotto() {
		return this.cLotto;
	}

	public void setCLotto(String cLotto) {
		this.cLotto = cLotto;
	}

	public String getCPiano1() {
		return this.cPiano1;
	}

	public void setCPiano1(String cPiano1) {
		this.cPiano1 = cPiano1;
	}

	public String getCPiano2() {
		return this.cPiano2;
	}

	public void setCPiano2(String cPiano2) {
		this.cPiano2 = cPiano2;
	}

	public String getCPiano3() {
		return this.cPiano3;
	}

	public void setCPiano3(String cPiano3) {
		this.cPiano3 = cPiano3;
	}

	public String getCPiano4() {
		return this.cPiano4;
	}

	public void setCPiano4(String cPiano4) {
		this.cPiano4 = cPiano4;
	}

	public String getCScala() {
		return this.cScala;
	}

	public void setCScala(String cScala) {
		this.cScala = cScala;
	}

	public String getCToponimo() {
		return this.cToponimo;
	}

	public void setCToponimo(String cToponimo) {
		this.cToponimo = cToponimo;
	}

	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getClasse() {
		return this.classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getCodiceEsito() {
		return this.codiceEsito;
	}

	public void setCodiceEsito(String codiceEsito) {
		this.codiceEsito = codiceEsito;
	}

	public String getFlagGraffato() {
		return this.flagGraffato;
	}

	public void setFlagGraffato(String flagGraffato) {
		this.flagGraffato = flagGraffato;
	}

	public String getIdCatastaleImmobile() {
		return this.idCatastaleImmobile;
	}

	public void setIdCatastaleImmobile(String idCatastaleImmobile) {
		this.idCatastaleImmobile = idCatastaleImmobile;
	}

	public String getIdImmobile() {
		return this.idImmobile;
	}

	public void setIdImmobile(String idImmobile) {
		this.idImmobile = idImmobile;
	}

	public String getIdNota() {
		return this.idNota;
	}

	public void setIdNota(String idNota) {
		this.idNota = idNota;
	}

	public BigDecimal getIidFornitura() {
		return this.iidFornitura;
	}

	public void setIidFornitura(BigDecimal iidFornitura) {
		this.iidFornitura = iidFornitura;
	}

	public BigDecimal getIidNota() {
		return this.iidNota;
	}

	public void setIidNota(BigDecimal iidNota) {
		this.iidNota = iidNota;
	}

	public String getMc() {
		return this.mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public String getMq() {
		return this.mq;
	}

	public void setMq(String mq) {
		this.mq = mq;
	}

	public String getNaturaImmobile() {
		return this.naturaImmobile;
	}

	public void setNaturaImmobile(String naturaImmobile) {
		this.naturaImmobile = naturaImmobile;
	}

	public String getRenditaEuro() {
		return this.renditaEuro;
	}

	public void setRenditaEuro(String renditaEuro) {
		this.renditaEuro = renditaEuro;
	}

	public String getSuperficie() {
		return this.superficie;
	}

	public void setSuperficie(String superficie) {
		this.superficie = superficie;
	}

	public String getTCivico1() {
		return this.tCivico1;
	}

	public void setTCivico1(String tCivico1) {
		this.tCivico1 = tCivico1;
	}

	public String getTCivico2() {
		return this.tCivico2;
	}

	public void setTCivico2(String tCivico2) {
		this.tCivico2 = tCivico2;
	}

	public String getTCivico3() {
		return this.tCivico3;
	}

	public void setTCivico3(String tCivico3) {
		this.tCivico3 = tCivico3;
	}

	public String getTEdificio() {
		return this.tEdificio;
	}

	public void setTEdificio(String tEdificio) {
		this.tEdificio = tEdificio;
	}

	public String getTIndirizzo() {
		return this.tIndirizzo;
	}

	public void setTIndirizzo(String tIndirizzo) {
		this.tIndirizzo = tIndirizzo;
	}

	public String getTInterno1() {
		return this.tInterno1;
	}

	public void setTInterno1(String tInterno1) {
		this.tInterno1 = tInterno1;
	}

	public String getTInterno2() {
		return this.tInterno2;
	}

	public void setTInterno2(String tInterno2) {
		this.tInterno2 = tInterno2;
	}

	public String getTLotto() {
		return this.tLotto;
	}

	public void setTLotto(String tLotto) {
		this.tLotto = tLotto;
	}

	public String getTPiano1() {
		return this.tPiano1;
	}

	public void setTPiano1(String tPiano1) {
		this.tPiano1 = tPiano1;
	}

	public String getTPiano2() {
		return this.tPiano2;
	}

	public void setTPiano2(String tPiano2) {
		this.tPiano2 = tPiano2;
	}

	public String getTPiano3() {
		return this.tPiano3;
	}

	public void setTPiano3(String tPiano3) {
		this.tPiano3 = tPiano3;
	}

	public String getTPiano4() {
		return this.tPiano4;
	}

	public void setTPiano4(String tPiano4) {
		this.tPiano4 = tPiano4;
	}

	public String getTScala() {
		return this.tScala;
	}

	public void setTScala(String tScala) {
		this.tScala = tScala;
	}

	public String getTToponimo() {
		return this.tToponimo;
	}

	public void setTToponimo(String tToponimo) {
		this.tToponimo = tToponimo;
	}

	public String getTipologiaImmobile() {
		return this.tipologiaImmobile;
	}

	public void setTipologiaImmobile(String tipologiaImmobile) {
		this.tipologiaImmobile = tipologiaImmobile;
	}

	public String getVani() {
		return this.vani;
	}

	public void setVani(String vani) {
		this.vani = vani;
	}

	public String getZona() {
		return this.zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	
}