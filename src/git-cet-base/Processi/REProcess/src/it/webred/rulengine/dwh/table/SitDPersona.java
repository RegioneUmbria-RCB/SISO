package it.webred.rulengine.dwh.table;

import java.sql.Timestamp;
import java.util.LinkedHashSet;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.Tabella;

public class SitDPersona extends TabellaDwh 
{

	private String cognome;
	private String nome;
	private String sesso;
	private String codfisc;
	private String flagCodiceFiscale;
	private String posizAna;
	private String statoCivile;
	private String desPersona;
	private DataDwh	dataInizioResidenza;
	private Relazione idExtStato = new Relazione(SitStato.class,new ChiaveEsterna());
	private Relazione  idExtProvinciaImm = new Relazione(SitProvincia.class, new ChiaveEsterna());
	private Relazione  idExtComuneImm = new Relazione(SitComune.class, new ChiaveEsterna());
	private DataDwh dataImm;
	private Relazione  idExtProvinciaEmi = new Relazione(SitProvincia.class, new ChiaveEsterna());
	private Relazione  idExtComuneEmi = new Relazione(SitComune.class, new ChiaveEsterna());
	private DataDwh dataEmi;
	private Relazione  idExtProvinciaMor = new Relazione(SitProvincia.class, new ChiaveEsterna());
	private Relazione  idExtComuneMor = new Relazione(SitComune.class, new ChiaveEsterna());
	private DataDwh dataMor;	
	private Relazione  idExtProvinciaNascita = new Relazione(SitProvincia.class, new ChiaveEsterna());
	private Relazione  idExtComuneNascita = new Relazione(SitComune.class, new ChiaveEsterna());
	private DataDwh dataNascita;
	private Relazione  idExtDPersonaMadre = new Relazione(SitDPersona.class, new ChiaveEsterna());
	private Relazione  idExtDPersonaPadre = new Relazione(SitDPersona.class, new ChiaveEsterna());
	private String indirizzoEmi;
	private String motivoCancellazioneApr;
	private DataDwh dataCancellazioneApr;
	private String motivoIscrizioneApr;
	private DataDwh dataIscrizioneApr;
	
	
	@Override
	public String getValueForCtrHash()
	{
		
		return cognome + nome + sesso + codfisc + posizAna + statoCivile + desPersona + dataInizioResidenza.getDataFormattata() + 
		 idExtStato + idExtProvinciaImm + idExtComuneImm + dataImm.getDataFormattata() + idExtProvinciaEmi + idExtComuneEmi + indirizzoEmi +
		 dataEmi.getDataFormattata() + idExtProvinciaMor + idExtComuneMor + dataMor.getDataFormattata() + 
		 flagCodiceFiscale +
		 idExtProvinciaNascita + idExtComuneNascita + dataNascita.getDataFormattata() + idExtDPersonaMadre + idExtDPersonaPadre +
		 (motivoCancellazioneApr != null? motivoCancellazioneApr + dataCancellazioneApr.getDataFormattata(): "") +
		 (motivoIscrizioneApr != null? motivoIscrizioneApr + dataIscrizioneApr.getDataFormattata(): "");
		
	}



	public String getCodfisc()
	{
		return codfisc;
	}



	public void setCodfisc(String codfisc)
	{
		this.codfisc = codfisc;
	}



	public String getCognome()
	{
		return cognome;
	}



	public void setCognome(String cognome)
	{
		this.cognome = cognome;
	}



	public DataDwh getDataEmi()
	{
		return dataEmi;
	}



	public void setDataEmi(DataDwh dataEmi)
	{
		this.dataEmi = dataEmi;
	}



	public DataDwh getDataImm()
	{
		return dataImm;
	}



	public void setDataImm(DataDwh dataImm)
	{
		this.dataImm = dataImm;
	}



	public DataDwh getDataInizioResidenza()
	{
		return dataInizioResidenza;
	}



	public void setDataInizioResidenza(DataDwh dataInizioResidenza)
	{
		this.dataInizioResidenza = dataInizioResidenza;
	}



	public DataDwh getDataMor()
	{
		return dataMor;
	}



	public void setDataMor(DataDwh dataMor)
	{
		this.dataMor = dataMor;
	}



	public DataDwh getDataNascita()
	{
		return dataNascita;
	}



	public void setDataNascita(DataDwh dataNascita)
	{
		this.dataNascita = dataNascita;
	}



	public String getDesPersona()
	{
		return desPersona;
	}



	public void setDesPersona(String desPersona)
	{
		this.desPersona = desPersona;
	}



	public String getNome()
	{
		return nome;
	}



	public void setNome(String nome)
	{
		this.nome = nome;
	}



	public String getPosizAna()
	{
		return posizAna;
	}



	public void setPosizAna(String posizAna)
	{
		this.posizAna = posizAna;
	}



	public String getSesso()
	{
		return sesso;
	}



	public void setSesso(String sesso)
	{
		this.sesso = sesso;
	}



	public String getStatoCivile()
	{
		return statoCivile;
	}



	public void setStatoCivile(String statoCivile)
	{
		this.statoCivile = statoCivile;
	}



