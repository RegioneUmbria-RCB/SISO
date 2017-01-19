package it.webred.ss.web.bean.wizard;

import it.webred.cs.jsf.manbean.ComuneNazioneNascitaMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.luoghi.LuoghiService;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ct.data.access.basic.anagrafe.dto.ComponenteFamigliaDTO;
import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.ejb.utility.ClientUtility;
import it.webred.jsf.bean.ComuneBean;
import it.webred.jsf.bean.SessoBean;
import it.webred.siso.ws.client.anag.client.PersonaFindResult;
import it.webred.ss.data.model.SsAnagrafica;

import java.util.Date;

import javax.naming.NamingException;

public class Anagrafica {
	
	private String cognome;
	private String nome;
	private Date dataNascita;
	private ComuneNazioneNascitaMan comuneNazioneNascitaMan=new ComuneNazioneNascitaMan();
	private boolean comNascNonValido;
	private String comuneOld; //Utilizzato solo per la lettura di casi precedenti alla modifica, per cui non ha funzionato lo script di bonifica
	private SessoBean datiSesso=new SessoBean();
	private String codiceFiscale;
	private String statoCivile;
	private String cittadinanza;
	private Long cittadinanzaAcq;
	private String cittadinanza2;
	private String idExtAnagrafeEnte;
	//private MinEtnicaBean minEtnicaMan = new MinEtnicaBean();;
	
	private Long id;
	
