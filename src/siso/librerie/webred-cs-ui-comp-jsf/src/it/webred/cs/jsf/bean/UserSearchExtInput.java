package it.webred.cs.jsf.bean;

import it.webred.cs.data.DataModelCostanti;
import it.webred.jsf.bean.SessoBean;

import java.util.ArrayList;
import java.util.List;

public class UserSearchExtInput {

	private String tipoRicerca;
	
	private String codiceFiscale;
	private String cognome;
	private String nome;
	private String identificativo;
	private Integer annoNascitaDa;
	private Integer annoNascitaA;
	//private Integer meseNascita;
	//private Integer giornoNascita;
	private SessoBean datiSesso = new SessoBean();
	private String alias;
	
	public UserSearchExtInput(){
		this.tipoRicerca= DataModelCostanti.TipoRicercaSoggetto.DATI_ANAGRAFICI;
	}
	
/*	public Date getDataNascita() throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
		String gg = (giornoNascita!=null && giornoNascita>0) ? StringUtils.padding(giornoNascita.toString(), 2, '0', true) : null;
		String mm = (meseNascita!=null && meseNascita>0) ? StringUtils.padding(meseNascita.toString(), 2, '0', true) : null;
		if(annoNascita!=null && gg!=null && mm!=null){
			String sData =  annoNascita+mm+gg;
				return sdf.parse(sData);
		}
		return null;	
	}*/

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
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

	public String getIdentificativo() {
		return identificativo;
	}

	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}

	public SessoBean getDatiSesso() {
		return datiSesso;
	}

	public void setDatiSesso(SessoBean datiSesso) {
		this.datiSesso = datiSesso;
	}
	
	public boolean isRicercaCf(){
		return "CF".equalsIgnoreCase(tipoRicerca);
	}
	
	public boolean isRicercaDatiAnagrafici(){
		return "ANAG".equalsIgnoreCase(tipoRicerca);
	}
	//SISO-948
	public boolean isRicercaAlias(){
		return "ALIAS".equalsIgnoreCase(tipoRicerca);
	}

	public String getTipoRicerca() {
		return tipoRicerca;
	}

	public void setTipoRicerca(String tipoRicerca) {
		this.tipoRicerca = tipoRicerca;
	}

	public List<String> validaCodiceFiscale(){
		List<String> msg = new ArrayList<String>();
		if(this.isRicercaCf()){
			if(codiceFiscale!=null && !codiceFiscale.isEmpty() && codiceFiscale.length()!=16)
				msg.add("Codice fiscale non valido");
		}
		return msg;
	}
	
	public List<String>  validaCognome(){
		List<String> msg = new ArrayList<String>();
		if(getCognome()==null || getCognome().length()<2)
			msg.add("Il campo 'cognome' è un parametro obbligatorio e deve essere di almeno due caratteri.");
		return msg;
	}
	
	public List<String>  validaSesso(){
		List<String> msg = new ArrayList<String>();
		if(getDatiSesso()==null || getDatiSesso().getSesso()==null || getDatiSesso().getSesso().isEmpty())
			msg.add("Il campo 'sesso' è un parametro obbligatorio");
		return msg;
	}
	
	public List<String>  validaAlias(){
		List<String> msg = new ArrayList<String>();
		if(getAlias()==null || getAlias().length()<2)
			msg.add("Il campo 'alias' è un parametro obbligatorio e deve essere di almeno due caratteri.");
		return msg;
	}
	
	public List<String>  validaAnnoNascita(String tipo){
		List<String> msg = new ArrayList<String>();
		if(DataModelCostanti.TipoRicercaSoggetto.SIGESS.equalsIgnoreCase(tipo)){
			if(getAnnoNascitaDa()==null || getAnnoNascitaDa()==0 )
				msg.add("Anno di nascita: impostare l'estremo iniziale dell'intervallo");
			
			if(getAnnoNascitaA()==null || getAnnoNascitaA()==0)
				msg.add("Anno di nascita: impostare l'estremo finale dell'intervallo");
		}else if(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA.equalsIgnoreCase(tipo) ||
				DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_MARCHE.equalsIgnoreCase(tipo)){
			if((getAnnoNascitaA()!=null && getAnnoNascitaA()>0) && (getAnnoNascitaDa()==null || getAnnoNascitaDa()==0))
				msg.add("Anno di nascita: specificare l'intervallo completo (l'estremo iniziale risulta non valorizzato)");
			
			if((getAnnoNascitaDa()!=null && getAnnoNascitaDa()>0) && (getAnnoNascitaA()==null || getAnnoNascitaA()==0))
				msg.add("Anno di nascita: specificare l'intervallo completo (l'estremo finale risulta non valorizzato)");
		}
		
		if(this.getAnnoNascitaA()!=null && this.getAnnoNascitaA()!=0   && 
		   this.getAnnoNascitaDa()!=null && this.getAnnoNascitaDa()!=0 && 
		   this.getAnnoNascitaA().intValue()<this.getAnnoNascitaDa().intValue())
			msg.add("Anno di nascita: definire correttamente l'intervallo della data di nascita");
		return msg;
	}

	public Integer getAnnoNascitaDa() {
		return annoNascitaDa;
	}

	public void setAnnoNascitaDa(Integer annoNascitaDa) {
		this.annoNascitaDa = annoNascitaDa;
	}

	public Integer getAnnoNascitaA() {
		return annoNascitaA;
	}

	public void setAnnoNascitaA(Integer annoNascitaA) {
		this.annoNascitaA = annoNascitaA;
	}
	
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public void clearCampi(){
		codiceFiscale=null;
		cognome=null;
		nome=null;
		identificativo=null;
		annoNascitaDa=null;
		annoNascitaA=null;
		datiSesso = new SessoBean();
		alias=null;
	}

	
	
	
}
