package it.webred.ss.web.bean.wizard;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsTbStatoCivile;
import it.webred.cs.jsf.manbean.ComuneNazioneNascitaMan;
import it.webred.cs.jsf.manbean.common.CommonDatiAnaBean;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.jsf.bean.ComuneBean;
import it.webred.jsf.bean.SessoBean;
import it.webred.siso.ws.ricerca.dto.PersonaDettaglio;
import it.webred.ss.data.model.SsAnagrafica;

import java.util.Date;

import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;

public class Anagrafica implements CommonDatiAnaBean {
	
	public static final String SEGNALATO_CF_ANONIMO = "ANONIMO";
	
	private Long id;
	
	private String cognome;
	private String nome;
	private Date dataNascita;
	private Date dataDecesso;
	private ComuneNazioneNascitaMan comuneNazioneNascitaMan=new ComuneNazioneNascitaMan();
	private boolean comNascNonValido;
	private String comuneOld; //Utilizzato solo per la lettura di casi precedenti alla modifica, per cui non ha funzionato lo script di bonifica
	private SessoBean datiSesso=new SessoBean();
	private String codiceFiscale;
	private String statoCivile;
	private String cittadinanza;
	private Long cittadinanzaAcq;
	private String cittadinanza2;
	private String idOrigWs;
	private String alias;
	//private MinEtnicaBean minEtnicaMan = new MinEtnicaBean();;
	
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
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}

	
	public void fillModel(SsAnagrafica anagrafica) {
		anagrafica.setId(id);
		anagrafica.setIdOrigWs(idOrigWs);
		anagrafica.setCognome(cognome);
		anagrafica.setNome(nome);
		anagrafica.setCf(codiceFiscale!=null ? codiceFiscale.toUpperCase() : null);
		anagrafica.setData_nascita(dataNascita);
		anagrafica.setDataDecesso(dataDecesso);
		anagrafica.setSesso(datiSesso.getSesso());
		anagrafica.setCittadinanza(cittadinanza);
		anagrafica.setCittadinanzaAcq(cittadinanzaAcq!=null && cittadinanzaAcq>0 ? cittadinanzaAcq : null);
		anagrafica.setCittadinanza2(cittadinanza2);
		anagrafica.setAlias(alias);
		
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
		idOrigWs = anagrafica.getIdOrigWs();
		codiceFiscale = anagrafica.getCf();
		dataNascita = anagrafica.getData_nascita();
		dataDecesso = anagrafica.getDataDecesso();
		datiSesso.setSesso(anagrafica.getSesso());
		comuneOld = anagrafica.getComune_nascita();
		alias = anagrafica.getAlias();
		
		//cittaNascita = anagrafica.getComune_nascita();
	    if(anagrafica.getComuneNascitaCod()!=null){
	    	Boolean attivo = CsUiCompBaseBean.isComuneItaAttivoByIstat(anagrafica.getComuneNascitaCod());
	    	ComuneBean comuneBean = new ComuneBean(anagrafica.getComuneNascitaCod(),anagrafica.getComuneNascitaDes(), anagrafica.getProvNascitaCod(), attivo);
	    	this.comuneNazioneNascitaMan.getComuneNascitaMan().setComune(comuneBean);
	    }else if(anagrafica.getStatoNascitaCod()!=null){
	    	AmTabNazioni amTabNazioni = CsUiCompBaseBean.getNazioneByIstat(anagrafica.getStatoNascitaCod(), anagrafica.getStatoNascitaDes());
			comuneNazioneNascitaMan.getNazioneNascitaMan().setNazione(amTabNazioni);
			comuneNazioneNascitaMan.setNazioneValue();
		}else
			this.comNascNonValido=true;
		
		statoCivile = anagrafica.getStato_civile();
		cittadinanza = anagrafica.getCittadinanza();
		cittadinanza2 = anagrafica.getCittadinanza2();
		cittadinanzaAcq = anagrafica.getCittadinanzaAcq();
		//minEtnicaMan= new MinEtnicaBean(anagrafica.getMinEtnica());
		
		disableAnagrafica = true;
				
	}
	
	public void initFromAnagraficaEsterna(PersonaDettaglio p, CsTbStatoCivile sc) throws NamingException {
		nome = p.getNome();
		cognome = p.getCognome();
		idOrigWs = p.getProvenienzaRicerca()+"@"+ (p.getIdentificativo()!=null ? p.getIdentificativo() : "");
		codiceFiscale = p.getCodfisc();
		dataNascita = p.getDataNascita();
		dataDecesso = p.getDataMorte();
		datiSesso.setSesso(p.getSesso());
		
		//Cittadinanza
		String nazionalita = p.getCittadinanza();
		cittadinanza = (nazionalita!=null && nazionalita.length() > 255) ? nazionalita.substring(0, 252) + "..." : nazionalita;
		
		//nascita
		comuneNazioneNascitaMan.init(p.getComuneNascita(), p.getNazioneNascita());
		if(comuneNazioneNascitaMan.isNazione() && comuneNazioneNascitaMan.getNazioneMan().getNazione()==null)
			comNascNonValido=true;
			
		statoCivile  = sc != null ? sc.getDescrizione() : null;
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
	public boolean isCittadinanzaStraniera(){
		boolean straniero = !DataModelCostanti.CITTADINANZA_ITA.equalsIgnoreCase(cittadinanza); // && !DataModelCostanti.CITTADINANZA_ITA.equalsIgnoreCase(cittadinanza2) ;
		return straniero;
	}
	public String getIdOrigWs() {
		return idOrigWs;
	}
	public void setIdOrigWs(String idOrigWs) {
		this.idOrigWs = idOrigWs;
	}
	
	public String getIdOrigWsTipo(){
		String tipo = null;
		if(!StringUtils.isBlank(idOrigWs)){
			String[] xx = idOrigWs.split("@");
			tipo = xx.length>0 ? xx[0] : null;
		}
		return tipo;
	}
	public String getIdOrigWsId(){
		String ide = null;
		if(!StringUtils.isBlank(idOrigWs)){
			String[] xx = idOrigWs.split("@");
			ide = xx.length>1 ? xx[1] : null;
		}
		return ide;
	}
	
	public boolean isAnonimo() {
		return  SEGNALATO_CF_ANONIMO.equalsIgnoreCase(this.codiceFiscale) ||
				SEGNALATO_CF_ANONIMO.equalsIgnoreCase(this.cognome) ||
				SEGNALATO_CF_ANONIMO.equalsIgnoreCase(this.nome);
	}
	public Date getDataDecesso() {
		return dataDecesso;
	}
	public void setDataDecesso(Date dataDecesso) {
		this.dataDecesso = dataDecesso;
	}
}
