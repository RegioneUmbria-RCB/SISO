package it.webred.ct.data.model.condono;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the MI_CONDONO database table.
 * 
 */
@Entity
@Table(name="MI_CONDONO")
public class CondonoPratica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long codicecondono;

	private String barraesibente;

	private String barranumcivico;

	private String capesibente;

	private String cfpiesibente;

	private String coddomanda;

	private String codicetipocondono;

	private String codicevia;

	private String comuneesibente;

    @Temporal( TemporalType.DATE)
	private Date datace;

    @Temporal( TemporalType.DATE)
	private Date datainspratica;

    @Temporal( TemporalType.DATE)
	private Date dataprovvedimento;

	private String esibente;

	private String esitocondono;

	private String flagperiodoabuso;

	private String indirizzoesibente;

    @Temporal( TemporalType.DATE)
	private Date momentomodifica;

	private String ncivico;

	private String ncivicoesibente;

	private String notecondono;

	private String pganno;

	private String pgarea;

    @Temporal( TemporalType.DATE)
	private Date pgdata;

	private String pgnumero;

	private String pgsub;

	private String progprovvedimento;

	private String progvia;

	private String provesibente;

	private String relazioneabusi;

	private String tipi;

	private String tipopratica;

	private String ute;

    public CondonoPratica() {
    }

	public long getCodicecondono() {
		return this.codicecondono;
	}

	public void setCodicecondono(long codicecondono) {
		this.codicecondono = codicecondono;
	}

	public String getBarraesibente() {
		return this.barraesibente;
	}

	public void setBarraesibente(String barraesibente) {
		this.barraesibente = barraesibente;
	}

	public String getBarranumcivico() {
		return this.barranumcivico;
	}

	public void setBarranumcivico(String barranumcivico) {
		this.barranumcivico = barranumcivico;
	}

	public String getCapesibente() {
		return this.capesibente;
	}

	public void setCapesibente(String capesibente) {
		this.capesibente = capesibente;
	}

	public String getCfpiesibente() {
		return this.cfpiesibente;
	}

	public void setCfpiesibente(String cfpiesibente) {
		this.cfpiesibente = cfpiesibente;
	}

	public String getCoddomanda() {
		return this.coddomanda;
	}

	public void setCoddomanda(String coddomanda) {
		this.coddomanda = coddomanda;
	}

	public String getCodicetipocondono() {
		return this.codicetipocondono;
	}

	public void setCodicetipocondono(String codicetipocondono) {
		this.codicetipocondono = codicetipocondono;
	}

	public String getCodicevia() {
		return this.codicevia;
	}

	public void setCodicevia(String codicevia) {
		this.codicevia = codicevia;
	}

	public String getComuneesibente() {
		return this.comuneesibente;
	}

	public void setComuneesibente(String comuneesibente) {
		this.comuneesibente = comuneesibente;
	}

	public Date getDatace() {
		return this.datace;
	}

	public void setDatace(Date datace) {
		this.datace = datace;
	}

	public Date getDatainspratica() {
		return this.datainspratica;
	}

	public void setDatainspratica(Date datainspratica) {
		this.datainspratica = datainspratica;
	}

	public Date getDataprovvedimento() {
		return this.dataprovvedimento;
	}

	public void setDataprovvedimento(Date dataprovvedimento) {
		this.dataprovvedimento = dataprovvedimento;
	}

	public String getEsibente() {
		return this.esibente;
	}

	public void setEsibente(String esibente) {
		this.esibente = esibente;
	}

	public String getEsitocondono() {
		return this.esitocondono;
	}

	public void setEsitocondono(String esitocondono) {
		this.esitocondono = esitocondono;
	}

	public String getFlagperiodoabuso() {
		return this.flagperiodoabuso;
	}

	public void setFlagperiodoabuso(String flagperiodoabuso) {
		this.flagperiodoabuso = flagperiodoabuso;
	}

	public String getIndirizzoesibente() {
		return this.indirizzoesibente;
	}

	public void setIndirizzoesibente(String indirizzoesibente) {
		this.indirizzoesibente = indirizzoesibente;
	}

	public Date getMomentomodifica() {
		return this.momentomodifica;
	}

	public void setMomentomodifica(Date momentomodifica) {
		this.momentomodifica = momentomodifica;
	}

	public String getNcivico() {
		return this.ncivico;
	}

	public void setNcivico(String ncivico) {
		this.ncivico = ncivico;
	}

	public String getNcivicoesibente() {
		return this.ncivicoesibente;
	}

	public void setNcivicoesibente(String ncivicoesibente) {
		this.ncivicoesibente = ncivicoesibente;
	}

	public String getNotecondono() {
		return this.notecondono;
	}

	public void setNotecondono(String notecondono) {
		this.notecondono = notecondono;
	}

	public String getPganno() {
		return this.pganno;
	}

	public void setPganno(String pganno) {
		this.pganno = pganno;
	}

	public String getPgarea() {
		return this.pgarea;
	}

	public void setPgarea(String pgarea) {
		this.pgarea = pgarea;
	}

	public Date getPgdata() {
		return this.pgdata;
	}

	public void setPgdata(Date pgdata) {
		this.pgdata = pgdata;
	}

	public String getPgnumero() {
		return this.pgnumero;
	}

	public void setPgnumero(String pgnumero) {
		this.pgnumero = pgnumero;
	}

	public String getPgsub() {
		return this.pgsub;
	}

	public void setPgsub(String pgsub) {
		this.pgsub = pgsub;
	}

	public String getProgprovvedimento() {
		return this.progprovvedimento;
	}

	public void setProgprovvedimento(String progprovvedimento) {
		this.progprovvedimento = progprovvedimento;
	}

	public String getProgvia() {
		return this.progvia;
	}

	public void setProgvia(String progvia) {
		this.progvia = progvia;
	}

	public String getProvesibente() {
		return this.provesibente;
	}

	public void setProvesibente(String provesibente) {
		this.provesibente = provesibente;
	}

	public String getRelazioneabusi() {
		return this.relazioneabusi;
	}

	public void setRelazioneabusi(String relazioneabusi) {
		this.relazioneabusi = relazioneabusi;
	}

	public String getTipi() {
		return this.tipi;
	}

	public void setTipi(String tipi) {
		this.tipi = tipi;
	}

	public String getTipopratica() {
		return this.tipopratica;
	}

	public void setTipopratica(String tipopratica) {
		this.tipopratica = tipopratica;
	}

	public String getUte() {
		return this.ute;
	}

	public void setUte(String ute) {
		this.ute = ute;
	}

}