	private boolean disableAnagrafica = false;
	
	
	public boolean isDisableAnagrafica() {
		return disableAnagrafica;
	}
	public void setDisableAnagrafica(boolean disableAnagrafica) {
		this.disableAnagrafica = disableAnagrafica;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getStatoCivile() {
		return statoCivile;
	}
	public void setStatoCivile(String statoCivile) {
		this.statoCivile = statoCivile;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getCittadinanza() {
		return cittadinanza;
	}
	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}
	public String getCittadinanza2() {
		return cittadinanza2;
	}
	public void setCittadinanza2(String cittadinanza2) {
		this.cittadinanza2 = cittadinanza2;
	}
	
	public Long getCittadinanzaAcq() {
		return cittadinanzaAcq;
	}
	public void setCittadinanzaAcq(Long cittadinanzaAcq) {
		this.cittadinanzaAcq = cittadinanzaAcq;
	}
	public void initFromAnagrafe(SitDPersona soggetto, ComponenteFamigliaDTO compDto, String codCsStaCiv){
		cognome = soggetto.getCognome();
		nome = soggetto.getNome();
		codiceFiscale = soggetto.getCodfisc();
		dataNascita = soggetto.getDataNascita();
		
		if("ITALIA".equals(compDto.getDesStatoNas())) {
			if(compDto.getCodComNas()!=null){
				ComuneBean comuneBean = new ComuneBean(compDto.getCodComNas(), compDto.getDesComNas(), compDto.getSiglaProvNas());
				this.comuneNazioneNascitaMan.getComuneNascitaMan().setComune(comuneBean);
			}else this.comNascNonValido=true;
		} else {
			if(compDto.getIstatStatoNas()!=null){
				AmTabNazioni amTabNazioni = CsUiCompBaseBean.getNazioneByIstat(compDto.getIstatStatoNas(), compDto.getDesStatoNas());
				this.comuneNazioneNascitaMan.setValue(this.comuneNazioneNascitaMan.getNazioneValue());
				this.comuneNazioneNascitaMan.getNazioneMan().setNazione(amTabNazioni);
			}else this.comNascNonValido=true;
		}
		
		datiSesso.setSesso(soggetto.getSesso());
		statoCivile = codCsStaCiv; 
		
		disableAnagrafica = true;
		idExtAnagrafeEnte = soggetto.getIdExt();
	}
	
	
	public void fillModel(SsAnagrafica anagrafica) {
		anagrafica.setId(id);
		anagrafica.setCognome(cognome);
		anagrafica.setNome(nome);
		anagrafica.setCf(codiceFiscale);
		anagrafica.setData_nascita(dataNascita);
		anagrafica.setSesso(datiSesso.getSesso());
		anagrafica.setCittadinanza(cittadinanza);
		anagrafica.setCittadinanzaAcq(cittadinanzaAcq!=null && cittadinanzaAcq>0 ? cittadinanzaAcq : null);
		anagrafica.setCittadinanza2(cittadinanza2);
		
		ComuneBean cb = this.comuneNazioneNascitaMan.getComuneNascitaMan().getComune();
		AmTabNazioni nb = this.comuneNazioneNascitaMan.getNazioneNascitaMan().getNazione();
		//anagrafica.setComune_nascita(cittaNascita);
		anagrafica.setComuneNascitaCod(cb!=null ? cb.getCodIstatComune() : null);
		anagrafica.setComuneNascitaDes(cb!=null ? cb.getDenominazione() : null);
		anagrafica.setProvNascitaCod(cb!=null ? cb.getSiglaProv():null);
		anagrafica.setStatoNascitaCod(nb!=null ? nb.getCodIstatNazione() : null);
		anagrafica.setStatoNascitaDes(nb!=null ? nb.getNazione(): null);
		anagrafica.setComune_nascita(comuneOld);//Per non perdere le vecchie informazioni
		
		anagrafica.setStato_civile(statoCivile);
		//anagrafica.setMinEtnica(minEtnicaMan.getTipoMinoranza());
		
	}
	
	public void initFromAnagrafica(SsAnagrafica anagrafica, boolean copia) {
		if(!copia) id = anagrafica.getId();
		duplicaFromAnagrafica(anagrafica);
	}
	
	public void duplicaFromAnagrafica(SsAnagrafica anagrafica) {
		comNascNonValido=false;
		nome = anagrafica.getNome();
		cognome = anagrafica.getCognome();
		codiceFiscale = anagrafica.getCf();
		dataNascita = anagrafica.getData_nascita();
		datiSesso.setSesso(anagrafica.getSesso());
		comuneOld = anagrafica.getLuogoDiNascita();
		
		//cittaNascita = anagrafica.getComune_nascita();
	    if(anagrafica.getComuneNascitaCod()!=null){
	    	ComuneBean comuneBean = new ComuneBean(anagrafica.getComuneNascitaCod(),anagrafica.getComuneNascitaDes(), anagrafica.getProvNascitaCod());
	    	this.comuneNazioneNascitaMan.getComuneNascitaMan().setComune(comuneBean);
	    }else if(anagrafica.getStatoNascitaCod()!=null){
	    	AmTabNazioni amTabNazioni = CsUiCompBaseBean.getNazioneByIstat(anagrafica.getStatoNascitaCod(), anagrafica.getStatoNascitaDes());
			comuneNazioneNascitaMan.getNazioneNascitaMan().setNazione(amTabNazioni);
			comuneNazioneNascitaMan.setValue(comuneNazioneNascitaMan.getNazioneValue());
		}else
			this.comNascNonValido=true;
		
		statoCivile = anagrafica.getStato_civile();
		cittadinanza = anagrafica.getCittadinanza();
		cittadinanza2 = anagrafica.getCittadinanza2();
		cittadinanzaAcq = anagrafica.getCittadinanzaAcq();
		//minEtnicaMan= new MinEtnicaBean(anagrafica.getMinEtnica());
		
		disableAnagrafica = true;
				
	}
	
	public void initFromAnagraficaSanitaria(PersonaFindResult p) throws NamingException {
		nome = p.getNome();
		cognome = p.getCognome();
		codiceFiscale = p.getCodfisc();
		dataNascita = p.getDataNascita();
		datiSesso.setSesso(p.getSesso());
		
		LuoghiService luoghiService = (LuoghiService)  ClientUtility.getEjbInterface("CT_Service", "CT_Config_Manager", "LuoghiServiceBean");

		//Cittadinanza
		String nazionalita = CsUiCompBaseBean.getCittadinanzaByIstat(p.getCodIstatCittadinanza());
		cittadinanza = (nazionalita!=null && nazionalita.length() > 255) ? nazionalita.substring(0, 252) + "..." : nazionalita;

		//nascita
		AmTabComuni comuneNascita = luoghiService.getComuneItaByIstat(p.getIstatComNas());
		if(comuneNascita!=null) {
			p.setDesStatoNas("ITALIA");
			p.setIstatComNas(comuneNascita.getCodIstatComune());
			p.setDesComNas(comuneNascita.getDenominazione());
			p.setSiglaProvNas(comuneNascita.getSiglaProv());
			ComuneBean comuneBean = new ComuneBean(p.getIstatComNas(),p.getDesComNas(), p.getSiglaProvNas());
			comuneNazioneNascitaMan.getComuneNascitaMan().setComune(comuneBean);
			//cittaNascita = comuneBean.getDenominazione();
			
		} else {
			
			AmTabNazioni amTabNazioni = CsUiCompBaseBean.getNazioneByIstat(p.getIstatComNas(), p.getDesStatoNas());
			if(amTabNazioni!=null){
			comuneNazioneNascitaMan.getNazioneNascitaMan().setNazione(amTabNazioni);
			comuneNazioneNascitaMan.setValue(comuneNazioneNascitaMan.getNazioneValue());
			//cittaNascita = amTabNazioni.getNazione();
			}else comNascNonValido=true;
		}
		
		disableAnagrafica = true;
		
	}
	
	public ComuneNazioneNascitaMan getComuneNazioneNascitaMan() {
		return comuneNazioneNascitaMan;
	}
	public void setComuneNazioneNascitaMan(ComuneNazioneNascitaMan comuneNazioneNascitaMan) {
		this.comuneNazioneNascitaMan = comuneNazioneNascitaMan;
	}
	public String getComuneOld() {
		return comuneOld;
	}
	public void setComuneOld(String comuneOld) {
		this.comuneOld = comuneOld;
	}
	public boolean isComNascNonValido() {
		return comNascNonValido;
	}
	public void setComNascNonValido(boolean comNascNonValido) {
		this.comNascNonValido = comNascNonValido;
	}
	public SessoBean getDatiSesso() {
		return datiSesso;
	}
	public void setDatiSesso(SessoBean datiSesso) {
		this.datiSesso = datiSesso;
	}
	public String getIdExtAnagrafeEnte() {
		return idExtAnagrafeEnte;
	}
	public void setIdExtAnagrafeEnte(String idExtAnagrafeEnte) {
		this.idExtAnagrafeEnte = idExtAnagrafeEnte;
	}
	
}
