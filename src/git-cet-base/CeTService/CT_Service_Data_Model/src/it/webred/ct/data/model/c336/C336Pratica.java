package it.webred.ct.data.model.c336;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;

 
/**
 * The persistent class for the S_C336_PRATICA database table.
 * 
 */
@Entity
@Table(name="S_C336_PRATICA")
public class C336Pratica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_PRATICA")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="c336PraticaSeq")
	@SequenceGenerator(name="c336PraticaSeq", sequenceName="S_C336_PRATICA_SEQ")
	private Long idPratica;

	@Column(name="COD_IRREG_ACCERTATA")
	private String codIrregAccertata;

	@Column(name="COD_STATO")
	private String codStato;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_FIN")
	private Date dtFin;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_INI")
	private Date dtIni;

	@Column(name="FL_INVIATA_NOTIFICA_ADT")
	private String flInviataNotificaAdt;

	@Column(name="FL_INVIATA_NOTIFICA_TITOLARE")
	private String flInviataNotificaTitolare;

	private String foglio;

	@Column(name="NOTA_CHIUSURA")
	private String notaChiusura;

	@Column(name="NOTA_ISTRUTTORIA")
	private String notaIstruttoria;

	private String particella;

	private String sub;

	@Column(name="USER_NAME_FIN")
	private String userNameFin;

	@Column(name="USER_NAME_INI")
	private String userNameIni;
	
	private String sezione;
	
	@Column(name="TIPO_PRATICA")
	private String tipoPratica;
	
    public C336Pratica() {
    }

	public Long getIdPratica() {
		return this.idPratica;
	}

	public void setIdPratica(Long idPratica) {
		this.idPratica = idPratica;
	}

	public String getCodIrregAccertata() {
		return codIrregAccertata;
	}

	public void setCodIrregAccertata(String codIrregAccertata) {
		this.codIrregAccertata = codIrregAccertata;
	}

	public String getCodStato() {
		return this.codStato;
	}

	public void setCodStato(String codStato) {
		this.codStato = codStato;
	}

	public Date getDtFin() {
		return this.dtFin;
	}

	public void setDtFin(Date dtFin) {
		this.dtFin = dtFin;
	}

	public Date getDtIni() {
		return this.dtIni;
	}

	public void setDtIni(Date dtIni) {
		this.dtIni = dtIni;
	}

	public String getFlInviataNotificaAdt() {
		return this.flInviataNotificaAdt;
	}

	public void setFlInviataNotificaAdt(String flInviataNotificaAdt) {
		this.flInviataNotificaAdt = flInviataNotificaAdt;
	}

	public String getFlInviataNotificaTitolare() {
		return this.flInviataNotificaTitolare;
	}

	public void setFlInviataNotificaTitolare(String flInviataNotificaTitolare) {
		this.flInviataNotificaTitolare = flInviataNotificaTitolare;
	}

	public String getFoglio() {
		return this.foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getNotaChiusura() {
		return this.notaChiusura;
	}

	public void setNotaChiusura(String notaChiusura) {
		this.notaChiusura = notaChiusura;
	}

	public String getNotaIstruttoria() {
		return this.notaIstruttoria;
	}

	public void setNotaIstruttoria(String notaIstruttoria) {
		this.notaIstruttoria = notaIstruttoria;
	}

	public String getParticella() {
		return this.particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
	}

	public String getSub() {
		return this.sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getUserNameFin() {
		return this.userNameFin;
	}

	public void setUserNameFin(String userNameFin) {
		this.userNameFin = userNameFin;
	}

	public String getUserNameIni() {
		return this.userNameIni;
	}

	public void setUserNameIni(String userNameIni) {
		this.userNameIni = userNameIni;
	}

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public String getTipoPratica() {
		return tipoPratica;
	}

	public void setTipoPratica(String tipoPratica) {
		this.tipoPratica = tipoPratica;
	}
	

}