	public Relazione getIdExtComuneEmi()
	{
		return idExtComuneEmi;
	}

	public void setIdExtComuneEmi(ChiaveEsterna IdExtComuneEmi)
	{
		Relazione r = new Relazione(SitComune.class,IdExtComuneEmi);
		this.idExtComuneEmi = r;	
	}


	public Relazione getIdExtComuneImm()
	{
		return idExtComuneImm;
	}
	public void setIdExtComuneImm(ChiaveEsterna IdExtComuneImm)
	{
		Relazione r = new Relazione(SitComune.class,IdExtComuneImm);
		this.idExtComuneImm = r;	
	}


	public Relazione getIdExtComuneMor()
	{
		return idExtComuneMor;
	}

	public void setIdExtComuneMor(ChiaveEsterna IdExtComuneMor)
	{
		Relazione r = new Relazione(SitComune.class,IdExtComuneMor);
		this.idExtComuneMor = r;	
	}

	public Relazione getIdExtComuneNascita()
	{
		return idExtComuneNascita;
	}

	public void setIdExtComuneNascita(ChiaveEsterna IdExtComuneNascita)
	{
		Relazione r = new Relazione(SitComune.class,IdExtComuneNascita);
		this.idExtComuneNascita = r;	
	}

	public Relazione getIdExtDPersonaMadre()
	{
		return idExtDPersonaMadre;
	}

	public void setIdExtDPersonaMadre(ChiaveEsterna IdExtDPersonaMadre)
	{
		Relazione r = new Relazione(SitDPersona.class,IdExtDPersonaMadre);
		this.idExtDPersonaMadre = r;	
	}

	public Relazione getIdExtDPersonaPadre()
	{
		return idExtDPersonaPadre;
	}

	public void setIdExtDPersonaPadre(ChiaveEsterna IdExtDPersonaPadre)
	{
		Relazione r = new Relazione(SitDPersona.class,IdExtDPersonaPadre);
		this.idExtDPersonaPadre = r;	
	}

	public Relazione getIdExtProvinciaEmi()
	{
		return idExtProvinciaEmi;
	}

	public void setIdExtProvinciaEmi(ChiaveEsterna IdExtProvinciaEmi)
	{
		Relazione r = new Relazione(SitProvincia.class,IdExtProvinciaEmi);
		this.idExtProvinciaEmi = r;	
	}

	public Relazione getIdExtProvinciaImm()
	{
		return idExtProvinciaImm;
	}

	public void setIdExtProvinciaImm(ChiaveEsterna IdExtProvinciaImm)
	{
		Relazione r = new Relazione(SitProvincia.class,IdExtProvinciaImm);
		this.idExtProvinciaImm = r;	
	}

	public Relazione getIdExtProvinciaMor()
	{
		return idExtProvinciaMor;
	}

	public void setIdExtProvinciaMor(ChiaveEsterna IdExtProvinciaMor)
	{
		Relazione r = new Relazione(SitProvincia.class,IdExtProvinciaMor);
		this.idExtProvinciaMor = r;	
	}

	public Relazione getIdExtProvinciaNascita()
	{
		return idExtProvinciaNascita;
	}

	public void setIdExtProvinciaNascita(ChiaveEsterna IdExtProvinciaNascita)
	{
		Relazione r = new Relazione(SitProvincia.class,IdExtProvinciaNascita);
		this.idExtProvinciaNascita = r;	
	}

	public Relazione getIdExtStato()
	{
		return idExtStato;
	}	
	public void setIdExtStato(ChiaveEsterna IdExtStato)
	{
		Relazione r = new Relazione(SitStato.class,IdExtStato);
		this.idExtStato = r;	
	}

	public String getFlagCodiceFiscale()
	{
		return flagCodiceFiscale;
	}

	public void setFlagCodiceFiscale(String flagCodiceFiscale)
	{
		this.flagCodiceFiscale = flagCodiceFiscale;
	}

	public String getIndirizzoEmi() {
		return indirizzoEmi;
	}

	public void setIndirizzoEmi(String indirizzoEmi) {
		this.indirizzoEmi = indirizzoEmi;
	}

	public String getMotivoCancellazioneApr() {
		return motivoCancellazioneApr;
	}

	public void setMotivoCancellazioneApr(String motivoCancellazioneApr) {
		this.motivoCancellazioneApr = motivoCancellazioneApr;
	}

	public DataDwh getDataCancellazioneApr() {
		return dataCancellazioneApr;
	}

	public void setDataCancellazioneApr(DataDwh dataCancellazioneApr) {
		this.dataCancellazioneApr = dataCancellazioneApr;
	}

	public String getMotivoIscrizioneApr() {
		return motivoIscrizioneApr;
	}

	public void setMotivoIscrizioneApr(String motivoIscrizioneApr) {
		this.motivoIscrizioneApr = motivoIscrizioneApr;
	}

	public DataDwh getDataIscrizioneApr() {
		return dataIscrizioneApr;
	}

	public void setDataIscrizioneApr(DataDwh dataIscrizioneApr) {
		this.dataIscrizioneApr = dataIscrizioneApr;
	}	

}
